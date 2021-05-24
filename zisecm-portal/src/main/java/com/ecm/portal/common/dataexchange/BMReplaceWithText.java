package com.ecm.portal.common.dataexchange;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.docx4j.TraversalUtil;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.finders.RangeFinder;
import org.docx4j.jaxb.Context;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.CTMarkupRange;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.P;
import org.docx4j.wml.ProofErr;
import org.docx4j.wml.R;

import com.ecm.core.entity.EcmUser;

public class BMReplaceWithText {
	public static org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();

	/***
	 * 交换书签
	 * 
	 * @param paragraphs
	 * @param data
	 * @param wordMLPackage
	 * @return
	 * @throws Exception
	 */
	public static boolean replaceBookmarkContents(List<Object> paragraphs, Map<String, String> data,
			WordprocessingMLPackage wordMLPackage,int flag) throws Exception {

		boolean result = false;
		RangeFinder rt = new RangeFinder("CTBookmark", "CTMarkupRange");
		new TraversalUtil(paragraphs, rt);

		for (CTBookmark bm : rt.getStarts()) {
			if (bm.getName() == null || bm.getName().startsWith("_"))
				continue;
			//是否需要交换
//			if(!needExchange(bm.getName(),flag))
//			{
//				continue;
//			}
			String value = data.get(bm.getName());
			if (value == null)
				value = " ";

			try {
				setValue(wordMLPackage, bm, value);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return result;
	}
	
	private static boolean needExchange(String bName, int flag)
	{
		if(flag==0)
		{
			if(bName.startsWith("ATTR_")||bName.startsWith("FUN_")
					||bName.startsWith("TBLID_")||bName.startsWith("RELONE_")||bName.startsWith("IMGATT_")
					||bName.startsWith("WFUONE_")||bName.startsWith("TBLSIGN_")
					||bName.startsWith("WF_")||bName.startsWith("WFDONE_")
					||bName.startsWith("WFUALL_")||bName.startsWith("IMGWF_")
					||bName.startsWith("RELONE_")||bName.startsWith("TBLID_")||bName.startsWith("TBLREL_"))
			{
				return true;
			}
			return false;
		}
		if(flag==1)
		{
			if(bName.startsWith("ATTR_")||bName.startsWith("FUN_")
					||bName.startsWith("TBLID_")||bName.startsWith("RELONE_"))
			{
				return true;
			}
		}
		else
		{
			if(bName.startsWith("IMG_")||bName.startsWith("WF_")||bName.startsWith("TBLSIGN_"))
			{
				return true;
			}
		}
		return false;
	}

	/***
	 * 设置书签值
	 * 
	 * @param wordMLPackage
	 * @param bm
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static boolean setValue(WordprocessingMLPackage wordMLPackage, CTBookmark bm, String value)
			throws Exception {
		boolean result = false;
		List<Object> theList = null;
		if (!(bm.getParent() instanceof P)) {
			return result;
		}
		R run = getR(bm);
		if (run != null) {
			run.getContent().clear();
			if (bm.getName().startsWith("IMG")) {
				if (value == null || value.trim().length() == 0) {
					org.docx4j.wml.Text t = factory.createText();
					run.getContent().add(t);
					t.setValue(" ");
				} 
				else if(value.indexOf(";")!=-1) {
					String[] str = value.split(";");
					org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
					org.docx4j.wml.Drawing drawing = null;
					ArrayList<Object> addToMK= new ArrayList<>();
					for (int j = 0; j < str.length; j++) {
						if (!(new File(str[j])).exists()) {
							org.docx4j.wml.Text t = factory.createText();
							addToMK.add(t);
							t.setValue(" ");
						}else {
							drawing = factory.createDrawing();
							byte[] bytes = BMReplaceForTable.getImageBytes(str[j]);
							String filenameHint = null;
							String altText = null;
							int id1 = 0;
							int id2 = 1;
							BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
							Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false,0);
							inline.getExtent().setCx(350*400*6);
		                    inline.getExtent().setCy(160*400*6);
							// Now add the inline in w:p/w:r/w:drawing
							drawing.getAnchorOrInline().add(inline);
							addToMK.add(drawing);
						}
					}
					run.getContent().addAll(addToMK);
					
				}
				else if(!(new File(value)).exists())
				{
//					if(value!=null&&value.equals("--")&&value.length()>2)
//						throw new Exception("Sign Image is not exists:"+value);
					org.docx4j.wml.Text t = factory.createText();
					run.getContent().add(t);
					t.setValue(value);
				}
				else
				{
					byte[] bytes = BMReplaceForTable.getImageBytes(value);
					String filenameHint = null;
					String altText = null;
					int id1 = 0;
					int id2 = 1;
					BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
					Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false,0);
					inline.getExtent().setCx(350*400*6);
                    inline.getExtent().setCy(160*400*6);
					// Now add the inline in w:p/w:r/w:drawing
					org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
					org.docx4j.wml.Drawing drawing = factory.createDrawing();
					drawing.getAnchorOrInline().add(inline);
					run.getContent().add(drawing);
				}
				result = true;
			} else {
				// 创建文本并添加到容器
				String val = value.toString();
				if (val.trim().length() == 0 || val.equals("nulldate")) {
					val = " ";
				}
				if(bm.getName().endsWith("c_drafter")||bm.getName().endsWith("r_creator_name")) {
					if(val.indexOf(" ")>0) {
						val = val.split(" ")[0];
					}
				}
				org.docx4j.wml.Text t = factory.createText();
				run.getContent().add(t);
				t.setValue(val);
				result = true;
			}

		} else {
			theList = ((ContentAccessor) (bm.getParent())).getContent();
			if (theList.size() > 0) {
				run = factory.createR();
				String val = value.toString();
				if (val.length() == 0 || val.equals("nulldate")) {
					val = " ";
				}
				if (bm.getName().startsWith("IMG")) {
					if (value == null || value.trim().length() == 0) {
						org.docx4j.wml.Text t = factory.createText();
						run.getContent().add(t);
						t.setValue(" ");
					} else if(!(new File(value)).exists())
					{
						throw new Exception("Sign Image is not exists:"+value);
					}
					else
					{
						byte[] bytes = BMReplaceForTable.getImageBytes(value);
						String filenameHint = null;
						String altText = null;
						int id1 = 0;
						int id2 = 1;
						BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
						Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false,0);
						inline.getExtent().setCx(350*400*6);
	                    inline.getExtent().setCy(160*400*6);
						// Now add the inline in w:p/w:r/w:drawing
						org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
						org.docx4j.wml.Drawing drawing = factory.createDrawing();
						drawing.getAnchorOrInline().add(inline);
						
						run.getContent().add(drawing);
					}
				}
				else 
				{
					if(bm.getName().endsWith("c_drafter")||bm.getName().endsWith("r_creator_name")) {
						if(val.indexOf(" ")>0) {
							val = val.split(" ")[0];
						}
					}
					org.docx4j.wml.Text t = factory.createText();
					run.getContent().add(t);
					t.setValue(val);
				}
				theList.add(1, run);
			}
		}
		return result;
	}

	/***
	 * 读取书签R
	 * @param bm
	 * @return
	 */
	public static R getR(CTBookmark bm) {
		List<Object> theList = null;
		if (bm.getParent() instanceof P) {
			theList = ((ContentAccessor) (bm.getParent())).getContent();
		} else {
			return null;
		}
		boolean bookmarkStart = false;
//		if(bm.getName().startsWith("ATTR_0_c_review_meeting"))
//		{
//		}
		List<R> rs = new ArrayList<R>();
		for (int i = 0; i < theList.size(); i++) {
			Object ox = theList.get(i);
			if (ox instanceof R) {
				if (bookmarkStart)
				{
					rs.add((R) ox);
				}
			} else if(ox instanceof ProofErr) //跳过
			{
				
			}
			else{
				//其他类型转换出错直接跳过,ProofErr
				try
				{
					Object oxContent = ((javax.xml.bind.JAXBElement) ox).getValue();
					if (oxContent instanceof CTBookmark) {
						//当前书签才算开始位置
						if(((CTBookmark) oxContent).getName().equals(bm.getName()))
						{
							bookmarkStart = true;
						}
						
					}
					else if (oxContent instanceof CTMarkupRange) 
					{
						//书签范围结束
						if(rs.size()>0)
						{
							break;
						}
					}
				}catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
		//如果书签中的R太多只保留一个
		if(rs.size()>=1)
		{
			for(int i=1;i<rs.size();i++)
			{
				theList.remove(rs.get(i));
			}
			return rs.get(0);
		}
		return null;
	}

}
