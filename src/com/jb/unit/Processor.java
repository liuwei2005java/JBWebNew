package com.jb.unit;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.util.EntityUtils;

import com.jb.dao.JBLogDao;
import com.jb.model.JBLog;
import com.jb.model.KeyInfo;
import com.jb.model.OCommodityAdjust;

public class Processor extends Thread{
	
	private JBLogDao jbLogDao;
	private HttpEntity entity;
	private KeyInfo keyInfo;
	private OCommodityAdjust oca;
	
	public Processor(HttpEntity entity,KeyInfo keyInfo,OCommodityAdjust oca,JBLogDao jbLogDao){
		this.jbLogDao = jbLogDao;
		this.entity = entity;
		this.keyInfo = keyInfo;
		this.oca = this.oca;
	}

	public void run() {
		run1();
	}
//	public synchronized void run1(){
	public void run1(){
		/*-----------保存数据库部分------------*/
	    String pageVal = "";
		try {
			pageVal = EntityUtils.toString(entity, "gbk");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String returnVal = pageVal.substring(pageVal.indexOf("alert('")+7,pageVal.indexOf("')"));
	    System.out.println(returnVal);
	    SimpleDateFormat sfInfo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
	    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		JBLog jbLog = new JBLog();
	    jbLog.setBcode(oca.getBcode());
	    jbLog.setBstorage(oca.getBstorage());
	    jbLog.setBtype(oca.getBtype());
	    jbLog.setDealNum(oca.getDealNum());
	    jbLog.setDelayTime(oca.getDelayTime());
	    jbLog.setOcaId(oca.getOcaId());
	    jbLog.setLowPrice(oca.getLowPrice());
	    jbLog.setStartPrice(oca.getStartPrice());
	    jbLog.setOrderNo(oca.getOrderNo());
	    jbLog.setJbId(null);
	    jbLog.setDateInfo(sfInfo.format(new Date()));
	    jbLog.setJbDate(sf.format(new Date()));
	    jbLog.setKeyInfo(keyInfo);
	    if(returnVal.indexOf("报单成功")!= -1 ){
	    	jbLog.setStatus(1);
	    	jbLog.setFailInfo("");
	    }else{
	    	jbLog.setStatus(0);
	    	jbLog.setFailInfo(returnVal);
	    }
	    jbLogDao.saveLog(jbLog);
	    
	    
	    /*-----------保存数据库部分------------*/
	}
	

}
