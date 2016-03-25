package com.apexsoft.system.security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.apexsoft.core.util.SessionNameUtil;
import com.apexsoft.system.admin.entity.AdminRole;
/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 
 * 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * function 
 * @author zlzhang
 * 2013-12-12下午02:43:58
 */

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	/**
	 * 构造器取得所有资源及其对应角色的定义。
	 */
	public MyInvocationSecurityMetadataSource(){	
		logger.info("初始化取到所有资源及其对应角色的定义。");
		loadResourceDefine();
	}
	/**
	 * 取得所有的资源和权限
	 */
	public static void loadResourceDefine(){
		//应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<AdminRole> adminRoleList = getAllAdminUserAuthority();
		//重新组装数据
		if(adminRoleList!=null){
			for(AdminRole role:adminRoleList){
				String url = role.getRoleOperatorURL();
				if(url!=null && !"".equals(url)){
					if(resourceMap.containsKey(url)){//如果包含
						Collection<ConfigAttribute> value = resourceMap.get(url);
						value.add(new SecurityConfig(role.getRoleCode()));
						resourceMap.put(url, value);
					}else{
						Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
						atts.add(new SecurityConfig(role.getRoleCode()));
						resourceMap.put(url, atts);
					}
				}
			}
		}
	}
	
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {		
		return null;
	}
    
	/**
	 * 根据请求的地址，找到访问它的权限集合
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object arg0)
			throws IllegalArgumentException {
		//判断是否是超级用户		
		HttpServletRequest request = ((FilterInvocation) arg0).getHttpRequest();
		Boolean isSuper = (Boolean)request.getSession().getAttribute(SessionNameUtil.LOGIN_ADMIN_ISSUPER);
		if(isSuper!=null){
			if(isSuper){//是超级用户的话,用户任何权限
				//System.out.println("超级用户，拥有任何权限");
				return null;
			}
		}
		String url = ((FilterInvocation) arg0).getRequestUrl();
		//去除参数
		//logger.info("原请求匹配地址是》》》》"+url);
		int firstQuestionMarkIndex = url.indexOf("?");
		if (firstQuestionMarkIndex != -1){
			url = url.substring(0, firstQuestionMarkIndex);			
		}
		//logger.info("去掉参数后请求匹配地址是》》》》"+url);
		//去除.action  即把XXX.action 改为XXX
		String[] urlStr = url.split("\\.");
		if(urlStr!=null && urlStr.length>0){
			url=urlStr[0];
		}
		//l/ogger.info("去掉.XXXX(.do或.action)后请求匹配地址是》》》》"+url);
		Iterator<String> ite = resourceMap.keySet().iterator();
		String tmpURL="";
		while (ite.hasNext()) {
			String resURL = ite.next();
			tmpURL="/"+resURL;//防止拦截地址少写前边的/
			//logger.info("受限地址为：》》》》》"+resURL);
			if (tmpURL.contains(url)) {//利用包含判断是否拦截
				//logger.info("找到请求地址的所需权限，权限为："+resourceMap.get(resURL).toString());				
				return resourceMap.get(resURL);
			}
		}
		//logger.info("请求改地址不需要权限：即登录用户都可访问");
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
	/**
	 * 初始化取到所有资源及其对应角色的定义。
	 * @return
	 */
	private static List<AdminRole> getAllAdminUserAuthority(){
		// 执行SQL  
		Connection con = null;   
		Statement stmt = null;  
		ResultSet rs = null; 
		List<AdminRole> adminRoleList= new ArrayList<AdminRole>();
		AdminRole role=null;
		    ApplicationContext ctx = new FileSystemXmlApplicationContext("classpath:/applicationContext-dao.xml"); 
			//ApplicationContext ctx = MyApplicationContextAware.getApplicationContext();
			DataSource dataSource = (DataSource)ctx.getBean("dataSource");
			try {
				con = dataSource.getConnection();
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); 
				rs = stmt.executeQuery("select a.roleCode, c.menuURL as roleOperatorURL"+
				                       " from admin_role a"+ 
				                       " left join admin_role_menu_authority b  on a.roleID=b.roleID"+
				                       " left join admin_menu_authority c on b.menuID=c.menuID"+ 
				                       " where c.menuLevel=2"+
				                       " union"+
				                       " select aa.roleCode, cc.operatorURL as roleOperatorURL"+
				                       " from admin_role aa"+
				                       " left join admin_role_operator_authority bb on aa.roleID=bb.roleID"+
				                       " left join admin_operator_authority cc on bb.operatorID=cc.operatorID");
				while(rs.next()){
					role = new AdminRole();
					role.setRoleCode(rs.getString("roleCode"));
					role.setRoleOperatorURL(rs.getString("roleOperatorURL"));
					adminRoleList.add(role);
					//System.out.println(rs.getString("roleCode")+rs.getString("roleOperatorURL"));
				}
			} catch (SQLException e) {
				System.out.println("执行sql异常");
				e.printStackTrace();
			} finally{
				con=null;
				stmt = null;  
				rs = null; 
			}
			
		return adminRoleList;
	}
	
	
	public static void setResourceMap() {
		loadResourceDefine();
	}			
	
}
