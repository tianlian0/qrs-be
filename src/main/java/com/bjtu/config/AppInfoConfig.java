package com.bjtu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//@ConfigurationProperties(prefix = "appinfo")
public class AppInfoConfig {

    private final static Logger log = LoggerFactory.getLogger(AppInfoConfig.class);
    //数据库
    private static String databasePwd;

    //图灵api
    private static String tulingApiKey;

    //百度语音
    private static String baiduVoiceAppId;
    private static String baiduVoiceAppKey;
    private static String baiduVoiceSecretKey;

    //百度语音
    private static String baiduFaceAppId;
    private static String baiduFaceAppKey;
    private static String baiduFaceSecretKey;

    static {
        Map<String, String> settings = new HashMap();
        try {
            Resource resource = new ClassPathResource("config.txt");
            InputStream is = resource.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = null;
            while ((s = br.readLine()) != null) {
                if (s != null && !s.trim().equals("") && s.indexOf("=") != -1) {
                    String[] setting = s.split("=", -1);
                    settings.put(setting[0], setting[1]);
                }
            }
            br.close();
            isr.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("配置出错");
            System.exit(1);
        }
        AppInfoConfig.databasePwd = settings.get("database-pwd");
        AppInfoConfig.tulingApiKey = settings.get("tuling-apiKey");
        AppInfoConfig.baiduVoiceAppId = settings.get("baidu-voice-appId");
        AppInfoConfig.baiduVoiceAppKey = settings.get("baidu-voice-appKey");
        AppInfoConfig.baiduVoiceSecretKey = settings.get("baidu-voice-secretKey");
        AppInfoConfig.baiduFaceAppId = settings.get("baidu-face-appId");
        AppInfoConfig.baiduFaceAppKey = settings.get("baidu-face-appKey");
        AppInfoConfig.baiduFaceSecretKey = settings.get("baidu-face-secretKey");
    }

    public static String getDatabasePwd() {
        return databasePwd;
    }

    public static String getTulingApiKey() {
        return tulingApiKey;
    }

    public static String getBaiduVoiceAppId() {
        return baiduVoiceAppId;
    }

    public static String getBaiduVoiceAppKey() {
        return baiduVoiceAppKey;
    }

    public static String getBaiduVoiceSecretKey() {
        return baiduVoiceSecretKey;
    }

    public static String getBaiduFaceAppId() {
        return baiduFaceAppId;
    }

    public static String getBaiduFaceAppKey() {
        return baiduFaceAppKey;
    }

    public static String getBaiduFaceSecretKey() {
        return baiduFaceSecretKey;
    }

    public void setDatabasePwd(String databasePwd) {
        AppInfoConfig.databasePwd = databasePwd;
    }

    public void setTulingApiKey(String tulingApiKey) {
        AppInfoConfig.tulingApiKey = tulingApiKey;
    }

    public void setBaiduVoiceAppId(String baiduVoiceAppId) {
        AppInfoConfig.baiduVoiceAppId = baiduVoiceAppId;
    }

    public void setBaiduVoiceAppKey(String baiduVoiceAppKey) {
        AppInfoConfig.baiduVoiceAppKey = baiduVoiceAppKey;
    }

    public void setBaiduVoiceSecretKey(String baiduVoiceSecretKey) {
        AppInfoConfig.baiduVoiceSecretKey = baiduVoiceSecretKey;
    }

    public void setBaiduFaceAppId(String baiduFaceAppId) {
        AppInfoConfig.baiduFaceAppId = baiduFaceAppId;
    }

    public void setBaiduFaceAppKey(String baiduFaceAppKey) {
        AppInfoConfig.baiduFaceAppKey = baiduFaceAppKey;
    }

    public void setBaiduFaceSecretKey(String baiduFaceSecretKey) {
        AppInfoConfig.baiduFaceSecretKey = baiduFaceSecretKey;
    }
}
