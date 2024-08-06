package com.et.impala;

import com.cloudera.impala.jdbc41.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class ImpalaTest {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 加载HANA JDBC驱动程序
            Class.forName("com.cloudera.impala.jdbc41.Driver");
            Properties p = new Properties();
            p.setProperty( "user", "System" );
            p.setProperty( "password", "Hyd20240531" );
            p.setProperty( "latency", "0" );
            p.setProperty( "communicationtimeout", "0" );
            // 指定要连接的数据库名称
            String currentschema = "default";
            // 拼接连接字符串
            String url = "jdbc:impala://172.24.4.35:21050/"+currentschema;
            //DataSource
            DataSource ds = new com.cloudera.impala.jdbc41.DataSource();
            ds.setURL(url);
            connection = ds.getConnection();
            //don't use DataSource
            //connection = DriverManager.getConnection( url, p );

            System.out.println("Schema: " + connection.getSchema());

            // 创建SQL语句
            String sqlQuery = "SELECT * FROM my_first_table";

            // 创建Statement对象
            statement = connection.createStatement();

            // 执行查询
            resultSet = statement.executeQuery(sqlQuery);

            // 处理结果集
            while (resultSet.next()) {
                // 读取结果集中的数据示例
                int column1 = resultSet.getInt("id");
                String column2 = resultSet.getString("name");
                // 打印结果
                System.out.println("id: " + column1 + ", name: " + column2);
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
