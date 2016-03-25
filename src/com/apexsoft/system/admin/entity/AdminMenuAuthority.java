package com.apexsoft.system.admin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单权限
 * function 
 * @author zlzhang
 * 2013-12-19上午09:39:52
 */
@SuppressWarnings("rawtypes")
public class AdminMenuAuthority implements Serializable,Comparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int menuID;
	private String menuName;   //菜单名称        
	private String menuURL;    //菜单地址
	private Integer menuLevel;     //显示的树状层次：顶层为1
	private Integer parentMenuID;  //父亲菜单ID:-1:无父节点
	private Integer menuOrder;     //显示排序 数值越高，显示越靠前
	private String operator;   //创建者
	private Date updateTime;
	private Date createTime;
	
	//父节点含有的子节点
	List<AdminMenuAuthority> childMenuList;
	
	
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuURL() {
		return menuURL;
	}
	public void setMenuURL(String menuURL) {
		this.menuURL = menuURL;
	}
	public int getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}
	public Integer getParentMenuID() {
		return parentMenuID;
	}
	public void setParentMenuID(Integer parentMenuID) {
		this.parentMenuID = parentMenuID;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}		
	public List<AdminMenuAuthority> getChildMenuList() {
		return childMenuList;
	}
	public void setChildMenuList(List<AdminMenuAuthority> childMenuList) {
		this.childMenuList = childMenuList;
	}
	//排序时的依据--咱们采用倒叙排列
	@Override
	public int compareTo(Object o) {
		if(this.menuOrder>((AdminMenuAuthority)o).getMenuOrder()){
			return -1;
		}else{
			return 1;
		}
	}		
}
