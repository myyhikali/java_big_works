package parser;

import javafx.util.Pair;
import match.MatchCrime;
import model.BeanCase;
import model.BeanCrime;
import model.BeanPrisoner;
import reader.ReadFilePath;
import tools.Csv;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tools.Csv.writer;

public class ParseToCsv {
    //writer(String filepath, Pair<String[], ArrayList<String[]>> data, boolean hasHeader, String charSet)

    public static String[] titles = {"案号", "法院名称", "地区", "时间", "一案人数", "年龄最小人员出生日期", "第一被告姓名", "性别", "身份证", "名族", "文化程度", "职业", "户籍", "罪名", "刑罚种类", "刑期", "财产刑种类", "财产刑金额", "毒品种类和数量或单位", "毒品单价"};
    public static Map<String, BeanPrisoner> PrisonerMap = new HashMap<>();

    public static void parseToCsv(String filePath, String saveName) {
        ArrayList<String[]> crimeList = new ArrayList<>();
        ArrayList<String> filePaths = new ReadFilePath().getFiles(filePath);
        ArrayList<BeanCrime> crimes = new ArrayList<>();
        Map<String, BeanPrisoner> prisonerMap = new HashMap<>();

        for (String fileName : filePaths) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            BeanCrime tempCrime = new MatchCrime().Match(fileName);

            for(String name:tempCrime.getPrisoners().keySet())
                if(!prisonerMap.containsKey(name))
                    prisonerMap.put(name,tempCrime.getPrisoners().get(name));    //合并 map

            crimes.add(tempCrime);
            crimeList.add(new String[]{tempCrime.getSerial(), tempCrime.getProcuratorate(), tempCrime.getArea(), dateFormat.format(tempCrime.getDate()), Integer.toString(tempCrime.getPrisoners().size()),
                    dateFormat.format(tempCrime.getMinimumAge()),
                    tempCrime.getFirstPrisoner().getName(), tempCrime.getFirstPrisoner().getSex(), tempCrime.getFirstPrisoner().getIdCard(), tempCrime.getFirstPrisoner().getNation(),
                    tempCrime.getFirstPrisoner().getLevel(), tempCrime.getFirstPrisoner().getWork(), tempCrime.getFirstPrisoner().getPlace(), tempCrime.getFirstPrisoner().showCrime(), tempCrime.getFirstPrisoner().getPrisonType(), tempCrime.getFirstPrisoner().getPrisonTime(),
                    tempCrime.getFirstPrisoner().getPenalty(), Float.toString(tempCrime.getFirstPrisoner().getPenaltySum()), tempCrime.showDrugs(), tempCrime.showAverageDrugs()});
        }
        ParseToJson.ParseToJson(prisonerMap,ParseToRelationList.parseToRelation(filePath));
        try {
            writer("E://学习//java项目//Test//" + saveName + ".csv", new Pair<>(titles, crimeList), true, "GBK");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        ParseToCsv.parseToCsv("E://学习//java项目//判决书//2018年1-6月份毒品刑事案件一审//舟山", "舟山");
    }

}
