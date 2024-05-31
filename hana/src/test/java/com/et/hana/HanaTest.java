package com.et.hana;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class HanaTest {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 加载HANA JDBC驱动程序
            Class.forName("com.sap.db.jdbc.Driver");
            Properties p = new Properties();
            p.setProperty( "user", "System" );
            p.setProperty( "password", "Hyd20240531" );
            p.setProperty( "latency", "0" );
            p.setProperty( "communicationtimeout", "0" );
            // 指定要连接的数据库名称
            String currentschema = "DEMO";
            // 拼接连接字符串
            String url = "jdbc:sap://localhost:39041/?currentschema=" + currentschema;

            connection = DriverManager.getConnection( url, p );
            // 连接到HANA数据库
            //connection = DriverManager.getConnection("jdbc:sap://localhost:39041/?databaseName=SYSTEM&user=System&password=Hyd20240531");
           // connection = DriverManager.getConnection("jdbc:sap://127.0.0.1:39041/?autocommit=false;", "System", "Hyd20240531");
            System.out.println("Schema: " + connection.getSchema());

            // 创建SQL语句
            String sqlQuery = "SELECT * FROM Employees";

            // 创建Statement对象
            statement = connection.createStatement();

            // 执行查询
            resultSet = statement.executeQuery(sqlQuery);

            // 处理结果集
            while (resultSet.next()) {
                // 读取结果集中的数据示例
                int column1 = resultSet.getInt("EmployeeID");
                String column2 = resultSet.getString("FirstName");
                // 打印结果
                System.out.println("EmployeeID: " + column1 + ", FirstName: " + column2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接、Statement和ResultSet
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
