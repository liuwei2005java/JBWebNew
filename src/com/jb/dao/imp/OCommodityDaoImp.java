package com.jb.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jb.dao.OCommodityDao;
import com.jb.model.OCommodityAdjust;
import com.jb.model.OCommodityRecord;
import com.jb.model.OCommodityRecordAll;
import com.jb.model.SwitchStatus;
import com.jb.unit.TTConnectionUnit;

public class OCommodityDaoImp extends HibernateDaoSupport implements
		OCommodityDao {

	public void delAll(String[] ids) {
		// TODO Auto-generated method stub
		for (int i = 0; i < ids.length; i++) {
			OCommodityAdjust oca = (OCommodityAdjust) this.getHibernateTemplate().getSessionFactory().getCurrentSession().get(OCommodityAdjust.class, ids[i]);
			try {
				this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(oca);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}

	}

	public List<OCommodityAdjust> getAdjustByKeyIdAndDate(String keyId,
			String dateStr,int isLow) {
		String whereStr = "";
		List<OCommodityAdjust> ocaList = new ArrayList<OCommodityAdjust>();
		if(isLow != -1){
			whereStr = " and isLow="+isLow;
		}
		String hql = "from OCommodityAdjust where keyInfo.keyId=? and ocaDate=? "+whereStr+" order by isLow desc,to_number(delayTime)";
		try {
			ocaList = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, keyId).setString(1, dateStr).list();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ocaList;
	}

	public boolean recordAll2Adjust(String keyId, String dateStr) {
		// TODO Auto-generated method stub
		String sql = "insert into OPTIONAL_COMMODITY_ADJUST(OCA_ID,KEY_ID,B_CODE,B_TYPE,B_STORAGE,DEAL_NUM,START_PRICE,OCA_DATE,ORDERNO) select OCR_ID,KEY_ID,B_CODE,B_TYPE,B_STORAGE,DEAL_NUM,START_PRICE,OCR_DATE,ORDERNO from OPTIONAL_COMMODITY_RECORD where KEY_ID=? AND OCR_DATE=?";
		//更新cc_id
		String updateSql = "update OPTIONAL_COMMODITY_ADJUST t set (CC_ID)=(select CC_ID from COMMODITY_CLASSIFY t2 where t.b_storage=t2.b_storage and t.b_type=t2.b_type and t.KEY_ID=t2.KEY_ID AND t2.CURRENTDATE=t.OCA_DATE and t.start_price=t2.start_price and t.DEAL_NUM=t2.DEAL_NUM) where KEY_ID=? AND OCA_DATE=?"; 
		try {
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setString(0, keyId).setString(1, dateStr).executeUpdate();
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(updateSql).setString(0, keyId).setString(1, dateStr).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void delRecord(String keyId, String dateStr) {
		String hqlR = "delete from OCommodityRecord where keyInfo.keyId=? and ocrDate=?";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hqlR).setString(0, keyId).setString(1, dateStr).executeUpdate();
	}
	
	public void delAdjust(String keyId, String dateStr) {
		String hqlA = "delete from OCommodityAdjust where keyInfo.keyId=? and ocaDate=?";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hqlA).setString(0, keyId).setString(1, dateStr).executeUpdate();
		//根据当前时间和keyid清空分类
		String delSql = "delete from COMMODITY_CLASSIFY t where KEY_ID=? and t.CURRENTDATE=?";
		try {
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(delSql).setString(0, keyId).setString(1, dateStr).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void saveRecord(OCommodityRecord ocr) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(ocr);
	}
	
	/**
	 * 保存所有标
	 * @param ocrAll
	 */
	public void saveRecordAll(OCommodityRecordAll ocrAll){
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(ocrAll);
	}

	public void updateAdjust(OCommodityAdjust oca) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().update(oca);
	}

	
	public void saveOrUpdateSS(SwitchStatus switchStatus){
		try {
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(switchStatus);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public SwitchStatus getSwitchStatus(String keyId, String dateStr) {
		SwitchStatus switchStatus = null;
		String hql = "from SwitchStatus where keyInfo.keyId=? and setDate=?";
		try {
			switchStatus = (SwitchStatus) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, keyId).setString(1, dateStr).uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return switchStatus;
	}
	/*--------------------------------------------------------------------*/
	/**
	 * 执行sql语句   更新或删除
	 * @param sql
	 */
	public void execSql(String sql){
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	public void insertZX(List<Map> zxList,String keyId){
		String sql = "insert into ZX_COMMODITY(B_CODE,B_TYPE,B_STORAGE,DEAL_NUM,START_PRICE,DQ_PRICE,ORDERNO,KEY_ID,IS_YX,VIP_CODE,FENLEI_CODE) values(?,?,?,?,?,?,?,?,?,?,?)";
		int index = 0;
		for (Iterator iterator = zxList.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			index++;
			String bcode = map.get("bcode").toString();
			String btype = map.get("btype").toString();
			String bstorage = map.get("bstorage").toString();
			String dealNum = map.get("dealNum").toString();
			String startjiage = map.get("startjiage").toString();
			String newjiage = map.get("newjiage").toString();
			String isYx = map.get("isYx").toString();
			String felleiCode = map.get("CC_CODE").toString();
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setString(0, bcode)
					.setString(1, btype)
					.setString(2, bstorage)
					.setString(3, dealNum)
					.setString(4, startjiage)
					.setString(5, newjiage)
					.setInteger(6, index)
					.setString(7, keyId)
					.setString(8, isYx)
					.setString(9, "")
					.setString(10, felleiCode).executeUpdate();
		}
	}
	
	public List<Map> getFenLei(String keyId){
		List<Map> result = new ArrayList<Map>();
		Map objMap = new HashMap();
		String sql = "select LOW_PRICE,JX_NUM,CC_CODE from COMMODITY_CLASSIFY where KEY_ID=? and JX_NUM>0";
		result = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setString(0, keyId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		return result;
	}
	
	
	public void updateZXPriceByBCode(String bcode,String  price,String  vipCode,String isYX){
		String sql = "update ZX_COMMODITY set DQ_PRICE=?,IS_YX=? where B_CODE=?";
		String insertVIPCodeSql = "insert into VIPCODE_TAB(B_CODE,DQ_PRICE,VIP_CODE,CREATEDATE) values(?,?,?,sysdate)";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setString(0, price).setString(1, isYX).setString(2, bcode).executeUpdate();
		//插入到VIPCODE_TAB表，记录这些标都是哪些人在抢
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(insertVIPCodeSql).setString(0, bcode).setString(1, price).setString(2, vipCode).executeUpdate();
	}
	
	
	public Map getZXListByKeyIdFenlei(String keyId,String fenleiCode){
		Map result = new HashMap();
		//有效个数
		int yxNum = 0;
		List<Map> zxList = new ArrayList<Map>();
		Map objMap = new HashMap();
		String sql = "select B_CODE,DQ_PRICE,IS_YX,DEAL_NUM from ZX_COMMODITY where KEY_ID=? AND FENLEI_CODE=? ORDER BY TO_NUMBER(DQ_PRICE)";
		
		List zxListTmp = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setString(0, keyId).setString(1, fenleiCode).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for (Iterator iterator = zxListTmp.iterator(); iterator.hasNext();) {
			Map map = (Map) iterator.next();
			String isYX = map.get("IS_YX").toString();
			if("0".equals(isYX)){
				zxList.add(map);
			}else{
				yxNum++;
			}
		}
		
		result.put("yxNum", yxNum);
		result.put("zxList", zxList);
		return result;
	}
	
	/**
	 * 更新标的状态,已保护或未保护,当bcode为null时,更新所有
	 * @param keyId
	 * @param status
	 * @param dateStr
	 * @param bcode
	 */
	public void updateOCommodityAdjustStatus(String keyId,String status,String dateStr,String bcode){
		String sql = "update OPTIONAL_COMMODITY_ADJUST set STATUS=? where KEY_ID=? and OCA_DATE=? and ";
		String whereStr = " 1=1";
		if(bcode != null){
			whereStr = "B_CODE='"+bcode+"'";
		}
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql+whereStr).setString(0, status).setString(1, keyId).setString(2, dateStr).executeUpdate();
	}
}
