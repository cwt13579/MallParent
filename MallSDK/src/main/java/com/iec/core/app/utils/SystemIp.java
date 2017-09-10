package com.iec.core.app.utils;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 获取服务器/客户端真实IP地址
 * @author LSQ
 *
 */
public class SystemIp {
	
	private static InetAddress inetAddress = null;
	//静态代码块,初始化对象
	static {
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	//获取系统IP地址
	public static String getSysIp(){
		return inetAddress.getHostAddress();
	}
	//获取系统IP对应的主机名
	public static String getSysLoc(){
		return inetAddress.getHostName();
	}
	

	static Pattern pattern = Pattern.compile("[\\.\\d]*");

	/**
	 * linux下取机器的真实ip地址
	 * @author lubang713
	 * @return
	 */
	public static String getHostIp(){
		String ip = null;

		try{
			
			InetAddress addr = InetAddress.getLocalHost();
			if(addr!=null){
				ip = addr.getHostAddress().toString();
			}
			
			if(ip == null || "127.0.0.1".equals(ip)){
				
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
				while(nets.hasMoreElements()){
					NetworkInterface ni = nets.nextElement();
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while(ips.hasMoreElements()){
						String _ip = ips.nextElement().getHostAddress();
						if(_ip != null && !"127.0.0.1".equals(_ip)){
							if(pattern.matcher(_ip).matches()){
								return _ip;
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ip!=null?ip:"127.0.0.1";
	}
	
	
	/**
	 * 测试
	 */
	public static void main(String[] args) {
		
		System.out.println("服务端IP：  "+SystemIp.getHostIp()+"  "+SystemIp.getSysLoc());
		
		
	}
	
}
