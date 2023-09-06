package com.chapter25_jdbc.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class C3P0Demo {
    @Test
    public void test01() throws Exception {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/mysql.properties"));
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        // 给数据源设置相关参数
        comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);

        comboPooledDataSource.setInitialPoolSize(10);
        comboPooledDataSource.setMaxPoolSize(50);
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println("连接成功");
        connection.close();
    }

    @Test
    public void test02() throws SQLException {
        // 利用配置文件来连接
        // 需要将 c3p0-config.xml 存放在src文件夹中，并且修改其中的driver, user, password等参数
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("corgi");
        Connection connection = comboPooledDataSource.getConnection();
        System.out.println("连接成功");
        connection.close();
    }
}
