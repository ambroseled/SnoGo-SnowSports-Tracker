package Phase_1;


import java.io.*;
import java.util.ArrayList;



public class CSVParser {

    private ArrayList<String[]> data = new ArrayList<String[]>();

    private void readFile(String filePath) {
        try {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null){
            data.add(line.split(","));
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
