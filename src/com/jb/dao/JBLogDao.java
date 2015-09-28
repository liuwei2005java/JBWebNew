package com.jb.dao;

import java.util.List;

import com.jb.model.JBLog;

public interface JBLogDao {

	/**
	 * 根据key的ID和日期查询
	 * @param keyId
	 * @param dateStr
	 * @return
	 */
	public List<JBLog> getList2KeyIdAndOcaId(String keyId,String dateStr);
	
	/**
	 * 保存
	 * @param jbLog
	 */
	public void saveLog(JBLog jbLog);
	
	/**
	 * 删除
	 * @param jbLog
	 */
	public void del(JBLog jbLog);
	
	/**
	 * 根据ID查询
	 * @param jbId
	 * @return
	 */
	public JBLog get(String jbId);
	
	/**
	 * 根据key的标识、竞标日期、标的状态、错误信息、种类、库存地点查询
	 * @param keyId
	 * @param dateStr
	 * @param status
	 * @param failInfo
	 * @param btype
	 * @param bstorage
	 * @return
	 */
	public List<JBLog> getListByParams(String keyId,String dateStr,String status,String failInfo,String btype,String bstorage);
}
