package Phase_1;

import java.io.*;
import java.util.ArrayList;

/**
 * This class parsers activity data from a csv file. .......
 */
public class CSVParser {

    /**
     * Data is read linne by line from the pasted file and
     * then each line and is then converted in an array list of
     * string arrays where each string array corresponds to
     * a line of the file.
     * @param filePath The path of the file to be read
     * @return An array list containing the data from the file
     */
    private ArrayList<String[]> readFile(String filePath) {
        ArrayList<String[]> initialData = new ArrayList<String[]>();
        try {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null){
            initialData.add(line.split(","));
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return initialData;
    }

    private ArrayList<ArrayList> changeType(ArrayList<String[]> initialData) {

    }
}
