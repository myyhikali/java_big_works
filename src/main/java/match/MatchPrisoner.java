package match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.hankcs.hanlp.dictionary.CustomDictionary;
import model.BeanPrisoner;
import parser.ParseNum;
import reader.ReadDocUtil;
import tools.Txt;

public class MatchPrisoner {
    public static String regexPrisoner =
//	"([一二三四五六七八九十]+[\\pP][被][告][人][\\u0391-\\uFFE5&&[^\n]]+)";   [一二三四五六七八九十]*[、]*]?
            "(([\\n\\r\\t]|[、])被告人[\\u0391-\\uFFE5&&[^\\n]]+?犯[\\u0391-\\uFFE5&&[^\n]]+?并处[\\u0391-\\uFFE5&&[0-9][^\n（(]]+)";

    public static String regexInfo = "([\\n\\r\\t ]被告人[\\u0391-\\uFFE5[A-Z][a-z][0-9] ,[^\\n\\r\\t]]+出生[\\u0391-\\uFFE5[A-Z][a-z][0-9] ,.[^；\\n\\r]]+)";

    public static String regexPrison = "管制|拘役|有期徒刑|无期徒刑死刑|剥夺政治权利|缓刑";
    public static String regexPenalty = "罚金|没收财产|没收个人财产|没受个人财产";


    private Pattern pattern = Pattern.compile(regexPrisoner);
    private Pattern infoPattern = Pattern.compile(regexInfo);

