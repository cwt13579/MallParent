package com.iec.core.xss.document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iec.core.app.core.JsonMapping;


public class DocumentMaker {

	private ApplicationContext context;
	private String fileSavePath;
	
	public DocumentMaker(ApplicationContext context, String fileSavePath) {
		super();
		this.context = context;
		this.fileSavePath = fileSavePath;
	}

	
	public List<Document> listDoc() {
		
		List<Document> doclist = new ArrayList<Document>();
		
		String[] beanNames = context.getBeanNamesForType(Object.class);

		for (String bean : beanNames) {
			if (!bean.endsWith("Controller")) {
				continue;
			}
			Document doc = new Document();
			
			Class beanClass = context.getType(bean);

			doc.setClassName(beanClass.getName());
			doc.setControllerName(bean);
			
			Annotation[]  ans  =  beanClass.getAnnotations();
			for(Annotation a : ans){
				if(!a.annotationType().toString().contains("RequestMapping")) continue;
				
				RequestMapping mapping = (RequestMapping)a;
				if(mapping.value()!=null && mapping.value().length>0){
					doc.setControllerPath(mapping.value()[0]);
				}
			}

			for (Method method : beanClass.getDeclaredMethods()) {

				Annotation[] annotation = method.getAnnotations();

				for (Annotation a : annotation) {
					MethodDoc methodDoc = new MethodDoc();
					methodDoc.setMethodName(method.getName());
					
					//web控制器
					if(a.annotationType().toString().contains("RequestMapping")){
					
							RequestMapping mapping = method.getAnnotation(RequestMapping.class);
							methodDoc.setDesc("");
							
							if(mapping.value()!=null && mapping.value().length>0){
								methodDoc.setPath(mapping.value()[0]);
							}
					
					//mina服务控制器
					}else if(a.annotationType().toString().contains("JsonMapping")){
						JsonMapping mapping = method.getAnnotation(JsonMapping.class);
						
						methodDoc.setDesc(mapping.mappingMemo());
						methodDoc.setPath(mapping.mappingName());
					}
					
	
					Class[] class_ = method.getParameterTypes();

					if (class_.length > 0) {
						for (int i = 0; i < class_.length; i++) {
							methodDoc.getParaList().add(class_[i].getSimpleName());
						}
					}
					
					doc.getMethodList().add(methodDoc);
				}	
			}
			
			doclist.add(doc);

		}

		return doclist;

	}

	public void write(String message){
		try {
			
		File f = new File(fileSavePath);
		if(f.exists()){
			f.delete();
		}else{
			f.mkdirs();
			f.createNewFile();
		}
			
		FileWriter fw = new FileWriter(fileSavePath);
		BufferedWriter bw = new BufferedWriter(fw);
		 
		bw.write(message);
		bw.newLine();
		
		bw.flush();
		fw.close();
		
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	

		public void document() throws Exception{
		
//		List<Document> doclist = listDoc();
//
//		VelocityEngine ve = new VelocityEngine();
//		
//		Properties properties = new Properties();
//		// 设置模板的路径
//		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,DocumentMaker.class.getResource("").getPath());
//		// 初始花velocity 让设置的路径生效
//		ve.init(properties);
//		
//		Template template = ve.getTemplate("document.vm", "UTF-8");
//		
//		VelocityContext context = new VelocityContext();
//		
//		context.put("doclist", doclist);
//		
//		StringWriter writer = new StringWriter();
//		
//		template.merge(context, writer);
//		
//		System.out.println(writer.toString());
//		write(writer.toString());
	}

	public ApplicationContext getContext() {
		return context;
	}
	public void setContext(ApplicationContext context) {
		this.context = context;
	}
	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}	
}
