package com.chapter25_jdbc;

import com.chapter25_jdbc.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Batch {
    @Test
    public void noBatch() {
        Connection connection = null;
        String sql = "insert into admin values(?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            long start = System.currentTimeMillis();
            for (int i = 1; i <= 5000; i++) {
                preparedStatement.setString(1, "tom" + i);
                preparedStatement.setString(2, "123456");
                preparedStatement.executeUpdate();
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }

    @Test
    public void withBatch() {
        Connection connection = null;
        String sql = "insert into admin values(?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            long start = System.currentTimeMillis();
            for (int i = 1; i <= 5000; i++) {
                preparedStatement.setString(1, "tom" + i);
                preparedStatement.setString(2, "123456");
                preparedStatement.addBatch();
                if (i % 1000 == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }
}
