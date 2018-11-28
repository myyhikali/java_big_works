package match;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.hankcs.hanlp.*;

import com.hankcs.hanlp.corpus.document.sentence.Sentence;
import com.hankcs.hanlp.corpus.document.sentence.word.IWord;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import model.BeanCase;
import model.BeanPrisoner;

import static parser.ParseNum.covertNum;

public class MatchCase {

    public static String Drug[] = {"[甲][基][苯][丙][胺]", "[冰][毒]", "[大][麻]", "[可][卡][因]", "[海][洛][因]", "[吗][啡]", "[卡][西][酮]", "[鸦][片]", "[K][粉]", "[摇][头][丸]", "[杜][冷][丁]", "[古][柯]", "[咖][啡][因]", "[三][唑][仑]", "[羟][基][丁][酸]"};

    public static String regexNum	= "([\\d\\s.]+|[一二三四五六七八九十百千万亿]+)([克]|[千][克]|[公][斤]|[斤]|[吨]|[毫][克]|[微][克]|g|kg|mg|ug|t)";
    public static String regexNumBak = "([\\d\\s.]+|[一二三四五六七八九十百千万亿]+)([粒]|([小][粒])|([小][包])|[包]|[袋]|([小][袋])|[块]|([小][块]))";

    public static String regexDrugName="("+"".join("|", Drug)+")";


