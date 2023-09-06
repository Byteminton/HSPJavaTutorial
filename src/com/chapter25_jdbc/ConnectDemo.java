package com.chapter25_jdbc;

import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectDemo {
    @Test
    public void connect_01() throws SQLException {
        // 注册驱动
        Driver driver = new Driver();
        // localhost 可以是ip地址
        String url = "jdbc:mysql://localhost:3306/db01";
        // 将用户名和密码封装到 properties对象中
        Properties properties = new Properties();
        // user 和 password 两个 key 是规定好的
        properties.setProperty("user", "root");
        properties.setProperty("password", "123456abcd");
        Connection connect = driver.connect(url, properties);

        String sql = "insert into actor values(null, '刘德华', '男', '1970-01-01', '110')";
        Statement statement = connect.createStatement();
        // 对语句进行执行，rows是影响的行数
        int rows = statement.executeUpdate(sql);

        statement.close();
        connect.close();
    }

    @Test
    public void connect_02() throws Exception {
        // 使用反射加载Driver类
        Class<?> cls = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) cls.newInstance();
        // 剩下同上
    }

    @Test
    public void connect_03() throws Exception {
        // 使用DriverManager
        Class<?> cls = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) cls.newInstance();
        String url = "jdbc:mysql://localhost:3306/db01";
        String user = "root";
        String password = "123456abcd";

        DriverManager.registerDriver(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void connect_04() throws Exception {
        // 由于加载类的时候自动进行了 DriverManager.registerDriver(driver), 因此可以省略一些代码
        // 其实加载类也不用写，JDBC4默认加载这个类
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/db01";
        String user = "root";
        String password = "123456abcd";

        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
        connection.close();
    }

    @Test
    public void connect_05() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/mysql.properties"));
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
        connection.close();
    }

}
