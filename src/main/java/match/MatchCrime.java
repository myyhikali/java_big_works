package match;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import model.BeanCrime;
import model.BeanPrisoner;

import reader.ReadDocUtil;


public class MatchCrime {
	public static String regexPlace
    	= "([公][诉][机][关][\\u0391-\\uFFE5&&[^，。]]+人民检察院)" +
				"|([（〔(][0-9]+[）〕)][浙][0-9]+[刑][初][\\u0391-\\uFFE5[^，。\\s\\S][0-9]]+?号)"+
    			"|([以][\\u0391-\\uFFE5[0-9]〔〕\\-（\\[）\\][ ]()&&[^。,]]+?[起][诉][决定]*[书][，]*[分别]*[指][控]被告人[\\u0391-\\uFFE5·&&[^，。\\n\\r]]+)"+ //[^，。\n\r]
    			"|([二][Ｏ〇oO一二三四五六七八九十[0-9][\\s][^;,，。（\\n\\r]]+[年][一二三四五六七八九十[0-9][\\s][^。\\n\\r]]+[月][一二三四五六七八九十[0-9][ ]&&[^。\\n\\r]]+[日]?)"+
				"|([2][Ｏ〇oO一二三四五六七八九十[0-9][\\s][^;,，。（\\n\\r]]+[年][一二三四五六七八九十[0-9][\\s][^。\\n\\r]]+[月][一二三四五六七八九十[0-9][ ]&&[^。\\n\\r]]+[日]?)";


//	public static String regexInfo="(经审理查明[\\s\\S.]+上述[\\u0391-\\uFFE5]*事实)" +
//			"|(经审理查明[\\s\\S.]+以上事实)"+
//			"|(公诉机关指控[\\s\\S.]+以上事实)"+
//			"|(公诉机关指控[\\s\\S.]+上述事实)"+
//			"|(人民检察院指控[\\s\\S.]+上述事实)"+
//			"|(人民检察院指控[\\s\\S.]+以上事实)";        //公诉机关指控  //人民检察院指控";  指控：。。。公诉机关认为

	
	private Pattern pattern = Pattern.compile(regexPlace);
	public BeanCrime Match(String fileName)
	{
		BeanCrime crime = new BeanCrime();
		String text = ReadDocUtil.readWord(fileName);

		System.out.println(text);
		Map<String,BeanPrisoner> prisonerMap=new MatchPrisoner().Match(text);
        List<BeanPrisoner> prisoners = new ArrayList<BeanPrisoner>();
        Matcher matcher = pattern.matcher(text);

//        Matcher infoMatcher = Pattern.compile(regexInfo).matcher(text);


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
        		System.out.println(matcher.group());
        	}
        	else if(matcher.group(3)!=null)   //第一被告
        	{
        		System.out.println(matcher.group());
				String regexFirst="[被][告][人][\\u0391-\\uFFE5·&&[^\\n\\r]]*?([犯]|[、])";
				String firstName="";
				Matcher firstMatcher = Pattern.compile(regexFirst).matcher(matcher.group());
				if(firstMatcher.find())
				{
					firstName = firstMatcher.group();
					firstName = firstName.replaceAll("被告人","");

					firstName = firstName.substring(0, firstName.length()-1);
					if(firstName.equals("")||firstName.length()==0)
					{
						for(String name:prisonerMap.keySet())
						{
							if(prisonerMap.get(name).getBirth()!=null)
							{
								firstName=name;
								break;
							}
						}
					}
				}
				else
					continue;

				crime.setFirstPrisoner(prisonerMap.get(firstName));
        	}  
        	else if(matcher.group(4)!=null||matcher.group(5)!=null) //判决日期
        	{
        		if(matcher.group().length()<18)  //判决日期
        		{
					System.out.println(matcher.group());
        			crime.setDate(parseDate(matcher.group()));
        		}
        	}
        }


		if(prisonerMap.keySet().size()==0)
			return null;
        if(crime.getFirstPrisoner()==null)

        	for(String name:prisonerMap.keySet())
        		crime.setFirstPrisoner(prisonerMap.get(name));

		crime.setDrugsList(new MatchDrug().MatchDrugs(text));

		for(String name:prisonerMap.keySet())
		{
			prisoners.add(prisonerMap.get(name));
		}
		crime.setPrisoners(prisonerMap);
        crime.setMinimumAge(compareDate(prisoners));


        return crime;
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
		dateString=dateString.replaceAll("Ｏ", "0");
		dateString=dateString.replaceAll("〇", "0");
		dateString=dateString.replaceAll("○", "0");
		dateString=dateString.replaceAll("o", "0");
		dateString=dateString.replaceAll(" ", "");
		dateString=dateString.replaceAll("\\s","");

		try {
			//System.out.println(dateString);
			return dateFormat.parse(dateString);
		} catch (Exception e) {
			SimpleDateFormat bakDataFormat= new SimpleDateFormat("yyyy年M月d日");
			try {
				return dateFormat.parse(dateString);
			} catch (ParseException e1) {
				return new Date();
			}
		}
		
	}
	public static Date compareDate(List<BeanPrisoner> prisoners)
	{
		int i=0;
		while(prisoners.get(i).getBirth()==null)
		{
			if(i==prisoners.size()-1)
				break;
			i++;
		}
		Date tempDate=prisoners.get(i).getBirth() ;
		for(i=i+1;i<prisoners.size();i++)
			if(prisoners.get(i).getBirth()==null)
				;
			else if( tempDate.getTime()>prisoners.get(i).getBirth().getTime())
				tempDate = prisoners.get(i).getBirth();
		return tempDate;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MatchCrime().Match("E:\\学习\\java项目\\判决书\\2018年1-6月份毒品刑事案件一审\\舟山\\（2017）浙0902刑初450号.doc");
	}
}
