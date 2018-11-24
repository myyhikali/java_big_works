package reader;

import java.io.File;
import java.util.ArrayList;

public class ReadFilePath {
    public ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                if(tempList[i].getName().endsWith("doc"))
                    files.add(tempList[i].getAbsolutePath());
            }
        }
        return files;
    }
    public static void main(String args[]){
        ArrayList<String> files=new ReadFilePath().getFiles("E://学习//java项目//判决书//2018年1-6月份毒品刑事案件一审//舟山");
        for(String fileName:files)
            System.out.println(fileName);
    }

}

