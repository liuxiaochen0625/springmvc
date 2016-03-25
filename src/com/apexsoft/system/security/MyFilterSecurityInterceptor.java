package com.apexsoft.system.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;


/**
 * ---过滤用户请求
 * 该过滤器的主要作用就是通过spring著名的IoC生成securityMetadataSource。
 * securityMetadataSource相当于本包中自定义的MyInvocationSecurityMetadataSourceService。
 * 该MyInvocationSecurityMetadataSourceService的作用提从数据库提取权限和资源，装配到HashMap中，
 * 供Spring Security使用，用于权限校验。
 * function 
 * @author zlzhang
 * 2013-12-12上午10:52:04
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 取得所有资源和权限的对应关系
	 */
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	@Override
	public void destroy() {
		//logger.info("销毁中。。。。。。");
	}
    
	/**
	 * 与 servlet 拥有一个 service() 方法（这个方法又调用 doPost() 或者 doGet()）来处理请求一样，
	 * 过滤器拥有单个用于处理请求和响应的方法——doFilter方法。
	 * 这个方法接受三个输入参数：一个 Servlet Request、response 和一个 Filter Chain 对象。
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//logger.info("过滤器拦截请求的资源");
		FilterInvocation fi = new FilterInvocation(request,response,chain);
		//这个类的作用本身很简单，就是把doFilter传进来的request,response和FilterChain对象保存起来，
		//供FilterSecurityInterceptor的处理代码调用
		invoke(fi);
	}
	
	/**
	 * 访问控制（授权）的默认拦截器
	 * @param fi
	 * @throws IOException
	 * @throws ServletException
	 */
	private void invoke(FilterInvocation fi) throws IOException,ServletException{
		//logger.info("访问控制(授权)的默认拦截器");
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//logger.info("初始化中。。。。。。");
		
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
	

}
