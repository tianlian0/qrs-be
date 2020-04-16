package com.bjtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bjtu.ApplicationEntryPoint;
import com.bjtu.Model.UserPic;
import com.bjtu.service.TulingCommunicationByVoice;

@Controller
public class VerifyController {

	//全局数据库链接
	private static Connection conn = null;
	public static String database_pwd = "Jb71X96ql**yFW1s";
	static {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://tongxx.top:3306/qrs?characterEncoding=UTF-8&useSSL=FALSE", "root", database_pwd);
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("设备注册失败，请检查网络连接！");
			System.exit(1);
		}
	}

	
	public static volatile boolean flag = true;

	//默认配置项
	public static String rtime = "3000";
	public static String spd = "5";
	public static String pit = "5";
	public static String vol = "4";
	public static String per = "4";
	private static String username = "admin";
	private static String password = "123";

	//注册设备以及获取配置项
	static {
		String sql1 = "INSERT INTO q_config(c_uuid) VALUES(?)";
		String sql2 = "SELECT c_rtime,c_spd,c_pit,c_vol,c_per FROM q_config where c_uuid=?";
		try (PreparedStatement pst = conn.prepareStatement(sql2);) {
			pst.setString(1, ApplicationEntryPoint.uuid);
			try (ResultSet rs = pst.executeQuery()){
				if (rs.next()) {
					rtime = rs.getString("c_rtime");
					spd = rs.getString("c_spd");
					pit = rs.getString("c_pit");
					vol = rs.getString("c_vol");
					per = rs.getString("c_per");
				}else {
					try (PreparedStatement pst2 = conn.prepareStatement(sql1)) {
						pst2.setString(1, ApplicationEntryPoint.uuid);
						pst2.execute();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("设备注册失败，请检查网络连接！");
			System.exit(1);
		}
	}

	//语音对话
	@RequestMapping(value = "/start")
	@ResponseBody
	public Map<String, String> start() {
		Map<String, String> resMap = new HashMap<String, String>();
		if (!VerifyController.flag) {
			resMap.put("code", "2");
			resMap.put("msg", "对话已经启动");
			return resMap;
		}
		VerifyController.flag = false;
		while (true) {
			TulingCommunicationByVoice test = new TulingCommunicationByVoice();
			test.voiceRecorder.captureAudio();
			try {
				Thread.sleep(Integer.valueOf(rtime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (test.responseMaster() == null) {
				break;
			}
		}
		VerifyController.flag = true;
		resMap.put("code", "0");
		resMap.put("msg", "对话结束");
		return resMap;
	}

	//更新设置
	@RequestMapping(value = "/updateConfig")
	public String updateConf(String rtime, String spd, String pit, String vol, String per, String oldPassword, String newPassword) {
		String sql = "";
		String sql1 = "UPDATE q_config set c_rtime=?, c_spd=?, c_pit=?, c_vol=?, c_per=?, c_password=? where c_uuid=? and c_password=?";
		String sql2 = "UPDATE q_config set c_rtime=?, c_spd=?, c_pit=?, c_vol=?, c_per=? where c_uuid=? and c_password=?";
		if (newPassword != null && !newPassword.equals("")) {
			sql = sql1;
		}else {
			sql = sql2;
		}
		try (PreparedStatement pst = conn.prepareStatement(sql);) {
			pst.setString(1, rtime);
			pst.setString(2, spd);
			pst.setString(3, pit);
			pst.setString(4, vol);
			pst.setString(5, per);
			if (newPassword != null && !newPassword.equals("")) {
				pst.setString(6, newPassword);
				pst.setString(7, ApplicationEntryPoint.uuid);
				pst.setString(8, oldPassword);
			}else {
				pst.setString(6, ApplicationEntryPoint.uuid);
				pst.setString(7, oldPassword);
			}
			int influe = pst.executeUpdate();
			if (influe <= 0) {
				return "configerror.html";
			}
		} catch (SQLException e) {
			return "configerror.html";
		}
		this.rtime = rtime;
		this.spd = spd;
		this.pit = pit;
		this.vol = vol;
		this.per = per;
		password = newPassword;
		return "configsuccess.html";
	}
	
	//获取设置
	@RequestMapping(value = "/getConfig")
	@ResponseBody
	public Map<String, Object> getConf() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Map<String, String> confMap = new HashMap<String, String>();
		resMap.put("code", "0");
		resMap.put("msg", "获取成功");
		confMap.put("rtime", rtime);
		confMap.put("spd", spd);
		confMap.put("pit", pit);
		confMap.put("vol", vol);
		confMap.put("per", per);
		resMap.put("config", confMap);
		return resMap;
	}

//	public static void inputStreamToFile(InputStream ins, File file) {
//		try {
//			OutputStream os = new FileOutputStream(file);
//			int bytesRead = 0;
//			byte[] buffer = new byte[8192];
//			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
//				os.write(buffer, 0, bytesRead);
//			}
//			os.close();
//			ins.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	//添加主人
//	@RequestMapping(value = "/addUserPic")
//	@ResponseBody
//	public Map<String, String> addUser(String picname, MultipartFile file) {
//		//权限判定
//		Map<String, String> resMap = login(username, password);
//		if (resMap.get("code").equals("1")) {
//			return resMap;
//		}
//		
//		//文件读取及文件合法性判定
//		File f = null;
//		if (file != null && file.getSize() > 0) {
//			try (InputStream ins = file.getInputStream();) {
//				f = new File(file.getOriginalFilename());
//				inputStreamToFile(ins, f);
//			} catch (IOException e) {
//				e.printStackTrace();
//				resMap.put("code", "1");
//				resMap.put("msg", "未知错误");
//				return resMap;
//			}
//		}
//		
//		if (f==null || file == null || file.getSize() <= 0) {
//			resMap.put("code", "1");
//			resMap.put("msg", "未知错误");
//			return resMap;
//		}
//
//		byte[] b = new byte[(int) f.length()];
//		try (FileInputStream fis = new FileInputStream(f);) {
//			fis.read(b);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//			resMap.put("code", "1");
//			resMap.put("msg", "未知错误");
//			return resMap;
//		}
//
//		//写入数据库
//		String sql = "insert INTO q_user(u_c_uuid,u_name,u_pic) VALUES(?,?,?)";
//		try (PreparedStatement pst = conn.prepareStatement(sql);) {
//			pst.setString(1, ApplicationEntryPoint.uuid);
//			pst.setString(2, picname);
//			pst.setBytes(3, b);
//			pst.execute();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			resMap.put("code", "1");
//			resMap.put("msg", "未知错误");
//			return resMap;
//		}
//		resMap.put("code", "0");
//		resMap.put("msg", "添加成功");
//		return resMap;
//	}
//	
//	//删除主人
//	@RequestMapping(value = "/deleteUserPic")
//	@ResponseBody
//	public Map<String, String> deleteUserPic(int id) {
//		//权限判定
//		Map<String, String> resMap = login(username, password);
//		if (resMap.get("code").equals("1")) {
//			return resMap;
//		}
//		//删除
//		String sql = "update q_user set u_state = '0' where u_c_uuid = ? and u_id = ?";
//		try (PreparedStatement pst = conn.prepareStatement(sql);) {
//			pst.setString(1, ApplicationEntryPoint.uuid);
//			pst.setString(2, String.valueOf(id));
//			pst.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			resMap.put("code", "1");
//			resMap.put("msg", "未知错误");
//			return resMap;
//		}
//		resMap.put("code", "0");
//		resMap.put("msg", "删除成功");
//		return resMap;
//	}
//	
//	//获取主人列表
//	@RequestMapping(value = "/getUserPic")
//	@ResponseBody
//	public List<UserPic> getUserPic() {
//		//权限判定
//		Map<String, String> resMap = login(username, password);
//		if (resMap.get("code").equals("1")) {
//			return new LinkedList<UserPic>();
//		}
//		//获取列表
//		String sql = "select * from q_user where u_c_uuid = ? and u_state = '1'";
//		try (PreparedStatement pst = conn.prepareStatement(sql);) {
//			pst.setString(1, ApplicationEntryPoint.uuid);
//			ResultSet rs = pst.executeQuery();
//			List<UserPic> res = new LinkedList<UserPic>();
//			while (rs.next()) {
//				UserPic userPic = new UserPic();
//				userPic.setU_id(rs.getString("u_id"));
//				userPic.setU_pic(rs.getBytes("u_pic"));
//				userPic.setU_createtime(rs.getTimestamp("u_createtime"));
//				res.add(userPic);
//			}
//			rs.close();
//			return res;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return new LinkedList<UserPic>();
//	}
	
	//登录
	@RequestMapping(value = "/login")
	public String login(String uname, String pwd) {
		Map<String, String> resMap = new HashMap<String, String>();
		String sql2 = "SELECT c_createtime FROM q_config where c_uuid=? and c_username=? and c_password=?";
		try (PreparedStatement pst = conn.prepareStatement(sql2);) {
			pst.setString(1, ApplicationEntryPoint.uuid);
			pst.setString(2, uname);
			pst.setString(3, pwd);
			try (ResultSet rs = pst.executeQuery()){
				if (rs.next()) {
					username = uname;
					password = pwd;
					return "config.html";
				}else {
					return "loginerror.html";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "loginerror.html";
		}
	}

}
