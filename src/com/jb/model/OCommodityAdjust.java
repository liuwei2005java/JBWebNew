package com.jb.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class OCommodityAdjust implements Serializable{

	private String ocaId;
	private String bcode;
	private String btype;
	private String bstorage;
	private String dealNum; 
	private String startPrice;
	private String ocaDate;
	private String lowPrice;
	private String delayTime;
	private int orderNo;
	private String isLow;
	private String addLowPrice;
	private KeyInfo keyInfo;
	private CommodityClassify commodityClassify;
	private String status;
	public String getOcaId() {
		return ocaId;
	}
	public void setOcaId(String ocaId) {
		this.ocaId = ocaId;
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
	public String getOcaDate() {
		return ocaDate;
	}
	public void setOcaDate(String ocaDate) {
		this.ocaDate = ocaDate;
	}
	public String getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}
	public String getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
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
	public CommodityClassify getCommodityClassify() {
		return commodityClassify;
	}
	public void setCommodityClassify(CommodityClassify commodityClassify) {
		this.commodityClassify = commodityClassify;
	}
	public String getAddLowPrice() {
		return addLowPrice;
	}
	public void setAddLowPrice(String addLowPrice) {
		this.addLowPrice = addLowPrice;
	}
	public String getIsLow() {
		return isLow;
	}
	public void setIsLow(String isLow) {
		this.isLow = isLow;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
