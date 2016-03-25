package com.apexsoft.system.admin.entity;

import java.io.Serializable;

/**
 * 角色操作（按钮）关系表
 * function 
 * @author zlzhang
 * 2013-12-19下午04:54:17
 */
public class AdminRoleOperatorAuthority implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int roleMenuOpID;
	private int roleID;          //角色ID
	private int menuID;          //菜单ID
	private int operatorID;      //操作ID
	
	public int getRoleMenuOpID() {
		return roleMenuOpID;
	}
	public void setRoleMenuOpID(int roleMenuOpID) {
		this.roleMenuOpID = roleMenuOpID;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}	
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	public int getOperatorID() {
		return operatorID;
	}
	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
	
	

}
