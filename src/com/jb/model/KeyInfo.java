package com.jb.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class KeyInfo implements Serializable{

	private String keyId;
	private String userName;
	private String passWord;
	private String keyWd;
	private String submit;
	private String kcode;
	private String tempImgNm;
	private String loginSourceNm;
	private int isUse;
	private Set<OCommodityAdjust> ocommodityAdjusts = new HashSet<OCommodityAdjust>(0);
	private Set<OCommodityRecord> ocommodityRecords = new HashSet<OCommodityRecord>(0);
	private Set<SwitchStatus> switchStatuss = new HashSet<SwitchStatus>(0);
	private Set<JBLog> jbLogs = new HashSet<JBLog>(0);
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getKeyWd() {
		return keyWd;
	}
	public void setKeyWd(String keyWd) {
		this.keyWd = keyWd;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	public String getKcode() {
		return kcode;
	}
	public void setKcode(String kcode) {
		this.kcode = kcode;
	}
	public String getTempImgNm() {
		return tempImgNm;
	}
	public void setTempImgNm(String tempImgNm) {
		this.tempImgNm = tempImgNm;
	}
	public String getLoginSourceNm() {
		return loginSourceNm;
	}
	public void setLoginSourceNm(String loginSourceNm) {
		this.loginSourceNm = loginSourceNm;
	}
	public Set<OCommodityAdjust> getOcommodityAdjusts() {
		return ocommodityAdjusts;
	}
	public void setOcommodityAdjusts(Set<OCommodityAdjust> ocommodityAdjusts) {
		this.ocommodityAdjusts = ocommodityAdjusts;
	}
	public Set<OCommodityRecord> getOcommodityRecords() {
		return ocommodityRecords;
	}
	public void setOcommodityRecords(Set<OCommodityRecord> ocommodityRecords) {
		this.ocommodityRecords = ocommodityRecords;
	}
	public Set<SwitchStatus> getSwitchStatuss() {
		return switchStatuss;
	}
	public void setSwitchStatuss(Set<SwitchStatus> switchStatuss) {
		this.switchStatuss = switchStatuss;
	}
	public Set<JBLog> getJbLogs() {
		return jbLogs;
	}
	public void setJbLogs(Set<JBLog> jbLogs) {
		this.jbLogs = jbLogs;
	}
	public int getIsUse() {
		return isUse;
	}
	public void setIsUse(int isUse) {
		this.isUse = isUse;
	}
	
}
