package com.apexsoft.core.hadoop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apexsoft.core.util.BrowserUtils;
import com.apexsoft.core.util.Constants;


/**
 * hdfs 文件系统管理工具类
 * function 
 * @author zlzhang
 * 2014-5-21下午05:39:24
 */
public class HDFSUploadUtil {
	private static Logger logger = LoggerFactory.getLogger(HDFSUploadUtil.class);
	
	
	/**
	 * 得到操作HDFS文件系统的类
	 * @param ip
	 * @param port
	 * @return
	 */
	public synchronized static FileSystem getFileSystem(String ip,int port){
		FileSystem fs = null;
		String url = "hdfs://" + ip + ":" + String.valueOf(port);
		Configuration config = new Configuration();
		config.set("fs.default.name", url);
		try {
			fs = FileSystem.get(config);/*操作HDFS文件系统的类*/  
			//fs = FileSystem.getLocal(config)//*操作本地文件系统的类*/ 
			logger.info("getFileSystem success ...");
		} catch (IOException e) {
			logger.info("getFileSystem failed !");
			e.printStackTrace();
		}
		return fs;
	}
     
	/**
	 * 得到操作本地文件系统的类
	 * @param ip
	 * @param port
	 * @return
	 */
	public synchronized static FileSystem getLocalFileSystem(String ip,int port){
		FileSystem fs = null;
		String url = "hdfs://" + ip + ":" + String.valueOf(port);
		Configuration config = new Configuration();
		config.set("fs.default.name", url);
		try {
			fs = FileSystem.get(config);/*操作HDFS文件系统的类*/  
			//fs = FileSystem.getLocal(config)//*操作本地文件系统的类*/ 
			logger.info("getFileSystem success ...");
		} catch (IOException e) {
			logger.info("getFileSystem failed !");
			e.printStackTrace();
		}
		return fs;
	}
	
	
	/**
	 * 上传本地文件到HDFS 文件系统
	 * @param path  目标路径
	 * @param in   文件流
	 * @return
	 */
	public synchronized static boolean uploadFile(String path,InputStream in){
		boolean flag=false;		
		try{
			FileSystem fs = HDFSUploadUtil.getFileSystem(Constants.HADOOP_IP, Constants.HADOOP_PORT);
			OutputStream out = fs.create(new Path(fs.getHomeDirectory()+"/"+path));
			IOUtils.copyBytes(in, out, 4096, true);
			flag=true;
		}catch(Exception e){
			
		}
		return flag;
	}
	
	
	/**
	 * 查询遍历所有节点
	 * @param fs
	 */
	public synchronized static void listNode(FileSystem fs){
		DistributedFileSystem dfs = (DistributedFileSystem)fs;
		try {
			DatanodeInfo[] infos = dfs.getDataNodeStats();
			//logger.info("------------------------------------------------------");
			for(DatanodeInfo node:infos){
				logger.info("HostName"+node.getHostName()+":/n,"+node.getDatanodeReport());
				//logger.info("------------------------------------------------------");
			}
		} catch (Exception e) {
			logger.info("list node list failed...");
			e.printStackTrace();
		}
	}
	
	/**
	 * 打印系统配置
	 * @param fs
	 */
	public synchronized static void listConfig(FileSystem fs){
		Iterator<Entry<String,String>> entrys = fs.getConf().iterator();
		while(entrys.hasNext()){
			Entry<String,String> item = entrys.next();
			logger.info(item.getKey()+" : "+item.getValue());
		}
	}
	
