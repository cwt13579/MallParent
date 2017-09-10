
package com.berchina.distribute.hystrix; 

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcException;

/** 

 * @author 作者姓名  E-mail: email地址 

 * @version 创建时间：2017年5月12日 下午2:41:21 

 * 类说明 

 */
@Activate(group = Constants.CONSUMER)
public class HystrixFilter implements Filter{

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)throws RpcException {
		DubboHystrixCommand command = new DubboHystrixCommand(invoker, invocation);
        return  command.execute();
	}

}
 