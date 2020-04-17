package com.bjtu.controller;


import com.bjtu.model.ResponseData;
import com.bjtu.service.FaceRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("face")
public class FaceController {

    @Autowired
    FaceRegService faceRegService;

    /**
     * 人脸认证（在设备对应的用户组下查找认证）
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/reg")
    @ResponseBody
    public ResponseData faceReg() {
        return faceRegService.start();
    }

    /**
     * 给某个用户添加人脸，也可用于新增用户
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/add")
    @ResponseBody
    public ResponseData addFace(@RequestParam String userId) {
        return faceRegService.addFace(userId);
    }

    /**
     * 给某个用户更新人脸（注：会造成该用户下之前的人脸数据全部删除）
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/update")
    @ResponseBody
    public ResponseData updateFace(@RequestParam String userId) {
        return faceRegService.updateFace(userId);
    }

    /**
     * 在设备对应的用户组下删除某个用户
     * @param userId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/delete")
    @ResponseBody
    public ResponseData deleteUser(@RequestParam String userId) {
        return faceRegService.deleteUser(userId);
    }

    /**
     * 获取设备对应的用户组下的所有用户数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/list")
    @ResponseBody
    public ResponseData getUserList() {
        return faceRegService.getUserList();
    }

    /**
     * 删除设备对应的用户组
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/deleteUserGroup")
    @ResponseBody
    public ResponseData deleteUserGroup() {
        return faceRegService.deleteUserGroup();
    }


}
