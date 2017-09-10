package com.cm.app;

import java.io.IOException;

import com.iec.core.app.middleware.Servers;

public class AppStart 
{
    public static void main( String[] args ) throws IOException
    { 
    	 Servers applicationServer = new Servers("classpath:spring-conf/applicationContext.xml");
	     applicationServer.start();
    }
}
