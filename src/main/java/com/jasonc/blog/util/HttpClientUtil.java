package com.jasonc.blog.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.util
 * @ClassName: HttpClientUtil
 * @Author: Jason Chan
 * @Description: 第三方登录工具类
 */
public class HttpClientUtil {
    /**
     * @return
     * @Author Jason Chan
     * @Description url 发送get请求
     * @Param [url]
     **/
    public static String doGet(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        // 发送一个get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 响应200成功，解析响应结果
        if (response.getStatusLine().getStatusCode() == 200) {
            // 获取响应结果
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } else return null;
    }

    /**
     * @return
     * @Author Jason Chan
     * @Description 将url请求响应结果由字符串转换为map
     * @Param [responseStr] 将url请求响应结果
     **/
    public static Map<String, String> getMap(String responseStr) {
        Map<String, String> responseMap = new HashMap<>();
        // 以&来解析字符串,[key=value&key=value] -> {key=value, key=value, key=value...}
        String[] keyAndValues = responseStr.split("\\&");
        for (String keyAndValue : keyAndValues) {
            // 以=来解析字符串,{key, value}
            String[] split = keyAndValue.split("=");
            // 将字符串存入map中
            if (split.length == 1) {
                // 只有key 没有value
                responseMap.put(split[0], null);
            } else {
                responseMap.put(split[0], split[1]);
            }
        }
        return responseMap;
    }
    /**
     * @return
     * @Author Jason Chan
     * @Description 将url请求响应结果由字符串转为json再转换为map
     * @Param [responseStr]
     **/
    public static Map<String, String> getMapByJson(String responseStr) {
        Map<String, String> responseMap = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(responseStr);
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            responseMap.put(key, value);
        }
        return responseMap;
    }
}
