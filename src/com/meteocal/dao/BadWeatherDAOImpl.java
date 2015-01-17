package com.meteocal.dao;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.general.utils.Util;
import com.meteocal.entity.BadWeather;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.entity.WeatherType;

@Singleton
public class BadWeatherDAOImpl extends AbstractDAO<BadWeather> implements
		BadWeatherDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EntityManager entityManager = getEntityManagerFactory()
			.createEntityManager();

	public BadWeatherDAOImpl() {
		super.setEntityManager(entityManager);
	}

	private List<EventType> eventTypes;

	@Override
	public List<BadWeather> getUserBadWeathers(Member creator) {

		Query query = getEntityManager().createQuery(
				"from BadWeather where badWeatherCreator =:creator");
		query.setParameter("creator", creator);
		List<BadWeather> result = query.getResultList();
		return result; 
	}

	@Override
	public BadWeather addBadWeather(BadWeather badWeather) {

	
		
		return super.merge(badWeather);
	}

	@Override
	public BadWeather getBadWeatherBy(Member badWeatherCreator,
			WeatherType weatherType, double minDegree, double maxDegree) {

		EntityTransaction tranaction = getEntityManager().getTransaction();
		BadWeather badWeather=null;
		try {
			if (tranaction.isActive() == false) {
				tranaction.begin();
			}

			Query query = getEntityManager()
					.createQuery(
							"from BadWeather where badWeatherCreator =:badWeatherCreator and weatherType =:weatherType and minDegree=:minDegree and maxDegree=:maxDegree");

			query.setParameter("badWeatherCreator", badWeatherCreator);
			query.setParameter("weatherType", weatherType);
			query.setParameter("minDegree", minDegree);
			query.setParameter("maxDegree", maxDegree);

			List<BadWeather> result = query.getResultList();
			tranaction.commit();
			badWeather= (Util.isNotEmpty(result) ? result.get(0) : null);
		} catch(Exception ex) {
			tranaction.rollback();
			
		}finally
		{
			return badWeather;
		}

	}

}
