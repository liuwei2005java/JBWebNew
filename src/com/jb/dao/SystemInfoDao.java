package com.jb.dao;

import java.util.List;

import com.jb.model.SystemInfo;

public interface SystemInfoDao {

	/**
	 * 得到所有的记录
	 * @return
	 */
	public List<SystemInfo> getAll();
	
	/**
	 * 更新或添加
	 * @param systemInfo
	 */
	public void saveOrUpdate(SystemInfo systemInfo);
	
	/**
	 * 删除
	 * @param systemInfo
	 */
	public void delete(SystemInfo systemInfo);
	
	/**
	 * 根据id查询
	 * @param sysId
	 * @return
	 */
	public SystemInfo get(String sysId);
}
