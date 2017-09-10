package com.cm.app.module.user;

import com.cm.app.common.exception.TxDataException;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

public class UserService {
    @Before({Tx.class})
	public User getAllUser() {
    	User user = User.dao.findById(1);
    	for(int i = 0 ; i < 8 ;i++) {
    		User item = new User();
    		item.set("user_name", "cwtt"+i);
    		item.set("user_mobile", "188888888888");
    		item.set("user_passwd", "123456");
    		item.set("user_status", 1);
    		if( i == 8) {
    			throw new TxDataException("采购失败");
    		}
    		item.save();
    	}
    	return user;
}
}
