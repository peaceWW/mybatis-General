package com.peace.util;

import com.alibaba.fastjson.JSONObject;
import com.peace.entity.TaskMode;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @package com.saicmotor.cep.common.tool
 * @author zhaoyun
 * @date 2018年4月19日 下午1:15:00
 * @descriprion:
 * @version 1.0.0.0
 *
 */
public class HttpsClientUtils {

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";

    private static Logger LOGGER      = LoggerFactory.getLogger(HttpsClientUtils.class);

    /** 重定向状态码 */
    public static final int REDIRECT_STATUS_CODE = 302;



    /**
     * HttpClient 调用https接口，表单提交的方式；
     *
     * @param httpsurl
     *            访问的url
     * @param parameter
     *            传入参数
     * @param log
     *            日志记录实体
     * @return ifSuccess =1 调用接口成功；data中返回接口数据
     * @description:
     */
    public static Map<String, String> getHttpsMessgFormSubmit(String httpsurl,Object parameter, Logger log) {
        StringBuffer buff = new StringBuffer();
        buff.append("url:");
        buff.append("请求地址连接"+httpsurl);
//        log.error(httpsurl);

        Map<String, String> restMaps = new HashMap<String, String>();
        restMaps.put("ifSuccess", "1");
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(httpsurl);

        InputStream inputStream = null;
        try {

             RequestEntity se = new StringRequestEntity(JSONObject.toJSON(parameter).toString(), "application/json", "UTF-8");
             postMethod.setRequestEntity(se);
             System.out.println("发送的jsaon字符串"+JSONObject.toJSON(parameter).toString());

            int status = httpClient.executeMethod(postMethod);
            if (200 == status) {
                inputStream = postMethod.getResponseBodyAsStream();
                String restult = IOUtils.toString(inputStream);
                restMaps.put("data", restult);
            } else {
                restMaps.put("ifSuccess", "0");
                log.error("httpClient 调用接口失败" + status);
            }
        } catch (UnsupportedEncodingException e) {
            restMaps.put("ifSuccess", "0");
            log.error("httpClient 调用接口失败", e);
        } catch (HttpException e) {
            restMaps.put("ifSuccess", "0");
            log.error("httpClient 调用接口失败", e);
        } catch (IOException e) {
            restMaps.put("ifSuccess", "0");
            log.error("httpClient 调用接口失败", e);
        } catch (Exception e) {
            restMaps.put("ifSuccess", "0");
            log.error("httpClient 调用接口失败", e);
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != postMethod) {
                    postMethod.releaseConnection();
                }
            } catch (Exception e) {
                log.error("httpClient 调用接口失败", e);
            }
        }
        return restMaps;
    }

    public static Map<String, String> getHttpsMessg(String httpsurl, Logger log) {
        StringBuffer buff = new StringBuffer();
        buff.append("url:");
        buff.append(httpsurl);
//        log.error(httpsurl);
        Map<String, String> restMaps = new HashMap<String, String>();
        restMaps.put("ifSuccess", "1");
        HttpClient httpClient = new HttpClient();
        GetMethod postMethod = new GetMethod(httpsurl);
        InputStream inputStream = null;
        try {
            int status = httpClient.executeMethod(postMethod);
            if (200 == status) {
                inputStream = postMethod.getResponseBodyAsStream();
                String restult = IOUtils.toString(inputStream);
                restMaps.put("data", restult);
            } else {
                restMaps.put("ifSuccess", "0");
                log.error("httpClient 调用接口失败" + status);
            }
        } catch (UnsupportedEncodingException e) {
            restMaps.put("ifSuccess", "0");
            log.error("httpClient 调用接口失败", e);
            restMaps.put("data", e.getMessage());
        } catch (HttpException e) {
            restMaps.put("ifSuccess", "0");
            log.error("httpClient 调用接口失败", e);
            restMaps.put("data", e.getMessage());
        } catch (IOException e) {
            restMaps.put("ifSuccess", "0");
            log.error("httpClient 调用接口失败", e);
            restMaps.put("data", e.getMessage());
        } catch (Exception e) {
            restMaps.put("ifSuccess", "0");
            log.error("httpClient 调用接口失败", e);
            restMaps.put("data", e.getMessage());
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != postMethod) {
                    postMethod.releaseConnection();
                }
            } catch (Exception e) {
                log.error("httpClient 调用接口失败", e);
            }
        }
        return restMaps;
    }

    public static void main(String[] args) {

    }




    public  static void sendToApp(List<TaskMode> modes) {
        String token = testGetToken();
        List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>() ;
        for (TaskMode mode:modes){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("Title",mode.getTitle());
            params.put("Title_en",mode.getTitleEn());
            params.put("Body",mode.getBody());
            params.put("Body_en",mode.getBodyEn());
            params.put("ToUsers",mode.getToUsers());
            params.put("User",mode.getUser());
            params.put("UAID",mode.getuAID());
            params.put("Type",mode.getType());
            paramList.add(params);
        }

        Map<String, String> result = null;
        if(modes.size()>1){
            result = HttpsClientUtils.getHttpsMessgFormSubmit("https://saic-cp-uat.joywok.com/appsapi/todos/batch?app_access_token="+token,paramList,LOGGER);
        }else{
            result = HttpsClientUtils.getHttpsMessgFormSubmit("https://saic-cp-uat.joywok.com/appsapi/todos/add?app_access_token="+token,paramList.get(0),LOGGER);
        }
        System.out.println("发送到手机端结果值"+result);
    }
    public  static String sendToApp(TaskMode mode,String token) {

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("Title",mode.getTitle());
        params.put("Title_en",mode.getTitleEn());
        params.put("Body",mode.getBody());
        params.put("Body_en",mode.getBodyEn());
        params.put("ToUsers",mode.getToUsers());
        params.put("User",mode.getUser());
        params.put("UAID",mode.getuAID());
        params.put("Type",mode.getType());
        Map<String, String> result = HttpsClientUtils.getHttpsMessgFormSubmit("https://saic-cp-uat.joywok.com/appsapi/todos/add?app_access_token="+token,params,LOGGER);
        return result.get("data");
    }

    public  static String testGetToken() {
        Map<String, String> result = HttpsClientUtils.getHttpsMessg("https://saic-cp-uat.joywok.com/appsapi/token/apptoken?corpid=9W8GSWagfjDEjIG4&corpsecret=LO12KX4gjmlIHuei3LUQwC2v2j2OcJRx&appid=0af2119113dee5ec6f0c94c274cdfab3",LOGGER);
        System.out.println(result);
        JSONObject json = JSONObject.parseObject(result.get("data"));
        String count =  (String)json.get("app_access_token");
        System.out.println(count);
        return count;
    }

    private static void testFunction1() {
        HttpClient httpClient = new HttpClient();
        GetMethod method = new GetMethod(
                "http://tpisdk.wellcloud.cc/api/operation/tenant/calls/988118bb-7ddc-40d9-bde6-4c61a696d95e/recordings");
        InputStream inputStream = null;
        try {
            int status = httpClient.executeMethod(method);
            inputStream = method.getResponseBodyAsStream();
            String restult = IOUtils.toString(inputStream);
            System.out.println(restult);
        } catch (HttpException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }










}
