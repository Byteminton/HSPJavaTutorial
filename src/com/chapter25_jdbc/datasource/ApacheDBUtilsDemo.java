package com.chapter25_jdbc.datasource;

import com.chapter25_jdbc.utils.DruidJDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ApacheDBUtilsDemo {

    //使用apache-DBUtils工具类+Druid完成对表的crud操作
    // 解决无法方便处理ResultSet的问题,将结果储存在一个ArrayList<T>里
    //commons-dbutils-1.3.jar
    @Test
    public void queryReturnMultipleRows() throws SQLException {
        Connection connection = DruidJDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from actor where sex = ?";
        // 最后的参数与?一一对应
        // Actor 里必须有一个无参构造器
        List<Actor> query = queryRunner.query(connection, sql, new BeanListHandler<>(Actor.class), "男");
        for (Actor actor : query) {
            System.out.println(actor);
        }
        // resultSet, preparedStatement 都会帮你关闭
        DruidJDBCUtils.close(null, null, connection);
    }

    @Test
    public void queryReturnSingleRow() throws SQLException {
        Connection connection = DruidJDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from actor where id = ?";
        // 因为知道返回的是一个对象，BeanHandler 这里进行修改
        // 没有结果的话会返回 null
        Actor actor = queryRunner.query(connection, sql, new BeanHandler<>(Actor.class), 1);
        System.out.println(actor);
        connection.close();
    }

    @Test
    public void queryReturnScalar() throws SQLException {
        Connection connection = DruidJDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select name from actor where id = ?";
        // 单行单列
        // 没有结果的话会返回 null
        Object obj = queryRunner.query(connection, sql, new ScalarHandler(), 1);
        System.out.println(obj);
        connection.close();
    }

    @Test
    public void DML() throws SQLException {
        Connection connection = DruidJDBCUtils.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update actor set name = '周星驰' where id = ?";
        // 增删改
        int affectedRows = queryRunner.update(connection, sql, 1);
        System.out.println(affectedRows);
        connection.close();
    }
}
