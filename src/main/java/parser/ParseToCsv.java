package parser;

import javafx.util.Pair;
import match.MatchCrime;
import model.BeanCase;
import model.BeanCrime;
import tools.Csv;

import java.util.ArrayList;

public class ParseToCsv {
    //writer(String filepath, Pair<String[], ArrayList<String[]>> data, boolean hasHeader, String charSet)
    BeanCrime crime = new MatchCrime().Match("E:\\学习\\java项目\\舟山\\（2016）浙0902刑初00262号.doc");
    String [] titles={"案号","法院名称","一案人数","年龄最小人员出生日期","第一被告姓名","罪名","刑罚种类","刑期","财产刑种类","财产刑金额","毒品种类和数量或单位","毒品单价"};


   // writer("E:\\学习\\java项目\\test.csv",new Pair<String[], ArrayList<String[]>>(),true,"UTF-8");
}
