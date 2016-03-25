package com.apexsoft.core.hadoop.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.apexsoft.core.hadoop.util.HDFSUploadUtil;
import com.apexsoft.core.util.Constants;

@Controller
@RequestMapping("hadoop")
public class UploadAction {
	
	
	@RequestMapping(value="/uploadList")
	public @ResponseBody ModelAndView uploadList(HttpServletRequest request,HttpServletResponse response){
		ModelAndView model = new ModelAndView("/hadoop/upload");
		String downDir = request.getSession().getServletContext().getRealPath(Constants.DOWN_DIR);
		List<String> downFiles=HDFSUploadUtil.listFile(HDFSUploadUtil.getFileSystem(Constants.HADOOP_IP, Constants.HADOOP_PORT), Constants.UPLOAD_DIR,downDir);	
		//System.out.println("downDir=="+downDir);
		model.addObject("fileList", downFiles);
		return model;
	}
	
	/**
	 * 文件上传
	 * @return
	 */
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public void upload(HttpServletRequest request,HttpServletResponse response){
		//response.setContentType("application/text;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");  
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
		net.sf.json.JSONObject jvo = new net.sf.json.JSONObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		List<MultipartFile> files = multipartRequest.getFiles("file");
		if(files!=null){
			for(int i=0;i<files.size();i++){
				MultipartFile file = files.get(i);
				if(file!=null){
					String realName = file.getOriginalFilename();
					try {
						HDFSUploadUtil.uploadFile(Constants.UPLOAD_DIR+"/"+realName, file.getInputStream());
						jvo.put("flag", true);
						jvo.put("msg", "上传成功！");
					} catch (IOException e) {
						jvo.put("flag", false);
						jvo.put("msg", "上传失败！");
						e.printStackTrace();
					}
				}
			}
		}	
		try {
			response.getWriter().write(jvo.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/downFile")
	public void downFile(HttpServletRequest request,HttpServletResponse response){
		String fileName = request.getParameter("fileName");
		HDFSUploadUtil.downFileFromHDFS(HDFSUploadUtil.getFileSystem(Constants.HADOOP_IP, Constants.HADOOP_PORT), request, response, Constants.UPLOAD_DIR, fileName);
	}
	
	/**
	 * 删除文件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/delFileByPath")
	public @ResponseBody Map<String,Object> delFileByPath(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String,Object>();
		String fileName= request.getParameter("fileName");
		if(null!= fileName && !"".equals(fileName)){
			boolean re = HDFSUploadUtil.rmdirs(HDFSUploadUtil.getFileSystem(Constants.HADOOP_IP, Constants.HADOOP_PORT), Constants.UPLOAD_DIR+"/"+fileName);
			if(re){
				result.put("flag", true);
				result.put("msg", "删除成功！");
			}else{
				result.put("flag", false);
				result.put("msg", "删除失败！");
			}
		}else{
			result.put("flag", false);
			result.put("msg", "删除失败！");
		}
		return result;
	}
	

}
