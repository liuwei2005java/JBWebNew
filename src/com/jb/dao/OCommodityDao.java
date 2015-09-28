package com.jb.dao;

import java.util.List;
import java.util.Map;

import com.jb.model.OCommodityAdjust;
import com.jb.model.OCommodityRecord;
import com.jb.model.OCommodityRecordAll;
import com.jb.model.SwitchStatus;

public interface OCommodityDao {

	/**
	 * ����key��ID����ǰ�����ں��Ƿ񾺱굽�׼۲������е���ѡ��Ʒ
	 * @param keyId
	 * @param dateStr
	 * @param isAddCut
	 * @return
	 */
	public List<OCommodityAdjust> getAdjustByKeyIdAndDate(String keyId,String dateStr,int isLow);
	
	/**
	 * ����key��id�͵�ǰ�����ڣ���record���������ӵ�Adjust��
	 * @param keyId
	 * @param dateStr
	 */
	public boolean recordAll2Adjust(String keyId,String dateStr);
	
	/**
	 * �����ѡ��Ʒԭʼ
	 * @param ocr
	 */
	public void saveRecord(OCommodityRecord ocr);
	
	/**
	 * ������ѡ��Ʒ����
	 * @param oca
	 */
	public void updateAdjust(OCommodityAdjust oca);
	
	//����idɾ����¼
	public void delAll(String[] ids);
	
	
	/**
	 * ��ӻ���¿���״̬��
	 * @param switchStatus
	 */
	public void saveOrUpdateSS(SwitchStatus switchStatus);
	
	/**
	 * ���ݵ�ǰ���ں�key��ʶ����
	 * @param keyId
	 * @param dateStr
	 * @return
	 */
	public SwitchStatus getSwitchStatus(String keyId,String dateStr);
	
	/**
	 * ����keyId������ɾ��ԭʼ��
	 * @param keyId
	 * @param dateStr
	 */
	public void delRecord(String keyId, String dateStr);
	
	/**
	 * ����keyId������ɾ��������
	 * @param keyId
	 * @param dateStr
	 */
	public void delAdjust(String keyId, String dateStr);
	
	/**
	 * �������б�
	 * @param ocrAll
	 */
	public void saveRecordAll(OCommodityRecordAll ocrAll);
	
	
	/**
	 * ִ��sql���   ���»�ɾ��
	 * @param sql
	 */
	public void execSql(String sql);
	
	public void insertZX(List<Map> zxList,String keyId);
	
	public List<Map> getFenLei(String keyId);
	
	public void updateZXPriceByBCode(String bcode,String  price,String  vipCode,String isYX);
	
	public Map getZXListByKeyIdFenlei(String keyId,String fenleiCode);
	
	/**
	 * ���±��״̬,�ѱ�����δ����,��bcodeΪnullʱ,��������
	 * @param keyId
	 * @param status
	 * @param dateStr
	 * @param bcode
	 */
	public void updateOCommodityAdjustStatus(String keyId,String status,String dateStr,String bcode);
}
