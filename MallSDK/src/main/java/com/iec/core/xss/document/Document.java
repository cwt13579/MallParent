package com.iec.core.xss.document;

import java.util.ArrayList;
import java.util.List;


public class Document {

	
	private String controllerPath;
	private String className;
	private String controllerName;
	private String desc;
	
	private List<MethodDoc> methodList = new ArrayList<MethodDoc>();
	
	public String getControllerPath() {
		return controllerPath;
	}
	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public List<MethodDoc> getMethodList() {
		return methodList;
	}
	public void setMethodList(List<MethodDoc> methodList) {
		this.methodList = methodList;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "Document [controllerPath=" + controllerPath + ", className="
				+ className + ", controllerName=" + controllerName + ", desc="
				+ desc + ", methodList=" + methodList + "]";
	}
	
	
	
}
