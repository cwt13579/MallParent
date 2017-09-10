package com.iec.core.app.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



/**
 * 加载资源文件
 * @author LiuSQ
 *
 *
 */
public class LoadProperties {
	
	// 单例模式
	private static LoadProperties loadPro = new LoadProperties();

	private  Map<String, String> mappro = null;

	// 构造方法
	public LoadProperties() {

		InputStream inStream = null;
		Properties property = null;

		try {
			
			//读取配置文件
			inStream = LoadProperties.class.getClassLoader().getResourceAsStream("prop.properties");
			property = new Properties();
			property.load(inStream);
			mappro = new HashMap<String, String>();
			Enumeration<?> enumer = property.keys();
			while(enumer.hasMoreElements()){
				String keyName = (String) enumer.nextElement();
				mappro.put(keyName, property.getProperty(keyName));
			}

		} catch (Exception e) {
			new Throwable(e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
					inStream = null;
				} catch (IOException e) {
					new Throwable(e);
				}
			}

		}

	}
	
	/**
	 * @function 获取该类实例对象
	 * @return
	 */
	public static LoadProperties getloadProManager() {
		return loadPro;
	}
	
	
	
	

	// 返回Map
	public Map<String, String> getMap_del() {
		return mappro;
	}
}
