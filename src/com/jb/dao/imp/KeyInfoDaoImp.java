package com.jb.dao.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jms.Session;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jb.dao.KeyInfoDao;
import com.jb.model.JBDate;
import com.jb.model.KeyInfo;
import com.jb.model.SwitchStatus;

public class KeyInfoDaoImp extends HibernateDaoSupport implements KeyInfoDao {

	public List<KeyInfo> getListAll() {
		String hql = "from KeyInfo";
		List<KeyInfo> keyInfos = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).list();
		
		return keyInfos;
	}
	
	/**
	 * 得到所有可以使用的key信息
	 * @return
	 */
	public List<KeyInfo> getListUseredAll() {
		String hql = "from KeyInfo where isUse=1";
		List<KeyInfo> keyInfos = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).list();
		
		return keyInfos;
	}

	public void delAll(String[] ids) {
		// TODO Auto-generated method stub
		String hqlRecord = "";
		String hqlAdjust = "";
		String hqlJBLog = "";
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			hqlRecord = "delete from OCommodityRecord where keyInfo.keyId=?";
			hqlAdjust = "delete from OCommodityAdjust where keyInfo.keyId=?";
			hqlJBLog = "delete from JBLog where keyInfo.keyId=?";
			try {
				this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hqlRecord).setString(0, id).executeUpdate();
				this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hqlAdjust).setString(0, id).executeUpdate();
				this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hqlJBLog).setString(0, id).executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			KeyInfo keyInfo = get(id);
			this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(keyInfo);
		}
		
	}

	public KeyInfo get(String id) {
		// TODO Auto-generated method stub
		return (KeyInfo) this.getHibernateTemplate().getSessionFactory().getCurrentSession().get(KeyInfo.class, id);
	}

	public void saveOrupdate(KeyInfo keyInfo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(this.getHibernateTemplate().getSessionFactory().getCurrentSession().merge(keyInfo));
	}

	public KeyInfo get2ByUserNameAndPassWd(String username,String passwd) {
		KeyInfo keyInfo = null;
		String hql = "from KeyInfo where userName=? and passWord=? and isUse=1";
		try {
			keyInfo = (KeyInfo) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, username).setString(1, passwd).uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return keyInfo;
	}

	public List<KeyInfo> getListByUserName(String userName) {
		String hql = "from KeyInfo where isUse=1 ";
		if(!"lw".equals(userName)){
			hql = hql + " and userName="+userName;
		}
		List<KeyInfo> keyInfos = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).list();
		
		return keyInfos;
	}

	public List<KeyInfo> isAllOK(String dateStr,String keyId) {
		String whereSql = "";
		if(!"".equals(keyId)){
			whereSql = " and keyInfo.keyId='"+keyId+"'";
		}
		String hql = "from SwitchStatus where setDate=? and isTZOK=0 " + whereSql;
		List<KeyInfo> keyInfos = new ArrayList<KeyInfo>();
		List<SwitchStatus> ssList = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, dateStr).list();
		for (Iterator iterator = ssList.iterator(); iterator.hasNext();) {
			SwitchStatus ss = (SwitchStatus) iterator.next();
			if(ss.getKeyInfo().getIsUse()==1){
				keyInfos.add(ss.getKeyInfo());
			}
		}
		
		return keyInfos;
	}

	public List<KeyInfo> getListAllLogin(String setdate,String keyId) {
		String whereSql = "";
		if(!"".equals(keyId)){
			whereSql = " and t.key_id='"+keyId+"'";
		}
//		String sql = "select t.* from key_info t left join switch_status t2 on (t.key_id=t2.key_id and t2.setdate=?) where t2.is_login_ok=0 and t.is_use=1 " + whereSql;
		String sql = "select t.* from key_info t left join switch_status t2 on (t.key_id=t2.key_id and t2.setdate=?) where t.is_use=1 " + whereSql;
		List<KeyInfo> keyInfos = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).addEntity(KeyInfo.class).setString(0, setdate).list();
		return keyInfos;
	}
	
	public void setJBDate(JBDate jbDate){
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(jbDate);
	}
	
	/**
	 * 根据日期字符串查找
	 * @param dateStr
	 * @return
	 */
	public JBDate getJBDateByDate(String dateStr){
		String hql = "from JBDate where jbDate=?";
		return (JBDate) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, dateStr).uniqueResult();
	}
	
	

}
