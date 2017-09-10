package com.iec.core.app.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 读取配置文件工具类
 * @author lubang713
 *
 */
public class PropsConfig{

	private static Logger logger=Logger.getLogger(PropsConfig.class);
	
	private static Properties props = new Properties();
	
	static{
		load();
	}
	
	private static synchronized void load() {
		
		try {
			if(props==null || props.isEmpty()){
				Resource resource = new ClassPathResource("/sysconfig.properties");
				props = PropertiesLoaderUtils.loadProperties(resource);

				
//				 String dir = PropsConfig.class.getClassLoader().getResource("").toURI().getPath();
//				 String configName = dir+"/sysconfig.properties";
//				 InputStream is = new BufferedInputStream(new FileInputStream(configName));
//				props.load(is);
			}
			
		} catch (FileNotFoundException e) {
			logger.error("PropsConfig.load FileNotFoundException", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("PropsConfig.load IOException", e);
		}catch (Exception e) {
			logger.error("PropsConfig.load IOException", e);
		}
			
	}

	public  static String getPropValue(String key) {
		if(props == null || props.isEmpty()){
			load();
		}
		return (String)props.get(key);
	}
	public static int getPropValue(String key,int defaultV) {
		return getPropValue(key)!=null?Integer.parseInt(getPropValue(key)):defaultV;
	}
	public static String getPropValue(String key,String defaultV) {
		return getPropValue(key)!=null?getPropValue(key):defaultV;
	}
	
	public static void main(String[] args){
		System.out.println(getPropValue("redis_server_count"));
		
	}
}
