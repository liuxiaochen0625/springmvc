package com.apexsoft.system.admin.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apexsoft.core.util.PageUtil;
import com.apexsoft.core.util.RequestHandler;
import com.apexsoft.core.util.SessionNameUtil;
import com.apexsoft.system.admin.entity.AdminMenuAuthority;
import com.apexsoft.system.admin.entity.AdminOperatorAuthority;
import com.apexsoft.system.admin.entity.AdminRole;
import com.apexsoft.system.admin.entity.AdminUser;
import com.apexsoft.system.admin.manager.AdminRoleManager;
import com.apexsoft.system.admin.manager.AdminUserManager;
import com.apexsoft.system.security.MyInvocationSecurityMetadataSource;

@Controller
@RequestMapping("user")
public class AdminUserAction {
	@Autowired
	private AdminUserManager userManager;
	@Autowired
	private AdminRoleManager roleManager;
	
	@RequestMapping(value="/menuModify")
	public @ResponseBody Map<String,Object> menuModify(HttpServletRequest request){
		HttpSession session = request.getSession();
		Integer menuID =  RequestHandler.getInteger(request, "menuID");
		System.out.println("menuID==="+menuID);
		Integer menuLevel = RequestHandler.getInteger(request, "menuLevel");
		Integer parentMenuID = RequestHandler.getInteger(request, "parentMenuID");		
		String menuName = request.getParameter("menuName");
		String menuURL = request.getParameter("menuURL");
		Integer menuOrder = RequestHandler.getInteger(request, "menuOrder");
		Map<String,Object> result = new HashMap<String,Object>();
		AdminMenuAuthority menu = new AdminMenuAuthority();
		menu.setCreateTime(new Date());
		menu.setMenuLevel(menuLevel);
		menu.setMenuName(menuName);
		menu.setMenuOrder(menuOrder);
		menu.setMenuURL(menuURL);
		menu.setOperator((String)session.getAttribute(SessionNameUtil.LOGIN_ADMIN_USER_USERNAME));
	    menu.setParentMenuID(parentMenuID);
	    menu.setUpdateTime(new Date());
		if(menuID!=null){
			if((String.valueOf(menuID).equals(String.valueOf(menu.getParentMenuID())))){
				result.put("flag", false);
				result.put("msg", "修改失败--不能选择自己为父节点！");
			}else{
				menu.setMenuID(menuID);
				roleManager.updateMenuAuthority(menu);
				result.put("flag", true);
				result.put("msg", "修改成功！");
			}
		}else{
			roleManager.addMenuAuthority(menu);
			result.put("flag", true);
			result.put("msg", "添加成功！");
		}
		return result;
	}
	/**
	 * 转向菜单权限添加或修改的权限
	 * @param menuID
	 * @return
	 */
	@RequestMapping(value="/toMenuModify")
	public @ResponseBody ModelAndView toMenuModify(@RequestParam(value = "menuID", required = false, defaultValue = "-1") int menuID){
		ModelAndView model = new ModelAndView("/admin/consult/menu_modify");
		//查询所有的父节点
		model.addObject("menuList", roleManager.getAllMenuList());
		if(menuID != -1){
			model.addObject("menu", roleManager.getMenuAuthByMenuID(menuID));
			model.addObject("menuID", menuID);
		}
		return model;
	}
	/**
	 *菜单对应的按钮权限管理
	 * @return
	 */
	@RequestMapping(value="/menuOpMng")
	public @ResponseBody ModelAndView menuOpMng(@RequestParam(value = "menuID", required = false, defaultValue = "-1") int menuID){
		ModelAndView model = new ModelAndView("/admin/menuOp_list");
		if(menuID!=-1){
			List<AdminOperatorAuthority> opList = roleManager.getMenuOPListByMenuID(menuID);
			model.addObject("opList", opList);
			model.addObject("menuID", menuID);
		}
		return model;
	}
	/**
	 * 菜单权限管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/menuMng")
	public @ResponseBody ModelAndView menuMng(HttpServletRequest request){
		ModelAndView model = new ModelAndView("/admin/menu_list");
		List<AdminMenuAuthority> menuList= roleManager.getAllMenuList();
		model.addObject("menuList", menuList);
		return model;
	}
	
	/**
	 * 用户管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/userMng")	
	public @ResponseBody ModelAndView userMng(HttpServletRequest request){
		ModelAndView model = new ModelAndView("/admin/user_list");
		String userName = request.getParameter("userName");
		Map<String, Object> map = new HashMap<String,Object>();
		if(map!=null && !"".equals(map)){
			map.put("userName", userName);
		}
		//开始分页
		int pageNo = PageUtil.getPageNo(request, "pageNo");
		int pageSize = PageUtil.getPageSize(request, "pageSize");
		map.put("pageStart", PageUtil.getFormByPage(pageNo, pageSize));
		map.put("pageEnd", PageUtil.getEndByPage(pageNo, pageSize));
		List<AdminUser> userList = null;
		int totalCount = userManager.getAdminUserCount(map);
		int totalPage=1;
		if(totalCount>0){
			totalPage=PageUtil.getPageCount(totalCount, pageSize);
			userList = userManager.getAdminUserPage(map);						
		}
		model.addObject("totalCount", totalCount);
		model.addObject("totalPage", totalPage);
		model.addObject("pageNo", pageNo);
		model.addObject("pageSize", pageSize);
		model.addObject("userList",userList);
		model.addObject("userName", userName);
		return model;
	}
	
	/**
	 * 用户角色管理
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/userRoleMng")	
	public @ResponseBody ModelAndView userRoleMng(@RequestParam(value = "userID", required = false, defaultValue = "-1") int userID){
		ModelAndView model = new ModelAndView("/admin/userRole_list");
		System.out.println("userID=="+userID);
		if(userID!=-1){
			//查询所有的角色
			List<AdminRole> roleList=roleManager.getAllRoleList();
			//查询用户的所有角色
			List<AdminRole> userRoleList = roleManager.getAllRoleListByUserID(userID);
			if(userRoleList!=null){
				StringBuffer userRoles=new StringBuffer();
				for(AdminRole role:userRoleList){
					if(role!=null){
						userRoles.append(role.getRoleCode()+",");//用户页面反选
					}					
				}
				model.addObject("userRoles", userRoles.toString());
			}
			model.addObject("userID", userID);
			model.addObject("roleList", roleList);
		}
		return model;
	}
	
	/**
	 * 赋予用户角色
	 * @return
	 */
	@RequestMapping(value="/grantUserRole")	
	public @ResponseBody Map<String,Object> grantUserRole(
			@RequestParam(value = "userID", required = false, defaultValue = "-1") int userID,
			@RequestParam(value= "roleIDs",required = false) String roleIDs){
		Map<String,Object> result= new HashMap<String,Object>();
		if(userID!=-1){
			//删除用户已有的角色
			roleManager.delUserRoleByUserID(userID);
			//用户的新增用户的角色
			if(roleIDs!=null && !"".equals(roleIDs)){
				String[] str=roleIDs.split(",");
				for(String id:str){
					if(id!=null && !"".equals(id)){
				       roleManager.addUserRole(userID, Integer.parseInt(id));
					}					
				}
			}
			MyInvocationSecurityMetadataSource.setResourceMap();
			result.put("flag", true);
		    result.put("msg", "授予成功！");
		}else{
		    result.put("flag", false);
		    result.put("msg", "参数异常--用户ID 为空！");
		}
		
		return result;
	}
	
