package com.iec.core.app.middleware.dubbo.service;
/**
 * 通用服务接口，分布式服务框架中的服务需要实现该接口
 * 
 * @author SCRCU
 * @version 1.0.0
 * @see     com.scrcu.ebank.common.service.ServiceRequest
 * @see     com.scrcu.ebank.common.service.ServiceResponse
 */
public interface IService {

    /**
     * 执行服务调用 
     * @param request 服务请求对象
     * @return 服务响应对象
     */
    ServiceResponse invoke(ServiceRequest request);
}
