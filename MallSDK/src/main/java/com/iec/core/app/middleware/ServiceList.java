package com.iec.core.app.middleware;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * 
 * @author Tumq
 *  app服务列表集合
 */
public class ServiceList {
	private List<Observer> serverList;

	public ServiceList() {
		this.serverList = new ArrayList<Observer>();
	}

	public List<Observer> getServerList() {
		return serverList;
	}

	public void setServerList(List<Observer> serverList) {
		this.serverList = serverList;
	}
}
