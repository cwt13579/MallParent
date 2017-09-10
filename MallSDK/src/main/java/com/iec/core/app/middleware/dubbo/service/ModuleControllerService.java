package com.iec.core.app.middleware.dubbo.service;

import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.iec.core.app.middleware.dubbo.IDubboService;
import com.iec.core.app.middleware.dubbo.scan.ModuleMethodScan;

/**
 * 
 * @author Tumq
 *
 */
public class ModuleControllerService extends BaseMethodService implements IDubboService {

	public ModuleControllerService() {
		super(new ModuleMethodScan());
	}
	
	/**
	 * @param code
	 * @param desc
	 * @param serviceResponse
	 * @return 返回个性化错误信息和错误描述
	 */
	public ServiceResponse rtnErrorInfo(String code,String desc,ServiceResponse serviceResponse){
		serviceResponse.setRespCode(code);
		serviceResponse.setRespMsg(desc);
		return serviceResponse;
		
	}
}
