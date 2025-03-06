package org.example;

import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ChartGenerator {

    public static void generatePieChart(List<CSVRecord> records, String fileName) throws IOException {
        Map<String, Integer> customerOrders = new HashMap<>();
        for (CSVRecord record : records) {
            String customer = record.get("Customer Name");
            customerOrders.put(customer, customerOrders.getOrDefault(customer, 0) + 1);
        }

        LinkedHashMap<String, Integer> sortedCustomers = customerOrders.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(5)
                .collect(LinkedHashMap::new, (m, v) -> m.put(v.getKey(), v.getValue()), Map::putAll);

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : sortedCustomers.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart("Top 5 Customers by Orders", dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        Color[] colors = {Color.RED, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.GREEN};

        int i = 0;
        for (String key : sortedCustomers.keySet()) {
            plot.setSectionPaint(key, colors[i++]);
        }

        ChartUtils.saveChartAsPNG(new File(fileName), chart, 600, 400);
    }

    public static void generateBarChart(List<CSVRecord> records, String fileName) throws IOException {
        Map<String, Integer> categoryOrders = new HashMap<>();
        for (CSVRecord record : records) {
            String category = record.get("Product Category");
            categoryOrders.put(category, categoryOrders.getOrDefault(category, 0) + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : categoryOrders.entrySet()) {
            dataset.addValue(entry.getValue(), "Orders", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart("Orders by Product Category", "Category", "Orders", dataset);
        ChartUtils.saveChartAsPNG(new File(fileName), chart, 600, 400);
    }
}
