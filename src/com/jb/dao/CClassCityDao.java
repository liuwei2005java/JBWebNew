package com.jb.dao;

import java.util.List;

import com.jb.model.CommodityClassify;

public interface CClassCityDao {

	/**
	 * ���ݵ�ǰ���ں�keyId��ѯ���з���
	 * @param currentDate
	 * @param keyId
	 * @return
	 */
	public List<CommodityClassify> getALLByCurrentDateKeyId(String currentDate,String keyId);
	
	/**
	 * ���ݵ�ǰ���ں�keyId����ѡ��Ʒ���е����ݣ���ӵ��������
	 * ��distinct t.b_storage,t.b_type,t.start_price�������ظ�
	 * @param currentDate
	 * @param keyId
	 */
	public boolean setCClassifyByAdjust(String currentDate,String keyId);
	
	/**
	 * ��ӻ����
	 * @param commodityClassify
	 */
	public void saveOrUpdate(CommodityClassify commodityClassify);
	
	/**
	 * ���ݷ���Idɾ�����࣬��ͬʱɾ����ѡ��Ʒ����Ķ�Ӧ��
	 * @param ccId
	 */
	public void delCClass(String ccId);
	
	/**
	 * ����id��ѯ
	 * @param ccId
	 * @return
	 */
	public CommodityClassify getCClass(String ccId);
	
	/**
	 * ���ݵ�ǰ���ڡ�keyid�ͷ���ID������ѡ��Ʒ�ĵ׼�
	 * @param currentDate
	 * @param keyId
	 * @param ccId
	 */
	public void updateLowPrice(String currentDate,String keyId,String ccId);
}
