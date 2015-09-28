package com.jb.dao;

import java.util.List;

import com.jb.model.CommodityClassify;

public interface CClassCityDao {

	/**
	 * 根据当前日期和keyId查询所有分类
	 * @param currentDate
	 * @param keyId
	 * @return
	 */
	public List<CommodityClassify> getALLByCurrentDateKeyId(String currentDate,String keyId);
	
	/**
	 * 根据当前日期和keyId将自选商品表中的数据，添加到分类表中
	 * 以distinct t.b_storage,t.b_type,t.start_price来区分重复
	 * @param currentDate
	 * @param keyId
	 */
	public boolean setCClassifyByAdjust(String currentDate,String keyId);
	
	/**
	 * 添加或更新
	 * @param commodityClassify
	 */
	public void saveOrUpdate(CommodityClassify commodityClassify);
	
	/**
	 * 根据分类Id删除分类，并同时删除自选商品表里的对应标
	 * @param ccId
	 */
	public void delCClass(String ccId);
	
	/**
	 * 根据id查询
	 * @param ccId
	 * @return
	 */
	public CommodityClassify getCClass(String ccId);
	
	/**
	 * 根据当前日期、keyid和分类ID更新自选商品的底价
	 * @param currentDate
	 * @param keyId
	 * @param ccId
	 */
	public void updateLowPrice(String currentDate,String keyId,String ccId);
}
