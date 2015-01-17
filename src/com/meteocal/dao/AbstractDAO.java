package com.meteocal.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.meteocal.general.Constants;
import com.meteocal.general.Marker;

@Singleton
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDAO<T extends Marker> implements Serializable {

	/**
	 * ;
	 */
	private static final long serialVersionUID = 1L;
	protected EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("primary");

	public AbstractDAO() {
	}
	
	@PostConstruct
	public void init()
	{
		
	}

	public AbstractDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	

	public T persist(T t) {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			t = entityManager.merge(t);
			transaction.commit();
			return t;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
			return null;

		}
		
	}

	public T getEntityByID(Class clss, Long ID) {
		return (T) entityManager.find(clss, ID);
	}

	public List<T> findAll(Class c) {
		Query query = entityManager.createQuery(String.format("from %s",
				c.getName()));
		List result = query.getResultList();
		return result;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManager(EntityManager entityManager) {

		this.entityManager = entityManager;
	}

	public void delete(Marker marker) {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();

			}
			marker = entityManager.merge(marker);
			entityManager.remove(marker);
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			throw Constants.RUNTIME_EXCEPTION;
		}
	}

	public <X extends Marker> X merge(X x) {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			if(x.getID()!=null)
			{
			x = entityManager.merge(x);
			}else
			{
				 entityManager.persist(x);
				 
			}
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
			throw Constants.RUNTIME_EXCEPTION;

		}
		return x;
	}

	public void refresh(Marker marked) {

		entityManager.refresh(marked);

	}
	
	

}
