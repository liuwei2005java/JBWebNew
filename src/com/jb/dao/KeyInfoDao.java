package com.jb.dao;

import java.util.List;

import com.jb.model.JBDate;
import com.jb.model.KeyInfo;

public interface KeyInfoDao {

	//��ѯ���е�key��¼
	public List<KeyInfo> getListAll();
	
	//��ѯ���е�δ��¼��key��¼
	public List<KeyInfo> getListAllLogin(String setdate,String keyId);
	
	//����username��ѯ
	public List<KeyInfo> getListByUserName(String userName);
	
	//�������
	public void saveOrupdate(KeyInfo keyInfo);
	
	//����idɾ����¼
	public void delAll(String[] ids);
	
	//����ID��ѯ
	public KeyInfo get(String id);
	
	/**
	 * �����û������������
	 * @return
	 */
	public KeyInfo get2ByUserNameAndPassWd(String username,String passwd);
	
	/**
	 * ���ݵ�ǰ���ڲ�ѯû������ȷ�ϵ�key
	 * @param dateStr
	 * @return
	 */
	public List<KeyInfo> isAllOK(String dateStr,String keyId);
	
	public void setJBDate(JBDate jbDate);
	
	/**
	 * ���������ַ�������
	 * @param dateStr
	 * @return
	 */
	public JBDate getJBDateByDate(String dateStr);
	
	/**
	 * �õ����п���ʹ�õ�key��Ϣ
	 * @return
	 */
	public List<KeyInfo> getListUseredAll();
}
