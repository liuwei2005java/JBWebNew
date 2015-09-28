package com.jb.dao;

import java.util.List;

import com.jb.model.SystemInfo;

public interface SystemInfoDao {

	/**
	 * �õ����еļ�¼
	 * @return
	 */
	public List<SystemInfo> getAll();
	
	/**
	 * ���»����
	 * @param systemInfo
	 */
	public void saveOrUpdate(SystemInfo systemInfo);
	
	/**
	 * ɾ��
	 * @param systemInfo
	 */
	public void delete(SystemInfo systemInfo);
	
	/**
	 * ����id��ѯ
	 * @param sysId
	 * @return
	 */
	public SystemInfo get(String sysId);
}
