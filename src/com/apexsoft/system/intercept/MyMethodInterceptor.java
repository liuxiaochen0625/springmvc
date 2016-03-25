package com.apexsoft.system.intercept;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 自定义拦截器
 * function 
 * @author zlzhang
 * 2013-12-10下午03:12:40
 */
public class MyMethodInterceptor implements MethodInterceptor{
			

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Object invoke(MethodInvocation arg0) throws Throwable {
		//logger.info(arg0.toString());
		try{
			return arg0.proceed();
		}catch (Exception e) {
			logger.error("发现不可处理的未捕获错误", e);
			return null;
		}
	}

}
