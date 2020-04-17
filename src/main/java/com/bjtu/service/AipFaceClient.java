package com.bjtu.service;

import com.baidu.aip.face.AipFace;
import com.bjtu.config.AppInfoConfig;


public class AipFaceClient {

    private volatile static AipFace aipFaceClient;

    public static AipFace getAipFace() {
        if (aipFaceClient == null) {
            synchronized (AipFaceClient.class) {
                if (aipFaceClient == null) {
                    // 初始化一个AipFace
                    aipFaceClient = new AipFace(AppInfoConfig.getBaiduFaceAppId(), AppInfoConfig.getBaiduFaceAppKey(),
                            AppInfoConfig.getBaiduFaceSecretKey());

                    // 可选：设置网络连接参数
                    aipFaceClient.setConnectionTimeoutInMillis(2000);
                    aipFaceClient.setSocketTimeoutInMillis(60000);

                    // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
                    //aipFaceClient.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
                    //aipFaceClient.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理
                }
            }
        }
        return aipFaceClient;
    }

}
