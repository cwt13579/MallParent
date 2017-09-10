
package com.berchina.order.service.common; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.berchina.distribute.cache.MethodCache;
import com.berchina.order.dao.IOrderDao;
import com.berchina.vo.MemberVo;

/** 

 * @author 作者姓名  E-mail: email地址 

 * @version 创建时间：2017年5月11日 下午7:40:51 

 * 类说明 

 */
@Service
public class BerOrderService {

	@Autowired
	protected IOrderDao orderDao;
	
	//@MethodCache(cacheSec=300,service=BerOrderService.class,resultType=MemberVo.class)
	public MemberVo findMemberById(String id) throws Exception {
		MemberVo member = orderDao.findMemberById(id);
		return member;
	}
	
	public void saveMember() throws Exception {
		for (int i = 0 ; i < 5; i++) {
			MemberVo member = new MemberVo();
			member.setMemberId("cwtt"+i+ System.currentTimeMillis());
			member.setNickName("hello"+i);
			orderDao.saveMember(member);
			if(i == 3) {
				Thread.sleep(60*1000);
			}
		}
	}
}
 