package com.jb.model;

import java.io.Serializable;

public class OCommodityRecord implements Serializable{

	private String ocrId;
	private String bcode;
	private String btype;
	private String bstorage;
	private String dealNum; 
	private String startPrice;
	private String ocrDate; 
	//最新价格
	private String zxjg;
	private int orderNo;
	private KeyInfo keyInfo;
	
	private String isOK;
	
	//最高价格
	private String maxPrice;
	//状态
	private String status;
	
	public String getOcrId() {
		return ocrId;
	}
	public void setOcrId(String ocrId) {
		this.ocrId = ocrId;
	}
	public String getBcode() {
		return bcode;
	}
	public void setBcode(String bcode) {
		this.bcode = bcode;
	}
	public String getBtype() {
		return btype;
	}
	public void setBtype(String btype) {
		this.btype = btype;
	}
	public String getBstorage() {
		return bstorage;
	}
	public void setBstorage(String bstorage) {
		this.bstorage = bstorage;
	}
	public String getDealNum() {
		return dealNum;
	}
	public void setDealNum(String dealNum) {
		this.dealNum = dealNum;
	}
	public String getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}
	public String getOcrDate() {
		return ocrDate;
	}
	public void setOcrDate(String ocrDate) {
		this.ocrDate = ocrDate;
	}
	public KeyInfo getKeyInfo() {
		return keyInfo;
	}
	public void setKeyInfo(KeyInfo keyInfo) {
		this.keyInfo = keyInfo;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getZxjg() {
		return zxjg;
	}
	public void setZxjg(String zxjg) {
		this.zxjg = zxjg;
	}
	public String getIsOK() {
		return isOK;
	}
	public void setIsOK(String isOK) {
		this.isOK = isOK;
	}
	public String getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
