package com.apexsoft.system.intercept;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;



/**
 * 自定义无权访问异常处理
 * function 
 * @author zlzhang
 * 2013-12-24上午10:34:43
 */
public class MyAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException ex) throws IOException, ServletException {
		boolean isAjax = isAjaxRequest(request);
        //System.out.println("是否是ajax请求：" + isAjax);
        if(!isAjax){
            request.setAttribute("isAjaxRequest", isAjax);
            request.setAttribute("message", ex.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/common/403.jsp");
//            response.sendRedirect(request.getContextPath()+"/authNotPass.jsp");
            dispatcher.forward(request, response);
        }else{
            response.setContentType("text/text;charset=UTF-8");
            PrintWriter out = response.getWriter();
            //Map<String,Object> map = new HashMap<String,Object>();
            //map.put("flag", true);
            //map.put("msg", "您好：您无权访问");
            //out.print(map);
            out.print("{\"msg\":\"" + "您好：您无权限访问" + "\"}");
            //response.getWriter().print("{\"flag\":\""+true+"\",\"msg\":\"您好：您无权访问！\"}");
            out.close();
        }
		
	}
	private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header))
            return true;
        else
            return false;
    }
}
