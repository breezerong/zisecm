package com.ecm.services.indexagent;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ParserDecorator;
import org.apache.tika.parser.epub.EpubParser;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.parser.iwork.IWorkPackageParser;
import org.apache.tika.parser.microsoft.OfficeParser;
import org.apache.tika.parser.microsoft.OldExcelParser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.odf.OpenDocumentParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.rtf.RTFParser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.parser.xml.DcXMLParser;
import org.elasticsearch.SpecialPermission;

final class TikaImpl {
  private static final Set<MediaType> EXCLUDES = new HashSet<>(Arrays.asList(new MediaType[] { MediaType.application("vnd.ms-visio.drawing"), 
          MediaType.application("vnd.ms-visio.drawing.macroenabled.12"), 
          MediaType.application("vnd.ms-visio.stencil"), 
          MediaType.application("vnd.ms-visio.stencil.macroenabled.12"), 
          MediaType.application("vnd.ms-visio.template"), 
          MediaType.application("vnd.ms-visio.template.macroenabled.12"), 
          MediaType.application("vnd.ms-visio.drawing") }));
  
  private static final Parser[] PARSERS = new Parser[] { 
      (Parser)new HtmlParser(), (Parser)new RTFParser(), (Parser)new PDFParser(), (Parser)new TXTParser(), (Parser)new OfficeParser(), (Parser)new OldExcelParser(), 
      
      ParserDecorator.withoutTypes((Parser)new OOXMLParser(), EXCLUDES), (Parser)new OpenDocumentParser(), (Parser)new IWorkPackageParser(), (Parser)new DcXMLParser(), 
      (Parser)new EpubParser() };
  
  private static final AutoDetectParser PARSER_INSTANCE = new AutoDetectParser(PARSERS);
  
  private static final Tika TIKA_INSTANCE = new Tika(PARSER_INSTANCE.getDetector(), (Parser)PARSER_INSTANCE);

  static String parse(InputStream content, Metadata metadata, int limit) throws Exception {
    SpecialPermission.check();
    try {
      return TIKA_INSTANCE.parseToString(content, metadata, limit);
    } catch (Exception e) {
      Throwable cause = e.getCause();
    }
	return ""; 
  }
   
}
