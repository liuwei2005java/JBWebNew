package com.jb.model;

public class SwitchStatus {

	private String ssId;
	private int isInfoCQ;
	private int isTZOK;
	private String setDate;
	private int maxFGRNum;
	private int isLoginOK;
	private int maxBTNum;
	private KeyInfo keyInfo;
	public String getSsId() {
		return ssId;
	}
	public void setSsId(String ssId) {
		this.ssId = ssId;
	}
	public int getIsInfoCQ() {
		return isInfoCQ;
	}
	public void setIsInfoCQ(int isInfoCQ) {
		this.isInfoCQ = isInfoCQ;
	}
	public int getIsTZOK() {
		return isTZOK;
	}
	public void setIsTZOK(int isTZOK) {
		this.isTZOK = isTZOK;
	}
	public String getSetDate() {
		return setDate;
	}
	public void setSetDate(String setDate) {
		this.setDate = setDate;
	}
	public KeyInfo getKeyInfo() {
		return keyInfo;
	}
	public void setKeyInfo(KeyInfo keyInfo) {
		this.keyInfo = keyInfo;
	}
	public int getMaxFGRNum() {
		return maxFGRNum;
	}
	public void setMaxFGRNum(int maxFGRNum) {
		this.maxFGRNum = maxFGRNum;
	}
	public int getIsLoginOK() {
		return isLoginOK;
	}
	public void setIsLoginOK(int isLoginOK) {
		this.isLoginOK = isLoginOK;
	}
	public int getMaxBTNum() {
		return maxBTNum;
	}
	public void setMaxBTNum(int maxBTNum) {
		this.maxBTNum = maxBTNum;
	}
	
}
