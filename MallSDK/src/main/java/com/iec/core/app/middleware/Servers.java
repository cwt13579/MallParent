package com.iec.core.app.middleware;

import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.iec.core.app.base.BeanContext;
import com.iec.core.app.utils.Utils;

/**
 * 
 * @author Tumq
 * 启动app服务 【观察者模式】
 */
public class Servers extends Observable implements IServer {
	private String applicationContextPath;
	private FileSystemXmlApplicationContext context;

	/** log4j **/
	private static final Logger logger = LoggerFactory.getLogger("IECCORE");

	public Servers(String applicationContextPath) {
		this.applicationContextPath = applicationContextPath;
	}

	public void start() {
		try {
			if (Utils.isNotEmpty(applicationContextPath)&& Utils.isEmpty(context)) {
				context = new FileSystemXmlApplicationContext(applicationContextPath);
			}
			BeanContext.setContext(context);
			ServiceList serviceList = context.getBean(ServiceList.class);
			for (Observer observer : serviceList.getServerList()) {
				this.addObserver(observer);
			}
		} catch (Exception e) {
			logger.error("[应用服务端加载配置文件失败]，请查看异常日志：", e);
			this.stop();
		}
		this.setChanged();
		this.notifyObservers(true);
	}

	public void stop() {
		try {
			if (null != context) {
				context.close();
				context.destroy();
			}
			this.setChanged();
			this.notifyObservers(false);
			logger.info("[关闭应用服务器]成功");
		} catch (Exception e) {
			logger.error("[应用服务器]停止失败，请查看异常日志", e);
		}
		System.exit(-1);
	}
}
