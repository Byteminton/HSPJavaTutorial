package com.chapter25_jdbc;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

/**
 * 解决SQL注入问题
 */
@SuppressWarnings({"all"})
public class PreparedStatementDemo {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入id:");
        String adminUser = sc.nextLine();
        System.out.print("请输入密码:");
        String adminPassword = sc.nextLine();
        // 万能id = 1' or, 万能密码 = '1' = '1
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/mysql.properties"));
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);

        String sql = "select id, password from admin where id = ? and password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, adminUser);// 给?赋值
        preparedStatement.setString(2, adminPassword);
        // 括号里不要再添sql
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            System.out.println("登陆成功");
        } else {
            System.out.println("登陆失败");
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
