package com.iec.core.app.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * 业务日志内容处理类
 * @author lubang713
 *
 */
public class LogHelper
{
    public final static String DEFAULT_SEPARATOR = "\t";
    
    /**
     * 返回符合统计格式的log内容
     * @param separator
     * @param objs
     * @return
     */
    public static void log(String logName, String separator, Object... params) {
        if (separator==null || separator.trim().isEmpty()) {
            separator = DEFAULT_SEPARATOR;
        }
        if (params == null || params.length <= 0) {
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        for (Object obj : params) {
            if (obj instanceof Number) {
                sb.append(returnZeroIfEmpty(obj));
            } else {
                sb.append(returnDashIfEmpty(obj));
            }
            sb.append(separator);
        }
        Logger.getLogger(logName).info(sb.toString());
    }
    


    /**
     * 检查参数
     * @param param
     * @return 如果为空，返回 -
     */
    public static String returnDashIfEmpty(Object param)
    {
        return (param == null || param.toString().isEmpty()) ? "-" : param.toString().replaceAll("[\r\n\t ]+", "");
    }

    /**
     * 检查参数
     * @param param
     * @return 如果为空，返回 0
     */
    public static String returnZeroIfEmpty(Object param)
    {
        return (param == null || param.toString().isEmpty()) ? "0" : param.toString().replaceAll("[\r\n\t ]+", "");
    }
    
	
    public static String getLocalAddress() {
		String address = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			if(addr!=null){
				address = addr.getHostAddress().toString();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		if(address == null){
			return "127.0.0.1";
		}
		return address;
	}



	public static void write(String serverIp, Integer serverPort, String methodName, boolean result,  String send,String receive, long sTime) {
		receive = receive!=null?receive:"";
		send = send!=null?send:"";
		
		LogHelper.log("minaclient", null,
				serverIp,
				serverPort,
				methodName,
				getLocalAddress(),
				send.length(),
				receive.length(),
				result,
				System.currentTimeMillis()-sTime);
		
	}
	
	public static void write(String methodName, boolean result, String send,String receive, long sTime) {
		receive = receive!=null?receive:"";
		send = send!=null?send:"";
		
		LogHelper.log("minaserver", null,
				methodName,
				getLocalAddress(),
				send.length(),
				receive.length(),
				result,
				System.currentTimeMillis()-sTime);
		
	}
	
}
