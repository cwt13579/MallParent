package com.iec.core.app.middleware.dubbo.scan;

/**
 * 
 * @author Tumq
 * @Date 2015年1月12日
 * @describe 唯一标识后台DUBBO服务
 */
public class DubboServiceKey {
	private String interfaceName;
	private String serviceGroup;

	public DubboServiceKey(String interfaceName, String serviceGroup) {
		super();
		this.interfaceName = interfaceName;
		this.serviceGroup = serviceGroup;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((interfaceName == null) ? 0 : interfaceName.hashCode());
		result = prime * result
				+ ((serviceGroup == null) ? 0 : serviceGroup.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DubboServiceKey other = (DubboServiceKey) obj;
		if (interfaceName == null) {
			if (other.interfaceName != null)
				return false;
		} else if (!interfaceName.equals(other.interfaceName))
			return false;
		if (serviceGroup == null) {
			if (other.serviceGroup != null)
				return false;
		} else if (!serviceGroup.equals(other.serviceGroup))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return interfaceName + ":" + serviceGroup;
	}

}
