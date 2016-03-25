package com.apexsoft.system.admin.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 角色表
 * function 
 * @author zlzhang
 * 2013-12-19上午09:32:52
 */
public class AdminRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int roleID;         //角色ID
	private String roleCode;    //角色编码：以ROLE_开头，不可重复，大写
    private String roleName;    //角色名称
    private String operator;    //创建者
    private Date createTime;    //创建时间
    
    //----以下字段不存数据库仅用于页面展示
    private String roleOperatorURL;  //拦截地址
    
    
	public int getRoleID() {
		return roleID;
	}
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRoleOperatorURL() {
		return roleOperatorURL;
	}
	public void setRoleOperatorURL(String roleOperatorURL) {
		this.roleOperatorURL = roleOperatorURL;
	} 
	
}
