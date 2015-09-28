package com.jb.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jb.dao.CClassCityDao;
import com.jb.model.CommodityClassify;

public class CClassCityDaoImp extends HibernateDaoSupport implements CClassCityDao {

	public void delCClass(String ccId) {
		//删除对应的自选商品
		String hql = "delete from OCommodityAdjust where commodityClassify.ccId=?";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, ccId).executeUpdate();
		//删除分类
		hql = "delete from CommodityClassify where ccId=?";
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, ccId).executeUpdate();
	}

	public List<CommodityClassify> getALLByCurrentDateKeyId(String currentDate,
			String keyId) {
		List<CommodityClassify> cclassifyList = new ArrayList<CommodityClassify>();
		String hql = "from CommodityClassify where keyId=? and currentDate=? order by classLevel desc,levelOrder desc";
		try {
			cclassifyList = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, keyId).setString(1, currentDate).list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return cclassifyList;
	}

	public CommodityClassify getCClass(String ccId) {
		// TODO Auto-generated method stub
		return (CommodityClassify) this.getHibernateTemplate().getSessionFactory().getCurrentSession().get(CommodityClassify.class, ccId);
	}

	public void saveOrUpdate(CommodityClassify commodityClassify) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(this.getHibernateTemplate().getSessionFactory().getCurrentSession().merge(commodityClassify));
	}

	public boolean setCClassifyByAdjust(String currentDate, String keyId) {
		//根据当前时间和keyid清空自选商品
		String delSql = "delete from OPTIONAL_COMMODITY_ADJUST where KEY_ID=? AND OCA_DATE=?";
		try {
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(delSql).setString(0, keyId).setString(1, currentDate).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		//根据当前时间和keyid清空分类
		delSql = "delete from COMMODITY_CLASSIFY t where KEY_ID=? and t.CURRENTDATE=?";
		try {
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(delSql).setString(0, keyId).setString(1, currentDate).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		// TODO Auto-generated method stub
		String sql = "insert into COMMODITY_CLASSIFY(CC_ID,KEY_ID,B_TYPE,B_STORAGE,START_PRICE,CURRENTDATE,DEAL_NUM,CC_CODE) select sys_guid(),KEY_ID,B_TYPE,B_STORAGE,START_PRICE,OCR_DATE,DEAL_NUM,replace(B_TYPE||'_'||B_STORAGE||'_'||DEAL_NUM,' ','') from ( select distinct KEY_ID,B_TYPE,B_STORAGE,START_PRICE,OCR_DATE,DEAL_NUM from OPTIONAL_COMMODITY_RECORD where KEY_ID=? AND OCR_DATE=?)";
		try {
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).setString(0, keyId).setString(1, currentDate).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void updateLowPrice(String currentDate, String keyId,String ccId) {
		// TODO Auto-generated method stub
		String updateSql = "update OPTIONAL_COMMODITY_ADJUST t set (LOW_PRICE,ADD_LOW_PRICE)=(select LOW_PRICE,ADD_LOW_PRICE from COMMODITY_CLASSIFY t2 where t.CC_ID=t2.CC_ID) where KEY_ID=? AND OCA_DATE=? AND CC_ID=?";
		try {
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(updateSql).setString(0, keyId).setString(1, currentDate).setString(2, ccId).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
