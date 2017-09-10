package com.cm.app.module.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.cm.common.mapping.UserMapping;
import com.iec.core.app.middleware.RequestMapping;
import com.iec.core.app.middleware.dubbo.service.IService;
import com.iec.core.app.middleware.dubbo.service.ModuleControllerService;
import com.iec.core.app.middleware.dubbo.service.ServiceRequest;
import com.iec.core.app.middleware.dubbo.service.ServiceResponse;
import com.jfinal.aop.Enhancer;

@Service(group=UserMapping.USER_FACADE,version="1.0",timeout=5000)
public class UserFacade extends ModuleControllerService implements IService{
	
  @RequestMapping(id=UserMapping.USER_LIST,memo=UserMapping.USER_LIST)
  public ServiceResponse getAllUser(ServiceRequest request) {
	  ServiceResponse response = new ServiceResponse();
	  System.out.println(request.getBodyValue("test"));
	  UserService service = Enhancer.enhance(UserService.class);
	  User user = service.getAllUser();
	  response.setBodyValue("name", user.getStr("user_name"));
	  return response;
  }
}
