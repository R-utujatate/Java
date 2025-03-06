package org.example;

public class MainApp {
    public static void main(String[] args) {
        String inputCsvFile = "C:\\Users\\Akshay\\Downloads\\Orders-Table 1.csv";
        String outputCsvFile = "Orders-Table-50.csv";
        String pdfFilePath = "Sales_Report.pdf";

        try {
            CsvProcessor.filterFirst50Records(inputCsvFile, outputCsvFile);
            PdfGenerator.generatePdf(outputCsvFile, pdfFilePath);
            System.out.println("PDF generated successfully: " + pdfFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
