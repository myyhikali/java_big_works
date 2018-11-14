package match;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import model.BeanPrisoner;
import parser.ParseNum;
import reader.ReadDocUtil;

public class MatchPrisoner {
	public static String regexPrisoner=
			"([判][决][如][下][\\s\\S|.]+[如][不][服][本][判][决])|"+
			"([被][告][人][\\u0391-\\uFFE5|[0-9]]+)|"+
	"([一二三四五六七八九十]+[、][被][告][人][\\u0391-\\uFFE5&&[^\n]]+)";
	public static String regexPrison = "管制|拘役|有期徒刑|无期徒刑死刑|剥夺政治权利|缓刑";
	public static String regexPenalty = "罚金|没收财产|没收个人财产|没受个人财产";

	
	private Pattern pattern = Pattern.compile(regexPrisoner);
	
	public Map<String,BeanPrisoner> Match(String text)
	{
		System.out.println(text);
		Matcher matcher = pattern.matcher(text);
		Map<String, BeanPrisoner> prisonerMap=new HashMap<String,BeanPrisoner>();
		
		while (matcher.find())
		{
			if(matcher.group(1)!=null)
			{
				System.out.println(matcher.group());
			}
			else if(matcher.group(2)!=null)
			{
				String matchString = matcher.group(2);
				if(matchString.contains("出生"))
				{
					String info[]=matchString.split("，|。");
					String findName = info[0].substring(3);
					BeanPrisoner prisoner = new BeanPrisoner();
					prisoner.setName(findName);
					prisoner.setSex(info[1]);
					prisoner.setBirth(MatchCrime.parseDate(info[2].split("出生")[0]));
					if(info[3].contains("身份证"))
					{
						prisoner.setIdCard(info[3]);
						prisoner.setNation(info[4]);
						prisoner.setLevel(info[5]);
						if(info[6].contains("住")|info[6].contains("户籍"))
							prisoner.setPlace(info[6]);
						else {
							prisoner.setWork(info[6]);
							prisoner.setPlace(info[7]);
						}
					}
					else{
						prisoner.setNation(info[3]);
						prisoner.setLevel(info[4]);
						if(info[5].contains("住")|info[5].contains("户籍"))
							prisoner.setPlace(info[5]);
						else {
							prisoner.setWork(info[5]);
							prisoner.setPlace(info[6]);
						}
					}

					if(!prisonerMap.containsKey(findName))
					{
						prisonerMap.put(findName, prisoner);
					}
				}
			}
			else if(matcher.group(3)!=null)
			{
				List<String> crimes=new ArrayList<String>();
				String prisonTime="";
				String type="";
				String penaltyType="";
				float penaltySum=0;
				String penaltyList[] = matcher.group().split("，|、|（|）|；");
				Matcher matcher2=Pattern.compile("[犯][\\u0391-\\uFFE5&&[^；，]]+[罪]").matcher(matcher.group());
				while(matcher2.find())
					crimes.add(matcher2.group());
				for(String info:penaltyList)
				{
					if(info.contains("管制")||info.contains("拘役")||info.contains("有期徒刑")||
							info.contains("无期徒刑")||info.contains("死刑")||info.contains("剥夺政治权利")||info.contains("缓刑")) {
						Matcher penaltyMatcher = Pattern.compile(regexPrison).matcher(info);
						if(penaltyMatcher.find())
							type=penaltyMatcher.group();
						prisonTime=info.substring(info.lastIndexOf(type),info.length()).replaceAll(type, "");
					}
					else if(info.contains("罚金")||info.contains("没收财产")||info.contains("没收个人财产")||info.contains("没受个人财产"))
					{
						Matcher penaltyMatcher = Pattern.compile(regexPenalty).matcher(info);
						if(penaltyMatcher.find())
							penaltyType=penaltyMatcher.group();
						penaltySum=ParseNum.covertNum(info.substring(info.lastIndexOf(penaltyType),info.length()-1).replaceAll(penaltyType, "").replaceAll("人民币",""));

					}
				}
				String name = penaltyList[1].substring(3).replaceAll(crimes.get(0),"");
				
				prisonerMap.get(name).setCrime(crimes);
				prisonerMap.get(name).setPenalty(penaltyType);
				prisonerMap.get(name).setPrisonType(type);
				prisonerMap.get(name).setPenaltySum(penaltySum);
				prisonerMap.get(name).setPrisonTime(prisonTime);
				System.out.println(penaltySum);
			}

		}
		prisonerMap=new MatchCase().Match(prisonerMap, text);
		
		return prisonerMap;
	}
	public static void main(String[] args) {
		new MatchPrisoner().Match(ReadDocUtil.readWord(
				//"E:\\学习\\java项目\\舟山\\（2016）浙0902刑初00262号.doc"
				"E:\\学习\\java项目\\舟山\\（2016）浙0903刑初00252号.doc"
				));
	}
}
