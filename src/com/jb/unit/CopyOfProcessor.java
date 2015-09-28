package com.jb.unit;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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

public class CopyOfProcessor implements Runnable {
	
	private JBLogDao jbLogDao;
	
	String cs = null;
	String urlhost = "";
	DefaultHttpClient httpclient = null;
	List<Cookie> cookies = null;
	HttpResponse httpResponse = null;
	HttpEntity httpEntity = null;
	long sleepNum = 0;
	KeyInfo keyInfo = new KeyInfo();
	OCommodityAdjust oca = new OCommodityAdjust();
	
	List<NameValuePair> qparams = new ArrayList<NameValuePair>();
	
	public CopyOfProcessor(String temp,String urlTemp,DefaultHttpClient httpclientTemp,List<Cookie> cookiesTemp,HttpResponse httpResponseTemp,HttpEntity httpEntityTemp,long sleepNum,KeyInfo keyInfo,OCommodityAdjust oca,JBLogDao jbLogDao){
		cs = temp;
		urlhost = urlTemp;
		httpclient = httpclientTemp;
		cookies = cookiesTemp;
		httpResponse = httpResponseTemp;
		httpEntity = httpEntityTemp;
		this.sleepNum = sleepNum;
		this.keyInfo = keyInfo;
		this.oca = oca;
		this.jbLogDao = jbLogDao;
	}

	public void run() {
		run1();
	}
//	public synchronized void run1(){
	public void run1(){
		String url = urlhost+"?"+cs;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
		try {
//			kx.enterComponentContent(url, "Home page", httpclient,
//			        cookies, httpResponse, httpEntity);
			httpclient = new DefaultHttpClient();
			 System.out.println("--- Enter: Home page ---");
			    System.out.println("--- Url:   " + url + " ---");
			    String codeStr = "";
			    try {
			    	codeStr = cs.substring(cs.indexOf("code="),cs.lastIndexOf("&totalAmount"));
			    	
				} catch (Exception e) {
					// TODO: handle exception
				}
			    setCookie(httpclient, cookies);
			    HttpGet httpget = new HttpGet(url);
			    //等待时间
//			    System.out.println("start="+formatter.format(new Date()));
			    try {
			    	Thread.sleep(sleepNum);
				} catch (Exception e) {
					// TODO: handle exception
				}
//				System.out.println("end="+formatter.format(new Date()));
			    HttpResponse response12 = httpclient.execute(httpget);
//			    System.out.println("execute="+formatter.format(new Date()));
			    /**
			     * 经过测试，开始的时间start，和执行后的时间，相差会多100毫秒，在计算的时候，要抛出这100毫秒
			     */
//			    System.out.println(formatter.format(new Date()));
			    HttpEntity entity33 = response12.getEntity();
			    /*-----------保存数据库部分------------*/
			    String pageVal = EntityUtils.toString(entity33, "gbk");
			    String returnVal = pageVal.substring(pageVal.indexOf("alert('")+7,pageVal.indexOf("')"));
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
			    if("报单成功".equals(returnVal)){
			    	jbLog.setStatus(1);
			    	jbLog.setFailInfo("");
			    }else{
			    	jbLog.setStatus(0);
			    	jbLog.setFailInfo(returnVal);
			    }
			    jbLogDao.saveLog(jbLog);
			    
			    /*-----------保存数据库部分------------*/
			    
			    /*----------------保存文件------------------*/
//			    FileOutputStream bos1= new FileOutputStream(jbiaoUtil.getLocalCatalog()+codeStr+"(日期)"+formatter.format(new Date())+".txt");
//			    entity33.writeTo(bos1);
			    /*------------保存文件-----------------*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setCookie(DefaultHttpClient httpclient, List<Cookie> cookies) {
	    if (cookies.isEmpty()) {
	      System.out.println("Cookie is empty.");
	      return;
	    } else {
	      for (int i = 0; i < cookies.size(); i++) {
	        System.out.println((i + 1) + " - " + cookies.get(i).toString());
	        httpclient.getCookieStore().addCookie(cookies.get(i));
	      }
	      System.out.println();
	    }
	  }

}
