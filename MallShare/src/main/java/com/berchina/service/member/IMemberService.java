
package com.berchina.service.member; 

import java.util.List;

import com.berchina.common.BerException;

/** 

 * @author 作者姓名  cwt

 * @version 创建时间：2017年5月5日 上午10:33:11 

 * 会员接口

 */

public interface IMemberService {

	List findMember() throws BerException;
}
 