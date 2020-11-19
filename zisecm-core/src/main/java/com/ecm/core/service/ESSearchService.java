package com.ecm.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.Suggest.Suggestion;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecm.core.entity.AggregationEntity;
import com.ecm.core.entity.EcmFormItem;
import com.ecm.core.entity.Pager;
import com.ecm.core.search.ESClient;
import com.ecm.core.search.SearchClient;
import com.ecm.icore.service.ISearchService;

@Component
public class ESSearchService extends EcmService implements ISearchService {

	private static final Logger logger = LoggerFactory.getLogger(ESSearchService.class);

	@Override
	public Map<String, Object> findByContent(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, boolean onlyProperty) {
		double searchTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		// TODO Auto-generated method stub
		SearchRequest searchRequest = new SearchRequest(ESClient.getInstance().getPackageName());
		searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(pager.getPageIndex());
		sourceBuilder.size(pager.getPageSize());
		// 按相关度排序
		sourceBuilder.explain(true);

		// 设置高亮，标题、名称、内容
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field(makeHighlightContent("title"));
		highlightBuilder.field(makeHighlightContent("name"));
		highlightBuilder.field(makeHighlightContent("filecontent"));

		sourceBuilder.highlighter(highlightBuilder);

		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("fileattr", keyword);
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder().should(matchQueryBuilder);
		if (!onlyProperty) {
			MatchQueryBuilder matchQueryBuilder2 = QueryBuilders.matchQuery("filecontent", keyword);
			boolQueryBuilder.should(matchQueryBuilder2);
		}
		BoolQueryBuilder boolQueryBuilder1 = null;
		boolean hasTypeName = false;
		for (String key : termCondition.keySet()) {
			TermsQueryBuilder termBuilder = QueryBuilders.termsQuery(key.substring(3), termCondition.get(key));
			if (boolQueryBuilder1 == null) {
				boolQueryBuilder1 = new BoolQueryBuilder().must(termBuilder);
			} else {
				boolQueryBuilder1.must(termBuilder);
			}
			if (key.equalsIgnoreCase("by_type_name") && !hasTypeName) {
				hasTypeName = true;
			}
		}
		if (typeNames != null && typeNames.size() > 0) {
			TermsQueryBuilder typeBuilder = QueryBuilders.termsQuery("c_arc_classic", typeNames);
			boolQueryBuilder1.must(typeBuilder);
		}
		if (boolQueryBuilder1 != null) {
			boolQueryBuilder1.must(boolQueryBuilder);
			sourceBuilder.query(boolQueryBuilder1);
		} else {
			sourceBuilder.query(boolQueryBuilder);
		}

		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 类型名称
		TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_type_name").field("type_name");
		sourceBuilder.aggregation(aggregation);
		// 聚类处理
		for (String fieldName : ESClient.getInstance().getFacetFields()) {
			if (!"type_name".equalsIgnoreCase(fieldName)) {
				aggregation = AggregationBuilders.terms("by_" + fieldName).field(fieldName);
				sourceBuilder.aggregation(aggregation);
			}
		}
		searchRequest.source(sourceBuilder);

		RestHighLevelClient client = ESClient.getInstance().getClient();
		try {
			SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

			pager.setTotal((int) response.getHits().getTotalHits().value);

			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			// 加载属性
			Arrays.stream(response.getHits().getHits()).forEach(i -> {
				// logger.info(i.getIndex());
				// logger.info(i.getId());

				Map<String, Object> attrs = i.getSourceAsMap();
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("ID", i.getId());
				for (String fieldName : ESClient.getInstance().getIncludeFields()) {
					// logger.info(fieldName +":" + attrs.get(fieldName).toString());
					values.put(fieldName.toUpperCase(), attrs.get(fieldName));
				}
				Map<String, Object> contentVal = new HashMap<String, Object>();
				contentVal.put("id", "01" + i.getId());
				contentVal.put("parentid", i.getId());
				contentVal.put("filecontent", attrs.get("filecontent"));
				values.put("children", contentVal);
				dataList.add(values);
			});
			List<AggregationEntity> terms = new ArrayList<AggregationEntity>();
			for (Aggregation item : response.getAggregations()) {
				ParsedStringTerms term = (ParsedStringTerms) item;
				// logger.info(term.getName());
				AggregationEntity en = new AggregationEntity();
				en.setFieldName(term.getName());
				for (Bucket val : term.getBuckets()) {
					en.getGroups().put(val.getKeyAsString(), val.getDocCount());
					// logger.info(val.getKey() + ":" + val.getDocCount());
				}
				terms.add(en);
			}
			searchTime = (System.currentTimeMillis() - searchTime) / 1000;
			map.put("searchTime", searchTime);
			map.put("terms", terms);
			map.put("list", dataList);
			// logger.info(response.getHits().getTotalHits().value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<String> getSuggestion(String keyword) {
		RestHighLevelClient restHighLevelClient = ESClient.getInstance().getClient();
		//field的名字,前缀(输入的text),以及大小size
		CompletionSuggestionBuilder   sgb = SuggestBuilders.completionSuggestion("suggestTitle").prefix(keyword)
                .size(10);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggestTitle", sgb);//添加suggest

        
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .suggest(suggestBuilder);
        // 发送es 请求
        searchRequest.source(searchSourceBuilder);
        SearchResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            restHighLevelClient.close();
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        Suggest suggest = getResponse.getSuggest();
		
        Set<String> suggestSet = new HashSet<>();//set
        int maxSuggest = 0;
        if (suggest!=null){
            Suggestion result = suggest.getSuggestion("suggestTitle");//获取suggest,name任意string
            for (Object term : result.getEntries()) {

                if (term instanceof CompletionSuggestion.Entry){
                	CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) term;
                    if (!item.getOptions().isEmpty()){
                        //若item的option不为空,循环遍历
                        for (CompletionSuggestion.Entry.Option option : item.getOptions()) {
                            String tip = option.getText().toString();
                            if (!suggestSet.contains(tip)){
                                suggestSet.add(tip);
                                ++maxSuggest;
                            }
                        }
                    }
                }
                if (maxSuggest>=ESClient.getInstance().getSuggestionCount()){
                    break;
                }
            }
        }

        List<String> suggests = Arrays.asList(suggestSet.toArray(new String[]{}));

        return suggests;

	}