	public static List<BeanCase> MatchCase(String text)
	{
        String[] testCase =text.split("。");

        List<BeanCase> cases = new ArrayList<>();

        Segment standard = StandardTokenizer.SEGMENT.enableCustomDictionary(true).enablePlaceRecognize(true).enableOrganizationRecognize(true);
        CustomDictionary.insert("老毛","nr 4096");
        CustomDictionary.insert("暂住处","n 4096");
        CustomDictionary.insert("办公室","n 4096");

            //CRFLexicalAnalyzer analyzer = new CRFLexicalAnalyzer();

            for (String sentence : testCase) {
                Pattern pattern = Pattern.compile(regexDrugName);
                String time="";
                String drugName="";
                float drugNum=0;
                String drugNumMagnitude="";

                Matcher drugMatcher = pattern.matcher(sentence);
                Matcher numMatcher = Pattern.compile(regexNum).matcher(sentence);
                Matcher numMatcherBak = Pattern.compile(regexNumBak).matcher(sentence);

                Stack<Term> nameStack=new Stack<>();
                Stack<Term> relationStack=new Stack<>();
                if(drugMatcher.find())
                    drugName=drugName.concat(drugMatcher.group());

                List<Term> sentence1=standard.seg(sentence);
                for(Term term:sentence1)
                {
                    //System.out.print(term);
                    if(term.nature.toString().equals("t")&&time.equals(""))
                        time = term.word;
                    if(term.nature.toString().equals("v"))
                    {
                        if(relationStack.empty())
                        {
                            if(!nameStack.empty())
                            {
                                relationStack.push(nameStack.peek());
                                relationStack.push(term);
                            }
                        }
                        else{
                            if(!nameStack.empty())
                            {
                                if(relationStack.peek().nature.toString().equals("nr")){
                                    relationStack.push(term);
                                }
                                else if(relationStack.peek().nature.toString().equals("v")){
                                    relationStack.pop();
                                }
                            }

                        }
                    }
                    else if(term.nature.toString().equals("nr")){
                        if(relationStack.empty()){
                            nameStack.push(term);
                            relationStack.push(term);
                        }
                        else{
                            if(relationStack.peek().nature.toString().equals("v"))
                            {
                                BeanCase beanCase=new BeanCase();
                                beanCase.setInfo(relationStack.pop().word);

                                String name1="";
                                if(!nameStack.empty())
                                {
                                    name1=nameStack.peek().word;
                                    beanCase.setPerson1(name1);

                                    String name2=term.word;
                                    beanCase.setPerson2(name2);
                                    nameStack.push(term);

                                    beanCase.setDrugType(drugName);
                                    if(numMatcherBak.find())
                                    {
                                        drugNum=covertNum(numMatcherBak.group(0));
                                        drugNumMagnitude=numMatcherBak.group(1);
                                    }
                                    if(numMatcher.find())
                                    {
                                        drugNum=covertNum(numMatcher.group(0));
                                        drugNumMagnitude=numMatcher.group(1);
                                    }
                                    beanCase.setNum(drugNum);
                                    beanCase.setMatitude(drugNumMagnitude);
                                    cases.add(beanCase);
                                }
                            }
                        }
                    }
                }

            }


//		Matcher matcher = pattern.matcher(text);
//		Set<String> names=prisonerMap.keySet();
//		String regexBuyer="([贩][卖][给][\\u0391-\\uFFE5|[、]&&[^，]]+)|([得][毒][资][\\u0391-\\uFFE5|[、]|0-9&&[^，]]+)"+
//		"|([被][告][人][\\u0391-\\uFFE5&&[^，]]+?)";
//
//		while (matcher.find())
//		{
//			if(matcher.group(1)!=null)
//			{
//				System.out.println(matcher.group());
//				Matcher infoMatcher=Pattern.compile(regexBuyer).matcher(matcher.group());
//				while(infoMatcher.find())
//				{
//					if(infoMatcher.group(1)!=null)
//						System.out.println(infoMatcher.group());
//					else if(infoMatcher.group(2)!=null)
//						System.out.println(infoMatcher.group());
//					else if(infoMatcher.group(3)!=null)
//						System.out.println(infoMatcher.group());
//				}
//			}
//		}
		
		return cases;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text="1、2017年3月的一天，“老毛”要求被告人朱伟斌将其随身携带的100克甲基苯丙胺（冰毒）出售，后被告人朱伟斌联系被告人叶辉寻找买家。随后，被告人叶辉微信联系李仙会，将毒品以每克130元的价格出售，并于当晚将100克甲基苯丙胺放在被告人朱伟斌住处楼下电灯柱旁，被李仙会取走后得毒资人民币12700元。尔后，被告人叶辉分4次通过微信转账的方式将毒资转给被告人朱伟斌，被告人朱伟斌又通过汇款、微信转账的方式将毒资转给“老毛”。2、同年2月至3月期间，被告人朱伟斌在舟山市定海区西园新村62幢302室暂住处向被告人叶辉贩卖甲基苯丙胺7次，共计4.9克。3、同年2月至3月期间，被告人朱伟斌在上述地点向谷世君贩卖甲基苯丙胺2次，共计1.4克。4、同年3月23日，被告人陈绍勤在舟山市定海区时代假日酒店二楼办公室内向被告人叶辉贩卖甲基苯丙胺3克。5、同日，被告人叶辉在时代假日酒店301房间向夏汉平贩卖甲基苯丙胺0.8克。（二）容留他人吸毒事实。1、2017年2月至3月期间，被告人谷世君在其暂住的舟山市定海区西园新村62幢302室东边卧室，容留叶辉吸食甲基苯丙胺10次。2、同年3月23日23时许，被告人夏汉平从叶辉处购得甲基苯丙胺后，在其暂住的舟山市定海区时代假日酒店301房间容留叶辉吸食甲基苯丙胺1次。3、同月24日7时许，被告人夏汉平在其暂住的时代假日酒店301房间容留叶辉吸食甲基苯丙胺1次。4、同日16时许，被告人夏汉平在其暂住的时代假日酒店502房间容留叶辉吸食甲基苯丙胺1次。公诉机关以书证、被告人供述、证人证言、鉴定意见、电子勘验笔录、检查笔录、辨认笔录、录像资料等证据予以证明上述事实";

		List<BeanCase> cases = MatchCase(text);
        String testCase[]= text.split("。");
        System.out.println(cases.size());

		for(BeanCase Case:cases)
        {
            System.out.println(Case.getPerson1()+Case.getInfo()+Case.getPerson2());
        }
	}

}
