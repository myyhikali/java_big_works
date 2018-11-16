package rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class RDFSelector {
    public static Resource Selector(Model RDFBasis, String resource){
        Model model = RDFBasis;

        model.write(System.out);

        // 获取John Smith的资源
        Resource vcard = model.getResource(resource);

        // 获取名字
        Resource name = (Resource) vcard.getRequiredProperty(VCARD.N).getObject();

        // 获取全名，会进行类型的转换
        String fullName = vcard.getRequiredProperty(VCARD.FN).getString();

        // 添加为john smith添加昵称资源
        vcard.addProperty(VCARD.NICKNAME, "Smithy")
                .addProperty(VCARD.NICKNAME, "Adman");

        return vcard;
    }
}
