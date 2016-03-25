package com.apexsoft.system.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.apexsoft.system.admin.entity.AdminRole;
import com.apexsoft.system.admin.entity.AdminUser;
import com.apexsoft.system.admin.manager.AdminRoleManager;
import com.apexsoft.system.admin.manager.AdminUserManager;

/**
 * 该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 * 该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 * function 
 * @author zlzhang
 * 2013-12-12下午02:17:34
 */
public class MyUserDetailsService implements UserDetailsService{
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
    private AdminUserManager userManager;
	@Autowired
    private AdminRoleManager roleManager;
    
	@Override
	public User loadUserByUsername(String userName)throws UsernameNotFoundException {		
		//logger.info("得到登录的用户名是"+userName);
		Collection<GrantedAuthority> auths = null;	
		//logger.info("得到登陆用户的权限和信息");
		//根据用户名查询真实用户
		AdminUser adminUser = userManager.getLoginUserByUserName(userName);
		boolean enabled=true;
		//如果用户存在则查询其拥有的所有的角色
		if(adminUser!=null){
			auths = new ArrayList<GrantedAuthority>();
			List<AdminRole> roleList = roleManager.getUserRolesByUserID(adminUser.getUserID());			
			if(roleList!=null&& roleList.size()>0){
				//System.out.println("roleList.size()>>>>==="+roleList.size());
				//System.out.println("登录用户有授予的角色");
				for(AdminRole role:roleList){
					if(role!=null){
						String roleCode = role.getRoleCode();
						if(roleCode!=null && !"".equals(roleCode)){
							auths.add(new SimpleGrantedAuthority(roleCode));
						}
					}
					
				}
			}
			//System.out.print("auths.size=="+auths.size());
			if("1".equals(adminUser.getFlag())){
				enabled = false;
			}
		}		 
		return new User(adminUser.getUserName(), adminUser.getPassword(), enabled, true, true, true, auths);
	}

}
