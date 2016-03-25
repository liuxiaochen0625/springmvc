package com.apexsoft.system.admin.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apexsoft.system.admin.dao.AdminRoleMapper;
import com.apexsoft.system.admin.entity.AdminMenuAuthority;
import com.apexsoft.system.admin.entity.AdminOperatorAuthority;
import com.apexsoft.system.admin.entity.AdminRole;

/**
 * 角色权限管理
 * function 
 * @author zlzhang
 * 2013-12-19下午05:42:46
 */
@Service
@Transactional
public class AdminRoleManager {
	@Autowired
	private AdminRoleMapper roleDao;
	
	/**
	 * 根据菜单ＩＤ查询菜单权限详细信息
	 * @param menuID
	 * @return
	 */
	public AdminMenuAuthority getMenuAuthByMenuID(int menuID){
		return roleDao.getMenuAuthByMenuID(menuID);
	}
	/**
	 * 修改菜单权限
	 * @param menuAuth
	 */
	public void updateMenuAuthority(AdminMenuAuthority menuAuth){
		roleDao.updateMenuAuthority(menuAuth);
	}
	/**
	 * 新增菜单权限
	 */
	public void addMenuAuthority(AdminMenuAuthority menuAuth){
		roleDao.addMenuAuthority(menuAuth);
	}
	/**
	 * 查询菜单权限对应的按钮权限
	 * @param menuID
	 * @return
	 */
	public List<AdminOperatorAuthority> getOpListByMenuID(int menuID){
		return roleDao.getOpListByMenuID(menuID);
	}
	/**
	 * 取得所有角色及对应的权限
	 * @return
	 */
	public List<AdminRole> getAllAdminUserAuthority(){
		return roleDao.getAllAdminUserAuthority();
	}
	
	/**
	 * 根据用户ID查询用户的角色
	 * @param userID
	 * @return
	 */
    public List<AdminRole> getUserRolesByUserID(int userID){
		return roleDao.getUserRolesByUserID(userID);
	}
    
    /**
     * 取出菜单权限树
     * @param userID
     * @return
     */
	public List<AdminMenuAuthority> getLoginMenuAuthorityTree(int userID){
    	List<AdminMenuAuthority> menuList = roleDao.getLoginMenuAuthorityList(userID);
    	return this.buildMenuTree(menuList);
    }
	
	/**
	 * 查询所有的菜单列表
	 * @return
	 */
	public List<AdminMenuAuthority> getAllMenuList(){
		List<AdminMenuAuthority> menuList = roleDao.getAllMenuList();
    	return this.buildMenuTree(menuList);
	}
    
    /**
	 * 查询所有角色
	 * @return
	 */
	public List<AdminRole> getAllRoleList(){
		return roleDao.getAllRoleList();
	}
	
	/**
	 * 查询用户的所有角色
	 * @param userID
	 * @return
	 */
	public List<AdminRole> getAllRoleListByUserID(int userID){
		return roleDao.getAllRoleListByUserID(userID);
	}
	/**
	 * 查询用户的角色
	 * @param userID
	 */
	public void delUserRoleByUserID(int userID){
		roleDao.delUserRoleByUserID(userID);
	}
	/**
	 * 新增用户的角色
	 * @param userID
	 * @param roleID
	 */
	public void addUserRole(int userID,int roleID){
		roleDao.addUserRole(userID, roleID);
	}
	/**
	 * 查询角色的菜单权限
	 * @param roleID
	 * @return
	 */
	public List<AdminMenuAuthority> getRoleMenuListByRoleID(int roleID){
		return roleDao.getRoleMenuListByRoleID(roleID);
	}
	/**
	 * 查询菜单对应的按钮权限 
	 * @param menuID
	 * @return
	 */
	public List<AdminOperatorAuthority> getMenuOPListByMenuID(int menuID){
		return roleDao.getMenuOPListByMenuID(menuID);
	}
	
	/**
	 * 查询角色菜单权限上对应的按钮权限
	 * @param roleID
	 * @param menuID
	 * @return
	 */
	public List<AdminOperatorAuthority> getRoleMenuOpByRoleIDAndMenuID(int roleID,int menuID){
		return roleDao.getRoleMenuOpByRoleIDAndMenuID(roleID, menuID);
	}
	
	/**
	 * 删除某个角色对应某个菜单权限上所有的按钮权限
	 * @param roleID
	 * @param menuID
	 */
	public void delRoleMenuOpByRoleIDAndMenuID(int roleID,int menuID){
		roleDao.delRoleMenuOpByRoleIDAndMenuID(roleID, menuID);
	}
	/**
	 * 新增某个角色对应某个菜单上对应的某个按钮权限 
	 * @param roleID
	 * @param menuID
	 * @param operatorID
	 */
	public void addRoleMenuOp(int roleID,int menuID,int operatorID){
		roleDao.addRoleMenuOp(roleID, menuID, operatorID);
	}
	
	/**
	 * 删除某个角色对应的所有菜单权限
	 * @param roleID
	 */
	public void delRoleMenuByRoleID(int roleID){
		roleDao.delRoleMenuByRoleID(roleID);
	}
	/**
	 * 新增角色对应的菜单权限
	 * @param roleID
	 * @param menuID
	 */
	public void addRoleMenu(int roleID,int menuID){
		roleDao.addRoleMenu(roleID, menuID);
	}
	
	/**
	 * 构建菜单权限树
	 * @param menuList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<AdminMenuAuthority> buildMenuTree(List<AdminMenuAuthority> menuList){
		//暂存父节点
    	List<AdminMenuAuthority> menuTree = null;
    	//暂存子节点
    	List<AdminMenuAuthority> childList = null;
    	//返回结果
    	List<AdminMenuAuthority> resultList = null;
    	if(menuList!=null && menuList.size()>0){
    		menuTree = new ArrayList<AdminMenuAuthority>();
    		resultList  = new ArrayList<AdminMenuAuthority>();
    		//第一步取出所有的父节点
    		for(AdminMenuAuthority pmenu:menuList){
    			if(pmenu.getParentMenuID()==-1){//权限树值支持两层
    				menuTree.add(pmenu);
    			}
    		}
    		//对父节点进行排序
    		Collections.sort(menuTree);
    		//再次循环菜单权限列表，取出所有父节点对应的子节点
    		for(AdminMenuAuthority pmenu:menuTree){
    			childList = pmenu.getChildMenuList();
    			for(AdminMenuAuthority cmenu:menuList){   				
    				if(cmenu.getParentMenuID()== pmenu.getMenuID()){ 					
    					if(childList==null){
    						childList = new ArrayList<AdminMenuAuthority>();
    						childList.add(cmenu);
    					}else{
    						childList.add(cmenu);
    					}
    				}    				
    			}
    			if(childList!=null){
    				//对子节点进行排序
        			Collections.sort(childList);
    				pmenu.setChildMenuList(childList);
    			}
    			//放入结果中
				resultList.add(pmenu);
				childList = null;
    		}
    	}  
    	return resultList;
	}
  
}
