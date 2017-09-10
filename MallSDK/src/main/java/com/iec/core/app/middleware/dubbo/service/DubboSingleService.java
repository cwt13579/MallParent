package com.iec.core.app.middleware.dubbo.service;

import com.iec.core.app.middleware.dubbo.scan.ControllerMethodScan;
import com.iec.core.app.middleware.dubbo.scan.ModuleClasses;


/**
 * @author tumq
 * @Date 2015年1月9日
 * @describe 
 */
public class DubboSingleService extends BaseMethodService {

	public DubboSingleService(ModuleClasses mp) {
		super(new ControllerMethodScan(mp));
	}
}
