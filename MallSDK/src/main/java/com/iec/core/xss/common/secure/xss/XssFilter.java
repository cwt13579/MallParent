package com.iec.core.xss.common.secure.xss;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import com.iec.core.app.base.BeanContext;


/**
 * xss特殊字符转换拦截器
 * @author lubang713
 * @date 2014-4-18
 */
public class XssFilter extends OncePerRequestFilter {
	
	private static final Logger log = LogManager.getLogger(XssFilter.class);
	
	private final static Pattern[] SAFE_VALUE_PATTERNS = new Pattern[]{
		Pattern.compile("[\\-]?[\\d\\w\\s\\:]*")
	};
	private final static Set<String> safe_parameters = new HashSet<String>();
	static{
		safe_parameters.add("tPageData");
	};

	private static final ThreadLocal<Set<String>> ingnoreParameters = new ThreadLocal<Set<String>>();

	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		
		try{
			Set<String> ignoreds = new HashSet<String>();
			ignoreds.addAll(safe_parameters);
			
			XssIgnored xssIgnored = getXssIgnored(request);
			
			if(xssIgnored != null){
				ignoreds.addAll(Arrays.asList(xssIgnored.ignoreParameters()));
			}
			
			ingnoreParameters.set(ignoreds);
			
			request = new Request(request);
			
			chain.doFilter(request, response);	
			
		}finally{
			ingnoreParameters.remove();
		}
    }
	
	
	private XssIgnored getXssIgnored(HttpServletRequest request) {
		try{
		String uri = request.getServletPath();

		String controllerName = getControllerName(uri);
		
		Class beanClass = BeanContext.getBean(controllerName).getClass();
		
		String cotrollerUri = null;
		
		RequestMapping contrlMapping = (RequestMapping)beanClass.getAnnotation(RequestMapping.class);
		if(contrlMapping != null && contrlMapping.value()!=null && contrlMapping.value().length>0){
			cotrollerUri = contrlMapping.value()[0];
		}else{
			cotrollerUri = controllerName.substring(0,controllerName.indexOf("Controller"));
		}
		cotrollerUri = cotrollerUri.startsWith("/")?cotrollerUri:"/"+cotrollerUri;
		
		return  getIgnored(beanClass,uri,cotrollerUri);
		}catch(Exception e){
			log.error(e);
		}
		
		return null;
		
	}


	private XssIgnored getIgnored(Class beanClass, String uri,String cotrollerUri) {
		
		for (Method method : beanClass.getDeclaredMethods()) {

			Annotation[] annotations = method.getAnnotations();
			for (Annotation a : annotations) {
			if(a.annotationType().toString().contains("RequestMapping")){
				RequestMapping mapping = method.getAnnotation(RequestMapping.class);
				
				String[] values = mapping.value();
				
				
				
				String mappingPath = null;
				if(values !=null && values.length>0){			
					mappingPath =  values[0];
				}else{
					mappingPath = method.getName();
				}
					
					if(mappingPath!=null){
						mappingPath = mappingPath.startsWith("/")?mappingPath:"/"+mappingPath;
						if(mappingPath.indexOf(".") != -1){
							mappingPath = mappingPath.substring(0,mappingPath.indexOf("."));
						}
	
						if(uri.lastIndexOf(".") != -1){
							uri = uri.substring(0,uri.lastIndexOf("."));
							 
						}
						
						if(uri.equalsIgnoreCase(cotrollerUri+mappingPath)){
							return method.getAnnotation(XssIgnored.class);
						}
					}
			}
		 }
		}
		return null;
	}


	private String getControllerName(String uri) {
		String controllerName = uri.substring(0,uri.lastIndexOf("/"))+"Controller";
		controllerName = controllerName.replace("/", "");
		return controllerName;
	}


	public static void main(String [] args){
		String uri = "/index/index.xhtml";
		int index = uri.lastIndexOf(".");
		
		String uri1 = uri.substring(0,index);

		
		System.out.println(uri1);
	}

	
	@Override
	public void destroy(){
		ingnoreParameters.remove();
	}
	
	private String escape(String name ,String value){
		try{
		Set<String> ignoredParameters = ingnoreParameters.get();
		
		if(name == null || value == null || value.trim().length()<1){
			return value;
		}
		
		if(ignoredParameters!=null && ignoredParameters.contains(name)){
			return value;
		}
		
		for(Pattern p : SAFE_VALUE_PATTERNS){
			if(p.matcher(value).matches()){
				return value;
			}
		}
		
		value = AntiXss.htmlAttributeEncode(value.trim());
		}catch(Exception e){
			log.error(e);
		}
		return value;
	}
	
	class Request extends HttpServletRequestWrapper{

		public Request(HttpServletRequest request) {
			super(request);
		}
		
		@Override
		public String getParameter(String name){
			return escape(name,super.getParameter(name));
		}
		
		@Override
		public String[] getParameterValues(String name){
			String[] values = super.getParameterValues(name);
			if(values!=null && values.length>0){
				for(int i = 0 ; i < values.length ; i++){
					values[i] = escape(name,values[i]);
				}
			}
			return values;
		}
	}
 
}
