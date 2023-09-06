package com.chapter25_jdbc;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class ResultSetDemo {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/mysql.properties"));
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        String sql = "select id, name, sex, borndate from actor";
        ResultSet resultSet = statement.executeQuery(sql);
        // 如果connection.close(), resultSet就不能再读取了
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            // 或 int id = resultSet.getInt("id");
            String name = resultSet.getString(2);
            String sex = resultSet.getString(3);
            Date date = resultSet.getDate(4);
            System.out.println(id + "\t" + name + "\t" + sex + "\t" + date);

        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}
