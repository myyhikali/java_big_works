package match;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.hankcs.hanlp.*;

import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import model.BeanCase;
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
        String[] testCase =text.split("。");
        Stack<IWord> nameStack=new Stack<>();
        Stack<IWord> relationStack=new Stack<>();

        for (String sentence : testCase)
        {
            String time="";
            Sentence sentence1= NLPTokenizer.analyze(sentence);
            for(IWord word:sentence1.wordList)
            {
                if(word.getLabel().equals("t")&&time.equals(""))
                    time = word.getValue();
                if(word.getLabel().equals("v"))
                {
                    if(relationStack.empty())
                    {
                        if(nameStack.empty())
                            ;
                        else
                        {
                            relationStack.push(nameStack.peek());
                            relationStack.push(word);
                        }
                    }
                    else{
                        if(nameStack.empty())
                            ;
                        else
                        {
                            if(relationStack.peek().getLabel().equals("nr")){
                                relationStack.push(word);
                            }
                            else if(relationStack.peek().getLabel().equals("v")){
                                relationStack.pop();
                            }
                        }

                    }
                }
                else if(word.getLabel().equals("nr")){
                    if(relationStack.empty()){
                        nameStack.push(word);
                        relationStack.push(word);
                    }
                    else{
                        if(relationStack.peek().getLabel().equals("v"))
                        {
                            BeanCase beanCase=new BeanCase();
                            beanCase.setInfo(relationStack.pop().getValue());

                            BeanPrisoner person1 = new BeanPrisoner();
                            String name1=relationStack.pop().getValue();
                            if(prisonerMap.containsKey(name1))
                                person1 = prisonerMap.get(name1);
                            else
                                person1.setName(name1);
                            beanCase.setPerson1(person1);

                            BeanPrisoner person2 = new BeanPrisoner();
                            String name2=relationStack.pop().getValue();
                            if(prisonerMap.containsKey(name2))
                                person1 = prisonerMap.get(name2);
                            else
                                person1.setName(name2);
                            beanCase.setPerson1(person2);
                        }
                    }
                }
            }
        }



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
		String text="1、2017年3月的一天，“老毛”要求被告人朱伟斌将其随身携带的100克甲基苯丙胺（冰毒）出售，后被告人朱伟斌联系被告人叶辉寻找买家。随后，被告人叶辉微信联系李仙会，将毒品以每克130元的价格出售，并于当晚将100克甲基苯丙胺放在被告人朱伟斌住处楼下电灯柱旁，被李仙会取走后得毒资人民币12700元。尔后，被告人叶辉分4次通过微信转账的方式将毒资转给被告人朱伟斌，被告人朱伟斌又通过汇款、微信转账的方式将毒资转给“老毛”。2、同年2月至3月期间，被告人朱伟斌在舟山市定海区西园新村62幢302室暂住处向被告人叶辉贩卖甲基苯丙胺7次，共计4.9克。3、同年2月至3月期间，被告人朱伟斌在上述地点向谷世君贩卖甲基苯丙胺2次，共计1.4克。4、同年3月23日，被告人陈绍勤在舟山市定海区时代假日酒店二楼办公室内向被告人叶辉贩卖甲基苯丙胺3克。5、同日，被告人叶辉在时代假日酒店301房间向夏汉平贩卖甲基苯丙胺0.8克。（二）容留他人吸毒事实。1、2017年2月至3月期间，被告人谷世君在其暂住的舟山市定海区西园新村62幢302室东边卧室，容留叶辉吸食甲基苯丙胺10次。2、同年3月23日23时许，被告人夏汉平从叶辉处购得甲基苯丙胺后，在其暂住的舟山市定海区时代假日酒店301房间容留叶辉吸食甲基苯丙胺1次。3、同月24日7时许，被告人夏汉平在其暂住的时代假日酒店301房间容留叶辉吸食甲基苯丙胺1次。4、同日16时许，被告人夏汉平在其暂住的时代假日酒店502房间容留叶辉吸食甲基苯丙胺1次。公诉机关以书证、被告人供述、证人证言、鉴定意见、电子勘验笔录、检查笔录、辨认笔录、录像资料等证据予以证明上述事实";
		String[] testCase =text.split("。");
		Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);


		for (String sentence : testCase)
		{

//			List<Term> termList = segment.seg(sentence);
//			System.out.println(termList);
//			for(Term t:termList)
//			{
//
//				if(t.nature.toString().equals("nr"))
//					System.out.print(t.word+t.nature);
//				else if(t.nature.toString().equals("v"))
//					System.out.print(t.word+t.nature);
//				else if(t.nature.toString().equals("n"))
//					System.out.print(t.word+t.nature);
//			}


//			System.out.print(sentence1.findWordsByLabel("时间词"));
//			System.out.print(sentence1.findWordsByLabel("人名"));
//			System.out.println(sentence1.findWordsByLabel("动词"));
		}
		
	}

}
