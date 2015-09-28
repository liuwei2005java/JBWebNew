package com.jb.model;

public class JbiaoUtil {

	private String yzmUrl = "";
	private String loginUrl = "";
	private String orderUrl = "";
	private String submitUrl = "";
	private String loginUrlIp = "";
	private String isTest = "";
	private String zxFrame = "";
	private String zxUrl = "";
	private SystemInfo systemInfo = new SystemInfo();
	private String urlStr = "";
	private String localCatalog = "";
	private String jbFinUrl = "";
	//得到order页面地址，不包含id
	private String orderUrlNoId = "";
	
	public JbiaoUtil(){
		super();
	}
	
	public JbiaoUtil(SystemInfo systemInfo){
		this.systemInfo = systemInfo;
		urlStr = systemInfo.getUrl()+"/"+systemInfo.getUrlContext();
	}
	
	public String getZxFrame() {
		return zxFrame;
	}
	public void setZxFrame(String zxFrame) {
		this.zxFrame = zxFrame.replace("(R)", urlStr);
	}
	public String getZxUrl() {
		return zxUrl;
	}
	public void setZxUrl(String zxUrl) {
		this.zxUrl = zxUrl.replace("(R)", urlStr);
	}
	public String getLoginUrlIp() {
		return loginUrlIp;
	}
	public void setLoginUrlIp(String loginUrlIp) {
		this.loginUrlIp = loginUrlIp.replace("(R)", urlStr);
	}
	public String getYzmUrl() {
		return yzmUrl;
	}
	public void setYzmUrl(String yzmUrl) {
		this.yzmUrl = yzmUrl.replace("(R)", urlStr);
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl.replace("(R)", urlStr);
	}
	public String getOrderUrl() {
		return orderUrl;
	}
	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl.replace("(R)", urlStr)+systemInfo.getOrderParam();
	}
	public String getSubmitUrl() {
		return submitUrl;
	}
	public void setSubmitUrl(String submitUrl) {
		this.submitUrl = submitUrl.replace("(R)", urlStr);
	}
	public String getIsTest() {
		return isTest;
	}
	public void setIsTest() {
		this.isTest = String.valueOf(systemInfo.getIsTest());
	}

	public SystemInfo getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

	public String getLocalCatalog() {
		return localCatalog;
	}

	public void setLocalCatalog() {
		this.localCatalog = systemInfo.getLocalCatalog();
	}

	public String getJbFinUrl() {
		return jbFinUrl;
	}

	public void setJbFinUrl(String jbFinUrl) {
		this.jbFinUrl = jbFinUrl.replace("(R)", urlStr);
	}

	public String getOrderUrlNoId() {
		return orderUrlNoId;
	}

	public void setOrderUrlNoId(String orderUrlNoId) {
		this.orderUrlNoId = orderUrlNoId.replace("(R)", urlStr);
	}
	
}
