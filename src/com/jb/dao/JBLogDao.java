package com.jb.dao;

import java.util.List;

import com.jb.model.JBLog;

public interface JBLogDao {

	/**
	 * ����key��ID�����ڲ�ѯ
	 * @param keyId
	 * @param dateStr
	 * @return
	 */
	public List<JBLog> getList2KeyIdAndOcaId(String keyId,String dateStr);
	
	/**
	 * ����
	 * @param jbLog
	 */
	public void saveLog(JBLog jbLog);
	
	/**
	 * ɾ��
	 * @param jbLog
	 */
	public void del(JBLog jbLog);
	
	/**
	 * ����ID��ѯ
	 * @param jbId
	 * @return
	 */
	public JBLog get(String jbId);
	
	/**
	 * ����key�ı�ʶ���������ڡ����״̬��������Ϣ�����ࡢ���ص��ѯ
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
