package com.iec.core.xss.document;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DocumentTest {

	public static void main(String[] args) throws Exception{
		String path = "/home/wasadmin/fmall/fmall_doc.html";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		applicationContext.start();

		DocumentMaker maker = new DocumentMaker(applicationContext,path);
		maker.document();
	}
	
}
