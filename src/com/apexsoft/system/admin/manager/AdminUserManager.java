package com.apexsoft.system.admin.manager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apexsoft.system.admin.dao.AdminUserMapper;
import com.apexsoft.system.admin.entity.AdminUser;

@Service("AdminUserManager")
@Transactional
public class AdminUserManager {
	@Autowired
	private AdminUserMapper adminUserMapper;
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	public AdminUser getLoginUserByUserName(String userName){
		AdminUser user=null;
		if(userName!=null && !"".equals(userName)){
			List<AdminUser> userList = adminUserMapper.getAdminUserListByUserName(userName);
			if(userList!=null){
				if(userList.size()>=1){
					user = userList.get(0);
				}
			}
		}
		return user;
	}
	
	/**
	 * 查询用户总数
	 * @param map
	 * @return
	 */
	public int getAdminUserCount(Map<String,Object> map){
		return adminUserMapper.getAdminUserCount(map);
	}
	/**
	 * 查询用户page
	 * @param map
	 * @return
	 */
	public List<AdminUser> getAdminUserPage(Map<String,Object> map){
		return adminUserMapper.getAdminUserPage(map);
	}

}
