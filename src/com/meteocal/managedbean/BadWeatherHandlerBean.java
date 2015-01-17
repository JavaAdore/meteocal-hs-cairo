package com.meteocal.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.facade.EventFacade;
import com.meteocal.entity.WeatherType;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@ManagedBean
@ViewScoped
public class BadWeatherHandlerBean implements Serializable {

	/**
	 * 
	 */
	@EJB
	private EventFacade eventFacade;

	private static final long serialVersionUID = 1L;

	List<WeatherType> weatherTypes;
	
	WeatherType weatherType;
	
	
	private double maxDegree;
	private double minDegree;
	private String name;
	
	@PostConstruct
	public void init() {

		loadData();
		
		 
	
	}

	private void loadData()
	{
		
		weatherTypes = eventFacade.getAllWeatherTypes();
		
		if(Util.isNotEmpty(weatherTypes))
		{
			weatherType = weatherTypes.get(0);
		}
	}
	
	
	public void addNewBadWeather()
	{
		try
		{
			eventFacade.addNewBadWeather(WebUtils.getCurrentUser() , weatherType,name , minDegree,maxDegree);
			WebUtils.fireInfoMessage(Constants.BAD_WEATHER_ADDED_SUCCESSFULLY);
		}catch(MeteocalExceltion meteocalExceltion)
		{
			WebUtils.fireErrorMessage(meteocalExceltion.getMessageKey());
		}
	}

	public List<WeatherType> getWeatherTypes() {
		return weatherTypes;
	}

	public void setWeatherTypes(List<WeatherType> weatherTypes) {
		this.weatherTypes = weatherTypes;
	}

	public WeatherType getWeatherType() {
		return weatherType;
	}

	public void setWeatherType(WeatherType weatherType) {
		this.weatherType = weatherType;
	}

	public double getMaxDegree() {
		return maxDegree;
	}

	public void setMaxDegree(double maxDegree) {
		this.maxDegree = maxDegree;
	}

	public double getMinDegree() {
		return minDegree;
	}

	public void setMinDegree(double minDegree) {
		this.minDegree = minDegree;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
