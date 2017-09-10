package com.iec.core.xss.document;

import java.util.ArrayList;
import java.util.List;

public class MethodDoc {

	private String methodName;
	private String path;
	private String desc;
	private List<String> paraList = new ArrayList<String>();
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<String> getParaList() {
		return paraList;
	}
	public void setParaList(List<String> paraList) {
		this.paraList = paraList;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "MethodDoc [methodName=" + methodName + ", path=" + path
				+ ", desc=" + desc + ", paraList=" + paraList + "]";
	}

	
	
}
