package com.iec.core.app.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iec.core.app.base.BaseService;

@Repository
public class CacheService  extends BaseService{
	@Autowired
	private CacheDao cacheDao;
	
	public String getSysHtml(){
		return this.retJson(this.cacheDao.getSysHtml()); 
	}
}
