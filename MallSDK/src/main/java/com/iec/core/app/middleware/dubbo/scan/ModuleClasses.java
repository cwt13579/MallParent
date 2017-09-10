package com.iec.core.app.middleware.dubbo.scan;

/**
 * 
 * @author Tumq
 *  模块类
 */
public class ModuleClasses {
	private String[] packNames;
	private String[] externClassNames;

	

	public String[] getPackNames() {
		return packNames;
	}



	public void setPackNames(String[] packNames) {
		this.packNames = packNames;
	}



	public String[] getExternClassNames() {
		return externClassNames;
	}



	public void setExternClassNames(String[] externClassNames) {
		this.externClassNames = externClassNames;
	}



	public String getPackSubPath(String packName) {
		return packName.replaceAll("\\.", "/");
	}

}
