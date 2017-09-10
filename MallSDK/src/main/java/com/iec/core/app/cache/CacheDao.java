package com.iec.core.app.cache;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.iec.core.app.base.BaseDao;

@Repository
public class CacheDao  extends BaseDao{

//	public List<Map<String, Object>> getArea(Long parentid) {
//		String sql = "select id,name,is_munic from mcs_area where pid = ? ";
//		return this.getJdbcTemplate().queryForList(sql,parentid);
//	}
	
	public List<Map<String,Object>> getSysHtml(){
		String sql = "select req_path,req_param,run_type from mcs_sys_html ";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
}
