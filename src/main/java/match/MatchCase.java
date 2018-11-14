package match;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.BeanPrisoner;

public class MatchCase {

	public static String regexCase=
//			"(\\s[0-9]+[、][\\u0391-\\uFFE5|0-9|.&&[^\n]]+)";
	"([被][告][人][\\u0391-\\uFFE5|0-9|.]+?[贩][卖][给][\\u0391-\\uFFE5|0-9|.]+?[毒][资][\\u0391-\\uFFE5|0-9|.&&[^。]]+)";
	public static String[] drugType = 
		{"甲基苯丙胺", "冰毒", "大麻", "可卡因", "海洛因", "吗啡", "卡西酮", "鸦片", "K粉", "摇头丸", "杜冷丁", "古柯","古柯碱", "咖啡因", "三唑仑", "羟基丁酸"};
	
	private Pattern pattern = Pattern.compile(regexCase);
	public Map<String,BeanPrisoner> Match(Map<String, BeanPrisoner> prisonerMap, String text)
	{
		Matcher matcher = pattern.matcher(text);
		Set<String> names=prisonerMap.keySet();
		String regexBuyer="([贩][卖][给][\\u0391-\\uFFE5|[、]&&[^，]]+)|([得][毒][资][\\u0391-\\uFFE5|[、]|0-9&&[^，]]+)"+
		"|([被][告][人][\\u0391-\\uFFE5&&[^，]]+?)";

		while (matcher.find())
		{
			if(matcher.group(1)!=null)
			{
				System.out.println(matcher.group());
				Matcher infoMatcher=Pattern.compile(regexBuyer).matcher(matcher.group());
				while(infoMatcher.find())
				{
					if(infoMatcher.group(1)!=null)
						System.out.println(infoMatcher.group());
					else if(infoMatcher.group(2)!=null)
						System.out.println(infoMatcher.group());
					else if(infoMatcher.group(3)!=null)
						System.out.println(infoMatcher.group());
				}
			}
		}
		
		return prisonerMap;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
