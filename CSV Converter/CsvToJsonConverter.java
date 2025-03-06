import java.io.*;
import java.util.*;

public class CsvToJsonConverter {

    public static void main(String[] args) {
        String csvFilePath = "C:\\Users\\Akshay\\Downloads\\Orders-Table 1.csv";  // Input CSV file
        String jsonFilePath = "a.json";       // Output JSON file

        try {
            List<Map<String, String>> jsonObjects = readCsv(csvFilePath);
            writeJson(jsonObjects, jsonFilePath);
            System.out.println("CSV successfully converted to JSON. Output: " + jsonFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Map<String, String>> readCsv(String filePath) throws IOException {
        List<Map<String, String>> dataList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String headerLine = br.readLine();
        if (headerLine == null) {
            br.close();
            throw new IOException("CSV file is empty.");
        }

        String[] headers = headerLine.split(",");
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Map<String, String> jsonObject = new LinkedHashMap<>();

            for (int i = 0; i < headers.length; i++) {
                jsonObject.put(headers[i].trim(), i < values.length ? values[i].trim() : "");
            }

            dataList.add(jsonObject);
        }
        br.close();
        return dataList;
    }

    private static void writeJson(List<Map<String, String>> data, String filePath) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write("[\n");

        for (int i = 0; i < data.size(); i++) {
            bw.write("  {\n");
            Map<String, String> obj = data.get(i);
            int count = 0;

            for (Map.Entry<String, String> entry : obj.entrySet()) {
                String key = escapeJson(entry.getKey());
                String value = escapeJson(entry.getValue());

                // Check if value is a number (don't put in quotes if it's numeric)
                if (value.matches("-?\\d+(\\.\\d+)?")) {
                    bw.write("    \"" + key + "\": " + value);
                } else {
                    bw.write("    \"" + key + "\": \"" + value + "\"");
                }

                if (++count < obj.size()) {
                    bw.write(",");
                }
                bw.write("\n");
            }

            bw.write("  }");
            if (i < data.size() - 1) {
                bw.write(",");
            }
            bw.write("\n");
        }

        bw.write("]");
        bw.close();
    }

    // Function to escape special JSON characters
    private static String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}
