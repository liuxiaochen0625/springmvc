package com.apexsoft.system.admin.entity;

import java.io.Serializable;

/**
 * 操作权限（按钮权限）
 * function 
 * @author zlzhang
 * 2013-12-19上午09:47:52
 */
public class AdminOperatorAuthority implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int operatorID;
	private int menuID;            //菜单ID
	private String operatorCode;   //操作编码：1：主要用来控制页面的显示与隐藏。2：同一菜单下唯一，不可重复。3：按钮编码即为页面上按钮的ID值。（暂定）
	private String operatorName;
	private String operatorURL;
	
	public int getOperatorID() {
		return operatorID;
	}
	public void setOperatorID(int operatorID) {
		this.operatorID = operatorID;
	}
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorURL() {
		return operatorURL;
	}
	public void setOperatorURL(String operatorURL) {
		this.operatorURL = operatorURL;
	}		
}
