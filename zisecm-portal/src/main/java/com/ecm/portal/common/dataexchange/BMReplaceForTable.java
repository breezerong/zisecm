package com.ecm.portal.common.dataexchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;

public class BMReplaceForTable {

	private static org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();

	public static boolean replaceBookmarkContents(List<Object> paragraphs,
			Map<String, List<ArrayList<String>>> data, WordprocessingMLPackage wordMLPackage, int flag)
					throws Exception {
		boolean result = false;
		RangeFinder rt = new RangeFinder("CTBookmark", "CTMarkupRange");
		new TraversalUtil(paragraphs, rt);

		for (CTBookmark bm : rt.getStarts()) {
			// do we have data for this one?
			if (bm.getName() == null)
				continue;
			List<ArrayList<String>> value = data.get(bm.getName());
			if (value == null)
				continue;

			try {
				Tbl tbl = getTable(bm);
				Tr tr1 = clearRow(tbl, 1);
				for (ArrayList<String> strs : value) {
					Tr tr = null;
					// 保留第一行数据样式
					if (tr1 != null) {
						tr = tr1;
						tr1 = null;
					} else {
						tr = factory.createTr();
					}
					// 清除单元格
					tr.getContent().clear();
					// 填充单元格
					for (String str : strs) {

						Tc tc = factory.createTc();

						// 添加文本
						P paragraph = factory.createP();
						R run = factory.createR();
						if (tr.getContent().size() != 2) {
							if (flag != 2) {
								org.docx4j.wml.Text t = factory.createText();
								t.setValue(str);
								run.getContent().add(t);
								
							}
						} else {
							// 添加图片到姓名同一单元格
							if (flag != 1) {
								String filenameHint = null;
								String altText = null;
								int id1 = 0;
								int id2 = 1;
								byte[] bytes = getImageBytes(str);// 获取图片的存放路径
								BinaryPartAbstractImage imagePart = BinaryPartAbstractImage
										.createImagePart(wordMLPackage, bytes);
								Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false);
								org.docx4j.wml.Drawing drawing = factory.createDrawing();
								drawing.getAnchorOrInline().add(inline);
								run.getContent().add(drawing);
								
							}
						}
						paragraph.getContent().add(run);
						tc.getContent().add(paragraph);

						// 单元格添加行
						tr.getContent().add(tc);
					}
					tbl.getContent().add(tr);
					result = true;
				}

			} catch (ClassCastException cce) {
				cce.printStackTrace();
			}
		}
		 return result;
	}

	public static byte[] getImageBytes(String filePath) throws Exception {
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			throw new Exception("File too large!");
		}
		byte[] bytes = new byte[(int) length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		if (offset < bytes.length) {
			throw new Exception("Could not completely read file");
		}
		is.close();
		return bytes;
	}

	/***
	 * 清除表格行，保留前二行（为了保留样式），表格第一行为头，第二行开始为数据
	 * 
	 * @param tbl
	 * @param dataIndex
	 *            数据开始行
	 */
	public static Tr clearRow(Tbl tbl, int dataIndex) {
		List<Object> list = tbl.getContent();
		int rowCount = list.size();
		Tr returnTr = null;
		for (int i = rowCount - 1; i >= dataIndex; i--) {
			// 第一行数据都要清除
			if (i == dataIndex) {
				if (list.get(1) instanceof Tr) {
					returnTr = (Tr) list.get(dataIndex);
				}

			}
			tbl.getContent().remove(i);
		}
		return returnTr;
	}

	public static Tbl getTable(CTBookmark bm) {
		if (bm.getParent() instanceof P) {
			P p = ((P) (bm.getParent()));
			if (p.getParent() instanceof Tc) {
				Tc tc = (Tc) p.getParent();
				if (tc.getParent() instanceof Tr) {
					Tr tr = (Tr) tc.getParent();
					if (tr.getParent() instanceof Tbl) {
						return (Tbl) tr.getParent();
					}
				}
			}
		}
		return null;
	}
}
