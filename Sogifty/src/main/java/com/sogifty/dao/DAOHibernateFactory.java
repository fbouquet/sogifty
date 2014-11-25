package com.sogifty.dao;

import org.hibernate.Session;

import com.sogifty.util.persistance.HibernateUtil;

public class DAOHibernateFactory extends DAOFactory {

	private GenericHibernateDAO instantiateDAO(Class daoClass) {
        try {
            GenericHibernateDAO dao = (GenericHibernateDAO)daoClass.newInstance();
            dao.setSession(getCurrentSession());
            return dao;
        } catch (Exception ex) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
        }
    }
 
    // You could override this if you don't want HibernateUtil for lookup
    protected Session getCurrentSession() {
        return HibernateUtil.getSessionFactory().getCurrentSession();
    }
    
	@Override
	public UserDAO getUserDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
