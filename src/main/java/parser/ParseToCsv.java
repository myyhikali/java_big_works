package parser;

import hibernate_test.BaseException;
import javafx.util.Pair;
import match.MatchCrime;
import model.BeanCase;
import model.BeanCrime;
import model.BeanDrug;
import model.BeanPrisoner;
import org.hibernate.Session;
import reader.ReadFilePath;
import tools.Csv;
import hibernate_test.*;

import java.awt.*;
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

    /**
     *
     * @param filePath  读取doc所在的文件夹位置
     * @param saveName  存储的json和csv文件名
     */
    public static void parseToCsv(String filePath, String saveName) {
        ArrayList<String[]> crimeList = new ArrayList<>();
        ArrayList<String> filePaths = new ReadFilePath().getFiles(filePath);
        ArrayList<BeanCrime> crimes = new ArrayList<>();
        Map<String, BeanPrisoner> prisonerMap = new HashMap<>();

        CrimeManager crimeManager = new CrimeManager();
        PrisonerManager prisonerManager = new PrisonerManager();
        DrugManager drugManager = new DrugManager();

        for (String fileName : filePaths) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            SimpleDateFormat bakDateFormat = new SimpleDateFormat("yyyy年M月d日");

            BeanCrime tempCrime = new MatchCrime().Match(fileName);
            String firstPrisonerName = tempCrime.getFirstPrisoner().getName();
            System.out.println("----First Prisoner"+firstPrisonerName);

            if(tempCrime==null||tempCrime.getFirstPrisoner()==null)
                continue;

            /*
             * @无数据库时映射
             */
            List<BeanPrisoner> prisoners = new ArrayList<>();
//            for(String name:tempCrime.getPrisoners().keySet()){
//                BeanPrisoner prisoner = tempCrime.getPrisoners().get(name);
//                prisoner.setBeanCrime(tempCrime);
//                prisoners.add(prisoner);
//            }
            /*
             * @无数据库时映射
             */
            try {
                Session session = HibernateUtil.getSession();
                session.beginTransaction();
                session.save(tempCrime);
                session.getTransaction().commit();
                session.close();

                int tempCrimeid=CrimeManager.MaxCrime().getCrimeid();
                tempCrime.setCrimeid(tempCrimeid);

                prisoners = new ArrayList<>();
                for(String name:tempCrime.getPrisoners().keySet()){
                    BeanPrisoner prisoner = tempCrime.getPrisoners().get(name);
                    prisoner.setCrimeid(tempCrime.getCrimeid());
                    prisoner.setBeanCrime(tempCrime);

                    prisoners.add(prisoner);
                    prisonerManager.CreatePrisoner(prisoner);
                }

                List<BeanDrug> drugs = new ArrayList<>();
                if(tempCrime.getDrugsList()!=null)
                    for(BeanDrug drug : tempCrime.getDrugsList()){
                        drug.setCrimeid(tempCrime.getCrimeid());
                        drug.setBeanCrime(tempCrime);
                        drugManager.CreateDrugs(drug);
                        drugs.add(drug);
                    }
                crimeManager.UpdateFirstPrisoner(prisonerManager.hasPrisoner(firstPrisonerName).getPrisonerid(), tempCrimeid);
            }
            catch (BaseException e) {
                System.out.println(e.getMessage());
            }

            for(BeanPrisoner prisoner:prisoners)
                if(!prisonerMap.containsKey(prisoner.getName()))
                    prisonerMap.put(prisoner.getName(),prisoner);    //合并 map

            crimes.add(tempCrime);
            String crimeDate ="";
            String minPrisonerBirth = "";
            try{
                crimeDate = dateFormat.format(tempCrime.getDate());
            }
            catch (Exception e)
            {
                crimeDate=bakDateFormat.format(tempCrime.getDate());
            }
            try{
                minPrisonerBirth = dateFormat.format(tempCrime.getFirstPrisoner().getBirth());
            }
            catch (Exception e)
            {
                minPrisonerBirth = bakDateFormat.format(tempCrime.getFirstPrisoner().getBirth());
            }
            crimeList.add(new String[]{tempCrime.getSerial(), tempCrime.getProcuratorate(), tempCrime.getArea(), crimeDate, Integer.toString(tempCrime.getPrisoners().size()),
                        minPrisonerBirth,
                        tempCrime.getFirstPrisoner().getName(), tempCrime.getFirstPrisoner().getSex(), tempCrime.getFirstPrisoner().getIdCard(), tempCrime.getFirstPrisoner().getNation(),
                        tempCrime.getFirstPrisoner().getLevel(), tempCrime.getFirstPrisoner().getWork(), tempCrime.getFirstPrisoner().getPlace(), tempCrime.getFirstPrisoner().getCrime(), tempCrime.getFirstPrisoner().getPrisonType(), tempCrime.getFirstPrisoner().getPrisonTime(),
                        tempCrime.getFirstPrisoner().getPenalty(), Float.toString(tempCrime.getFirstPrisoner().getPenaltySum()), tempCrime.showDrugs(), tempCrime.showAverageDrugs()});


        }
        ParseToJson.ParseToJson(prisonerMap,ParseToRelationList.parseToRelation(filePath),saveName);
        try {
            writer("/Users/mac/Desktop/Output/" + saveName + ".csv", new Pair<>(titles, crimeList), true, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void sqlParseToCsv(String area, String saveName) {
        ArrayList<String[]> crimeList = new ArrayList<>();

        CrimeManager crimeManager = new CrimeManager();
        PrisonerManager prisonerManager = new PrisonerManager();
        DrugManager drugManager = new DrugManager();

        try {
            List<BeanCrime> crimes = crimeManager.loadAllCrimes();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");


            for(BeanCrime tempCrime:crimes)
            {
                BeanPrisoner firstPrisoner = prisonerManager.getPrisoner(tempCrime.getFirstprisonerid());
                try {
                    crimeList.add(new String[]{tempCrime.getSerial(), tempCrime.getProcuratorate(), tempCrime.getArea(), dateFormat.format(tempCrime.getDate()), Integer.toString(tempCrime.getPrisoners().size()),
                            dateFormat.format(tempCrime.getMinimumAge()),
                            firstPrisoner.getName(), firstPrisoner.getSex(), firstPrisoner.getIdCard(), firstPrisoner.getNation(),
                            firstPrisoner.getLevel(), firstPrisoner.getWork(),firstPrisoner.getPlace(), firstPrisoner.getCrime(), firstPrisoner.getPrisonType(), firstPrisoner.getPrisonTime(),
                            firstPrisoner.getPenalty(), Float.toString(firstPrisoner.getPenaltySum()), tempCrime.showDrugs(), tempCrime.showAverageDrugs()});
                }
                catch (Exception e){
                    crimeList.add(new String[]{tempCrime.getSerial(), tempCrime.getProcuratorate(), tempCrime.getArea(), dateFormat.format(tempCrime.getDate()), Integer.toString(tempCrime.getPrisoners().size()),
                            "",
                            firstPrisoner.getName(), firstPrisoner.getSex(), firstPrisoner.getIdCard(), firstPrisoner.getNation(),
                            firstPrisoner.getLevel(), firstPrisoner.getWork(),firstPrisoner.getPlace(), firstPrisoner.getCrime(), firstPrisoner.getPrisonType(), firstPrisoner.getPrisonTime(),
                            firstPrisoner.getPenalty(), Float.toString(firstPrisoner.getPenaltySum()), tempCrime.showDrugs(), tempCrime.showAverageDrugs()});
                    }
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        //ParseToJson.ParseToJson(prisonerMap,ParseToRelationList.parseToRelation(filePath),saveName);
        try {
            writer("/Users/mac/Desktop/" + saveName + ".csv", new Pair<>(titles, crimeList), true, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        ParseToCsv.parseToCsv("/Users/mac/Desktop/2018yearsixto/杭州", "杭州");
    }

}
