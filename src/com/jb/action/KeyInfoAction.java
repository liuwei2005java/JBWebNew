package com.jb.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.interceptor.ApplicationAware;

import com.jb.dao.KeyInfoDao;
import com.jb.dao.OCommodityDao;
import com.jb.dao.SystemInfoDao;
import com.jb.model.JBDate;
import com.jb.model.KeyInfo;
import com.jb.model.SwitchStatus;
import com.jb.model.SystemInfo;
import com.opensymphony.xwork2.ActionSupport;

public class KeyInfoAction extends ActionSupport implements ApplicationAware{

	private KeyInfo keyInfo = new KeyInfo();
	private String msg = "";
	private KeyInfoDao keyInfoDao;
	private String ids = "";
	private OCommodityDao ocommodityDao;
	private String maxBTNum = "";
	private String maxFGRNum = "";
	private String lowMaxFGNum = "";
	private String lowMaxBTNum = "";
	private String isLowFin = "";
	private String isAddCut = "";
	private SystemInfoDao systemInfoDao;
	private Map application;
	
	public String list() {
		return SUCCESS;
	}

	public String getListJson() {
		JsonConfig cfg = new JsonConfig();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("ocommodityAdjusts")
						|| name.equals("ocommodityRecords") || name.equals("switchStatuss") || name.equals("jbLogs")) {
					return true;
				} else {
					return false;
				}
			}
		});

		JSONSerializer js = new JSONSerializer();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject.put("total", 1);

		List<KeyInfo> keyInfos = keyInfoDao.getListAll();
		for (Iterator iterator = keyInfos.iterator(); iterator.hasNext();) {
			KeyInfo obj = (KeyInfo) iterator.next();
			SwitchStatus ss = ocommodityDao.getSwitchStatus(obj.getKeyId(), dateStr);
			JSONObject pinfo = JSONObject.fromObject(obj, cfg);
			try {
				pinfo.put("maxBTNum", ss.getMaxBTNum());
			} catch (Exception e) {
				pinfo.put("maxBTNum", "");
			}
			try {
				pinfo.put("maxFGRNum", ss.getMaxFGRNum());
				
			} catch (Exception e) {
				pinfo.put("maxFGRNum", "");
			}
			try {
				pinfo.put("isLoginOK", ss.getIsLoginOK());
			} catch (Exception e) {
				pinfo.put("isLoginOK", 0);
			}
			
			String jsonStr = pinfo.toString();
			jsonArray.add(jsonStr);
		}
		jsonObject.put("rows", jsonArray);
		try {
			msg = jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "check";
	}

	public String add() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		JSONObject jsonObject = new JSONObject();
		SwitchStatus ss = null;
		if (StringUtils.isBlank(keyInfo.getKeyId())) {
			keyInfo.setKeyId(null);
		}
		keyInfo.setTempImgNm("heibai"+keyInfo.getUserName()+".jpg");
		keyInfo.setLoginSourceNm("denglu"+keyInfo.getUserName()+".txt");
		keyInfoDao.saveOrupdate(keyInfo);
		
		//查询key的状态
		ss = ocommodityDao.getSwitchStatus(keyInfo.getKeyId(), dateStr);
		if(ss == null){
			ss = new SwitchStatus();
		}
		ss.setMaxBTNum(Integer.parseInt(maxBTNum));
		ss.setMaxFGRNum(Integer.parseInt(maxFGRNum));
		
		ss.setSetDate(dateStr);
		ss.setKeyInfo(keyInfo);
		try {
			ocommodityDao.saveOrUpdateSS(ss);
		} catch (Exception e) {
			// TODO: handle exception
		}
		

		jsonObject.put("success", "true");
		try {
			msg = jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "check";
	}

	public String delAll() {
		JSONObject jsonObject = new JSONObject();
		String[] idArrs = ids.split(";");
		keyInfoDao.delAll(idArrs);
		jsonObject.put("success", "true");
		msg = jsonObject.toString();
		return "check";
	}
	
	public String reLoginStatus() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		JSONObject jsonObject = new JSONObject();
		String[] idArrs = ids.split(";");
		for (int i = 0; i < idArrs.length; i++) {
			SwitchStatus ss = ocommodityDao.getSwitchStatus(idArrs[i], dateStr);
			ss.setIsLoginOK(0);
			ocommodityDao.saveOrUpdateSS(ss);
		}
		jsonObject.put("success", "true");
		msg = jsonObject.toString();
		return "check";
	}
	
	public String getStartDate(){
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String JB_DATE_TIME = "";
		try {
			JB_DATE_TIME = application.get("JB_DATE_TIME").toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		JSONObject jsonObject = new JSONObject();
		if("".equals(JB_DATE_TIME)){
			//根据日期字符串查找
			JBDate temp = keyInfoDao.getJBDateByDate(df.format(date));
			if(temp==null){
				SystemInfo systemInfo = systemInfoDao.getAll().get(0);
				DefaultHttpClient httpclient = new DefaultHttpClient();
				String xmlStr = "";
				while(1>0){
					try {
						String url = "http://"+systemInfo.getUrl()+"/servlet/getHqXML?partitionID=11&hqID=18888&h="+System.currentTimeMillis();
						HttpGet getZXFrame = new HttpGet(url);
						HttpResponse responseZXFrame = httpclient.execute(getZXFrame);
						HttpEntity entityZXFrame = responseZXFrame.getEntity();
						xmlStr = EntityUtils.toString(entityZXFrame, "gbk");
						if(xmlStr.indexOf("<S>2</S>")!=-1){
							Date d = new Date();
							//将竞标开始时间+竞标共消耗的小时数，放到application中
							Calendar ca = Calendar.getInstance();
							ca.setTime(d);
							double seconds = Double.parseDouble(systemInfo.getJbHours())*60;
						    ca.add(Calendar.MINUTE, (int)seconds);
						    application.put("JB_DATE_TIME", ca.getTime().getTime());
							
							JBDate jbDate = new JBDate();
							jbDate.setJbId(null);
							jbDate.setStatus(1);
							jbDate.setDateInfo(new Timestamp(d.getTime()));
							jbDate.setJbDate(df.format(date));
							jbDate.setJbTimeslong(String.valueOf(ca.getTime().getTime()));
							this.keyInfoDao.setJBDate(jbDate);
							
							break;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}else {
				application.put("JB_DATE_TIME", temp.getJbTimeslong());
			}
			
		}
		
		
		jsonObject.put("success", "true");
		msg = jsonObject.toString();
		return "check";
	}

	public KeyInfo getKeyInfo() {
		return keyInfo;
	}

	public void setKeyInfo(KeyInfo keyInfo) {
		this.keyInfo = keyInfo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public KeyInfoDao getKeyInfoDao() {
		return keyInfoDao;
	}

	public void setKeyInfoDao(KeyInfoDao keyInfoDao) {
		this.keyInfoDao = keyInfoDao;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public OCommodityDao getOcommodityDao() {
		return ocommodityDao;
	}

	public void setOcommodityDao(OCommodityDao ocommodityDao) {
		this.ocommodityDao = ocommodityDao;
	}

	public String getMaxBTNum() {
		return maxBTNum;
	}

	public void setMaxBTNum(String maxBTNum) {
		this.maxBTNum = maxBTNum;
	}

	public String getMaxFGRNum() {
		return maxFGRNum;
	}

	public void setMaxFGRNum(String maxFGRNum) {
		this.maxFGRNum = maxFGRNum;
	}

	public String getLowMaxFGNum() {
		return lowMaxFGNum;
	}

	public void setLowMaxFGNum(String lowMaxFGNum) {
		this.lowMaxFGNum = lowMaxFGNum;
	}

	public String getLowMaxBTNum() {
		return lowMaxBTNum;
	}

	public void setLowMaxBTNum(String lowMaxBTNum) {
		this.lowMaxBTNum = lowMaxBTNum;
	}

	public String getIsLowFin() {
		return isLowFin;
	}

	public void setIsLowFin(String isLowFin) {
		this.isLowFin = isLowFin;
	}

	public String getIsAddCut() {
		return isAddCut;
	}

	public void setIsAddCut(String isAddCut) {
		this.isAddCut = isAddCut;
	}

	public SystemInfoDao getSystemInfoDao() {
		return systemInfoDao;
	}

	public void setSystemInfoDao(SystemInfoDao systemInfoDao) {
		this.systemInfoDao = systemInfoDao;
	}

	public void setApplication(Map application) {
		// TODO Auto-generated method stub
		this.application = application;
	}

}
