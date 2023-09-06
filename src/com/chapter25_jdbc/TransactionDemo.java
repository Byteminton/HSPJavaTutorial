package com.chapter25_jdbc;

import com.chapter25_jdbc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDemo {
    public static void main(String[] args) {
        Connection connection = null;
        String sql = "update actor set name = ? where id = ?";
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "周润发");
            preparedStatement.setInt(2, 1);
            preparedStatement.executeUpdate();
            int i = 1 / 0; // 创建一个异常
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback(); // 默认状态是回到事务开始状态
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }
}
