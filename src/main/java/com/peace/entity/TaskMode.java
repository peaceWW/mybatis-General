package com.peace.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : TaskMode  //类名
 * @Description : 待办任务模型  //描述
 * @Author : peaceWW  //作者
 * @Date: 2020-07-14 08:54  //时间
 */
public class TaskMode {
    private String title;
    private String titleEn;
    private String body;
    private String bodyEn;
    private String toUsers;
    private String user;
    private String uAID;
    private String type;
    private String roleCode;
    private String userName;

    private final static  String RFS_SM=" 这个是角色RFS_SM的消息";
    private final static  String MAC_M="这个是角色MAC_M的消息";
    private final static  String SALES_CONSULTANT="这个是角色SALES_CONSULTANT的消息";
    private final static  String SALES_MANAGER="这个是角色SALES_MANAGER的消息";
    private final static  String RFS_SM_EN="This is the body of RFS_SM_EN";
    private final static  String MAC_M_EN="This is the body of MAC_M_EN";
    private final static  String SALES_CONSULTANT_EN="This is the body of SALES_CONSULTANT_EN";
    private final static  String SALES_MANAGER_EN="This is the body of SALES_MANAGER_EN";

    TaskMode(){

    }

    public TaskMode(String toUsers,String roleCode){
        this.toUsers = toUsers.substring(0,toUsers.length()-1);
        if(roleCode.equalsIgnoreCase("RFS_SM")){
            this.body = TaskMode.RFS_SM;
            this.bodyEn = TaskMode.RFS_SM_EN;
        }else if(roleCode.equalsIgnoreCase("MAC_M")){
            this.body = TaskMode.MAC_M;
            this.bodyEn = TaskMode.MAC_M_EN;
        }else if(roleCode.equalsIgnoreCase("SALES_CONSULTANT")){
            this.body = TaskMode.SALES_CONSULTANT;
            this.bodyEn = TaskMode.SALES_CONSULTANT_EN;
        }else if(roleCode.equalsIgnoreCase("SALES_MANAGER")){
            this.body = TaskMode.SALES_MANAGER;
            this.bodyEn = TaskMode.SALES_MANAGER_EN;
        }
        this.type ="num";
        this.uAID ="001";
        this.title="测试标题";
        this.user ="qeioc";
    }



    public TaskMode(Task task){
        this.toUsers = task.getUserAccount();
        if(task.getRoleCode().equalsIgnoreCase("RFS_SM")){
            this.body = "亲爱的"+task.getUserName()+"，\n" +
                    "您今日有"+task.getTodayNum()+"名客户待跟进，七日内有"+task.getSevenDaysNum()+"名客户待跟进，逾期"+task.getOverdueNum()+"名用户待跟进。请及时进入工作列表完成你的工作。谢谢";
            this.bodyEn = "Dear "+task.getUserName()+",\n" +
                    "Today you have "+task.getTodayNum()+" customers waiting to be followed up, and "+task.getSevenDaysNum()+" customers waiting to be followed up within 7 days, "+task.getOverdueNum()+" overdue customers waiting to be followed up. Please open the work list to complete your job in time. Thanks.";
        }else if(task.getRoleCode().equalsIgnoreCase("MAC_M")){
            this.body = "亲爱的"+task.getUserName()+"，\n" +
                    "您今日有"+task.getTodayNum()+"名客户待跟进，七日内有"+task.getSevenDaysNum()+"名客户待跟进，逾期"+task.getOverdueNum()+"名用户待跟进。请及时进入工作列表完成你的工作。谢谢";
            this.bodyEn = "Dear "+task.getUserName()+",\n" +
                    "Today you have "+task.getTodayNum()+" customers waiting to be followed up, and "+task.getSevenDaysNum()+" customers waiting to be followed up within 7 days, "+task.getOverdueNum()+" overdue customers waiting to be followed up. Please open the work list to complete your job in time. Thanks.";
        }else if(task.getRoleCode().equalsIgnoreCase("SALES_CONSULTANT")){
            this.body = "亲爱的"+task.getUserName()+"，您的团队今日有"+task.getTodayNum()+"名客户待跟进，七日内有"+task.getSevenDaysNum()+"名客户待跟进，逾期"+task.getOverdueNum()+"名用户待跟进。请及时进入‘销售总监’查看员工工作情况。谢谢。";
            this.bodyEn = "Dear "+task.getUserName()+",\n" +
                    "Today your team has "+task.getTodayNum()+" customers waiting to be followed up, and "+task.getSevenDaysNum()+" customers waiting to be followed up within 7 days, "+task.getOverdueNum()+" overdue customers waiting to be followed up. Please click \"Sales Manager\" to check team members' work situation. Thanks.";
        }else if(task.getRoleCode().equalsIgnoreCase("SALES_MANAGER")){
            this.body = "亲爱的"+task.getUserName()+"，您的管理区域BKK今日有"+task.getTodayNum()+"名客户待跟进，七日内有"+task.getSevenDaysNum()+"名客户待跟进，逾期"+task.getOverdueNum()+"名用户待跟进。请安排您的员工完成任务，谢谢。";
            this.bodyEn = "Dear "+task.getUserName()+",\n" +
                    "In your management region BKK there is "+task.getTodayNum()+" customers waiting to be followed up today, and "+task.getSevenDaysNum()+" customers waiting to be followed up within 7 days, "+task.getOverdueNum()+" overdue customers waiting to be followed up. Please arrange team member to work these tasks. Thanks.";
        }
        this.type ="num";
        this.uAID =task.getId().toString();
        this.title="测试标题";
        this.user ="qeioc";
    }

    public static List<TaskMode> transMsg(List<Task> tasks){
        List<TaskMode> taskModes = new ArrayList<TaskMode>();
        Map<String,String> users = new HashMap<String,String>();
        for(Task task:tasks){
            String tousers = users.get(task.getRoleCode());
            if(tousers==null){
                tousers ="";
            }
            tousers+= task.getUserAccount()+",";
            users.put(task.getRoleCode(),tousers);
        }

        for(String roleCode:users.keySet()){
            TaskMode tm =  new TaskMode(users.get(roleCode),roleCode);
            taskModes.add(tm);
        }
        return taskModes;
    }
    public static List<TaskMode> transBatchMsg(List<Task> tasks){
        List<TaskMode> taskModes = new ArrayList<TaskMode>();
        for(Task task:tasks){
            TaskMode tm =  new TaskMode(task);
            taskModes.add(tm);
        }
        return taskModes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyEn() {
        return bodyEn;
    }

    public void setBodyEn(String bodyEn) {
        this.bodyEn = bodyEn;
    }

    public String getToUsers() {
        return toUsers;
    }

    public void setToUsers(String toUsers) {
        this.toUsers = toUsers;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getuAID() {
        return uAID;
    }

    public void setuAID(String uAID) {
        this.uAID = uAID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