	/**
	 * 角色管理
	 * @return
	 */
	@RequestMapping(value="/roleMng")
	public @ResponseBody ModelAndView roleMng(){
		ModelAndView model = new ModelAndView("/admin/role_list");
		List<AdminRole> roleList = roleManager.getAllRoleList();
		model.addObject("roleList", roleList);
		return model;
	}
	
	/**
	 * 角色菜单权限管理
	 * @param roleID
	 * @return
	 */
	@RequestMapping(value="/roleMenuMng")
	public @ResponseBody ModelAndView roleMenuMng(@RequestParam(value = "roleID", required = false, defaultValue = "-1") int roleID){
		ModelAndView model = new ModelAndView("/admin/roleMenu_list");
		List<AdminMenuAuthority> menuList = roleManager.getAllMenuList();
		if(roleID!=-1){
			List<AdminMenuAuthority> roleMenuList = roleManager.getRoleMenuListByRoleID(roleID);
			model.addObject("roleMenuList", roleMenuList);
			model.addObject("roleID", roleID);
		}
		model.addObject("menuList", menuList);
		return model;		
	}
	
	/**
	 * 角色菜单权限对应的按钮权限管理
	 * @return
	 */
	@RequestMapping(value="/roleMenuOpMng")
	public @ResponseBody ModelAndView roleMenuOpMng(
			@RequestParam(value = "roleID", required = false, defaultValue = "-1") int roleID,
			@RequestParam(value = "menuID", required = false, defaultValue = "-1") int menuID){
		ModelAndView model = new ModelAndView("/admin/roleMenuOp_list");
		//查询菜单对应的按钮按钮权限
		if(menuID!=-1){
			List<AdminOperatorAuthority> opList = roleManager.getMenuOPListByMenuID(menuID);
			if(roleID!=-1){
				List<AdminOperatorAuthority> roleOpList = roleManager.getRoleMenuOpByRoleIDAndMenuID(roleID, menuID);
				model.addObject("roleOpList", roleOpList);
				model.addObject("roleID", roleID);
			}
			model.addObject("opList", opList);
			model.addObject("menuID", menuID);
		}
		return model;
	}
	
