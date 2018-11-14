package match;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.BeanCrime;
import model.BeanPrisoner;

import reader.ReadDocUtil;


public class MatchCrime {
	public static String regexPlace
    	= "([公][诉][机][关][\\u0391-\\uFFE5&&[^，。]]+[院])|([（][0-9]+[）][浙][0-9]+[刑][初][\\u0391-\\uFFE5&&[^，。]&&[0-9]]+[号])"+
    			"|([人][民][检][察][院][以][\\u0391-\\uFFE5|[0-9]|[（]|[）]]+[起][诉][书][指][控][\\u0391-\\uFFE5&&[^，。]]+)"+
    			
    			"|([二][\\u0391-\\uFFE5|[0-9]]+[年][\\u0391-\\uFFE5]+[日])"+
    			"|([经][审][理][查][明][\\s\\S|.]+?([上][述][事][实]|[以][上][事][实]))";
	
	
	private Pattern pattern = Pattern.compile(regexPlace);
	public void Match(String fileName)
	{
		
		BeanCrime crime = new BeanCrime();
		String text = ReadDocUtil.readWord(fileName);

		Map<String,BeanPrisoner> prisonerMap=new MatchPrisoner().Match(text);
        List<BeanPrisoner> prisoners = new ArrayList<BeanPrisoner>();
		
        Matcher matcher = pattern.matcher(text);
        while( matcher.find() )
        {
        	if(matcher.group(1)!=null) //检察院
        	{
        		crime.setProcuratorate(matcher.group());
        		crime.setArea(matcher.group().substring(4).replaceAll("人民检察院",""));	
        	}
        	else if(matcher.group(2)!=null)  //案件号
        	{
        		crime.setSerial(matcher.group());
        	}
        	else if(matcher.group(3)!=null)
        	{
				System.out.println(matcher.group(3));
				String regexFirst="[被][告][人][\\u0391-\\uFFE5&&]+?([犯]|[、])";
				Matcher firstMatcher = Pattern.compile(regexFirst).matcher(matcher.group(3));
				if(firstMatcher.find())
				{
					String firstName = firstMatcher.group();
					firstName = firstName.substring(3, firstName.length()-1);
					crime.setFirstPrisoner(prisonerMap.get(firstName));
				}
        	}  
        	else if(matcher.group(4)!=null) //判决日期
        	{
        		if(matcher.group().length()<15)  //判决日期
        		{
        			crime.setDate(parseDate(matcher.group()));
        		}
        	}
        	else if(matcher.group(5)!=null) //证据毒品信息
        	{
        		System.out.println(matcher.group());
        		crime.setDrugs(new MatchDrug().MatchDrugs(matcher.group()));
        	}
        }

        for(String name:prisonerMap.keySet())
		{
			prisoners.add(prisonerMap.get(name));
		}
		crime.setPrisoners(prisoners);
	}
	public static Date parseDate(String dateString){
		SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy年MM月dd日");
		dateString=dateString.replaceAll("三十", "3");
		dateString=dateString.replaceAll("二十", "2");
		dateString=dateString.replaceAll("十", "1");
		dateString=dateString.replaceAll("一", "1");
		dateString=dateString.replaceAll("二", "2");
		dateString=dateString.replaceAll("三", "3");
		dateString=dateString.replaceAll("四", "4");
		dateString=dateString.replaceAll("五", "5");
		dateString=dateString.replaceAll("六", "6");
		dateString=dateString.replaceAll("七", "7");
		dateString=dateString.replaceAll("八", "8");
		dateString=dateString.replaceAll("九", "9");
		dateString=dateString.replaceAll("O", "0");
		dateString=dateString.replaceAll("o", "0");
		
		try {
			return dateFormat.parse(dateString);
		} catch (Exception e) {
			return null;
		}
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MatchCrime().Match("E:\\学习\\java项目\\舟山\\（2016）浙0902刑初00262号.doc");
	}
}
