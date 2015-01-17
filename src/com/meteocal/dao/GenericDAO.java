package com.meteocal.dao;

import javax.ejb.Singleton;

@Singleton
public class GenericDAO extends AbstractDAO{
	
	
	public GenericDAO()
	{
		super.setEntityManager(entityManagerFactory.createEntityManager());
	}
	

}
