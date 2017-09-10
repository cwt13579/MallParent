package com.iec.core.app.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import org.apache.log4j.Logger;


/**
 * 根据url生成html文件
 * @author LiuSQ
 *
 */
public class ToHtmlUtils {
	
	
	private final static Logger logger = Logger.getLogger(ToHtmlUtils.class);

	private static Map<String,String> paramMap = ToHtmlProperties.getloadProManager().getMap();
	
	/**
	 * 生成静态页
	 * @param srcUrl 源url
	 * @param destPath 目标生成路径
	 * @return
	 */
	public static boolean toHtml(String srcUrl,String destPath){
		
		logger.info("==========create html params srcUrl "+srcUrl);
		logger.info("==========create html params destPath "+destPath);
		
		BufferedReader reader = null;
		BufferedWriter  writer = null;
		
		try {
			  
			  /****建立连接****/
			  URL url = new URL(srcUrl); 
			  URLConnection uu = url.openConnection();
			  
			  /****读取一行 写入一行****/
			  //读取流
			  reader = new BufferedReader(new InputStreamReader(uu.getInputStream(),"utf-8"));
			  //写入流
			  writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath),"utf-8"));
			  String line = null;
			  while((line=reader.readLine()) != null){
				  line = line.replaceAll(paramMap.get("local_url"),paramMap.get("server_url"));
			      writer.write(line);
			      writer.newLine();
			  }
			  return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			logger.error(e);
			 return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error(e);
			 return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
			 return false;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
			return false;
		} catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			 return false;
		}finally{
			try {
				if(reader != null){
					reader.close();
					reader = null;
				}
				if(writer != null){
					writer.close();
					writer = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
		}

	}
	

	
	
    public static void main(String[] args){
    	//toHtml("http://mcs.iecmall.com:8081/Merchant/getServiceList.do?UI=allAppListUI&serv_type=0","E://123.html");
    	
    	/*String s = "/McsWeb/Merchant/pushMessage.do";
    
    	System.out.println(StringUtils.substringBefore(StringUtils.substringAfterLast(s,"/"),"."));
    	System.out.println(StringUtils.substringAfterLast(StringUtils.substringBeforeLast(s,"/"), "/") );*/
    	
    /*	String s = "http://mcs.berchina.com:8081/";
    	System.out.println(System.getProperty("user.dir"));*/
    	
    	String line = "http://127.0.0.1:80/McsWeb/js/xmlhttp.js";
    	line = line.replaceAll("127.0.0.1","iuy");
    	System.out.println(line);
    	
    	
    }

}
