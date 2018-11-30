package match;

import java.util.regex.Pattern;

import model.BeanDrug;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.*;
import parser.ParseNum;

import static parser.ParseNum.covertNum;


public class MatchDrug {
	public static String Drug[] = {"[甲][基][苯][丙][胺]", "[冰][毒]", "[大][麻]", "[可][卡][因]", "[海][洛][因]", "[吗][啡]", "[卡][西][酮]", "[鸦][片]", "[K][粉]", "[摇][头][丸]", "[杜][冷][丁]", "[古][柯]", "[咖][啡][因]", "[三][唑][仑]", "[羟][基][丁][酸]"};
	//public static String Drug[] = {"([甲][基][苯][丙][胺])", "([冰][毒])", "([大][麻])", "([可][卡][因])", "([海][洛][因])", "([吗][啡])", "([卡][西][酮])", "([鸦][片])", "([K][粉])", "([摇][头][丸])", "([杜][冷][丁])", "([古][柯])", "([咖][啡][因])", "([三][唑][仑])", "([羟][基][丁][酸])"};

	public String regexPrice = "([\\d\\s.]+|[一二三四五六七八九十百千万]+)([元]|[人][民][币])";
	public String regexPriceBak="([\\d\\s.]+|[一二三四五六七八九十百千万]+)(([美][元])|([欧][元])|([日][元])|([港][币])|([英][镑]))";
	public String regexNum	= "([\\d\\s.]+|[一二三四五六七八九十百千万亿]+)([克]|[千][克]|[公][斤]|[斤]|[吨]|[毫][克]|[微][克]|g|kg|mg|ug|t)";
	public String regexNumBak = "([\\d\\s.]+|[一二三四五六七八九十百千万亿]+)([粒]|([小][粒])|([小][包])|[包]|[袋]|([小][袋])|[块]|([小][块])|[个])";

	public String regexDrugName="("+"".join("|", Drug)+")";


	public List<BeanDrug> drugList=new ArrayList<BeanDrug>();
	public List<BeanDrug> MatchDrugs(String text)
	{
		String [] sentences=text.split("[。；;]");
		String drugName=null;
		boolean drugNameFlag = false;

		boolean drugPriceFlag = false;
		float drugPrice = 0;
		String drugPriceMagnitude = null;

		String drugNumber[]=null;

		LinkedList<String[]> drugNumberStack = new LinkedList<String[]>();

		for(String s:sentences) {
			if(!drugNameFlag||drugName!=null) {
				Pattern patternDrugName = Pattern.compile(regexDrugName);
				Matcher drugNameMatcher = patternDrugName.matcher(s);

				if(drugNameMatcher.find()) {
					drugNameFlag = true;
					drugName = drugNameMatcher.group();

				}
			}

			if(!drugPriceFlag||drugPrice!=0) {
				Pattern patternPrice = Pattern.compile(regexPrice);
				Matcher priceMatcher = patternPrice.matcher(s);

				Pattern patternPriceBak = Pattern.compile(regexPriceBak);
				Matcher priceBakMatcher = patternPriceBak.matcher(s);

				if(priceMatcher.find()) {
					drugPriceFlag = true;
					drugPrice = covertNum(priceMatcher.group(1));

					drugPriceMagnitude = "元";
				}
				else if(priceBakMatcher.find()){
					drugPrice=covertNum(priceBakMatcher.group(0));
					drugPriceMagnitude=priceBakMatcher.group(1);
				}

				List<String[]> drugNumberMatch=new ArrayList<String[]>();
				Pattern patternDrugNum= Pattern.compile(regexNum);
				Matcher drugNumMatcher = patternDrugNum.matcher(s);
				if(drugNumMatcher.find()) {
					String [] drugs= {drugNumMatcher.group(1),drugNumMatcher.group(2)};
					drugNumberMatch.add(drugs);

					while(drugNumMatcher.find()) {
						String []drugs2={drugNumMatcher.group(1),drugNumMatcher.group(2)};
						drugNumberMatch.add(drugs2);
					}
					for(int i=0;i<drugNumberMatch.size();i++) {
						drugNumberStack.add(drugNumberMatch.get(i));
					}
				}
				else {
					Pattern patternNumBak = Pattern.compile(regexNumBak);
					Matcher numBakMatcher = patternNumBak.matcher(s);
					while(numBakMatcher.find()) {
						String []drugs={numBakMatcher.group(1),numBakMatcher.group(2)};
						drugNumberMatch.add(drugs);
					}
				}


				if(drugNameFlag&&drugPriceFlag&&drugNumberStack.size()>0) {

					drugNumber=drugNumberStack.pop();
					drugNumberStack.clear();
					BeanDrug beanDrug = new BeanDrug();
					beanDrug.setDrugPriceMagnitude( drugPriceMagnitude);
					beanDrug.setDrugType(drugName);
					beanDrug.setDrugPrice(drugPrice);
					beanDrug.setDrugMagnitude(drugNumber[1]);
					beanDrug.setDrugNum(covertNum(drugNumber[0]));
					drugList.add(beanDrug);

					drugNameFlag = false;
					drugName ="";
					drugPriceFlag = false;
					drugPrice = 0;
				}
				if(s==sentences[sentences.length-1] && drugNameFlag && drugPriceFlag && drugNumberStack.size()>0) {
					BeanDrug beanDrug = new BeanDrug();
					beanDrug.setDrugPriceMagnitude(drugPriceMagnitude);
					beanDrug.setDrugPrice(drugPrice);
					beanDrug.setDrugNum(0);
					beanDrug.setDrugType(drugName);
					drugList.add(beanDrug);
				}
			}
		}
		return drugList;

	}
	public static void main(String[] args) {
		List<BeanDrug> drugs=new MatchDrug().MatchDrugs("2017年5月14日，胡伟电话联系被告人刘先乾购买甲基苯丙胺（冰毒），刘先乾表示买的量大可送货上门，后两人经商量决定以每克150元的价格交易100克，刘先乾负责将毒品从嘉善送到金华。");
		for(BeanDrug drug:drugs)
		{
			System.out.println(drug.getDrugType());
			System.out.println(drug.getDrugPrice());
		}

	}

}
