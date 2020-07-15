package com.peace.demo;

import com.alibaba.fastjson.JSONObject;
import com.peace.dao.PushErrorMapper;
import com.peace.dao.TaskMapper;
import com.peace.entity.PushError;
import com.peace.entity.Task;
import com.peace.entity.TaskExample;
import com.peace.entity.TaskMode;
import com.peace.util.HttpsClientUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.xml.crypto.Data;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            PushErrorMapper errorMapper = session.getMapper(PushErrorMapper.class);
            TaskExample exam = new TaskExample();
            List<Task> tasks = new ArrayList<Task>();
            tasks = mapper.selectByExample(exam);
//            tasks.add(mapper.selectByPrimaryKey(1L));
            String token =HttpsClientUtils.testGetToken();
            for(Task task:tasks){
                TaskMode tm = new TaskMode(task);
                System.out.println("start time"+new Date().toString());
                String result =HttpsClientUtils.sendToApp(tm,token);
                System.out.println("end time"+new Date().toString());
                PushError pe = new PushError();
                if(result.contains("errcode")){
                    pe.setError(result);
                }
                pe.setCreateTime(new Date());
                pe.setUserId(task.getId());
                pe.setUserName(tm.getToUsers());
                errorMapper.insert(pe);
                session.commit();
            }

//            tasks.add(mapper.selectByPrimaryKey(1L));
//            tasks.add(mapper.selectByPrimaryKey(2L));
//            List<TaskMode> modes = TaskMode.transBatchMsg(tasks);
//            for(TaskMode mode:modes){
//                String result =HttpsClientUtils.sendToApp(mode);
//                PushError pe = new PushError();
//                if(result.contains("errcode")){
//                    pe.setError(result);
//                }
//                pe.setCreateTime(new Date());
//                pe.setUserName(mode.getToUsers());
//                errorMapper.insert(pe);
//
//            }


            session.close();

        }
    }
}
