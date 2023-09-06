package com.chapter26_manhan_tower.dao;

import com.chapter26_manhan_tower.utils.DruidJDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @param <T> T 指定之后操作的类型
 */
public class BasicDAO<T> {
    private QueryRunner qr = new QueryRunner();

    public int update(String sql, Object... parameters) {
        Connection connection = null;
        int affectedRows = 0;
        try {
            connection = DruidJDBCUtils.getConnection();
            affectedRows = qr.update(connection, sql, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DruidJDBCUtils.close(null, null, connection);
        return affectedRows;
    }

    /**
     *
     * @param sql
     * @param clazz 传入一个类的Class对象比如Actor.class
     * @param parameters
     * @return
     * @throws SQLException
     */
    public List<T> queryReturnMultipleRows(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = DruidJDBCUtils.getConnection();
        // 最后的参数与?一一对应
        // T 里必须有一个无参构造器
        try {
            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // resultSet, preparedStatement 都会帮你关闭
            // return 之前一定会执行这句话
            DruidJDBCUtils.close(null, null, connection);
        }
    }

    public T queryReturnSingleRow(String sql, Class<T> clazz, Object... parameters) {
        Connection connection = DruidJDBCUtils.getConnection();
        // 因为知道返回的是一个对象，BeanHandler 这里进行修改
        // 没有结果的话会返回 null
        T t = null;
        try {
            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidJDBCUtils.close(null, null, connection);
        }
    }

    public Object queryReturnScalar(String sql, Object... parameters) {
        Connection connection = DruidJDBCUtils.getConnection();
        // 单值
        // 没有结果的话会返回 null
        Object obj = null;
        try {
            return qr.query(connection, sql, new ScalarHandler(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidJDBCUtils.close(null, null, connection);
        }
    }
}
