package reader;

import match.MatchCrime;
import model.BeanCrime;
import model.BeanPrisoner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReadFilePath {
    public ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                if(tempList[i].getName().endsWith("doc")||tempList[i].getName().endsWith("docx"))
                    if (!tempList[i].getName().contains("~$"))
                        files.add(tempList[i].getAbsolutePath());
            }
        }
        return files;
    }
    public ArrayList<BeanCrime> getCrimes(String path)
    {
        ArrayList<String> filePaths = new ReadFilePath().getFiles(path);
        ArrayList<BeanCrime> crimes = new ArrayList<>();
        Map<String,BeanPrisoner> prisonerMap = new HashMap<>();

        for(String fileName:filePaths) {
            BeanCrime tempCrime = new MatchCrime().Match(fileName);
            prisonerMap.putAll(tempCrime.getPrisoners());    //合并 map
            crimes.add(tempCrime);
        }
        return crimes;
    }

    public static void main(String args[]){
        ArrayList<String> files=new ReadFilePath().getFiles("E:\\学习\\java项目\\判决书\\2018年1-6月份毒品刑事案件一审\\温州");
        for(String fileName:files)
            System.out.println(fileName);
    }

}

