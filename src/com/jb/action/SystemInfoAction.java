package com.jb.action;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import com.jb.dao.SystemInfoDao;
import com.jb.model.SystemInfo;
import com.opensymphony.xwork2.ActionSupport;

public class SystemInfoAction extends ActionSupport{

	private SystemInfoDao systemInfoDao;
	private SystemInfo systemInfo = new SystemInfo();
	private String msg = "";
	private String ids = "";
	
	public String list(){
		
		return SUCCESS;
	}
	
	public String delAll(){
		JSONObject jsonObject = new JSONObject();
		String[] idsArrs = ids.split(";");
		for (int i = 0; i < idsArrs.length; i++) {
			SystemInfo ss = systemInfoDao.get(idsArrs[i]);
			systemInfoDao.delete(ss);
		}
		
		jsonObject.put("success", "true");
		try {
			msg = jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "check";
	}
	
	public String add(){
		JSONObject jsonObject = new JSONObject();

		if (StringUtils.isBlank(systemInfo.getSysId())) {
			systemInfo.setSysId(null);
		}
		systemInfoDao.saveOrUpdate(systemInfo);

		jsonObject.put("success", "true");
		try {
			msg = jsonObject.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		return "check";
	}
	
	public String getListJson() {
		JSONSerializer js = new JSONSerializer();
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject.put("total", 1);

		List<SystemInfo> systemInfos = systemInfoDao.getAll();
		for (Iterator iterator = systemInfos.iterator(); iterator.hasNext();) {
			SystemInfo obj = (SystemInfo) iterator.next();
			JSONObject pinfo = JSONObject.fromObject(obj);
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

	public SystemInfoDao getSystemInfoDao() {
		return systemInfoDao;
	}

	public void setSystemInfoDao(SystemInfoDao systemInfoDao) {
		this.systemInfoDao = systemInfoDao;
	}

	public SystemInfo getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(SystemInfo systemInfo) {
		this.systemInfo = systemInfo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
