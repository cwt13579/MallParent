package com.iec.core.app.middleware.dubbo;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.container.Main;
import com.iec.core.app.base.BeanContext;
import com.iec.core.app.middleware.dubbo.service.IService;

/**
 * 
 * @author Tumq
 *
 */
public class DubboServer implements Observer {

	/** log4j **/
	private static final Logger logger = LoggerFactory.getLogger("IECCORE");

	@Override
	public void update(Observable o, Object arg) {
		if ((Boolean) arg) {
			// context.start();
			new Thread(new Runnable() {

				@Override
				public void run() {
					Map<String, IService> serviceMap = BeanContext.getApplicationContext().getBeansOfType(IService.class);
					for (IService dubboService : serviceMap.values()) {
						try {
							if (IService.class.isAssignableFrom(IDubboService.class)&&dubboService instanceof IDubboService) {
								logger.info(dubboService.getClass().getName()+ " start init");
								((IDubboService) dubboService).init(BeanContext.getApplicationContext());
							}
						} catch (Exception e) {
							logger.error(dubboService.getClass().getName()+ " 初始化错误", e);
							System.exit(-1);
							return;
						}
					}
					logger.info("[DUBBO应用程序启动]成功");
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
						}
					}
				}
			}).start();

		} else {
			synchronized (this) {
				notifyAll();
			}
		}

	}
}