    public Map<String, BeanPrisoner> Match(String text) {

        Matcher matcher = pattern.matcher(text);
        Matcher prisonerMatcher = infoPattern.matcher(text);
        Map<String, BeanPrisoner> prisonerMap = new HashMap<>();

        while (prisonerMatcher.find()) {
            String matchString = prisonerMatcher.group();
            System.out.println(matchString);
            int index = 0;
            String info[] = matchString.trim().split("[，,。]");
            String findName = info[index++].replaceAll("被告人", "").replaceAll("[\\s\\n\\t\\r]", "");
            if (findName.contains("（")) {
                int temp = findName.lastIndexOf("（");
                findName = findName.substring(0, temp);
            }
            if (findName.contains("(")) {
                int temp = findName.lastIndexOf("(");
                findName = findName.substring(0, temp);
            }
            System.out.println(findName);
            BeanPrisoner prisoner = new BeanPrisoner();
            prisoner.setName(findName);
            //CustomDictionary.insert(findName, "nr 2048");
            Txt.WriteDictionary(findName + " nr 2048", "E:\\学习\\java项目\\HanLP-master\\HanLP-master\\data\\dictionary\\custom\\毒贩词库.txt");

            String regularIdcard = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                    "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
            String regularLever = "文化|小学|初中|高中|中专|大专|文盲|大学";
            Matcher idMarcher = Pattern.compile(regularIdcard).matcher(matchString);

            if (idMarcher.find())
                prisoner.setIdCard(idMarcher.group());

            for(;index<info.length&&index<9;index++)
            {
                Matcher leverMarcher = Pattern.compile(regularLever).matcher(info[index]);
                if (info[index].contains("男") ||info[index].contains("女"))
                {
                    prisoner.setSex(info[index]);
                }
                else if(info[index].contains("出生"))
                {
                    prisoner.setBirth(MatchCrime.parseDate(info[index].split("出生")[0]));
                }
                else if (info[index].endsWith("族"))
                    prisoner.setNation(info[index]);
                else if (leverMarcher.find())
                {
                    prisoner.setLevel(info[index++]);
                    if (info[index].contains("住") || info[index].contains("户籍")) {
                        prisoner.setPlace(info[index]);
                    } else {
                        prisoner.setWork(info[index++]);
                        prisoner.setPlace(info[index]);
                    }
                }

            }

//
//
//            if (!info[1].contains("男") && !info[1].contains("女"))
//                index++;
//            prisoner.setSex(info[index++]);
//            if (info[index].contains("出生"))
//                prisoner.setBirth(MatchCrime.parseDate(info[index++].split("出生")[0]));
//            else if (info[index].contains("族"))
//                prisoner.setNation(info[index++]);
//            if (info[index].contains("身份证")) {
//                index++;
//            }
//            if (info[index].contains("出生"))
//                prisoner.setBirth(MatchCrime.parseDate(info[index++].split("出生")[0]));
//            else if (info[index].contains("族"))
//                prisoner.setNation(info[index++]);
//            prisoner.setLevel(info[index++]);
//            if (info[index].contains("住") || info[index].contains("户籍")) {
//                prisoner.setPlace(info[index++]);
//            } else {
//                prisoner.setWork(info[index++]);
//                prisoner.setPlace(info[index++]);
//            }

            if (!prisonerMap.containsKey(findName)) {
                prisonerMap.put(findName, prisoner);
            }
        }

        while (matcher.find()) {
            if (matcher.group() != null && !matcher.group().contains("中华人民共和国") && matcher.group().length() < 130&&!matcher.group().contains("建议")) //
            {
                System.out.println(matcher.group());
                List<String> crimes = new ArrayList<String>();
                String prisonTime = "";
                String type = "";
                String penaltyType = "";
                float penaltySum = 0;
                String penaltyList[] = matcher.group().trim().split("[，（）；;]");
                Matcher matcher2 = Pattern.compile("犯[\\u0391-\\uFFE5&&[^；，][、]]+?罪").matcher(matcher.group()); //犯xx、xx罪  不能分割、
                while (matcher2.find())
                    crimes.add(matcher2.group());
                for (String info : penaltyList) {
                    if (info.contains("管制") || info.contains("拘役") || info.contains("有期徒刑") ||
                            info.contains("无期徒刑") || info.contains("死刑") || info.contains("剥夺政治权利") || info.contains("缓刑")) {
                        Matcher penaltyMatcher = Pattern.compile(regexPrison).matcher(info);
                        if (penaltyMatcher.find())
                            type = penaltyMatcher.group();
                        prisonTime = info.substring(info.lastIndexOf(type), info.length()).replaceAll(type, "");
                    } else if (info.contains("罚金") || info.contains("没收财产") || info.contains("没收个人财产") || info.contains("没受个人财产")) {
                        Matcher penaltyMatcher = Pattern.compile(regexPenalty).matcher(info);
                        if (penaltyMatcher.find())
                            penaltyType = penaltyMatcher.group();
                        penaltySum = ParseNum.covertNum(info.substring(info.lastIndexOf(penaltyType), info.length() - 1).replaceAll(penaltyType, "").replaceAll("人民币", ""));
                    }
                }
                String name;
                if (penaltyList[0].contains("被告人"))
                {
                    name = penaltyList[0].replaceAll("被告人|[\\n\\r]", "").replaceAll(crimes.get(0), "");
                    Matcher nameMatcher = Pattern.compile("被告人[\\u0391-\\uFFE5&&[^\n]]+?犯").matcher(penaltyList[0]);
                    if(nameMatcher.find())
                    {
                        name=nameMatcher.group().replaceAll("被告人","");
                        name=name.substring(0,name.length()-1);
                    }
                }
                else
                    name = penaltyList[1].replaceAll("被告人|[\\n\\r]", "").replaceAll(crimes.get(0), "");
                System.out.println(penaltyList[0]);
                System.out.println(name);
                prisonerMap.get(name).setCrime(crimes);
                prisonerMap.get(name).setPenalty(penaltyType);
                prisonerMap.get(name).setPrisonType(type);
                prisonerMap.get(name).setPenaltySum(penaltySum);
                prisonerMap.get(name).setPrisonTime(prisonTime);
            }
        }

        return prisonerMap;
    }

    public static void main(String[] args) {
        new MatchPrisoner().Match(ReadDocUtil.readWord(
                //"E:\\学习\\java项目\\舟山\\（2016）浙0902刑初00262号.doc"
                "E:\\学习\\java项目\\判决书\\2018年1-6月份毒品刑事案件一审\\舟山\\（2017）浙0903刑初367号.doc"
        ));
        //"E:\\学习\\java项目\\判决书\\2018年1-6月份毒品刑事案件一审\\舟山\\（2017）浙0902刑初354号.doc"
        //"E:\\学习\\java项目\\判决书\\2018年1-6月份毒品刑事案件一审\\舟山\\（2017）浙0922刑初55号.docx"
    }
}