	/**
	 * 授予角色菜单权限对应的按钮权限
	 * @return
	 */
	@RequestMapping(value="/grantRoleMenuOpAuthority")
	public @ResponseBody Map<String,Object> grantRoleMenuOpAuthority(
			@RequestParam(value = "roleID", required = false, defaultValue = "-1") int roleID,
			@RequestParam(value = "menuID", required = false, defaultValue = "-1") int menuID,
			@RequestParam(value = "operatorIDs", required = false) String operatorIDs){
		Map<String,Object> result= new HashMap<String,Object>();
		if(roleID!=-1 && menuID!=-1){
			roleManager.delRoleMenuOpByRoleIDAndMenuID(roleID, menuID);
			if(operatorIDs!=null && !"".equals(operatorIDs)){
				String[] str = operatorIDs.split(",");
				for(String id:str){
					if(id!=null && !"".equals(id)){
						roleManager.addRoleMenuOp(roleID, menuID, Integer.parseInt(id));
					}
				}
			}
			MyInvocationSecurityMetadataSource.setResourceMap();
			result.put("flag", true);
		    result.put("msg", "授权成功！");
		}else{
			result.put("flag", false);
		    result.put("msg", "参数异常--角色ID或菜单ID 为空！");
		}
		return result;
	}
	
	/**
	 * 授予用户菜单权限
	 * @return
	 */
	@RequestMapping(value="/grantRoleMenuAuthority")
	public @ResponseBody Map<String,Object> grantRoleMenuAuthority(
			@RequestParam(value = "roleID", required = false, defaultValue = "-1") int roleID,
			@RequestParam(value = "menuIDs", required = false) String menuIDs){
		Map<String,Object> result= new HashMap<String,Object>();
		if(roleID!=-1){
			roleManager.delRoleMenuByRoleID(roleID);
			if(menuIDs!="" && !"".equals(menuIDs)){
				String[] str= menuIDs.split(",");
				for(String id:str){
					if(id!=null && !"".equals(id)){
						roleManager.addRoleMenu(roleID, Integer.parseInt(id));
					}
				}
			}
			MyInvocationSecurityMetadataSource.setResourceMap();
			result.put("flag", true);
		    result.put("msg", "授权成功！");
		}else{
			result.put("flag", false);
		    result.put("msg", "参数异常--角色ID为空！");
		}
		return result;
	}
	
}
