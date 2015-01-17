package com.meteocal.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.meteocal.general.Marker;

/**
 * The persistent class for the events database table.
 * 
 */
@Entity
@Table(name = "bad_weather")
public class BadWeather implements Serializable, Marker {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ID;

	private String name;

	@ManyToOne
	private Member badWeatherCreator;

	@ManyToOne
	private WeatherType weatherType;

	private double minDegree;

	private double maxDegree;
	

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Member getBadWeatherCreator() {
		return badWeatherCreator;
	}

	public void setBadWeatherCreator(Member badWeatherCreator) {
		this.badWeatherCreator = badWeatherCreator;
	}

	public WeatherType getWeatherType() {
		return weatherType;
	}

	public void setWeatherType(WeatherType weatherType) {
		this.weatherType = weatherType;
	}

	public double getMinDegree() {
		return minDegree;
	}

	public void setMinDegree(double minDegree) {
		this.minDegree = minDegree;
	}

	public double getMaxDegree() {
		return maxDegree;
	}

	public void setMaxDegree(double maxDegree) {
		this.maxDegree = maxDegree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BadWeather other = (BadWeather) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

}