package com.apexsoft.system.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.apexsoft.system.admin.entity.AdminUser;

/**
 * 系统用户表
 * function 
 * @author zlzhang
 * 2013-12-19上午11:27:24
 */
public interface AdminUserMapper {
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	public List<AdminUser> getAdminUserListByUserName(@Param("userName") String userName);
	/**
	 * 查询用户总数
	 * @param map
	 * @return
	 */
	public int getAdminUserCount(Map<String,Object> map);
	/**
	 * 查询用户page
	 * @param map
	 * @return
	 */
	public List<AdminUser> getAdminUserPage(Map<String,Object> map);
	
	
	

}
