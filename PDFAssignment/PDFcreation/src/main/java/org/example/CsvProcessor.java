package org.example;

import org.apache.commons.csv.*;

import java.io.*;
import java.util.List;

public class CsvProcessor {
    public static void filterFirst50Records(String inputFile, String outputFile) throws IOException {
        try (
                Reader reader = new FileReader(inputFile);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(false).build());
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withQuoteMode(QuoteMode.ALL))
        ) {
            csvPrinter.printRecord(csvParser.getHeaderNames());
            int count = 0;
            for (CSVRecord record : csvParser) {
                csvPrinter.printRecord(record);
                if (++count >= 50) break;
            }
        }
    }

    public static List<CSVRecord> readCsv(String filePath) throws IOException {
        try (
                Reader reader = new FileReader(filePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build())
        ) {
            return csvParser.getRecords();
        }
    }
}
