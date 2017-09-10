package com.cm.app;

import java.io.IOException;

import com.cm.app.module.user.User;
import com.iec.core.app.middleware.Servers;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	 // 启动 数据库插件
    	 C3p0Plugin c3p0Plugin = new C3p0Plugin("jdbc:mysql://127.0.0.1:3306/cwt?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull", "root", "1234");
		 c3p0Plugin.setDriverClass("com.mysql.jdbc.Driver");
		 ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		 arp.addMapping("user", User.class);
		 System.out.println("数据库连接中...");
		 c3p0Plugin.start();
		 arp.start();
		 System.out.println("数据库连接成功...");
		 
    	 Servers applicationServer = new Servers("classpath:applicationContext.xml");
	     applicationServer.start();
    }
}
