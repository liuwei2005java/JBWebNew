package com.jb.model;

public class SystemInfo {

	private String sysId;
	private String url;
	private int isTest;
	private String orderParam;
	private String localCatalog;
	private int isAddCut;
	private String urlContext;
	//竞标过程小时数
	private String jbHours;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getIsTest() {
		return isTest;
	}
	public void setIsTest(int isTest) {
		this.isTest = isTest;
	}
	public String getOrderParam() {
		return orderParam;
	}
	public void setOrderParam(String orderParam) {
		this.orderParam = orderParam;
	}
	public String getLocalCatalog() {
		return localCatalog;
	}
	public void setLocalCatalog(String localCatalog) {
		this.localCatalog = localCatalog;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public int getIsAddCut() {
		return isAddCut;
	}
	public void setIsAddCut(int isAddCut) {
		this.isAddCut = isAddCut;
	}
	public String getJbHours() {
		return jbHours;
	}
	public void setJbHours(String jbHours) {
		this.jbHours = jbHours;
	}
	public String getUrlContext() {
		return urlContext;
	}
	public void setUrlContext(String urlContext) {
		this.urlContext = urlContext;
	}
	
}
