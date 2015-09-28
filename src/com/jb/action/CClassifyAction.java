package com.jb.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import com.jb.dao.CClassCityDao;
import com.jb.dao.KeyInfoDao;
import com.jb.dao.OCommodityDao;
import com.jb.model.CommodityClassify;
import com.jb.model.KeyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CClassifyAction extends ActionSupport {

	private List<KeyInfo> keyInfos = new ArrayList<KeyInfo>();
	private String msg = "";
	private KeyInfoDao keyInfoDao;
	private OCommodityDao ocommodityDao;
	private CommodityClassify commodityClassify = new CommodityClassify();
	private CClassCityDao cclassCityDao;
	private String ids = "";
	private String keyId = "";

	public String list() {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		String username = session.get("username").toString();
		keyInfos = keyInfoDao.getListByUserName(username);
		return SUCCESS;
	}

	public String getListJson() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String currentDate = df.format(new Date());
		
		JsonConfig cfg = new JsonConfig();
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("ocommodityAdjusts")) {
					return true;
				} else {
					return false;
				}
			}
		});

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject.put("total", 1);

		List<CommodityClassify> cclassifyList = cclassCityDao.getALLByCurrentDateKeyId(currentDate, keyId);
		for (Iterator iterator = cclassifyList.iterator(); iterator.hasNext();) {
			CommodityClassify obj = (CommodityClassify) iterator.next();
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

	public String modify() {
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String currentDate = df.format(new Date());
		cclassCityDao.saveOrUpdate(commodityClassify);
		
		//更新自选商品的底价
		cclassCityDao.updateLowPrice(currentDate, commodityClassify.getKeyId(),commodityClassify.getCcId());
		
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
		for (int i = 0; i < idArrs.length; i++) {
			cclassCityDao.delCClass(idArrs[i]);
		}
		jsonObject.put("success", "true");
		msg = jsonObject.toString();
		return "check";
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

	public List<KeyInfo> getKeyInfos() {
		return keyInfos;
	}

	public void setKeyInfos(List<KeyInfo> keyInfos) {
		this.keyInfos = keyInfos;
	}

	public OCommodityDao getOcommodityDao() {
		return ocommodityDao;
	}

	public void setOcommodityDao(OCommodityDao ocommodityDao) {
		this.ocommodityDao = ocommodityDao;
	}

	public CommodityClassify getCommodityClassify() {
		return commodityClassify;
	}

	public void setCommodityClassify(CommodityClassify commodityClassify) {
		this.commodityClassify = commodityClassify;
	}

	public CClassCityDao getCclassCityDao() {
		return cclassCityDao;
	}

	public void setCclassCityDao(CClassCityDao cclassCityDao) {
		this.cclassCityDao = cclassCityDao;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

}
