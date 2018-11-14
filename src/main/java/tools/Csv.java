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

        String[]  headers = data.getKey();
        ArrayList<String []> content = null;
        if(hasHeader)
            content = data.getValue();

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

    public static void main(String[] args) {
        String[] headers = {"编号","姓名","年龄"};
        String[] content = {"12365","张山","34"};
        ArrayList<String []> tmp = new ArrayList();
        tmp.add(content);
        try {
            writer("Hello.csv", new Pair<String[], ArrayList<String[]>>(headers, tmp), true, "UTF-8");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
