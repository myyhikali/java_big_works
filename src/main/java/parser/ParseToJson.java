package parser;

import hibernate_test.BaseException;
import hibernate_test.CaseManager;
import hibernate_test.CrimeManager;
import model.BeanCase;
import model.BeanPrisoner;
import tools.Txt;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParseToJson {


    public static void ParseToJson(Map<String, BeanPrisoner> prisonerMap, List<BeanCase> cases,String savePath,String saveName){

        String nodes="\"nodes\":[ ";
        String links="\"links\":[ ";

        System.out.println(prisonerMap.keySet());
        for(String name:prisonerMap.keySet())
        {
            String birthTime = ParseToCsv.formatTime(prisonerMap.get(name).getBirth());
            String crimeTime = ParseToCsv.formatTime(prisonerMap.get(name).getBeanCrime().getDate());
            nodes=nodes.concat("{\"id\":\""+name+"\",\"group\":1," +
                    "\"crimeType\":"+"\""+prisonerMap.get(name).getCrime()+"\","+
                    "\"birth\":"+"\""+birthTime+"\","+

                    "\"penalty\":"+"\""+prisonerMap.get(name).getPenalty()+"\","+
                    "\"penaltySum\":"+"\""+Float.toString(prisonerMap.get(name).getPenaltySum())+"\","+
                    "\"prisonType\":"+"\""+prisonerMap.get(name).getPrisonType()+"\","+
                    "\"prisonTime\":"+"\""+prisonerMap.get(name).getPrisonTime()+"\","+
                    "\"address\":"+"\""+prisonerMap.get(name).getWork()+"\","+
                    "\"level\":"+"\""+prisonerMap.get(name).getLevel()+"\","+
                    "\"sex\":"+"\""+prisonerMap.get(name).getSex()+"\","+
                    "\"nation\":"+"\""+prisonerMap.get(name).getNation()+"\","+
                    "\"idCard\":"+"\""+prisonerMap.get(name).getIdCard()+"\","+
                    "},\r\n"+
                    "{\"id\":\""+prisonerMap.get(name).getBeanCrime().getSerial()+"\",\"group\":30" +",\"area\":\""+prisonerMap.get(name).getBeanCrime().getArea()+"\"," +
                    "\"criminalProcura\":"+"\""+prisonerMap.get(name).getBeanCrime().getProcuratorate()+"\","+
                    "\"criminalCaseTime\":"+"\""+crimeTime+"\""+"},\r\n");


            links=links.concat("{\"source\":\"" +name+ "\",\"target\":\""+ prisonerMap.get(name).getBeanCrime().getSerial()+ "\",\"value\":120,\"relation\":\""+"案号"+"\"},\r\n");

        }
        for(BeanCase Case:cases)
        {
            CaseManager manager = new CaseManager();
            if(prisonerMap.containsKey(Case.getPerson1())&&prisonerMap.containsKey(Case.getPerson2())&&!Case.getPerson1().equals(Case.getPerson2()))
            {
                /**
                 * 入库
                 */
                try {
                    manager.CreateCase(Case);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
                /**
                 * 入库
                 */
                links=links.concat("{\"source\":\"" +Case.getPerson1()+ "\",\"target\":\""+ Case.getPerson2()+ "\",\"value\":120,\"relation\":\""+Case.getInfo()+"\"},\r\n");
            }
        }
        String text="{".concat(nodes).concat("],").concat(links).concat("]}");

        Txt.WriteDictionary(text,savePath+saveName+".json",false,"UTF-8");
    }

}
