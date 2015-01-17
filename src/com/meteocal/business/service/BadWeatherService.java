package com.meteocal.business.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.meteocal.entity.BadWeather;
import com.meteocal.entity.Member;
import com.meteocal.entity.WeatherType;

@Local
public interface BadWeatherService extends Serializable {

	
	public List< BadWeather> getUserBadWeathers(Member creator);
	public BadWeather addBadWeather(BadWeather badWeather) ;
	public BadWeather getBadWeatherBy(Member badWeatherCreator ,WeatherType weatherType , double min , double max );
}
