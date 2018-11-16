package rdf;


import javafx.util.Pair;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import tools.Csv;
import tools.Format;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RDFBasic {
    private static String preURI = "http://drugCrime/";
    private static String caseRelation = "http://drugCrime/caseRelation/";
    private static String personRelation = "http://drugCrime/personRelation/";
    private static String[] attribute = {
            "drugCrimeCase",
            "year",
            "courtRegion",
            "theNumberOfPeopleInFirstFilling",
            "theSmallestOfAllPeople",
            "crime",
            "torture",
            "sentence",
            "propertyPenalty",
            "fine",
            "drug",
            "drugUnitPrice"
    };


    public static void main(String[] args) throws IOException {
        Pair<String[], ArrayList<String[]>> data = Csv.read("tmp/csv/舟山.csv", true, "UTF-8");
        String[] header = data.getKey();
        ArrayList<String[]> record = data.getValue();

        Model model = ModelFactory.createDefaultModel();

        Property drugCrimeCase = model.createProperty(caseRelation + attribute[0]);
        Property year = model.createProperty(caseRelation + attribute[1]);
        Property courtRegion = model.createProperty(caseRelation + attribute[2]);
        Property theNumberOfPeopleInFirstFilling = model.createProperty(caseRelation + attribute[3]);
        Property theSmallestOfAllPeople = model.createProperty(caseRelation + attribute[4]);
        Property crime = model.createProperty(caseRelation + attribute[5]);
        Property torture = model.createProperty(caseRelation + attribute[6]);
        Property sentence = model.createProperty(caseRelation + attribute[7]);
        Property propertyPenalty = model.createProperty(caseRelation + attribute[8]);
        Property fine = model.createProperty(caseRelation + attribute[9]);
        Property drug = model.createProperty(caseRelation + attribute[10]);
        Property drugUnitPrice = model.createProperty(caseRelation + attribute[11]);

        Map<String, String> nsPrefixes = new HashMap<String, String>();
        nsPrefixes.put("案件属性", caseRelation);
        nsPrefixes.put("毒品犯罪", preURI);



        for (int i = 0; i < record.size(); ++i) {
            Resource crimeCase = model.createResource(preURI + record.get(i)[4]);


            crimeCase.addProperty(drugCrimeCase, record.get(i)[0]); // 案号
            crimeCase.addProperty(year, Format.getCaseYear(record.get(i)[0]));  // 案件年份
            crimeCase.addProperty(courtRegion, record.get(i)[1]);   // 法庭
            crimeCase.addProperty(theNumberOfPeopleInFirstFilling, record.get(i)[2]);    // 一案人数
            crimeCase.addProperty(theSmallestOfAllPeople, record.get(i)[3]);    // 年龄最小的人的生日
            crimeCase.addProperty(crime, record.get(i)[5]);    // 罪名
            crimeCase.addProperty(torture, record.get(i)[6]);   // 刑法种类
            crimeCase.addProperty(sentence, record.get(i)[7]);   // 刑期
            crimeCase.addProperty(propertyPenalty, record.get(i)[8]);   // 财产性种类
            crimeCase.addProperty(fine, record.get(i)[9]);   // 财产性金额
            crimeCase.addProperty(drug, record.get(i)[10]);   //毒品种类和数量和单位
            crimeCase.addProperty(drugUnitPrice, record.get(i)[11]);   //毒品单价


            model.setNsPrefixes(nsPrefixes);
            Writer text = new FileWriter("./tmp/rdf/text.rdf");
            model.write(text, "Turtle");

        }

    }
}
