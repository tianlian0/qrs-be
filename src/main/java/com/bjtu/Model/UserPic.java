package com.bjtu.Model;

import java.sql.Timestamp;

public class UserPic {
	private String u_id;
	private byte[] u_pic;
	private Timestamp u_createtime;
	
	public Timestamp getU_createtime() {
		return u_createtime;
	}
	public String getU_id() {
		return u_id;
	}
	public byte[] getU_pic() {
		return u_pic;
	}
	public void setU_createtime(Timestamp u_createtime) {
		this.u_createtime = u_createtime;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public void setU_pic(byte[] u_pic) {
		this.u_pic = u_pic;
	}
}
