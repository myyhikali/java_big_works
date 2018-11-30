package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Txt {
    public static void WriteDictionary(String text,String filePath)
    {
        FileWriter fw = null;
        try {
            File f=new File(filePath);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        //pw.format("UTF-8",null);
        pw.print(text+" ");
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
