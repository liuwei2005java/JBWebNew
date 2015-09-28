package com.jb.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.jb.dao.KeyInfoDao;
import com.jb.model.KeyInfo;
import com.jb.unit.UdpGetClientMacAddr;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private String username = "";
	private String passwd = "";
	private String msg = "";
	private KeyInfoDao keyInfoDao;
	
	public String login() {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		//得到客户端IP
		String ip = request.getRemoteAddr();
		String result = "";
		if("lw".equals(username) && "1".equals(passwd)){
			//mac地址
			UdpGetClientMacAddr mac = null;
			try {
				mac = new UdpGetClientMacAddr(ip);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String macStr = "";
			try {
				if(ip.indexOf("127.0.0.1")==-1){
					macStr = mac.GetRemoteMacAddr();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if("00:22:FA:88:D2:F2".equals(macStr) || "00:24:7E:12:C6:E3".equals(macStr) || ip.indexOf("127.0.0.1")!=-1){
				session.put("username", "lw");
				setMenu2Session(true,session);
				result = SUCCESS;
			}else{
				result = "error";
				msg = "error";
			}
			
		}else{
			KeyInfo keyInfo = keyInfoDao.get2ByUserNameAndPassWd(username, passwd);
			if(keyInfo == null){
				result = "error";
				msg = "error";
			}else {
				session.put("username", username);
				session.put("KEYINFO-USERNAME", username);
				setMenu2Session(false,session);
				result = SUCCESS;
			}
		}
		
		return result;
	}
	
	private void setMenu2Session(boolean isAdmin,Map session){
		JSONArray jsonObjectArrs = new JSONArray();
		JSONArray objArray = new JSONArray();
		JSONObject attributes = new JSONObject();
		JSONObject obj = new JSONObject();
		obj.put("text", "基础类设置");
		obj.put("state", "open");
		JSONObject child = new JSONObject();
		
		if(isAdmin){
			child.put("id", "jb1");
			child.put("text", "Key信息维护");
			attributes.put("url", "keyInfo_list.action");
			child.put("attributes", attributes);
			objArray.add(child);
			
			child = new JSONObject();
			child.put("id", "jb2");
			child.put("text", "自选商品");
			attributes = new JSONObject();
			attributes.put("url", "oca_list.action");
			child.put("attributes", attributes);
			objArray.add(child);
			
			child = new JSONObject();
			child.put("id", "jb5");
			child.put("text", "自选商品分类");
			attributes = new JSONObject();
			attributes.put("url", "cclass_list.action");
			child.put("attributes", attributes);
			objArray.add(child);
			
			child = new JSONObject();
			child.put("id", "jb3");
			child.put("text", "系统设置");
			attributes = new JSONObject();
			attributes.put("url", "systemInfo_list.action");
			child.put("attributes", attributes);
			objArray.add(child);
			
			child = new JSONObject();
			child.put("id", "jb4");
			child.put("text", "竞标日志");
			attributes = new JSONObject();
			attributes.put("url", "jbLog_list.action");
			child.put("attributes", attributes);
			objArray.add(child);
		}else {
			child = new JSONObject();
			child.put("id", "jb2");
			child.put("text", "自选商品");
			attributes = new JSONObject();
			attributes.put("url", "oca_list.action");
			child.put("attributes", attributes);
			objArray.add(child);
			
			child = new JSONObject();
			child.put("id", "jb5");
			child.put("text", "自选商品分类");
			attributes = new JSONObject();
			attributes.put("url", "cclass_list.action");
			child.put("attributes", attributes);
			objArray.add(child);
		}
		
		obj.put("children", objArray);
		
		jsonObjectArrs.add(obj);
		System.out.println(jsonObjectArrs.toString());
		session.put("menuJson", jsonObjectArrs.toString());
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
	
}
