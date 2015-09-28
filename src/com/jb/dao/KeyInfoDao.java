package com.jb.dao;

import java.util.List;

import com.jb.model.JBDate;
import com.jb.model.KeyInfo;

public interface KeyInfoDao {

	//查询所有的key记录
	public List<KeyInfo> getListAll();
	
	//查询所有的未登录的key记录
	public List<KeyInfo> getListAllLogin(String setdate,String keyId);
	
	//根据username查询
	public List<KeyInfo> getListByUserName(String userName);
	
	//保存更新
	public void saveOrupdate(KeyInfo keyInfo);
	
	//根据id删除记录
	public void delAll(String[] ids);
	
	//根据ID查询
	public KeyInfo get(String id);
	
	/**
	 * 根据用户名和密码查找
	 * @return
	 */
	public KeyInfo get2ByUserNameAndPassWd(String username,String passwd);
	
	/**
	 * 根据当前日期查询没有最终确认的key
	 * @param dateStr
	 * @return
	 */
	public List<KeyInfo> isAllOK(String dateStr,String keyId);
	
	public void setJBDate(JBDate jbDate);
	
	/**
	 * 根据日期字符串查找
	 * @param dateStr
	 * @return
	 */
	public JBDate getJBDateByDate(String dateStr);
	
	/**
	 * 得到所有可以使用的key信息
	 * @return
	 */
	public List<KeyInfo> getListUseredAll();
}
