package com.et;

import cn.smallbun.screw.core.query.DatabaseQuery;
import cn.smallbun.screw.core.query.mysql.MySqlDataBaseQuery;
import cn.smallbun.screw.core.query.mysql.model.MySqlColumnModel;
import cn.smallbun.screw.core.query.mysql.model.MySqlTableModel;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.poi.xwpf.usermodel.*;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseComparison {
    public static void main(String[] args) {
        // Configure database connection information
        HikariConfig hikariConfig1 = new HikariConfig();
        hikariConfig1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig1.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/database1");
        hikariConfig1.setUsername("root");
        hikariConfig1.setPassword("123456");
        hikariConfig1.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig1.setMinimumIdle(2);
        hikariConfig1.setMaximumPoolSize(5);
        DataSource dataSource1 = new HikariDataSource(hikariConfig1);
        DatabaseQuery query1 = new MySqlDataBaseQuery(dataSource1);

        HikariConfig hikariConfig2 = new HikariConfig();
        hikariConfig2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig2.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/database2");
        hikariConfig2.setUsername("root");
        hikariConfig2.setPassword("123456");
        hikariConfig2.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig2.setMinimumIdle(2);
        hikariConfig2.setMaximumPoolSize(5);
        DataSource dataSource2 = new HikariDataSource(hikariConfig2);
        DatabaseQuery query2 = new MySqlDataBaseQuery(dataSource2);

        try {
            // Retrieve table structure
            List<MySqlTableModel> tableInfos1 = (List<MySqlTableModel>) query1.getTables();
            List<MySqlTableModel> tableInfos2 = (List<MySqlTableModel>) query2.getTables();

            // Create Word document
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.createRun().setText("Database Table and Field Comparison");
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);

            // Create table for database comparison
            XWPFTable table = document.createTable();
            XWPFTableRow headerRow = table.getRow(0);
            headerRow.getCell(0).setText("Table Name");
            headerRow.addNewTableCell().setText("Exists in Database 1");
            headerRow.addNewTableCell().setText("Exists in Database 2");

            // Compare tables
            Map<String, MySqlTableModel> tableMap1 = new HashMap<>();
            for (MySqlTableModel tableInfo : tableInfos1) {
                tableMap1.put(tableInfo.getTableName(), tableInfo);
            }

            Map<String, MySqlTableModel> tableMap2 = new HashMap<>();
            for (MySqlTableModel tableInfo : tableInfos2) {
                tableMap2.put(tableInfo.getTableName(), tableInfo);
            }

            // Record table differences
            for (String tableName : tableMap1.keySet()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(tableName);
                row.getCell(1).setText("Yes");
                row.getCell(2).setText(tableMap2.containsKey(tableName) ? "Yes" : "No");
            }

            for (String tableName : tableMap2.keySet()) {
                if (!tableMap1.containsKey(tableName)) {
                    XWPFTableRow row = table.createRow();
                    row.getCell(0).setText(tableName);
                    row.getCell(1).setText("No");
                    row.getCell(2).setText("Yes");
                }
            }

            // Add table differences title
            document.createParagraph().createRun().setText("\nTable Differences:");
            for (String tableName : tableMap1.keySet()) {
                if (!tableMap2.containsKey(tableName)) {
                    document.createParagraph().createRun().setText("Table " + tableName + " does not exist in Database 2");
                } else {
                    compareColumns(document, tableMap1.get(tableName), tableMap2.get(tableName), query1, query2);
                }
            }

            for (String tableName : tableMap2.keySet()) {
                if (!tableMap1.containsKey(tableName)) {
                    document.createParagraph().createRun().setText("Table " + tableName + " does not exist in Database 1");
                }
            }

            // Save Word document
            try (FileOutputStream out = new FileOutputStream("D://tmp/Database_Comparison.docx")) {
                document.write(out);
            }

            System.out.println("Database table and field differences have been generated in the Word document.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void compareColumns(XWPFDocument document, MySqlTableModel mySqlTableModel1, MySqlTableModel mySqlTableModel2, DatabaseQuery query1, DatabaseQuery query2) {
        // Retrieve column information for both tables
        List<MySqlColumnModel> columnNames1 = (List<MySqlColumnModel>) query1.getTableColumns(mySqlTableModel1.getTableName());
        List<MySqlColumnModel> columnNames2 = (List<MySqlColumnModel>) query2.getTableColumns(mySqlTableModel2.getTableName());

        // Create mappings from column names to column models
        Map<String, MySqlColumnModel> columnsMap1 = new HashMap<>();
        for (MySqlColumnModel column : columnNames1) {
            columnsMap1.put(column.getColumnName(), column);
        }

        Map<String, MySqlColumnModel> columnsMap2 = new HashMap<>();
        for (MySqlColumnModel column : columnNames2) {
            columnsMap2.put(column.getColumnName(), column);
        }

        // Create column difference table
        XWPFTable columnTable = document.createTable();
        XWPFTableRow columnHeaderRow = columnTable.getRow(0);
        columnHeaderRow.getCell(0).setText("Column Name");
        columnHeaderRow.addNewTableCell().setText("Exists in Database 1");
        columnHeaderRow.addNewTableCell().setText("Exists in Database 2");

        // Compare columns
        for (String columnName : columnsMap1.keySet()) {
            XWPFTableRow row = columnTable.createRow();
            row.getCell(0).setText(columnName);
            row.getCell(1).setText("Yes");
            row.getCell(2).setText(columnsMap2.containsKey(columnName) ? "Yes" : "No");

            if (!columnsMap2.containsKey(columnName)) {
                document.createParagraph().createRun().setText("Column " + columnName + " does not exist in table " + mySqlTableModel2.getTableName());
            } else {
                // Compare column types and other properties
                MySqlColumnModel column1 = columnsMap1.get(columnName);
                MySqlColumnModel column2 = columnsMap2.get(columnName);
                // Compare column types
                if (!column1.getDataType().equals(column2.getDataType())) {
                    document.createParagraph().createRun().setText("Column " + columnName + " in table " + mySqlTableModel1.getTableName() + " has type " + column1.getDataType() +
                            ", while in table " + mySqlTableModel2.getTableName() + " it has type " + column2.getDataType());
                }
            }
        }

        // Check reverse differences
        for (String columnName : columnsMap2.keySet()) {
            if (!columnsMap1.containsKey(columnName)) {
                document.createParagraph().createRun().setText("Column " + columnName + " does not exist in table " + mySqlTableModel1.getTableName());
            }
        }
    }
}