	private String cleanString(String keyword) {
		// TODO Auto-generated method stub
		return keyword.replace("'", "").replace(" ", "");
	}

	/**
	 * 封装高亮查询字段
	 */
	private HighlightBuilder.Field makeHighlightContent(String fieldName) {
		HighlightBuilder.Field highlightContent = new HighlightBuilder.Field(fieldName);
		highlightContent.preTags("<span class='searchhighlight'>");
		highlightContent.postTags("</span>");
		highlightContent.highlighterType("unified");
		return highlightContent;
	}

	@Override
	public Map<String, Object> findByQuery(String token, Pager pager, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findByContent(String token, Pager pager, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> findByContent(String token, Pager pager, String keyword, boolean onlyProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 处理方式每次翻页都从0开始，
	 */
	@Override
	public Map<String, Object> findByContentScroll(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, boolean onlyProperty) {
		// TODO Auto-generated method stub

		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
		double searchTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		RestHighLevelClient client = ESClient.getInstance().getClient();
		SearchResponse response = null;
		try {

			SearchRequest searchRequest = new SearchRequest(ESClient.getInstance().getPackageName());

			searchRequest.scroll(scroll);

			searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);

			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			sourceBuilder.from(0);
			sourceBuilder.size(pager.getPageSize());
			// 设置高亮，标题、名称、内容
			HighlightBuilder highlightBuilder = new HighlightBuilder();
			// highlightBuilder.field(makeHighlightContent("title"));
			highlightBuilder.field(makeHighlightContent("fileattr"));
			highlightBuilder.field(makeHighlightContent("filecontent"));

			sourceBuilder.highlighter(highlightBuilder);

			
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			if(onlyProperty) {
				keyword = "*"+keyword+"*";
				for(String fld: ESClient.getInstance().getSearchFields()) {
					WildcardQueryBuilder q = QueryBuilders.wildcardQuery(fld, keyword);
					boolQueryBuilder.should(q);
				}
//				WildcardQueryBuilder q = QueryBuilders.wildcardQuery("fileattr", keyword);
//				boolQueryBuilder.should(q);
			}else {
				MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("fileattr", keyword);
				boolQueryBuilder.should(matchQueryBuilder);
			}
			if (!onlyProperty) {
				MatchQueryBuilder matchQueryBuilder2 = QueryBuilders.matchQuery("filecontent", keyword);
				boolQueryBuilder.should(matchQueryBuilder2);
			}
			BoolQueryBuilder boolQueryBuilder1 = null;
			boolean hasTypeName = false;
			for (String key : termCondition.keySet()) {
				TermsQueryBuilder termBuilder = QueryBuilders.termsQuery(key.substring(3), termCondition.get(key));
				if (boolQueryBuilder1 == null) {
					boolQueryBuilder1 = new BoolQueryBuilder().must(termBuilder);
				} else {
					boolQueryBuilder1.must(termBuilder);
				}
				if (key.equalsIgnoreCase("by_type_name") && !hasTypeName) {
					hasTypeName = true;
				}
			}
			//状态必须为“利用”
			TermsQueryBuilder statusBuilder = QueryBuilders.termsQuery("status", SearchClient.getInstance().getReleaseStatus());
			
			if (boolQueryBuilder1 == null) {
				boolQueryBuilder1 = new BoolQueryBuilder().must(statusBuilder);
			} else {
				boolQueryBuilder1.must(statusBuilder);
			}
			
			//状态必须为“利用”
			//TermsQueryBuilder noboxBuilder = QueryBuilders.termsQuery("type_name", "卷盒");
			
//			if (boolQueryBuilder1 == null) {
//				boolQueryBuilder1 = new BoolQueryBuilder().mustNot(noboxBuilder);
//			} else {
//				boolQueryBuilder1.mustNot(noboxBuilder);
//			}
			
			if (typeNames != null && typeNames.size() > 0) {
				TermsQueryBuilder typeBuilder = QueryBuilders.termsQuery("c_arc_classic", typeNames);
				if (boolQueryBuilder1 == null) {
					boolQueryBuilder1 = new BoolQueryBuilder().must(typeBuilder);
				} else {
					boolQueryBuilder1.must(typeBuilder);
				}
			}
			if (boolQueryBuilder1 != null) {
				boolQueryBuilder1.must(boolQueryBuilder);
				sourceBuilder.query(boolQueryBuilder1);
			} else {
				sourceBuilder.query(boolQueryBuilder);
			}

			sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
			// 类型名称
			TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_type_name").field("type_name");
			sourceBuilder.aggregation(aggregation);
			// 聚类处理
			for (String fieldName : ESClient.getInstance().getFacetFields()) {
				if (!"type_name".equalsIgnoreCase(fieldName)) {
					aggregation = AggregationBuilders.terms("by_" + fieldName).field(fieldName);
					sourceBuilder.aggregation(aggregation);
				}
			}
			searchRequest.source(sourceBuilder);
			response = client.search(searchRequest, RequestOptions.DEFAULT);
			pager.setTotal((int) response.getHits().getTotalHits().value);
			pager.setScrollId(response.getScrollId());
			// 聚类信息第一次获取
			List<AggregationEntity> terms = new ArrayList<AggregationEntity>();
			for (Aggregation item : response.getAggregations()) {
				ParsedStringTerms term = (ParsedStringTerms) item;
				// logger.info(term.getName());
				AggregationEntity en = new AggregationEntity();
				en.setFieldName(term.getName());
				for (Bucket val : term.getBuckets()) {
					en.getGroups().put(val.getKeyAsString(), val.getDocCount());
					// logger.info(val.getKey() + ":" + val.getDocCount());
				}
				terms.add(en);
			}
			// 后台翻页处理
			for (int i = 0; i < pager.getPageIndex()
					&& (response.getHits().getTotalHits().value / pager.getPageSize() >= pager.getPageIndex()); i++) {
				SearchScrollRequest scrollRequest = new SearchScrollRequest(pager.getScrollId());
				scrollRequest.scroll(scroll);
				try {
					response = client.scroll(scrollRequest, RequestOptions.DEFAULT);
				} catch (IOException e) {
					e.printStackTrace();
				}
				pager.setScrollId(response.getScrollId());
			}

			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			// 加载属性
			Arrays.stream(response.getHits().getHits()).forEach(i -> {
				// logger.info(i.getIndex());
				// logger.info(i.getId());

				Map<String, Object> attrs = i.getSourceAsMap();
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("ID", i.getId());
				for (String fieldName : ESClient.getInstance().getIncludeFields()) {
					// logger.info(fieldName +":" + attrs.get(fieldName).toString());
					values.put(fieldName.toUpperCase(), attrs.get(fieldName));
				}

//	            logger.info("Map方式打印高亮内容");
//	            logger.info(i.getHighlightFields());
//
//	            logger.info("遍历高亮集合，打印高亮片段:");
				String highlightText = "";
				for (String hkey : i.getHighlightFields().keySet()) {
					Text[] text = i.getHighlightFields().get(hkey).getFragments();
					for (Text str : text) {
						if (highlightText.length() < ESClient.getInstance().getHeightTextLen()) {
							highlightText += " " + str.string().replace("\n", " ");
						} else {
							highlightText += "...";
							break;
						}
						// logger.info(str.string());
					}
				}

//	            Map<String, Object> contentVal = new HashMap<String, Object>();
//				contentVal.put("ID", "01" + i.getId());
//				contentVal.put("parentId", i.getId());
//				contentVal.put("highlightText", heightText);
				values.put("highlightText", highlightText);
				dataList.add(values);
			});

			searchTime = (System.currentTimeMillis() - searchTime) / 1000;
			map.put("searchTime", searchTime);
			map.put("aggregations", terms);
			map.put("list", dataList);
			// logger.info(response.getHits().getTotalHits().value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 根据ScrollID 翻页
	 * 
	 * @param token
	 * @param pager
	 * @param typeNames
	 * @param keyword
	 * @param termCondition
	 * @param onlyProperty
	 * @return
	 */
	public Map<String, Object> findByContentScrollEx(String token, Pager pager, List<String> typeNames, String keyword,
			Map<String, List<String>> termCondition, boolean onlyProperty) {
		// TODO Auto-generated method stub
		if (pager.getScrollId() == null) {
			return findByContentScroll(token, pager, typeNames, keyword, termCondition, onlyProperty);
		}
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(5L));
		double searchTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		RestHighLevelClient client = ESClient.getInstance().getClient();
		SearchResponse response = null;
		try {
			// 第一次查询设置ScrollID
			if (pager.getScrollId().length() < 5) {
				SearchRequest searchRequest = new SearchRequest(ESClient.getInstance().getPackageName());

				searchRequest.scroll(scroll);

				searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);

				SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
				sourceBuilder.from(pager.getPageIndex());
				sourceBuilder.size(pager.getPageSize());
				// 设置高亮，标题、名称、内容
				HighlightBuilder highlightBuilder = new HighlightBuilder();
				highlightBuilder.field(makeHighlightContent("title"));
				highlightBuilder.field(makeHighlightContent("name"));
				highlightBuilder.field(makeHighlightContent("filecontent"));

				sourceBuilder.highlighter(highlightBuilder);

				MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("fileattr", keyword);
				BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder().should(matchQueryBuilder);
				if (!onlyProperty) {
					MatchQueryBuilder matchQueryBuilder2 = QueryBuilders.matchQuery("filecontent", keyword);
					boolQueryBuilder.should(matchQueryBuilder2);
				}
				BoolQueryBuilder boolQueryBuilder1 = null;
				boolean hasTypeName = false;
				for (String key : termCondition.keySet()) {
					TermsQueryBuilder termBuilder = QueryBuilders.termsQuery(key.substring(3), termCondition.get(key));
					if (boolQueryBuilder1 == null) {
						boolQueryBuilder1 = new BoolQueryBuilder().must(termBuilder);
					} else {
						boolQueryBuilder1.must(termBuilder);
					}
					if (key.equalsIgnoreCase("by_type_name") && !hasTypeName) {
						hasTypeName = true;
					}
				}
				if (typeNames != null && typeNames.size() > 0) {
					TermsQueryBuilder typeBuilder = QueryBuilders.termsQuery("c_arc_classic", typeNames);
					boolQueryBuilder1.must(typeBuilder);
				}
				if (boolQueryBuilder1 != null) {
					boolQueryBuilder1.must(boolQueryBuilder);
					sourceBuilder.query(boolQueryBuilder1);
				} else {
					sourceBuilder.query(boolQueryBuilder);
				}

				//状态必须为“利用”
				TermsQueryBuilder statusBuilder = QueryBuilders.termsQuery("status", SearchClient.getInstance().getReleaseStatus());
				if (boolQueryBuilder1 == null) {
					boolQueryBuilder1 = new BoolQueryBuilder().must(statusBuilder);
				} else {
					boolQueryBuilder1.must(statusBuilder);
				}
				
				sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
				// 类型名称
				TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_type_name").field("type_name");
				sourceBuilder.aggregation(aggregation);
				// 聚类处理
				for (String fieldName : ESClient.getInstance().getFacetFields()) {
					if (!"type_name".equalsIgnoreCase(fieldName)) {
						aggregation = AggregationBuilders.terms("by_" + fieldName).field(fieldName);
						sourceBuilder.aggregation(aggregation);
					}
				}
				searchRequest.source(sourceBuilder);
				response = client.search(searchRequest, RequestOptions.DEFAULT);
				pager.setTotal((int) response.getHits().getTotalHits().value);
			} else {// 根据Scroll ID查询
				SearchScrollRequest scrollRequest = new SearchScrollRequest(pager.getScrollId());
				scrollRequest.scroll(scroll);
				try {
					response = client.scroll(scrollRequest, RequestOptions.DEFAULT);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			pager.setScrollId(response.getScrollId());

			List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
			// 加载属性
			Arrays.stream(response.getHits().getHits()).forEach(i -> {
				// logger.info(i.getIndex());
				// logger.info(i.getId());

				Map<String, Object> attrs = i.getSourceAsMap();
				Map<String, Object> values = new HashMap<String, Object>();
				values.put("ID", i.getId());
				for (String fieldName : ESClient.getInstance().getIncludeFields()) {
					// logger.info(fieldName +":" + attrs.get(fieldName).toString());
					values.put(fieldName.toUpperCase(), attrs.get(fieldName));
				}
				Map<String, Object> contentVal = new HashMap<String, Object>();
				contentVal.put("id", "01" + i.getId());
				contentVal.put("parentid", i.getId());
				contentVal.put("filecontent", attrs.get("filecontent"));
				values.put("children", contentVal);
				dataList.add(values);
			});
			List<AggregationEntity> terms = new ArrayList<AggregationEntity>();
			for (Aggregation item : response.getAggregations()) {
				ParsedStringTerms term = (ParsedStringTerms) item;
				// logger.info(term.getName());
				AggregationEntity en = new AggregationEntity();
				en.setFieldName(term.getName());
				for (Bucket val : term.getBuckets()) {
					en.getGroups().put(val.getKeyAsString(), val.getDocCount());
					// logger.info(val.getKey() + ":" + val.getDocCount());
				}
				terms.add(en);
			}
			searchTime = (System.currentTimeMillis() - searchTime) / 1000;
			map.put("searchTime", searchTime);
			map.put("aggregations", terms);
			map.put("list", dataList);
			// logger.info(response.getHits().getTotalHits().value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Object findByCard(String token, String gridName, Pager pager, String typeName, List<EcmFormItem> conditions) {
		// TODO Auto-generated method stub
		return null;
	}

}