	/**
	 * 创建目录和父目录
	 * @param fs
	 * @param dirName
	 */
	public synchronized static void mkdir(FileSystem fs,String dirName){
		Path workDir = fs.getWorkingDirectory();
		String dir = workDir + "/" + dirName;
		Path src = new Path(dir);
		boolean succ;
		try {
			succ = fs.mkdirs(src);
			if(succ){
				logger.info("create directory "+ dir + " successed.");
			}else{
				logger.info("create directory "+ dir + " failed");
			}
		} catch (IOException e) {
			logger.info("create directory "+ dir + " failed.");
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除目录和子目录
	 * @param fs
	 * @param dirName
	 */
	public synchronized static boolean rmdirs(FileSystem fs,String dirName){
		boolean result = false;
		Path workDir = fs.getWorkingDirectory();
		String dir = workDir +"/"+dirName;
		Path src = new Path(dir);
		try {
			result = fs.delete(src, true);
			if(result){
				logger.info("remove directory "+ dir + " successed.");
			}else{
				logger.info("remove directory "+ dir + " failed.");
			}
		} catch (IOException e) {
			logger.info("remove directory "+ dir + " failed.");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 上传目录和文件
	 * @param fs  hdfs
	 * @param local   本地文件地址
	 * @param remote  hdfs目录
	 */
	public synchronized static void upload(FileSystem fs,String local,String remote){
		Path workDir = fs.getWorkingDirectory();
		Path dst = new Path(workDir+"/"+remote);
		Path src = new Path(local);
		try {
			fs.copyFromLocalFile(false, true, src, dst);
			logger.info("upload"+ local + " to " + remote + "successed.");
		} catch (IOException e) {
			logger.info("upload"+ local + " to " + remote + "failed.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 下载目录或文件
	 * @param fs
	 * @param local
	 * @param remote
	 */
	public synchronized static void download(FileSystem fs,String local,String remote){
		Path workDir = fs.getWorkingDirectory();
		Path dst = new Path(workDir+"/"+remote);
		Path src = new Path(local);
		try {
			fs.copyToLocalFile(false, dst, src);
			logger.info("download from "+ remote + " to " + local + "successed.");
		} catch (IOException e) {
			logger.info("download from "+ remote + " to " + local + "failed.");
			e.printStackTrace();
		}
	}
	
	/**
	 * 字节数转换
	 * @param size
	 * @return
	 */
	public synchronized static String converSize(long size){
		String result = String.valueOf(size);
		if(size < 1024 * 1024 ){
			result = String.valueOf(size/1024)+"KB";
		}else if(size >= 1024* 1024 && size < 1024 * 1024 * 1024){
			result = String.valueOf(size/1024/1024)+"MB";
		}else if(size >= 1024 * 1024 * 1024  && size < 1024 * 1024 * 1024 * 1024){
			result = String.valueOf(size/1024/1024/1024)+"GB";
		}else{
			result = result+"B";
		}
		return result;
	}
	
	/**
	 * 从HDFS 下载文件
	 * @param request
	 * @param response
	 * @param downDir  目录
	 * @param fileName 文件名称
	 */
	public synchronized static void downFileFromHDFS(FileSystem fs,HttpServletRequest request,HttpServletResponse response,String downDir,String fileName){
		Path workDir = fs.getWorkingDirectory();//hdfs工作目录
		try{
			String filepath = workDir.toString()+"/"+downDir+"/"+fileName;
			InputStream in = fs.open(new Path(filepath));
			if(in!=null){
				response.setContentType("application/x-download;charset=UTF-8"); 
				if(BrowserUtils.isIE(request)){
					fileName = URLEncoder.encode(fileName, "UTF-8");					
				}else{
					fileName = new String(fileName.getBytes(),"ISO-8859-1");
				}
				OutputStream out = response.getOutputStream();
				response.addHeader("Content-Disposition", "attachment;filename="+fileName);
				byte[] buffer = new byte[in.available()];
				in.read(buffer);
				in.close();
				out.write(buffer);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 遍历HDFS 上的文件和目录
	 * @param fs
	 * @param path
	 */
	public synchronized static List<String> listFile(FileSystem fs,String path,String downpath){
		List<String> fileList = null;
		Path workDir = fs.getWorkingDirectory();//hdfs工作目录
		Path dst;
		if(null == path || "".equals(path)){			
			dst = new Path(workDir + "/" +path);	
		}else{
			dst = new Path(path);
		}
		try{
			String relativePath = "";
			FileStatus[] fList = fs.listStatus(dst);
			if(null != fList && fList.length>0){
				for(FileStatus f : fList){
					if(null!=f){
						relativePath = new StringBuffer().append(f.getPath().getParent()).append("/")
						     .append(f.getPath().getName()).toString();
						if(f.isDir()){
							//listFile(fs,relativePath,downPath);是目录则不处理
						}else{
							logger.info(converSize(f.getLen())+"---------"+relativePath);
						}
					}
				}
				Path[] paths = FileUtil.stat2Paths(fList);
				fileList = new ArrayList<String>();
				for(Path p:paths){
					String fileName = p.toString().substring((p.toString()).lastIndexOf("/")+1, (p.toString()).length());
					//logger.info("fileName=="+fileName);
					String filepath = downpath+"//"+fileName;
					//logger.info("filepath=="+filepath);
					filepath=(filepath.toString()).replaceAll("\\\\", "//");
					//logger.info("filepath=="+filepath);
					OutputStream out = new FileOutputStream(filepath);
					InputStream in = fs.open(p);
					IOUtils.copyBytes(in, out, 40960, false);
					fileList.add(fileName);
				}
			}
			
		}catch(Exception e){
			logger.error("list files of "+ path + "failed :");			
			e.printStackTrace();
		}
		return fileList;
	}
	
	
	/**
	 * 向HDFS文件系统中写文件
	 * @param fs
	 * @param path
	 * @param data
	 */
	public synchronized static void write(FileSystem fs,String path,String data){
	      Path workDir = fs.getWorkingDirectory();
	      Path dst = new Path(workDir +"/"+path);
	      try {
			FSDataOutputStream dos = fs.create(dst);
			dos.writeUTF(data);
			dos.close();
			logger.info("write content to "+ path +" successed.");
		} catch (IOException e) {
			logger.error("write content to "+ path + " failed.");
			e.printStackTrace();
		}
	      
	}
	
	/**
	 * 向HDFS文件系统中某个文件追加内容
	 * @param fs
	 * @param path
	 * @param data
	 */
	public synchronized static void append(FileSystem fs,String path,String data){
	      Path workDir = fs.getWorkingDirectory();
	      Path dst = new Path(workDir +"/"+path);
	      try {
			FSDataOutputStream dos = fs.append(dst);
			dos.writeUTF(data);
			dos.close();
			logger.info("append content to "+ path +" successed.");
		} catch (IOException e) {
			logger.error("append content to "+ path + " failed.");
			e.printStackTrace();
		}
	      
	}
	
	/**
	 * 读取文件
	 * @param fs
	 * @param path
	 * @return
	 */
	public synchronized static String read(FileSystem fs,String path){
		String content = null;
		Path workDir = fs.getWorkingDirectory();
	    Path dst = new Path(workDir +"/"+path);
	    try {
			FSDataInputStream dis = fs.open(dst);
			content = dis.readUTF();
			dis.close();
			logger.info("read content from "+ path +" successed.");
		} catch (IOException e) {
			logger.info("read content from "+ path +" failed.");
			e.printStackTrace();
		}
	    return content;
	}
	
	
	public static final void main(String[] arg){
		FileSystem fs = HDFSUploadUtil.getFileSystem("192.168.192.139", 9000);
		HDFSUploadUtil.listNode(fs);
		//HDFSUploadUtil.listConfig(fs);
		//HDFSUploadUtil.mkdir(fs, "upload/images");
		HDFSUploadUtil.rmdirs(fs, "upload/images");
	}
	

}
