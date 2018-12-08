package python;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BeanCrime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PythonCSV {
    public static void covert4ML() throws IOException, InterruptedException {
        try {
            System.out.println("----------------------Start Covert----------------------");
            System.out.println("Start Python......");
            String[] args1 = new String[] { "python", "c:\\source\\javabigwork\\tmp\\python\\data_covert.py"};
            Process pr=Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Python => " + line);
            }
            in.close();
            pr.waitFor();
            System.out.println("----------------------End Covert----------------------");
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static String predict(String data) throws IOException, InterruptedException {
        String result = "0";
        try {
            String json = createJSON(data);
            System.out.println("----------------------Start Predict----------------------");
            System.out.println("Start Python......");
            String[] args1 = new String[] { "python", "c:\\source\\javabigwork\\tmp\\python\\ml.py", json};
            Process pr=Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Python => " + line);
                result = line;
            }
            in.close();
            pr.waitFor();
            System.out.println("----------------------End Predict----------------------");

        }
        catch (Exception e) {
            throw e;
        }
        return result;
    }

    private static String createJSON(String data) throws JsonProcessingException {

        //    translate = {"cyclohexanone": "K粉", "heroin": "海洛因", "methamphetamine": "甲基苯丙胺", "marijuana": "大麻"

//    ,"hurt": "伤害", "produce": "制造", "accept": "容留", "hold": "持有", "transport": "运输", "opium": "鸦片", "Caffeine": "咖啡因"}

        BeanCrime4RG crime4RG = new BeanCrime4RG();
        if(data.contains("容留"))
            crime4RG.setAccept(1);
        else
            crime4RG.setAccept(0);
        if(data.contains("伤害"))
            crime4RG.setHurt(1);
        else
            crime4RG.setHurt(0);
        if(data.contains("持有"))
            crime4RG.setHold(1);
        else
            crime4RG.setHold(0);
        if(data.contains("运输"))
            crime4RG.setTransport(1);
        else
            crime4RG.setTransport(0);
        if(data.contains("生产"))
            crime4RG.setProduce(1);
        else
            crime4RG.setProduce(0);

        String regexNum	= "([\\d\\s.]+|[一二三四五六七八九十百千万亿]+)([克]|[千][克]|[公][斤]|[斤]|[吨]|[毫][克]|[微][克]|g|kg|mg|ug|t)";
        Pattern pattern = Pattern.compile(regexNum);

        Map<String, Double> drugMap = new HashMap<String, Double>();
        drugMap.put("甲基苯丙胺", 0.0);
        drugMap.put("K粉", 0.0);
        drugMap.put("海洛英", 0.0);
        drugMap.put("大麻", 0.0);
        drugMap.put("鸦片", 0.0);
        drugMap.put("咖啡因", 0.0);

        String[] drugs = {"冰毒", "甲基苯丙胺", "K粉", "海洛英", "大麻", "鸦片", "咖啡因", "摇头丸", "吗啡"};

        for(int i = 0; i < drugs.length; ++i) {
            String drug = drugs[i];
            if (data.contains(drugs[i])) {
                int index = data.indexOf(drugs[i]);
                Matcher matcher = pattern.matcher(data.substring(index));
                if (matcher.find()) {
                    double weight = Double.valueOf(matcher.group(1));
                    if (matcher.group(2).equals("kg") || matcher.group(2).equals("千克"))
//                System.out.println(matcher.group(1));
//                System.out.println(matcher.group(2));
                        weight *= 1000;

                    if(drug.equals("冰毒") || drug.equals("摇头丸"))
                        drug = "甲基苯丙胺";
                    if(drug.equals("吗啡") )
                        drug = "鸦片";

                    if(drugMap.containsKey(drug)){
                        drugMap.put(drug, drugMap.get(drug) + weight);
                    }
                    else
                        drugMap.put(drug, weight);

                }
            }
        }


        crime4RG.setCyclohexanone(drugMap.get("K粉"));
        crime4RG.setHeroin(drugMap.get("海洛英"));
        crime4RG.setMethamphetamine(drugMap.get("甲基苯丙胺"));
        crime4RG.setMarijuana(drugMap.get("大麻"));
        crime4RG.setCaffeine(drugMap.get("咖啡因"));
        crime4RG.setMarijuana(drugMap.get("鸦片"));




        /*
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(crime4RG);
        System.out.println("Message => Json:" + json);


        json = json.replace("\"", "\\\"");

        return json;
    }


    public static void main(String[] args) throws JsonProcessingException {


    }

}
