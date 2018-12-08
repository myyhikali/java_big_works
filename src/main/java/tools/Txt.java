package tools;

import java.io.*;

public class Txt {
    public static void WriteDictionary(String text,String filePath,Boolean append,String charSet)
    {
        FileWriter fw = null;
        try {
            File f=new File(filePath);
            FileOutputStream writerStream = new FileOutputStream(f,append);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, charSet));
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        PrintWriter pw = new PrintWriter(fw);
//        //pw.format("UTF-8",null);
//        pw.print(text+" ");
//        pw.flush();
//        try {
//            fw.flush();
//            pw.close();
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

}

