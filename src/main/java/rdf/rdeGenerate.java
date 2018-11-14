package rdf;


import javafx.util.Pair;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;
import tools.Csv;

import java.io.IOException;
import java.util.ArrayList;

public class rdeGenerate {
    static String preURI = "http://drugcrime/";



    public static void main(String[] args) throws IOException {
        Pair<String[], ArrayList<String[]>> data = Csv.read("tmp/csv/舟山.csv", false, "UFT-8");
        String[] header = data.getKey();
        ArrayList<String[]> record = data.getValue();

        Model model = ModelFactory.createDefaultModel();

        for (int i = 0; i < record.size(); ++i) {
            Resource crimeCase = model.createResource(preURI + record.get(i)[0]);

            crimeCase.addProperty(VCARD.Region, record.get(i)[1]); // 法庭区域
            crimeCase.addProperty(VCARD.N, record.get(i)[2]);   // 一案人数
            crimeCase.addProperty(VCARD.BDAY, record.get(i)[3]);    // 年龄最小人员的出生日期
            crimeCase.addProperty(VCARD.NAME, record.get(i)[4]);    // 第一被告姓名
            crimeCase.addProperty(VCARD.Given, record.get(i)[5]);    // 罪名
            crimeCase.addProperty(VCARD.CLASS, record.get(i)[6]);   // 刑法种类
//            johnSmith.addProperty(VCARD., record.get(i)[7]);   // 刑期
//            johnSmith.addProperty(VCARD.AGENT, record.get(i)[8]);   // 财产性种类
//            johnSmith.addProperty(VCARD.AGENT, record.get(i)[9]);   // 财产性金额
//            johnSmith.addProperty(VCARD.AGENT, record.get(i)[10]);   //毒品种类和数量和单位
//            johnSmith.addProperty(VCARD.AGENT, record.get(i)[111);   //毒品单价
            model.write(System.out);
        }

    }
}
