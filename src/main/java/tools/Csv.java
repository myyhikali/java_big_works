package tools;


import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Csv {
    public static void writer(String filepath, Pair<String[], ArrayList<String[]>> data, boolean hasHeader, String charSet) throws IOException {

        CsvWriter csvWriter = new CsvWriter(filepath, ',', Charset.forName(charSet));

        Pair<String[], ArrayList<String[]>> formatData = preProcess(data);

        String[]  headers = formatData.getKey();
        ArrayList<String []> content = null;
        if(hasHeader)
            content = formatData.getValue();

        csvWriter.writeRecord(headers);

        if(content != null)
            for(String[] a: content)
                csvWriter.writeRecord(a);

        csvWriter.close();
    }

    public static Pair<String[], ArrayList<String[]>> read(String filepath, boolean hasHeader, String charSet) throws IOException {

        CsvReader csvReader = new CsvReader(filepath, ',', Charset.forName(charSet));
        String[] header = null;
        ArrayList<String[]> list = new ArrayList<String []>();
        if(hasHeader && csvReader.readHeaders())
            header = csvReader.getHeaders();

        while (csvReader.readRecord()) {
            list.add(csvReader.getValues());
        }

        return new Pair<String[], ArrayList<String[]>>(header, list);
    }


    public static Pair<String[], ArrayList<String[]>> preProcess(Pair<String[], ArrayList<String[]>> data){
        String[] header = data.getKey();
        ArrayList<String[]> list = data.getValue();

        String[] newHeader = new String[header.length];
        ArrayList<String[]> newList = new ArrayList<String[]>();

        for (int i = 0; i < header.length; ++i){
            newHeader[i] = toSemiangle(header[i]);
        }
        for (int i = 0; i < list.size(); ++i){
            String[] newRecord = new String[header.length];
            for (int j = 0; j < list.get(i).length; ++j){
                newRecord[j] = toSemiangle(list.get(i)[j]);
            }
            newList.add(newRecord);
        }


        return new Pair<String[], ArrayList<String[]>>(newHeader, newList);

    }

    /**
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * 将字符串中的全角字符转为半角
     * @param src 要转换的包含全角的任意字符串
     * @return  转换之后的字符串
     */

    private static String toSemiangle(String src) {
        if(src==null)
            return "";
        char[] c = src.toCharArray();
        for (int index = 0; index < c.length; index++) {
            if (c[index] == 12288) {// 全角空格
                c[index] = (char) 32;
            } else if (c[index] > 65280 && c[index] < 65375) {// 其他全角字符
                c[index] = (char) (c[index] - 65248);
            }
        }
        return String.valueOf(c);
    }


    public static void main(String[] args) throws IOException {
        read("./tmp/csv/舟山.csv",true, "UTF-8");


    }
}
