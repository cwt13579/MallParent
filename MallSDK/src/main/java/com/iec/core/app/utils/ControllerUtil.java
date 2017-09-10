package com.iec.core.app.utils;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Controller工具类,json转bean,bean转json
 * @author dengxz
 *
 */
public class ControllerUtil {
	
	private static final Logger log = LogManager.getLogger(ControllerUtil.class);
	
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	
	private static final String DEFAULT_DOUBLE_PATTERN = "0.00";
	
	/**
	 * 可映射Controller中多个对象
	 * tPageData：{"pageUtil":{"first":1,"max":10,"orderBy":"TEST_ID asc"},"test":{"testName":"titleName"}}
	 * {"page":{"recordOfPage":5,"currentPage":1,"totalPage":0,"totalRecord":0},demo:{"name":"abc","address":"efg"}}
	 *  {"test":{"testName":"titleName","pageUtil":{pagesize:1,nowpage:2}}
	 * @param request
	 * @param obj
	 * @throws Exception
	 */
	public static void jsonToBean(HttpServletRequest request, Object obj) throws Exception {		
		Enumeration<?> e = request.getParameterNames();
		String pName = null;
		if (e.hasMoreElements()) {			
			pName = e.nextElement().toString();
		}		
		if (log.isDebugEnabled()) {
			log.debug(pName);
		}
		//tPageData 为分页查询参数，为系统唯一参数，不能出现冲突
		if(null == pName || !pName.equals("tPageData"))
			return;
		
		String json = request.getParameter(pName);
		
		Gson g = new GsonBuilder().setDateFormat(DEFAULT_DATE_PATTERN).create();
		json = json.indexOf("{")==0?json:"{"+json+"}";
		
		try
		{
			Object controller = g.fromJson(json, obj.getClass());
			
			List<Field>fields = new ArrayList<Field>();
			
			Field f1[] = obj.getClass().getDeclaredFields();
			Class cls = obj.getClass().getSuperclass();
			Field f2[] =cls.getDeclaredFields();
			
			for (Field field : f1) {
				fields.add(field);
			}
			for (Field field : f2) {
				fields.add(field);
			}
			if(controller!=null){
				for (int i = 0; i < fields.size(); i++) {			
					if (fields.get(i).getDeclaredAnnotations().length == 0) {
						fields.get(i).setAccessible(true);
						fields.get(i).set(obj, fields.get(i).get(controller));
					}
				}
			}
		}
		catch(Exception ex) {
			log.error("Convert to bean error:"+ex.getMessage());
		}
	}
	
	public static void pageListToJson(HttpServletResponse response, Object list,Object page) throws Exception{
		Gson g = new GsonBuilder().create();	
		String json = g.toJson(list);
		String ps = g.toJson(page);
		PrintWriter pw = null;
		try {
			response.setContentType("text/json;charset=utf-8");
			pw = response.getWriter();			
			String s = "{\"page\":"+ps+",\"data\":" + json + "}";
			if (log.isDebugEnabled()) {
				log.debug(s);
			}
			
			pw.write(s);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	public static void pageListToJson(HttpServletResponse response, Object list,Object page,
			String datePattern, String doublePattern) throws Exception{
		if (datePattern == null) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		if (doublePattern == null) {
			doublePattern = DEFAULT_DOUBLE_PATTERN;
		}
		
		Gson g = new GsonBuilder().setDateFormat(datePattern)
				.registerTypeAdapter(Double.class, new JsonDoubleSerializer(doublePattern))
				.create();	
		String json = g.toJson(list);
		String ps = g.toJson(page);
		PrintWriter pw = null;
		try {
			response.setContentType("text/json;charset=utf-8");
			pw = response.getWriter();			
			String s = "{\"page\":"+ps+",\"data\":" + json + "}";
			if (log.isDebugEnabled()) {
				log.debug(s);
			}
			System.out.println(s);
			pw.write(s);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	 
		/**
		 * chenhs add @ 2012/08/23 
		 * @param response
		 * @param list
		 * @param totalRows
		 * @param datePattern
		 * @param doublePattern
		 * @param appendJson
		 * @throws Exception
		 */
	public static void pageListToJson(HttpServletResponse response, Object list,Object page,
		 String datePattern, String doublePattern, String appendJson) throws Exception {
		if (datePattern == null) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		if (doublePattern == null) {
			doublePattern = DEFAULT_DOUBLE_PATTERN;
		}
		Gson g = new GsonBuilder().setDateFormat(datePattern)
			.registerTypeAdapter(Double.class, new JsonDoubleSerializer(doublePattern))
			.create();
		String json = g.toJson(list);
		String ps = g.toJson(page);
		PrintWriter pw = null;
		try {
			response.setContentType("text/json;charset=utf-8");
			pw = response.getWriter();
			String s = "{\"page\":"+ps+",\"data\":" + json + appendJson + "}";
			if (log.isDebugEnabled()) {
				log.debug(s);
			}
			pw.write(s);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	
	
	public static void toJson(HttpServletResponse response, String sign) throws Exception {		
		PrintWriter pw = null;
		try {
			response.setContentType("text/json;charset=utf-8");
			pw = response.getWriter();			
			String s = "{\"result\":\"" + sign + "\"}";
			if (log.isDebugEnabled()) {
				log.debug(s);
			}
			pw.write(s);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	public static void writeJson(HttpServletResponse response, String json) throws Exception {		
		PrintWriter pw = null;
		try {
			response.setContentType("text/json;charset=utf-8");
			pw = response.getWriter();
			if (log.isDebugEnabled()) {
				log.debug(json);
			}
			pw.write(json);
		} catch (Exception e) {
			throw e;
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
