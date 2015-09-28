package com.jb.dao.imp;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jb.dao.SystemInfoDao;
import com.jb.model.SystemInfo;

public class SystemInfoDaoImp extends HibernateDaoSupport implements
		SystemInfoDao {

	public void delete(SystemInfo systemInfo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().delete(systemInfo);
	}

	public SystemInfo get(String sysId) {
		
		return (SystemInfo) this.getHibernateTemplate().getSessionFactory().getCurrentSession().get(SystemInfo.class, sysId);
	}

	public List<SystemInfo> getAll() {
		// TODO Auto-generated method stub
		String hql = "from SystemInfo";
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql).list();
	}

	public void saveOrUpdate(SystemInfo systemInfo) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(systemInfo);
	}

}
