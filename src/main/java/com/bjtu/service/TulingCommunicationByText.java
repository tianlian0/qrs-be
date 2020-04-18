package com.bjtu.service;

import com.bjtu.config.AppInfoConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bjtu.ApplicationEntryPoint;

public class TulingCommunicationByText {
    private static final String requestUrl = "http://openapi.tuling123.com/openapi/api/v2";

    public static final String apiKey = AppInfoConfig.getTulingApiKey();
    private static final String userId = ApplicationEntryPoint.uuid; //"600394";
 
    private static final String contentType = "text/plain";
 
    private static final String encoding = getContentType();
 
    public String getResponse(String request){
        JSONObject perception = new JSONObject();
        JSONObject inputText = new JSONObject();
        inputText.put("text", request);
        perception.put("inputText", inputText);
 
        JSONObject userInfo = new JSONObject();
        userInfo.put("apiKey", apiKey);
        userInfo.put("userId", userId);
 
        JSONObject root = new JSONObject();
        root.put("reqType", 0);
        root.put("perception", perception);
        root.put("userInfo", userInfo);
        String params = root.toString();
        // {"userInfo":{"apiKey":"3ec0310e2e4047318e142709c6513a31","userId":"407154"},"reqType":0,"perception":{"inputText":{"text":"你开心吗"}}}
        System.out.println(params);
 
        try {
            String resultString = HttpUtil.postGeneralUrl(requestUrl, contentType, params, encoding);
            // {"emotion":{"robotEmotion":{"a":0,"d":0,"emotionId":0,"p":0},"userEmotion":{"a":0,"d":0,"emotionId":0,"p":0}},"intent":{"actionName":"","code":10004,"intentName":""},"results":[{"groupType":1,"resultType":"text","values":{"text":"有你陪伴才开心"}}]}
            System.out.println(resultString);
 
            JSONObject resultJson = new JSONObject(resultString);
            JSONArray results = resultJson.getJSONArray("results");
            JSONObject values = ((JSONObject)(results.get(0))).getJSONObject("values");
            String text = values.getString("text");
            return text;
        } catch (Exception e) {
            e.printStackTrace();
            return "我不知道怎么回答你";
        }
    }
 
    private static String getContentType(){
        String encoding = "UTF-8";
        if (requestUrl.contains("nlp")) {
            encoding = "GBK";
        }
 
        return encoding;
    }
}