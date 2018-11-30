package parser;

import model.BeanCase;
import model.BeanPrisoner;
import tools.Txt;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParseToJson {

//    {
//        "nodes": [
//        {"id": "Cravatte", "group": 1},
//        {"id": "Count", "group": 2},
//        {"id": "OldMan", "group": 3},
//        {"id": "Labarre", "group": 2}
//  ],
//        "links": [
//        {"source": "Cravatte", "target": "Count", "value": 40},
//        {"source": "Cravatte", "target": "Labarre", "value":  10},
//        {"source": "Count", "target": "OldMan", "value": 20},
//        {"source": "OldMan", "target": "Labarre", "value": 40},
//        {"source": "OldMan", "target": "Count", "value": 30}
//  ]
//    }

    public static void ParseToJson(Map<String, BeanPrisoner> prisonerMap, List<BeanCase> cases){

        String nodes="\"nodes\":[ ";
        String links="\"links\":[ ";
        System.out.println(prisonerMap.keySet());
        for(String name:prisonerMap.keySet())
        {
            nodes=nodes.concat("{\"id\":\""+name+"\",\"group\":1},");
        }
        for(BeanCase Case:cases)
        {
            if(prisonerMap.containsKey(Case.getPerson1())&&prisonerMap.containsKey(Case.getPerson2()))
            {
                links=links.concat("{\"source\":\"" +Case.getPerson1()+ "\",\"target\":\""+ Case.getPerson2()+ "\",\"value\":40},");
            }
        }
        String text="{".concat(nodes).concat("],").concat(links).concat("]}");
        Txt.WriteDictionary(text,"E://学习//java项目//Test//json.txt");
    }

}
