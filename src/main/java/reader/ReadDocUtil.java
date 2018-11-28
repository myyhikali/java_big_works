package reader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ReadDocUtil {

	public static String readWord(String fileName){  //commons-collections4-4.2.jar poi-4.0.0.jar   poi-ooxml-4.0.0.jar  poi-scratchpad-4.0.0.jar

        File file = new File(fileName);

        if(file.getName().endsWith(".doc")){
            return readWord_2003(file);
        }
        else if(file.getName().endsWith(".docx")){
            return readWord_2007(fileName);
        }
        else{
            System.out.println("该文件不是word文档，请重新选择！");
            return "";
        }
    }
	public static String readWord_2003(File file){
        String text = "";          
        try{
            InputStream stream = new FileInputStream(file);
            HWPFDocument document = new HWPFDocument(stream);
            WordExtractor word = new WordExtractor(document);
            text = word.getText();
            //去掉word文档中的多个换行
            text = text.replaceAll("(\\r\\n){2,}", "\r\n");
            text = text.replaceAll("(\\n){2,}", "\n");
            System.out.println("读取Word文档成功！");
            stream.close();
            return text;
        }catch(Exception e){
            e.printStackTrace();           
        }
        return "";
    }
    public static String readWord_2007(String fileName){
        String text = "";
        try{
            OPCPackage oPCPackage = POIXMLDocument.openPackage(fileName);
            XWPFDocument xwpf = new XWPFDocument(oPCPackage);
            POIXMLTextExtractor ex = new XWPFWordExtractor(xwpf);
            text = ex.getText();
            //去掉word文档中的多个换行
            text = text.replaceAll("(\\r\\n){2,}", "\r\n");
            text = text.replaceAll("(\\n){2,}", "\n");
            System.out.println("读取Word文档成功！");
            return text;
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
