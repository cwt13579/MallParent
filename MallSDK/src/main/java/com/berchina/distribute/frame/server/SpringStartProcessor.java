package com.berchina.distribute.frame.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.berchina.distribute.frame.annotation.BerBean;
import com.berchina.distribute.frame.annotation.BerService;
import com.berchina.distribute.frame.annotation.BerServiceImpl;

/**
 * 
 * 创建于:2016年7月17日<br>
 * 版权所有(C) 2016 宝润兴业<br>
 * spring环境启动处理器，将spring中的服务实现bean(BerServiceImpl)注册到服务实现管理器中
 * @author chender
 * @version 1.0
 *
 */
@BerBean
public class SpringStartProcessor implements BeanPostProcessor {
	private Logger logger=Logger.getLogger(this.getClass());
	@Override
	public Object postProcessAfterInitialization(Object obj, String beanId) throws BeansException {
		Class<?> clz=obj.getClass();
		BerServiceImpl bsi=clz.getAnnotation(BerServiceImpl.class);
		if(bsi!=null){
			List<Class<?>> faces=new ArrayList<Class<?>>();
			Class<?> parent=clz;
			while(parent!=null){
				Class<?>[] array = parent.getInterfaces();
				if(array!=null&&array.length!=0){
					for(Class<?> c:array){
						faces.add(c);
					}
				}
				parent=parent.getSuperclass();
			}
			// 如果存在拥有BerService注解的接口，则按接口名注册服务
			if (faces != null && faces.size() != 0) {
				for (Class<?> one : faces) {
					BerService bs = one.getAnnotation(BerService.class);
					if (bs != null) {
						ServiceImplManager.reg(one, obj,bsi.tag());
						return obj;//为了规范管理，一个类最多只能是一个BerService的实现
					}
				}
			}
			ServiceImplManager.reg(clz, obj,bsi.tag());
		}
		return obj;
	}
	@Override
	public Object postProcessBeforeInitialization(Object obj, String beanId)
			throws BeansException {
		return obj;
	}
}
