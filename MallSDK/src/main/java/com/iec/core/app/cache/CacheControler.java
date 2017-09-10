package com.iec.core.app.cache;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.JsonSyntaxException;
import com.iec.core.app.core.JsonMapping;
import com.iec.core.app.utils.GsonUtils;
import com.iec.core.app.utils.Utils;


/**
 * 清理缓存
 * @author ASUS
 *
 */
@Controller
public class CacheControler {
	
	//private static final Logger log = Logger.getLogger(CacheControler.class);
	private static final Logger log = Logger.getLogger("IECCORE");
	
	@Autowired
	private CacheService cacheService;
	
	private static CacheService scacheService;
	
	@PostConstruct
	public void init() {
		scacheService = this.cacheService;
	}
	
	@JsonMapping(mappingMemo = "清理缓存", mappingName = "cache_clean")
	public static String cleanCache(String params) {
		String reJson = "";
		try {
			Cache cache = GsonUtils.getJson(params, Cache.class);
			if(Utils.isNotEmpty(cache.getCache_key())){
				CacheUtils.getCacheInstance().remove(cache.getCache_key());
				reJson = "{\"code\":\"0\",\"data\":\"\",\"desc\":\"clean success\"}";
			}else{
				reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"pass params error\"}";
			}
		}  catch(JsonSyntaxException e){
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"params to bean error\"}";
			log.error(e);
			e.printStackTrace();
		}catch (Exception e) {
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"clean error\"}";
			log.error(e);
			e.printStackTrace();
		}
		log.info("request[" + params + "],response[" + reJson + "]");
		return reJson;
	}
	
	@JsonMapping(mappingMemo = "获取缓存数据", mappingName = "cache_get_data")
	public static String getCacheData(String params){
		String reJson = "";
		try{
			Cache cache = GsonUtils.getJson(params,Cache.class);
			if(Utils.isNotEmpty(cache.getCache_key())){
				String cache_value = "";
				if(CacheUtils.getCacheInstance().containsKey(cache.getCache_key())){
					cache_value = CacheUtils.getCacheInstance().get(cache.getCache_key()).toString();
				}
				String data = "{\"cache_value\":\""+cache_value+"\"}";
				reJson = "{\"code\":\"0\",\"data\":"+data+",\"desc\":\"success\"}";;
			}else{
				reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"pass params error\"}";
			}
		} catch(JsonSyntaxException e){
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"params to bean error\"}";
			log.error(e);
			e.printStackTrace();
		}catch (Exception e) {
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"app system error\"}";
			log.error(e);
			e.printStackTrace();
		}
		log.info("request[" + params + "],response[" + reJson + "]");
		return reJson;
	}
	
	@JsonMapping(mappingMemo = "获取静态页列表", mappingName = "cache_html_list")
	public static String getCacheHtmlList(String params) {
		String reJson = "";
		try {
			reJson = scacheService.getSysHtml();
		}  catch(JsonSyntaxException e){
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"params to bean error\"}";
			log.error(e);
			e.printStackTrace();
		}catch (Exception e) {
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"app system error\"}";
			log.error(e);
			e.printStackTrace();
		}
		log.info("request[" + params + "],response[" + reJson + "]");
		return reJson;
	}
	
	

	@JsonMapping(mappingMemo="更新静态化定时缓存数据",mappingName="cache_update_html_timing")
	public static String updateHtmlTimingCache(String params){
		String reJson = "";
		try {
			Cache cache = GsonUtils.getJson(params,Cache.class);
			if(Utils.isNotEmpty(cache.getCache_key()) && Utils.isNotEmpty(cache.getCache_value())){		
				if(CacheUtils.getCacheInstance().containsKey(cache.getCache_key())){
	
					String data = CacheUtils.getCacheInstance().get(cache.getCache_key()).toString();
					String cacheValue = cache.getCache_value();
					
					// 如果存在更新时间戳,不存在则加入
					String timestamp = cacheValue.substring(0, cacheValue.indexOf(","));   // 时间戳
					String conents =  cacheValue.substring(cacheValue.indexOf(",")+1);     // 内容
					int indexContents = data.indexOf(conents);  // 相同内容起始位置
					
					if(indexContents != -1 ){    // 内容存在，更新时间戳
						int indexTimestamp = data.substring(0,indexContents).lastIndexOf(";")+1;  // 相同内容时间的起始位置
						data = data.substring(0, indexTimestamp) + timestamp + data.substring(indexContents);  // 替换时间
					}else{    // 不存在相同内容，追加
						data += ";"+cacheValue;
					}
					
					// 替换缓存中的值
					CacheUtils.getCacheInstance().replace(cache.getCache_key(), data);
				}else{
					CacheUtils.getCacheInstance().add(cache.getCache_key(),cache.getCache_value(),CacheUtils.setOutTimeByHours(8));
				}
				
				reJson = "{\"code\":\"0\",\"data\":\"\",\"desc\":\"success\"}";
			}else {
				reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"pass params error\"}";
			}
		}  catch(JsonSyntaxException e){
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"params to bean error\"}";
			log.error(e);
			e.printStackTrace();
		}catch (Exception e) {
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"app system error\"}";
			log.error(e);
			e.printStackTrace();
		}
		log.info("request[" + params + "],response[" + reJson + "]");
		
		return reJson;
	}
	
	@JsonMapping(mappingMemo="更新静态化日终缓存数据",mappingName="cache_update_html_dayout")
	public static String updateHtmlDayoutCache(String params){
		String reJson = "";
		try {
			Cache cache = GsonUtils.getJson(params,Cache.class);
			if(Utils.isNotEmpty(cache.getCache_key()) && Utils.isNotEmpty(cache.getCache_value())){
				if(CacheUtils.getCacheInstance().containsKey(cache.getCache_key())){
					String data = CacheUtils.getCacheInstance().get(cache.getCache_key()).toString();
					String cacheValue = cache.getCache_value();
					
					int index = data.indexOf(cacheValue); 
					// 存在不需要操作,不存在则加入
					if(index == -1){
						data += ";" + cacheValue;
						// 替换缓存中的值
						CacheUtils.getCacheInstance().replace(cache.getCache_key(), data);
					}
				}else{
					CacheUtils.getCacheInstance().add(cache.getCache_key(),cache.getCache_value(),CacheUtils.setOutTimeByHours(8));
				}
				
				reJson = "{\"code\":\"0\",\"data\":\"\",\"desc\":\"success\"}";
			}else {
				reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"pass params error\"}";
			}
		}  catch(JsonSyntaxException e){
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"params to bean error\"}";
			log.error(e);
			e.printStackTrace();
		}catch (Exception e) {
			reJson = "{\"code\":\"1\",\"data\":\"\",\"desc\":\"app system error\"}";
			log.error(e);
			e.printStackTrace();
		}
		log.info("request[" + params + "],response[" + reJson + "]");
		
		return reJson;
	}
	
}
