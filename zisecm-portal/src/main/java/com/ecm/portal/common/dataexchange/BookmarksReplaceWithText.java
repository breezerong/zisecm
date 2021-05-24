package com.ecm.portal.common.dataexchange;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.finders.RangeFinder;
import org.docx4j.jaxb.Context;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.structure.HeaderFooterPolicy;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.CTMarkupRange;
import org.docx4j.wml.CTOdso.Table;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.P;


/**
 * Replace text between w:bookmarkStarts and corresponding w:bookmarkEnds
 * with specified data, matching on bookmark's @w:name 
 *
 */
public class BookmarksReplaceWithText {
	
	/*
	 * Requirements:
	 * - bookmarkStart and End must be in the same paragraph
	 * - no attempt is made to check whether the start of some other bookmark is
	 *   in that range.  If it is, it will get deleted!
	 * - no attempt is made to preserve the rPr
	 * - mdp only right now
	 */
	
	//protected static Logger log = LoggerFactory.getLogger(BookmarksReplaceWithText.class);
	
	private static boolean DELETE_BOOKMARK = false;
	
	private static org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
	

	public static void main(String[] args) throws Exception {
		
		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage
				.load(new java.io.File("D:\\temp\\headtest.docx"));

		/**
		 *传入 wordMLPackage对象就可以，
		 *这样 headerAddition方法处理完成之后，保存word文档
		 */
		headerAddition(wordMLPackage);
		
		// save the docx...
		wordMLPackage.save(new java.io.File("D:\\temp\\headtest23.docx"));
	}

	private void replaceBookmarkContents(List<Object> paragraphs,  Map<DataFieldName, String> data) throws Exception {

		RangeFinder rt = new RangeFinder("CTBookmark", "CTMarkupRange");
		new TraversalUtil(paragraphs, rt);
		
		for (CTBookmark bm : rt.getStarts()) {
			
			// do we have data for this one?
			if (bm.getName()==null) continue;
			String value = data.get(new DataFieldName(bm.getName()));
			if (value==null) continue;
				
			try {
				// Can't just remove the object from the parent,
				// since in the parent, it may be wrapped in a JAXBElement
				List<Object> theList = null;
				if (bm.getParent() instanceof P) {
					System.out.println("OK!");
					theList = ((ContentAccessor)(bm.getParent())).getContent();
				} else {
					continue;
				}

				int rangeStart = 1;
				int rangeEnd=-1;
				int i = 0;
				for (Object ox : theList) {
					Object listEntry = XmlUtils.unwrap(ox); 
//					if (listEntry.equals(bm)) {
//						if (DELETE_BOOKMARK) {
//							rangeStart=i;
//						} else {
//							rangeStart=i+1;							
//						}
//					} else if (listEntry instanceof  CTMarkupRange) {
//						if ( ((CTMarkupRange)listEntry).getId().equals(bm.getId())) {
//							if (DELETE_BOOKMARK) {
//								rangeEnd=i;
//							} else {
//								rangeEnd=i-1;							
//							}
//							break;
//						}
//					}
					if(listEntry instanceof org.docx4j.wml.R) {
						rangeStart=i-1;
						rangeEnd=i;
					}
					i++;
				}
				
				if (rangeStart>0 && rangeEnd>rangeStart) {
					
					// Delete the bookmark range
					for (int j =rangeEnd; j>rangeStart; j--) {
						theList.remove(j);
					}
					
					// now add a run
					org.docx4j.wml.R  run = factory.createR();
					org.docx4j.wml.Text  t = factory.createText();
					run.getContent().add(t);		
					t.setValue(value);
					
					theList.add(rangeStart+1, run);
				}
				
			} catch (Exception cce) {

				//log.error(cce.getMessage(), cce);
			}
		}

		
	}

	private static void headerAddition(WordprocessingMLPackage wordMLPackage) throws Exception {

		List<SectionWrapper> sectionWrappers = wordMLPackage.getDocumentModel().getSections();
		
		for (SectionWrapper sw : sectionWrappers) {
			HeaderFooterPolicy hfp = sw.getHeaderFooterPolicy();
			
			System.out.println("\n\nSECTION  \n");
			
			System.out.println("Headers:");
			if (hfp.getFirstHeader()!=null) System.out.println("-first------"+hfp.getFirstHeader()); 
			if (hfp.getDefaultHeader()!=null) {
				
				HeaderPart dhp = hfp.getDefaultHeader();
				
				List<Object> theList = null;
				theList = dhp.getContent();
				
				System.out.println(dhp.getContent().size());
				
				int line=0;
				int column=0;
				
				for(Object ox : theList) {
					if(!(ox instanceof P)) {
						Object oo = ((javax.xml.bind.JAXBElement)ox).getValue();
						if(oo instanceof org.docx4j.wml.Tbl) {
							org.docx4j.wml.Tbl table = ((org.docx4j.wml.Tbl)oo);
							System.out.println("table.getContent():"+table.getContent());
							List<Object> trList = table.getContent();
							for(Object otr : trList) {
								if(otr instanceof org.docx4j.wml.Tr) {
									org.docx4j.wml.Tr thisTr = (org.docx4j.wml.Tr)otr;
									System.out.println("thisTr.getContent():"+thisTr.getContent());
									List<Object> tcList = thisTr.getContent();
									for(Object otc : tcList) {
										Object otcContent = ((javax.xml.bind.JAXBElement)otc).getValue();
										if(otcContent instanceof org.docx4j.wml.Tc) {
											org.docx4j.wml.Tc thisTcContent = (org.docx4j.wml.Tc)otcContent;
											System.out.println("thisTcContent.getContent():"+thisTcContent.getContent());
											List<Object> actualTC = thisTcContent.getContent();
											for(Object actualC : actualTC) {
												System.out.println("actualC:"+actualC);
												List<Object> tcP = ((P)actualC).getContent();
												
												// now add a run
												org.docx4j.wml.R  run = factory.createR();
												org.docx4j.wml.Text  t = factory.createText();
												run.getContent().add(t);		
												t.setValue("测试中文字段");
												
												tcP.add(0,run);
											}
										}
										column++;
									}
								}
								line++;
							}
						}
						System.out.println(((javax.xml.bind.JAXBElement)ox).getValue());
					}
				}
				
			}
			if (hfp.getEvenHeader()!=null) System.out.println("-even------"+hfp.getEvenHeader()); 
			
//			if (hfp.getFirstFooter()!=null) System.out.println("-first"); 
//			if (hfp.getDefaultFooter()!=null) System.out.println("-default"); 
//			if (hfp.getEvenFooter()!=null) System.out.println("-even"); 
			
		}		
	}
}
