package com.peace.demo;

import com.alibaba.fastjson.JSONObject;
import com.peace.dao.TaskMapper;
import com.peace.entity.Task;
import com.peace.entity.TaskExample;
import com.peace.entity.TaskMode;
import com.peace.util.HttpsClientUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
            TaskMapper mapper = session.getMapper(TaskMapper.class);
            TaskExample exam = new TaskExample();
            List<Task> tasks = new ArrayList<Task>();
//            tasks = mapper.selectByExample(exam);
//            tasks.add(mapper.selectByPrimaryKey(1L));
            tasks.add(mapper.selectByPrimaryKey(2L));
            List<TaskMode> modes = TaskMode.transMsg(tasks);
            HttpsClientUtils.sendToApp(modes);
//            if(modes.size()>1){
//                System.out.println("结果的json"+JSONObject.toJSON(modes).toString());
//
//            }

        }
    }
}
