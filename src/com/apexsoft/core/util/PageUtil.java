package com.apexsoft.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页帮助类
 * function 
 * @author zlzhang
 * 2013-12-21下午12:07:47
 */
public class PageUtil {
	
	/**
	 * 得到页码
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static int getPageNo(HttpServletRequest request, String pageNo){
		String str = request.getParameter(pageNo);
		int page=1;
		try {
			if(str!=null && !"".equals(str)){
				page=Integer.parseInt(str);
			}
			if(page<1){
				page=1;
			}
		} catch (Exception e) {
			page=1;
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 查询每页显示数量
	 * @param request
	 * @param pageSize
	 * @return
	 */
	public static int getPageSize(HttpServletRequest request, String pageSize){
		String str = request.getParameter(pageSize);
		int page_size=20;
		try {
			if(str!=null && !"".equals(str)){
				page_size=Integer.parseInt(str);
			}
			if(page_size<1){
				page_size=20;
			}
		} catch (Exception e) {
			page_size=20;
			e.printStackTrace();
		}
		return page_size;
	}
	
	/**
	 * 起始
	 * @param page_no
	 * @param page_size
	 * @return
	 */
	public static int getFormByPage(int page_no,int page_size){
		return (page_no-1)*page_size;
	}
	
	/**
	 * 截止
	 * @param page_no
	 * @param page_size
	 * @return
	 */
	public static int getEndByPage(int page_no,int page_size){
		return page_no*page_size;
	}
	
	/**
	 * 计算总页数
	 * @param count 总数量
	 * @param size 每页大小
	 * @return 总页数
	 */
	public static int getPageCount(int count,int size){
		if(count>=1 && size>=1 && count >= size){
			if(count%size==0){
				return count/size;
			}else{
				return count/size +1;
			}
		}else{
			return 1;
		}
	}

}
