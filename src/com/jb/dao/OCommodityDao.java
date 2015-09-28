package com.jb.dao;

import java.util.List;
import java.util.Map;

import com.jb.model.OCommodityAdjust;
import com.jb.model.OCommodityRecord;
import com.jb.model.OCommodityRecordAll;
import com.jb.model.SwitchStatus;

public interface OCommodityDao {

	/**
	 * 根据key的ID、当前的日期和是否竞标到底价查找所有的自选商品
	 * @param keyId
	 * @param dateStr
	 * @param isAddCut
	 * @return
	 */
	public List<OCommodityAdjust> getAdjustByKeyIdAndDate(String keyId,String dateStr,int isLow);
	
	/**
	 * 根据key的id和当前的日期，将record里的数据添加到Adjust里
	 * @param keyId
	 * @param dateStr
	 */
	public boolean recordAll2Adjust(String keyId,String dateStr);
	
	/**
	 * 添加自选商品原始
	 * @param ocr
	 */
	public void saveRecord(OCommodityRecord ocr);
	
	/**
	 * 更新自选商品调整
	 * @param oca
	 */
	public void updateAdjust(OCommodityAdjust oca);
	
	//根据id删除记录
	public void delAll(String[] ids);
	
	
	/**
	 * 添加或更新开关状态表
	 * @param switchStatus
	 */
	public void saveOrUpdateSS(SwitchStatus switchStatus);
	
	/**
	 * 根据当前日期和key标识查找
	 * @param keyId
	 * @param dateStr
	 * @return
	 */
	public SwitchStatus getSwitchStatus(String keyId,String dateStr);
	
	/**
	 * 根据keyId和日期删除原始表
	 * @param keyId
	 * @param dateStr
	 */
	public void delRecord(String keyId, String dateStr);
	
	/**
	 * 根据keyId和日期删除调整表
	 * @param keyId
	 * @param dateStr
	 */
	public void delAdjust(String keyId, String dateStr);
	
	/**
	 * 保存所有标
	 * @param ocrAll
	 */
	public void saveRecordAll(OCommodityRecordAll ocrAll);
	
	
	/**
	 * 执行sql语句   更新或删除
	 * @param sql
	 */
	public void execSql(String sql);
	
	public void insertZX(List<Map> zxList,String keyId);
	
	public List<Map> getFenLei(String keyId);
	
	public void updateZXPriceByBCode(String bcode,String  price,String  vipCode,String isYX);
	
	public Map getZXListByKeyIdFenlei(String keyId,String fenleiCode);
	
	/**
	 * 更新标的状态,已保护或未保护,当bcode为null时,更新所有
	 * @param keyId
	 * @param status
	 * @param dateStr
	 * @param bcode
	 */
	public void updateOCommodityAdjustStatus(String keyId,String status,String dateStr,String bcode);
}
