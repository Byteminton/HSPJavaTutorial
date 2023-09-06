package com.chapter25_jdbc.dao.test;


import com.chapter25_jdbc.dao.dao.ActorDAO;
import com.chapter25_jdbc.dao.domain.Actor;
import org.junit.Test;

import java.util.List;

public class TestDAO {
    @Test
    public void testUsage() {
        ActorDAO actorDAO = new ActorDAO();
        // 测试多行数据
        List<Actor> actors = actorDAO.queryReturnMultipleRows("select * from actor where sex = ?",
                Actor.class, "男");
        for (Actor actor : actors) {
            System.out.println(actor);
        }
        // 测试单行数据
        Actor result1 = actorDAO.queryReturnSingleRow("select * from actor where id = ?",
                Actor.class, 1);
        System.out.println(result1);
        // 测试单值数据
        Object o = actorDAO.queryReturnScalar("select name from actor where id = ?", 1);
        System.out.println(o);
        // 测试DML
        int affectedRows = actorDAO.update("update actor set name = '周润发' where id = ?", 1);
        System.out.println(affectedRows > 0? "执行成功": "执行失败");

    }
}
