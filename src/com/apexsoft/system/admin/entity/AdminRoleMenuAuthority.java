package com.apexsoft.system.admin.entity;

import java.io.Serializable;

/**
 * 角色菜单关系表
 * function 
 * @author zlzhang
 * 2013-12-19上午09:54:08
 */
public class AdminRoleMenuAuthority implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int roleMenuID;
	private int roleID;             //角色ID
	private String menuID;          //菜单权限ID
	
	
	
	public int getRoleMenuID() {
		return roleMenuID;
	}
	public void setRoleMenuID(int roleMenuID) {
		this.roleMenuID = roleMenuID;
	}
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	public String getMenuID() {
		return menuID;
	}
	public void setMenuID(String menuID) {
		this.menuID = menuID;
	}
    
}
