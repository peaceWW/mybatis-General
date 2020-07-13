package com.peace.demo;

import com.peace.dao.TmUserMapper;
import com.peace.entity.TmUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @ClassName : TestMybatis  //类名
 * @Description : 测试Mybatis  //描述
 * @Author : peaceWW  //作者
 * @Date: 2020-07-13 21:04  //时间
 */
public class TestMybatis {
    public static void main(String[] args) throws Exception{
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TmUserMapper mapper = session.getMapper(TmUserMapper.class);
            TmUser user = mapper.selectByPrimaryKey(1L);
            System.out.println(user);
        }
    }
}
