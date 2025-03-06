package org.example;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    public static void generatePdf(String csvFilePath, String pdfFilePath) throws Exception {
        PdfWriter writer = new PdfWriter(pdfFilePath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        List<CSVRecord> records = CsvProcessor.readCsv(csvFilePath);

        // Generate Pie Chart and Bar Chart
        String pieChartPath = "pie_chart.png";
        String barChartPath = "bar_chart.png";
        ChartGenerator.generatePieChart(records, pieChartPath);
        ChartGenerator.generateBarChart(records, barChartPath);


        document.add(new Paragraph("Sales Report").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER));

        // Create a table with 2 Columns for Side-by-Side Graphs
        Table chartTable = new Table(new float[]{1, 1});
        chartTable.setWidth(UnitValue.createPercentValue(100));

        // Load Images and Scale them
        Image pieChart = new Image(ImageDataFactory.create(pieChartPath)).setAutoScale(false).scaleToFit(250, 250);
        Image barChart = new Image(ImageDataFactory.create(barChartPath)).setAutoScale(false).scaleToFit(250, 250);


        Cell pieCell = new Cell().add(pieChart).setBorder(null).setTextAlignment(TextAlignment.CENTER);
        chartTable.addCell(pieCell);


        Cell barCell = new Cell().add(barChart).setBorder(null).setTextAlignment(TextAlignment.CENTER);
        chartTable.addCell(barCell);


        document.add(chartTable);
        document.add(new Paragraph("\n"));

        // Add Table Data Below Charts
        document.add(new Paragraph("Orders Data").setBold().setFontSize(16));
        Table table = createTable(records, 50);
        document.add(table);

        document.close();
    }

    private static Table createTable(List<CSVRecord> records, int rowCount) {
        Table table = new Table(new float[]{3, 6, 5, 3, 3});
        table.setWidth(UnitValue.createPercentValue(100));

        String[] headers = {"Order ID", "Customer", "Product", "Sales ($)", "Profit ($)"};
        for (String header : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(header))
                    .setBold().setFontSize(12).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .setTextAlignment(TextAlignment.CENTER));
        }

        for (int i = 0; i < Math.min(rowCount, records.size()); i++) {
            CSVRecord record = records.get(i);
            table.addCell(new Cell().add(new Paragraph(record.get("Order ID"))).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(record.get("Customer Name"))).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph(record.get("Product Category"))).setTextAlignment(TextAlignment.LEFT));
            table.addCell(new Cell().add(new Paragraph("$" + record.get("Sales"))).setTextAlignment(TextAlignment.RIGHT));
            table.addCell(new Cell().add(new Paragraph("$" + record.get("Profit"))).setTextAlignment(TextAlignment.RIGHT));
        }

        return table;
    }
}
