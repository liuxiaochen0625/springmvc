package com.apexsoft.system.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.apexsoft.system.admin.entity.AdminMenuAuthority;
import com.apexsoft.system.admin.entity.AdminOperatorAuthority;
import com.apexsoft.system.admin.entity.AdminRole;

/**
 * 
 * function 
 * @author zlzhang
 * 2013-12-19下午05:35:45
 */
public interface AdminRoleMapper {
	
	
	/**
	 * 根据菜单ＩＤ查询菜单权限详细信息
	 * @param menuID
	 * @return
	 */
	public AdminMenuAuthority getMenuAuthByMenuID(@Param("menuID") int menuID);
	/**
	 * 修改菜单权限
	 * @param menuAuth
	 */
	public void updateMenuAuthority(AdminMenuAuthority menuAuth);
	/**
	 * 新增菜单权限
	 */
	public void addMenuAuthority(AdminMenuAuthority menuAuth);
	/**
	 * 查询菜单权限对应的按钮权限
	 * @param menuID
	 * @return
	 */
	public List<AdminOperatorAuthority> getOpListByMenuID(@Param("menuID") int menuID);
	
	/**
	 * 取得所有角色及对应的权限
	 * @return
	 */
	public List<AdminRole> getAllAdminUserAuthority();
	/**
	 * 根据用户名查询用户的权限
	 * @param userID
	 * @return
	 */
	public List<AdminRole> getUserRolesByUserID(@Param("userID") int userID);
	/**
	 * 查询登录用户的菜单列表
	 * @param userID
	 * @return
	 */
	public List<AdminMenuAuthority> getLoginMenuAuthorityList(@Param("userID") int userID);
	
	/**
	 * 查询所有的菜单列表
	 * @return
	 */
	public List<AdminMenuAuthority> getAllMenuList();
	/**
	 * 查询所有角色
	 * @return
	 */
	public List<AdminRole> getAllRoleList();
	
	/**
	 * 查询用户的所有角色
	 * @param userID
	 * @return
	 */
	public List<AdminRole> getAllRoleListByUserID(@Param("userID") int userID);
	/**
	 * 查询用户的角色
	 * @param userID
	 */
	public void delUserRoleByUserID(@Param("userID") int userID);
	/**
	 * 新增用户的角色
	 * @param userID
	 * @param roleID
	 */
	public void addUserRole(@Param("userID") int userID,@Param("roleID") int roleID);
	/**
	 * 查询角色的菜单权限
	 * @param roleID
	 * @return
	 */
	public List<AdminMenuAuthority> getRoleMenuListByRoleID(@Param("roleID") int roleID);
	/**
	 * 查询菜单对应的按钮权限 
	 * @param menuID
	 * @return
	 */
	public List<AdminOperatorAuthority> getMenuOPListByMenuID(@Param("menuID") int menuID);
	
	/**
	 * 查询角色菜单权限上对应的按钮权限
	 * @param roleID
	 * @param menuID
	 * @return
	 */
	public List<AdminOperatorAuthority> getRoleMenuOpByRoleIDAndMenuID(
			@Param("roleID") int roleID,@Param("menuID") int menuID);
	
	/**
	 * 删除某个角色对应某个菜单权限上所有的按钮权限
	 * @param roleID
	 * @param menuID
	 */
	public void delRoleMenuOpByRoleIDAndMenuID(
			@Param("roleID") int roleID,@Param("menuID") int menuID);
	/**
	 * 新增某个角色对应某个菜单上对应的某个按钮权限 
	 * @param roleID
	 * @param menuID
	 * @param operatorID
	 */
	public void addRoleMenuOp(
			@Param("roleID") int roleID,@Param("menuID") int menuID,@Param("operatorID") int operatorID);
	
	/**
	 * 删除某个角色对应的所有菜单权限
	 * @param roleID
	 */
	public void delRoleMenuByRoleID(@Param("roleID") int roleID);
	/**
	 * 新增角色对应的菜单权限
	 * @param roleID
	 * @param menuID
	 */
	public void addRoleMenu(@Param("roleID") int roleID,@Param("menuID") int menuID);
}
