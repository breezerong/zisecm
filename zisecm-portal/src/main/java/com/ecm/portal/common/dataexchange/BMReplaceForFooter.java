package com.ecm.portal.common.dataexchange;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.docx4j.jaxb.Context;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;

import com.ecm.core.entity.EcmDocument;


public class BMReplaceForFooter {
	
	private static org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
	private static List<HeadAttrBean> attrList = new ArrayList<HeadAttrBean>();
	public static void main(String[] args) throws Exception {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put( "c_title", "测试程序");
		map.put( "c_coding", "POROC00001");
		map.put( "c_revision", "A");
		map.put( "c_unit", "D");
		map.put( "c_doc_status", "执行");
		String fileName = "D:\\Temp\\headtest.docx";
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new File(fileName));
		
		//headerAddition(wordMLPackage,map);
		
		
		wordMLPackage.save(new File(fileName.replace(".docx", "_out.docx")));
	}
	
	private static HeadAttrBean getHeadBean(int r,int c)
	{
		for(int i=0;i<attrList.size();i++)
		{
			HeadAttrBean bean = attrList.get(i);
			if(bean.getRowIndex()==r&&bean.getColumIndex()==c)
				return bean;
		}
		return null;
	}
	
	public static boolean headerAddition(WordprocessingMLPackage wordMLPackage,EcmDocument obj,Map<String, String> textMap,List<ArrayList<String>> list) throws Exception 
	{
		boolean result = false;
		List<SectionWrapper> sectionWrappers = wordMLPackage.getDocumentModel().getSections();
		//int headCount=0;
		//遍历所有章节
		for (SectionWrapper sw : sectionWrappers) {
			//headCount ++;
			//读取页眉页脚
			HeaderFooterPolicy hfp = sw.getHeaderFooterPolicy();
			if (hfp.getDefaultHeader()!=null) {
				HeaderPart dhp = hfp.getDefaultHeader();
				List<Object> theList = null;
				theList = dhp.getContent();
				for(Object ox : theList) {
					if(!(ox instanceof P)) {
						Object oo = ((javax.xml.bind.JAXBElement)ox).getValue();
						if(oo instanceof org.docx4j.wml.Tbl) {
							org.docx4j.wml.Tbl table = ((org.docx4j.wml.Tbl)oo);
							if(table.getTblPr().getTblCaption()==null||!table.getTblPr().getTblCaption().getVal().startsWith("ECMHead"))
								break;
							result = true;
							String headName = table.getTblPr().getTblCaption().getVal();
							ConfigurationUtils.getHeadCfg("session");
							HeadBean hBean = ConfigurationUtils.getHeadBean(headName);
							if(hBean==null)
								throw new Exception("Can not file HeadBean:"+headName);
							for(HeadAttrBean aBean:hBean.getHeadAttrs())
							{
								String attrName = aBean.getAttrName().trim();
								String val = textMap.get(attrName);
								if(val==null)
								{
									val = AttrUtils.getDataByBookMarkName(list,obj,attrName,null);
									textMap.put(attrName, val);
								}
								setTableValue(table,aBean.getRowIndex(),aBean.getColumIndex(),val);
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	
	public static boolean setTableValue(Tbl table,int row, int col,String value)
	{
		List<Object> trList = table.getContent();
		int r = 0;
		int c = 0;
		//遍历行 
		for(Object otr : trList) {
			if(otr instanceof org.docx4j.wml.Tr) {
				if(r!=row)
				{
					r++;
					continue;
				}
				c = 0;
				org.docx4j.wml.Tr thisTr = (org.docx4j.wml.Tr)otr;
				List<Object> tcList = thisTr.getContent();
				//遍历列
				for(Object otc : tcList) {
					Object otcContent = ((javax.xml.bind.JAXBElement)otc).getValue();
					if(otcContent instanceof org.docx4j.wml.Tc) {
						if(c!=col)
						{
							c++;
							continue;
						}
						org.docx4j.wml.Tc thisTcContent = (org.docx4j.wml.Tc)otcContent;
						List<Object> actualTC = thisTcContent.getContent();
						for(Object actualC : actualTC) {
							//P p = (P)actualC;
							List<Object> tcP = ((P)actualC).getContent();
							if(tcP.size()==0)
							{
								org.docx4j.wml.R  run = factory.createR();
								org.docx4j.wml.Text  t = factory.createText();
								run.getContent().add(t);		
								t.setValue(value);
								tcP.add(0,run);
								return true;
							}
							else
							{
								
//								for(Object tcr : tcP) {
//									if(tcr instanceof R)
//									{
//										R run = (R)tcr;
//										run.getContent().clear();
//										org.docx4j.wml.Text  t = factory.createText();
//										run.getContent().add(t);		
//										t.setValue(value);
//										return true;
//									}
//								}
								tcP.clear();
								org.docx4j.wml.R  run = factory.createR();
								org.docx4j.wml.Text  t = factory.createText();
								run.getContent().add(t);		
								t.setValue(value);
								tcP.add(0,run);
								return true;
							}
						}
						c++;
					}
				}
				r++;
			}
		}
		return false;
	}
	

	/*
	private static void headerAddition(WordprocessingMLPackage wordMLPackage,Map<String, String> map) throws Exception {

		List<SectionWrapper> sectionWrappers = wordMLPackage.getDocumentModel().getSections();
		int headCount=0;
		//遍历所有章节
		for (SectionWrapper sw : sectionWrappers) {
			headCount ++;
			//读取页眉页脚
			HeaderFooterPolicy hfp = sw.getHeaderFooterPolicy();
			if (hfp.getDefaultHeader()!=null) {
				HeaderPart dhp = hfp.getDefaultHeader();
				List<Object> theList = null;
				theList = dhp.getContent();
				int line=0;
				int column=0;
				for(Object ox : theList) {
					if(!(ox instanceof P)) {
						Object oo = ((javax.xml.bind.JAXBElement)ox).getValue();
						if(oo instanceof org.docx4j.wml.Tbl) {
							org.docx4j.wml.Tbl table = ((org.docx4j.wml.Tbl)oo);
							if(!table.getTblPr().getTblCaption().getVal().equals("ECMHeadTable"))
								break;
							List<Object> trList = table.getContent();
							//遍历行 
							for(Object otr : trList) {
								if(otr instanceof org.docx4j.wml.Tr) {
									org.docx4j.wml.Tr thisTr = (org.docx4j.wml.Tr)otr;
									List<Object> tcList = thisTr.getContent();
									//遍历列
									for(Object otc : tcList) {
										Object otcContent = ((javax.xml.bind.JAXBElement)otc).getValue();
										if(otcContent instanceof org.docx4j.wml.Tc) {
											org.docx4j.wml.Tc thisTcContent = (org.docx4j.wml.Tc)otcContent;
											List<Object> actualTC = thisTcContent.getContent();
											
											for(Object actualC : actualTC) {
												List<Object> tcP = ((P)actualC).getContent();
												if(tcP.size()==0)
												{
													HeadAttrBean bean = getHeadBean(line,column);
													if(bean!=null)
													{
														org.docx4j.wml.R  run = factory.createR();
														org.docx4j.wml.Text  t = factory.createText();
														run.getContent().add(t);		
														t.setValue(bean.getAttrValue());
														tcP.add(0,run);
													}
												}
												else
												{
													HeadAttrBean bean = new HeadAttrBean();
													for(Object tc : tcP) {
														//Object thisTc = ((javax.xml.bind.JAXBElement)tc).getValue();
														if(tc instanceof R)
														{
															R r = (R)tc;
															List<Object> rts = r.getContent();
			
																for(Object t : rts) 
																{
																	Object thisT = null;
																	try
																	{
																		thisT = ((javax.xml.bind.JAXBElement)t).getValue();
																	}
																	catch(Exception ex)
																	{
																		//ex.printStackTrace();
																		thisT = t;
																	}
																	if(thisT instanceof Text)
																	{
																		if(bean!=null&&bean.getBookmarkName()!=null)
																		{
																			bean.setAttrValue(map.get(bean.getBookmarkName()));
																			((Text)thisT).setValue(map.get(bean.getBookmarkName()));
																		}
																		else 
																		{
																			bean = getHeadBean(line,column);
																			if(bean!=null)
																			{
																				((Text)thisT).setValue(bean.getAttrValue());
																			}
																		}
																	}
																}
														}
														else
														{
															Object thisTc = ((javax.xml.bind.JAXBElement)tc).getValue();
															if(thisTc instanceof CTBookmark)
															{
																String bName = ((CTBookmark) thisTc).getName();
																if(bName.startsWith("ATTR_")||bName.startsWith("FUN_")||bName.startsWith("c_"))
																{
																	bean.setBookmarkName(bName);
																	bean.setRowIndex(line);
																	bean.setColumIndex(column);
																	attrList.add(bean);
																}
															}
														}
													
												}
											}
											}
										}
										column++;
									}
								}
								line++;
							}
						}
					}
				}
			}
		}		
	}
	*/
}
