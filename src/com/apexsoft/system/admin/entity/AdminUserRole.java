package com.apexsoft.system.admin.entity;

import java.io.Serializable;

/**
 * 用户角色表
 * function 
 * @author zlzhang
 * 2013-12-19上午09:37:01
 */
public class AdminUserRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userRoleID;
	private int userID;       //用户ID
	private int roleID;       //角色ID
	
	
	public int getUserRoleID() {
		return userRoleID;
	}
	public void setUserRoleID(int userRoleID) {
		this.userRoleID = userRoleID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
	

}
