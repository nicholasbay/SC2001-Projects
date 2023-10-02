package project_2;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToCSV {
    public static void writeFile(String filename, String[] headers, String[][] data) throws Exception {
        try {
            FileWriter csvWriter = new FileWriter(filename);

            // Write headers to file
            csvWriter.append(String.join(",", headers));
            csvWriter.append("\n");

            // Write data to file
            int i = 0, j = 0;
            do {
                while (i < data.length) {
                    csvWriter.append(data[i][j]);
                    if (i != data.length - 1) {
                        csvWriter.append(",");
                    }
                    i++;
                }
                csvWriter.append("\n");
                i = 0;
                j++;
            } while (j < data[0].length);
            
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
