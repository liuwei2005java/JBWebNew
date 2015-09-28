package com.jb.model;

import java.sql.Timestamp;
import java.util.Date;

public class JBDate {

	private String jbId;
	private int status;
	private Timestamp dateInfo;
	private String jbDate;
	private String jbTimeslong;
	public String getJbId() {
		return jbId;
	}
	public void setJbId(String jbId) {
		this.jbId = jbId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getDateInfo() {
		return dateInfo;
	}
	public void setDateInfo(Timestamp dateInfo) {
		this.dateInfo = dateInfo;
	}
	public String getJbDate() {
		return jbDate;
	}
	public void setJbDate(String jbDate) {
		this.jbDate = jbDate;
	}
	public String getJbTimeslong() {
		return jbTimeslong;
	}
	public void setJbTimeslong(String jbTimeslong) {
		this.jbTimeslong = jbTimeslong;
	}
	
}
