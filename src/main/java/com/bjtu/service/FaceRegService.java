package com.bjtu.service;

import com.baidu.aip.face.AipFace;
import com.bjtu.ApplicationEntryPoint;
import com.bjtu.controller.VerifyController;
import com.bjtu.model.ResponseData;
import com.bjtu.utils.ImageUtil;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FaceRegService {

    private final static Logger log = LoggerFactory.getLogger(FaceRegService.class);

    private String groupIdOfDevice = ApplicationEntryPoint.uuid;

    private AipFace aipFaceClient = AipFaceClient.getAipFace();

    private static OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
    private static boolean isUseCamera = false;

    @Autowired
    VerifyController verifyController;


    public ResponseData start() {
        try {
            while (true) {
                if (isUseCamera) {
                    Thread.sleep(5000);
                    continue;
                } else {
                    isUseCamera = true;
                }
                grabber.start();   //开始获取摄像头数据
                Frame frame = grabber.grab();
                grabber.stop();
                isUseCamera = false;
                // 传入可选参数调用接口
                HashMap<String, String> options = new HashMap<String, String>();
                //"取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串"
                String image = ImageUtil.toBase64(frame);
                String imageType = "BASE64";
                String groupIdList = groupIdOfDevice;

                // 人脸搜索
                JSONObject res = aipFaceClient.search(image, imageType, groupIdList, options);
                log.info(res.toString());
                if (res.has("error_msg") && "SUCCESS".equals(res.get("error_msg").toString())) {
                    JSONArray userList = res.getJSONObject("result").getJSONArray("user_list");
                    if (userList.length() > 0 && userList.getJSONObject(0).getDouble("score") > 70) {
                        Map<String, String> resu = verifyController.start();
                        log.info(resu.toString());
                    }
                }
                Thread.sleep(5000);//500毫秒刷新一次图像
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (grabber != null) {
                try {
                    grabber.stop();//停止抓取
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseData.fail("人脸认证失败！");
    }

   /* public ResponseData faceReg() {
        //OpenCVFrameGrabber grabber = null;
        try {
            //grabber = new OpenCVFrameGrabber(0);
            if (isUseCamera) {
                Thread.sleep(1000);
            }
            if (isUseCamera) {
                return ResponseData.fail("人脸认证失败！");
            }
            grabber.start();   //开始获取摄像头数据
            int t = 0;
            while (true) {
                Frame frame = grabber.grab();
                // 传入可选参数调用接口
                HashMap<String, String> options = new HashMap<String, String>();

                //"取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串"
                String image = ImageUtil.toBase64(frame);
                String imageType = "BASE64";
                String groupIdList = groupIdOfDevice;

                // 人脸搜索
                JSONObject res = aipFaceClient.search(image, imageType, groupIdList, options);
                log.info(res.toString());
                if (res.has("error_msg") && "SUCCESS".equals(res.get("error_msg").toString())) {
                    JSONArray userList = res.getJSONObject("result").getJSONArray("user_list");
                    if (userList.length() > 0 && userList.getJSONObject(0).getDouble("score") > 70)
                        return ResponseData.success("人脸认证成功！");
                }
                Thread.sleep(500);//500毫秒刷新一次图像
                t++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.fail("人脸认证失败！");
    }*/

    public ResponseData addFace(String userId) {
        if (userId == null || userId.trim().equals(""))
            return ResponseData.fail("用户id为空！");
        //OpenCVFrameGrabber grabber = null;
        try {
            if (isUseCamera) {
                Thread.sleep(1000);
            }
            if (isUseCamera) {
                ResponseData.fail("用户添加失败！");
            } else {
                isUseCamera = true;
            }
            grabber.start();   //开始获取摄像头数据
            Frame frame = grabber.grab();
            grabber.stop();
            isUseCamera = false;

            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            String image = ImageUtil.toBase64(frame);
            String imageType = "BASE64";
            // 人脸更新
            JSONObject res = aipFaceClient.addUser(image, imageType, groupIdOfDevice, userId, options);
            log.info(res.toString());
            if (res.has("error_msg") && "SUCCESS".equals(res.get("error_msg").toString())) {
                return ResponseData.success("用户添加成功！");
            } else {
                return ResponseData.fail("用户添加失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.fail("用户添加失败！");
    }

    public ResponseData updateFace(String userId) {
        if (userId == null || userId.trim().equals(""))
            return ResponseData.fail("用户id为空！");
        try {
            if (isUseCamera) {
                Thread.sleep(1000);
            }
            if (isUseCamera) {
                ResponseData.fail("更新人脸失败！");
            } else {
                isUseCamera = true;
            }
            grabber.start();   //开始获取摄像头数据
            Frame frame = grabber.grab();
            grabber.stop();
            isUseCamera = false;

            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();

            String image = ImageUtil.toBase64(frame);
            String imageType = "BASE64";
            // 人脸更新
            JSONObject res = aipFaceClient.addUser(image, imageType, groupIdOfDevice, userId, options);
            log.info(res.toString());
            if (res.has("error_msg") && "SUCCESS".equals(res.get("error_msg").toString())) {
                return ResponseData.success("用户人脸更新成功！");
            } else {
                return ResponseData.fail("用户人脸更新失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.fail("用户人脸更新失败！");
    }

    public ResponseData deleteUser(String userId) {
        if (userId == null || userId.trim().equals(""))
            return ResponseData.fail("用户id为空！");
        try {
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            // 删除用户
            JSONObject res = aipFaceClient.deleteUser(groupIdOfDevice, userId, options);
            log.info(res.toString());
            if (res.has("error_msg") && "SUCCESS".equals(res.get("error_msg").toString())) {
                return ResponseData.success("用户删除成功！");
            } else {
                return ResponseData.fail("用户删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.fail("用户删除失败！");
    }

    public ResponseData getUserList() {
        try {
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            //options.put("start", "0");
            //options.put("length", "50");
            // 获取用户列表
            JSONObject res = aipFaceClient.getGroupUsers(groupIdOfDevice, options);
            log.info(res.toString());
            if (res.has("error_msg") && "SUCCESS".equals(res.get("error_msg").toString())) {
                return ResponseData.success("用户列表获取成功!", res.getJSONObject("result").getJSONArray("user_id_list").toList());
            } else {
                return ResponseData.fail("用户列表获取失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.fail("用户列表获取失败！");
    }

    public ResponseData deleteUserGroup() {
        try {
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();

            // 删除用户组
            JSONObject res = aipFaceClient.groupDelete(groupIdOfDevice, options);
            log.info(res.toString());
            if (res.has("error_msg") && "SUCCESS".equals(res.get("error_msg").toString())) {
                return ResponseData.success("用户组删除成功！");
            } else {
                return ResponseData.fail("用户组删除失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.fail("用户组删除失败！");
    }
}
