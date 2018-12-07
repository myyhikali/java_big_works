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
    private static String regexPrisoner =
//	"([一二三四五六七八九十]+[\\pP][被][告][人][\\u0391-\\uFFE5&&[^\n]]+)";   [一二三四五六七八九十]*[、]*]?

            "(([\\n\\r\\t ]|[、])被告人[\\u0391-\\uFFE5·&&[^\\n]]+?犯[\\u0391-\\uFFE5[ ]（）()[0-9],&&[^\\n]]+?并处[\\u0391-\\uFFE5[0-9].[ ]&&[^\\n\\r（(,，。]]+)";

    //·:：[A-Z][a-z][0-9][ ]()（）,
    private static String regexInfo = "([\\n\\r\\t ]被告人[\\u0391-\\uFFE5·:：\"“”&[A-Z][a-z][0-9][ ]()（）,&&[^；\\n\\r\\t]]+生[\\u0391-\\uFFE5[A-Z][a-z][0-9]()（）\\- ,；;.?\\n\\r&&[^.。]]+。" +
            "[\\u0391-\\uFFE5[A-Z][a-z][0-9]\\- ,.&&[^.。]]*)|" +
            "([\\n\\r\\t ]被告人[\\u0391-\\uFFE5·:：\"“”&[A-Z][a-z][0-9][ ]()（）,&&[^\\n\\r\\t]]+族[\\u0391-\\uFFE5[A-Z][a-z][0-9]()（）\\- ,；;.?\\n\\r&&[^.。]]+。" +
            "[\\u0391-\\uFFE5[A-Z][a-z][0-9]\\- ,.&&[^.。]]*)";

    private static String regexPrison = "管制|拘役|有期徒刑|无期徒刑死刑|剥夺政治权利|缓刑";
    private static String regexPenalty = "罚金|没收财产|没收个人财产|没受个人财产";


    private Pattern pattern = Pattern.compile(regexPrisoner);
    private Pattern infoPattern = Pattern.compile(regexInfo);

    public Map<String, BeanPrisoner> Match(String text) {

        Matcher matcher = pattern.matcher(text);
        Matcher prisonerMatcher = infoPattern.matcher(text);
        Map<String, BeanPrisoner> prisonerMap = new HashMap<>();
        //System.out.println(text);

        while (prisonerMatcher.find()) {
            String regularLever = "文化|小学|初中|高中|中专|大专|文盲|大学|职高|专科|本科|职业高中";
            Matcher leverMarcher = Pattern.compile(regularLever).matcher(prisonerMatcher.group());

            String matchString = prisonerMatcher.group();
            System.out.println(matchString);
            int index = 0;
            String info[] = matchString.trim().split("[，,。]");
            if(info[0].contains("罪")&&info[0].contains("犯") || info.length<3 ||!leverMarcher.find()&&!matchString.contains("族")&&!matchString.contains("身份证")&&!matchString.contains("出生"))
                continue;
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
//            CustomDictionary.insert(findName, "nr 2048");
            // Txt.WriteDictionary(findName + " nr 2048", "E:\\学习\\java项目\\HanLP-master\\HanLP-master\\data\\dictionary\\custom\\毒贩词库.txt");

            String regularIdcard = "^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$";

            Matcher idMarcher = Pattern.compile(regularIdcard).matcher(matchString);

            if (idMarcher.find())
                prisoner.setIdCard(idMarcher.group());

            for(;index<info.length&&index<9;index++)
            {
                leverMarcher = Pattern.compile(regularLever).matcher(info[index]);
                if (info[index].contains("男") ||info[index].contains("女"))
                {
                    prisoner.setSex(info[index]);
                }
                else if(info[index].contains("日出生"))
                {
                    prisoner.setBirth(MatchCrime.parseDate(info[index].split("出生")[0]));
                }
                else if(info[index].contains("日生"))
                {
                    prisoner.setBirth(MatchCrime.parseDate(info[index].split("生")[0]));
                }
                else if (info[index].endsWith("族"))
                    prisoner.setNation(info[index]);
                else if (leverMarcher.find())
                {
                    if(info[index].contains("住") || info[index].contains("户籍"))
                        continue;
                    prisoner.setLevel(info[index++]);
                    if (info[index].contains("住") || info[index].contains("户籍")) {
                        prisoner.setPlace(info[index]);
                    } else {
                        prisoner.setWork(info[index++]);
                        prisoner.setPlace(info[index]);
                    }
                }
                else if(info[index].contains("住") || info[index].contains("户籍"))
                    prisoner.setPlace(info[index]);

            }
            if (!prisonerMap.containsKey(findName)) {
                prisonerMap.put(findName, prisoner);
            }

        }
        if(prisonerMap.size()==0)
        {
            String findName="";
            BeanPrisoner prisoner = new BeanPrisoner();
            Matcher bakNameMatcher = Pattern.compile("姓    名\\t[\\u0391-\\uFFE5]+").matcher(text);
            Matcher bakSexMatcher = Pattern.compile("性别\\t[\\u0391-\\uFFE5]+").matcher(text);
            Matcher bakNationMatcher = Pattern.compile("民族\\t[\\u0391-\\uFFE5]+").matcher(text);
            Matcher bakLeverMatcher = Pattern.compile("文化程度\\t[\\u0391-\\uFFE5]+").matcher(text);
            Matcher bakPlaceMatcher = Pattern.compile("户[ ]*籍[ ]*地\\t[\\u0391-\\uFFE5[0-9]]+").matcher(text);
            Matcher bakIdMatcher = Pattern.compile("号码\\t[0-9]+").matcher(text);
            if(bakNameMatcher.find())
            {
                findName = bakNameMatcher.group().substring(bakNameMatcher.group().lastIndexOf("\t")+1);
                System.out.println(findName);
            }
            prisoner.setName(findName);
            if(bakSexMatcher.find())
                prisoner.setSex(bakSexMatcher.group().substring(bakSexMatcher.group().lastIndexOf("\t")+1));
            if(bakNationMatcher.find())
                prisoner.setNation(bakNationMatcher.group().substring(bakNationMatcher.group().lastIndexOf("\t")+1));
            if(bakLeverMatcher.find())
                prisoner.setLevel(bakLeverMatcher.group().substring(bakLeverMatcher.group().lastIndexOf("\t")+1));
            if(bakPlaceMatcher.find())
                prisoner.setPlace(bakPlaceMatcher.group().substring(bakPlaceMatcher.group().lastIndexOf("\t")+1));
            if(bakIdMatcher.find())
                prisoner.setIdCard(bakIdMatcher.group());

            if (!prisonerMap.containsKey(findName)) {
                prisonerMap.put(findName, prisoner);
            }
        }


        while (matcher.find()) {
            if (matcher.group() != null && !matcher.group().contains("中华人民共和国") && matcher.group().length() < 130&&!matcher.group().contains("建议")) //
            {
                System.out.println(matcher.group());
                String crimes="";
                String prisonTime = "";
                String type = "";
                String penaltyType = "";
                float penaltySum = 0;
                String penaltyList[] = matcher.group().trim().split("[，()（）；;]");
                Matcher matcher2 = Pattern.compile("犯[\\u0391-\\uFFE5[、]&&[^；，]]+?罪").matcher(matcher.group()); //犯xx、xx罪
                while (matcher2.find())
                    crimes=crimes.concat(matcher2.group());
                for (String info : penaltyList) {
                    if (info.contains("管制") || info.contains("拘役") || info.contains("有期徒刑") ||
                            info.contains("无期徒刑") || info.contains("死刑") || info.contains("剥夺政治权利") || info.contains("缓刑")) {
                        Matcher penaltyMatcher = Pattern.compile(regexPrison).matcher(info);
                        if (penaltyMatcher.find())
                            type = penaltyMatcher.group();
                        prisonTime = info.substring(info.lastIndexOf(type), info.length()).replaceAll(type, "");
                    } else if (info.contains("罚金") || info.contains("没收财产") || info.contains("没收个人财产") || info.contains("没受个人财产")) {
                        if(!info.contains("缴纳")&&!info.contains("交纳"))
                        {
                            Matcher penaltyMatcher = Pattern.compile(regexPenalty).matcher(info);
                            if (penaltyMatcher.find())
                                penaltyType = penaltyMatcher.group();
                            String money = info.substring(info.lastIndexOf(penaltyType), info.length()).replaceAll(penaltyType, "").
                                    replaceAll("人民币", "").replaceAll("元","").replaceAll(" ","");
                            System.out.println(money);
                            penaltySum = ParseNum.covertNum(money);
                        }
                    }
                }
                String name="";
                if (penaltyList[0].contains("被告人"))
                {
                    Matcher nameMatcher = Pattern.compile("被告人[\\u0391-\\uFFE5，·&&[^\\n。]]+?犯").matcher(penaltyList[0]);
                    if(nameMatcher.find())
                    {
                        name=nameMatcher.group().replaceAll("被告人","");
                        name=name.substring(0,name.lastIndexOf("犯"));
                    }
                }
                else
                    name = penaltyList[0].substring(0,penaltyList[0].lastIndexOf("犯"));
                System.out.println(name);
                if(!prisonerMap.containsKey(name))
                    continue;
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
                "E:\\学习\\java项目\\判决书\\2017年走私、贩卖、运输、制造毒品罪\\温州\\（2017）浙0302刑初236号.doc"
        ));
    }
}
