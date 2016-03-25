package com.apexsoft.system.security;

import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * 如果用户请求受保护的HTTP请求，但是他们没有认证， AuthenticationEntryPoint 就会被调用。 
 * 类处理将对应响应发送给用户，这样验证就可以开始了。
 *  Spring Security提供了三个具体实现：
 *     LoginUrlAuthenticationEntryPoint 对应表单认证， 
 *     BasicProcessingFilterEntryPoint 对应HTTP基本认证过程，
 *     CasProcessingFilterEntryPoint 对应JA-SIG中心认证服务（CAS）登录
 * function 
 * @author zlzhang
 * 2013-12-10下午04:25:23
 */
public class MyLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{
	
	public MyLoginUrlAuthenticationEntryPoint(String loginFormUrl){
		super(loginFormUrl);
	}

}
