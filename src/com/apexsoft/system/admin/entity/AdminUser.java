package com.apexsoft.system.admin.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统用户表
 * function 
 * @author zlzhang
 * 2013-12-19上午09:23:36
 */
public class AdminUser implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userID;
	private String userName;   //登录名
	private String password;   //密码
	private String realName;   //真实姓名
	private String phone;      //手机号
	private int flag;          //状态：0 无效 1有效
	private String operator;   //创建者
	private Date lastLoginTime;//最后一次登录时间
	private Date createTime;   //创建时间
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "userID"+this.userID+";userName:"+this.userName+";realName="+this.realName;
	}
	
	

}
