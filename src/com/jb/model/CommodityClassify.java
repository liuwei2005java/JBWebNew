package com.jb.model;

import java.util.HashSet;
import java.util.Set;

public class CommodityClassify {
 
	private String ccId;
	private String btype;
	private String bstorage;
	private String startPrice;
	private String lowPrice;
	private int jxnum;
	private int classLevel;
	private int levelOrder;
	private String currentDate;
	private String keyId;
	private String addLowPrice;
	private String dealNum;
	private String ccCode;
	private Set<OCommodityAdjust> ocommodityAdjusts = new HashSet<OCommodityAdjust>(0);
	public String getCcId() {
		return ccId;
	}
	public void setCcId(String ccId) {
		this.ccId = ccId;
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
	public String getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}
	public String getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	public int getClassLevel() {
		return classLevel;
	}
	public void setClassLevel(int classLevel) {
		this.classLevel = classLevel;
	}
	public int getLevelOrder() {
		return levelOrder;
	}
	public void setLevelOrder(int levelOrder) {
		this.levelOrder = levelOrder;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public Set<OCommodityAdjust> getOcommodityAdjusts() {
		return ocommodityAdjusts;
	}
	public void setOcommodityAdjusts(Set<OCommodityAdjust> ocommodityAdjusts) {
		this.ocommodityAdjusts = ocommodityAdjusts;
	}
	public int getJxnum() {
		return jxnum;
	}
	public void setJxnum(int jxnum) {
		this.jxnum = jxnum;
	}
	public String getAddLowPrice() {
		return addLowPrice;
	}
	public void setAddLowPrice(String addLowPrice) {
		this.addLowPrice = addLowPrice;
	}
	public String getDealNum() {
		return dealNum;
	}
	public void setDealNum(String dealNum) {
		this.dealNum = dealNum;
	}
	public String getCcCode() {
		return ccCode;
	}
	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}
	
}
