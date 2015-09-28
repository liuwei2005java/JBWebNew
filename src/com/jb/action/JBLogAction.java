package com.jb.action;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import com.jb.dao.JBLogDao;
import com.jb.dao.KeyInfoDao;
import com.jb.dao.OCommodityDao;
import com.jb.dao.SystemInfoDao;
import com.jb.model.JBLog;
import com.jb.model.JbiaoUtil;
import com.jb.model.KeyInfo;
import com.jb.model.OCommodityAdjust;
import com.jb.model.OCommodityRecord;
import com.jb.model.SwitchStatus;
import com.jb.model.SystemInfo;
import com.jb.unit.ProcessorMain;
import com.jb.unit.PropertiesUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class JBLogAction extends ActionSupport {

	private List<KeyInfo> keyInfos = new ArrayList<KeyInfo>();
	private String msg = "";
	private KeyInfoDao keyInfoDao;
	private String keyId = "";
	private String status = "1";
	private String failInfo = "";
	private String btype = "";
	private String bstorage = "";
	private JBLogDao jbLogDao;

	public String list() {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		String username = session.get("username").toString();
		keyInfos = keyInfoDao.getListByUserName(username);
		return SUCCESS;
	}
	
	public String getListJson() {
		JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("keyInfo")) {
					return true;
				} else {
					return false;
				}
			}
		});

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject.put("total", 1);

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String dateStr = df.format(new Date());
		
		List<JBLog> jblogs = jbLogDao.getListByParams(keyId, dateStr, status, failInfo, btype, bstorage);
		for (Iterator iterator = jblogs.iterator(); iterator.hasNext();) {
			JBLog obj = (JBLog) iterator.next();
			JSONObject pinfo = JSONObject.fromObject(obj, cfg);
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

	public List<KeyInfo> getKeyInfos() {
		return keyInfos;
	}

	public void setKeyInfos(List<KeyInfo> keyInfos) {
		this.keyInfos = keyInfos;
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

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFailInfo() {
		return failInfo;
	}

	public void setFailInfo(String failInfo) {
		this.failInfo = failInfo;
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

	public JBLogDao getJbLogDao() {
		return jbLogDao;
	}

	public void setJbLogDao(JBLogDao jbLogDao) {
		this.jbLogDao = jbLogDao;
	}
	
}
