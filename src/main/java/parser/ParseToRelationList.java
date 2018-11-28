package parser;

import match.MatchCase;
import match.MatchCrime;
import model.BeanCase;
import model.BeanCrime;
import model.BeanPrisoner;
import reader.ReadDocUtil;
import reader.ReadFilePath;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseToRelationList {
    private static String regexRelation = "(经审理查明[\\s\\S.]+上述事实)"+
            "|(经审理查明[\\s\\S.]+以上事实)";
    public static List<BeanCase> parseToRelation(String filePath)
    {
        ArrayList<String> filePaths=new ReadFilePath().getFiles(filePath);
        List<BeanCase> cases = new ArrayList<BeanCase>();

        for (String fileName : filePaths) {
            String text = ReadDocUtil.readWord(fileName);

            Matcher matcher= Pattern.compile(regexRelation).matcher(text);
            while(matcher.find())
            {
                cases.addAll(MatchCase.MatchCase(matcher.group()));
            }
        }

        return  cases;
    }

    public static void main(String args[]){
        List<BeanCase> cases = parseToRelation("E://学习//java项目//判决书//2018年1-6月份毒品刑事案件一审//舟山");
        for(BeanCase Case:cases)
        {
            System.out.println(Case.getPerson1()+Case.getInfo()+Case.getPerson2());
        }
    }

}
