package com.jb.dao.imp;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jb.dao.JBLogDao;
import com.jb.model.JBLog;

public class JBLogDaoImp extends HibernateDaoSupport implements JBLogDao {

	public void del(JBLog jbLog) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(jbLog);
	}

	public JBLog get(String jbId) {
		// TODO Auto-generated method stub
		return (JBLog) this.getHibernateTemplate().getSessionFactory().getCurrentSession().get(JBLog.class, jbId);
	}

	public List<JBLog> getList2KeyIdAndOcaId(String keyId, String dateStr) {
		String hql = "from JBLog where keyInfo.keyId=? and jbDate=?";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).setString(0, keyId).setString(1, dateStr).list();
	}
	
	public List<JBLog> getListByParams(String keyId,String dateStr,String status,String failInfo,String btype,String bstorage){
		String whereStr = " jbDate="+dateStr+" and status="+status;
		if(!"".equals(keyId)){
			whereStr += " and keyInfo.keyId='"+keyId+"'";
		}
		if(!"".equals(failInfo)){
			whereStr += " and failInfo like '%"+failInfo+"%'";
		}
		if(!"".equals(btype)){
			whereStr += " and btype like '%"+btype+"%'";
		}
		if(!"".equals(bstorage)){
			whereStr += " and bstorage like '%"+bstorage+"%'";
		}
		String hql = "from JBLog where " + whereStr + " order by orderNo";
		try {
			return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public void saveLog(JBLog jbLog) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().save(jbLog);
	}

}
