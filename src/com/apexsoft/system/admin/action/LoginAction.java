package com.apexsoft.system.admin.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apexsoft.core.util.SessionNameUtil;
import com.apexsoft.system.admin.entity.AdminMenuAuthority;
import com.apexsoft.system.admin.entity.AdminRole;
import com.apexsoft.system.admin.entity.AdminUser;
import com.apexsoft.system.admin.manager.AdminRoleManager;
import com.apexsoft.system.admin.manager.AdminUserManager;

/**
 * 登录转向
 * function 
 * @author zlzhang
 * 2013-12-19下午01:48:05
 */
@Controller
@RequestMapping("login")
public class LoginAction {
	@Autowired
	private AdminUserManager adminUserManager;
	@Autowired
	private AdminRoleManager adminRoleManager;
		
    /**
     * 转向登录页面
     * @return
     */
	@RequestMapping(value="/toLogin/login")	
	public @ResponseBody ModelAndView toLogin(HttpServletRequest request){
		//从springsecurity中取出已登录的用户名
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");		
		if(securityContextImpl!=null&& !"".equals(securityContextImpl)){
			//String userName = securityContextImpl.getAuthentication().getName();
			ModelAndView  model = new ModelAndView("main");
			getLoginUserDataSource(request,model);
			return model;
		}else{
			return new ModelAndView("login");
		}		
	}
	
	/**
	 * 登录成功后，转向主页面
	 * @return
	 */
	@RequestMapping(value="/loginSuccess/main")	
	public @ResponseBody ModelAndView main(HttpServletRequest request){
		ModelAndView  model = new ModelAndView("main");		
		getLoginUserDataSource(request,model);
		return model;
	}
	
	private void getLoginUserDataSource(HttpServletRequest request,ModelAndView model){
		HttpSession session = request.getSession();
		//从springsecurity中取出已登录的用户名
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		String userName = securityContextImpl.getAuthentication().getName();
		AdminUser adminUser = adminUserManager.getLoginUserByUserName(userName);
		List<AdminMenuAuthority> adminMenuList=null;
		//登录用户的角色
		String roleNames="";
		StringBuffer str = new StringBuffer();
		
		if(adminUser!=null){
			//是否超级用户
			boolean isSuper=false;
			if(1==adminUser.getUserID()){//默认id为1的用户为超级用户
				isSuper=true;
			}
			//取出登录用户的左侧菜单树
			if(isSuper){//如果是超级用户
				adminMenuList= adminRoleManager.getAllMenuList();
			}else{
				adminMenuList= adminRoleManager.getLoginMenuAuthorityTree(adminUser.getUserID());
			}			
			
			//取出登录用户的所有角色
			List<AdminRole> roleList = adminRoleManager.getUserRolesByUserID(adminUser.getUserID());
			if(roleList!=null && roleList.size()>0){
				for(AdminRole role:roleList){
					if(role!=null){
						str.append(role.getRoleName()+",");
					}					
				}
				roleNames = str.toString();
				if(roleNames!=null && !"".equals(roleNames)){
					roleNames = roleNames.substring(0, roleNames.length()-1);
				}				
			}
			//一些信息村放入session中
			session.setAttribute(SessionNameUtil.LOGIN_ADMIN_ISSUPER, isSuper);
			session.setAttribute(SessionNameUtil.LOGIN_ADMIN_USER_ID, adminUser.getUserID());
			session.setAttribute(SessionNameUtil.LOGIN_ADMIN_USER_ROLENAMES, roleNames);
			session.setAttribute(SessionNameUtil.LOGIN_ADMIN_USER_USERNAME, adminUser.getUserName());
		}
		
		model.addObject("adminMenuList", adminMenuList);
		model.addObject("adminUser", adminUser);
		model.addObject("roleNames",roleNames);
	}

}
