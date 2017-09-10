package com.berchina.vo;

import java.util.HashMap;

public class HashMapExt extends HashMap<String,Object>{

	private static final long serialVersionUID = 1L;
    private Integer pageSize;
    private Integer pageIndex;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	@Override
	public String toString() {
		return "HashMapExt [pageSize=" + pageSize + ", pageIndex=" + pageIndex
				+ "]";
	}
}
