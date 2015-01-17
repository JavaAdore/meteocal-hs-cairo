package com.meteocal.business.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import com.meteocal.dao.BadWeatherDAO;
import com.meteocal.entity.BadWeather;
import com.meteocal.entity.Member;
import com.meteocal.entity.WeatherType;

@Singleton 
public class BadWeatherServiceImpl implements BadWeatherService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	BadWeatherDAO badWeatherDAO;

	@Override
	public List<BadWeather> getUserBadWeathers(Member creator) {
		return badWeatherDAO.getUserBadWeathers(creator);
	}

	@Override
	public BadWeather addBadWeather(BadWeather badWeather) {
		BadWeather result = badWeatherDAO.getBadWeatherBy(
				badWeather.getBadWeatherCreator(), badWeather.getWeatherType(),
				badWeather.getMinDegree(), badWeather.getMaxDegree());

		if (result != null) {
			badWeather.setID(result.getID());
		}
		return badWeatherDAO.addBadWeather(badWeather);
	}

	@Override
	public BadWeather getBadWeatherBy(Member badWeatherCreator,
			WeatherType weatherType, double min, double max) {
		return badWeatherDAO.getBadWeatherBy(badWeatherCreator, weatherType,
				min, max);
	}

}
