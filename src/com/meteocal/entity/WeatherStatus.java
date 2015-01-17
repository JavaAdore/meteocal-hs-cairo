package com.meteocal.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.meteocal.general.Marker;


/**
 * The persistent class for the badweather database table.
 * 
 */
@Entity
@Table(name="WeatherStatus")
@Cacheable(false)
public class WeatherStatus implements Serializable , Marker{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long ID;
	
	@Column(name="max_degree")
	private Double maxDegree;
	
	@Column(name="min_degree")
	private Double minDegree;

	@Column(name="name_of_weather")
	private String nameOfWeather;
	
	@Column(name="is_bad_weather")
	private Integer isBadWeather;

	@OneToOne
	@JoinColumn(name = "event_id", referencedColumnName = "id")
	private Event event;
	@Column(name="weather_icon")
	private String weatherIcon;
	
	@Column(name="weather_status_code")
	private Integer weatherStatusCode;
	
	private String country;

	private String city;

	public WeatherStatus() {
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	

	public Double getMaxDegree() {
		return maxDegree;
	}

	public void setMaxDegree(Double maxDegree) {
		this.maxDegree = maxDegree;
	}

	public Double getMinDegree() {
		return minDegree;
	}

	public void setMinDegree(Double minDegree) {
		this.minDegree = minDegree;
	}

	public String getNameOfWeather() {
		return nameOfWeather;
	}

	public void setNameOfWeather(String nameOfWeather) {
		this.nameOfWeather = nameOfWeather;
	}

	public Integer getIsBadWeather() {
		return isBadWeather;
	}

	public void setIsBadWeather(Integer isBadWeather) {
		this.isBadWeather = isBadWeather;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getWeatherIcon() {
		return weatherIcon;
	}

	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}

	public Integer getWeatherStatusCode() {
		return weatherStatusCode;
	}

	public void setWeatherStatusCode(Integer weatherStatusCode) {
		this.weatherStatusCode = weatherStatusCode;
	}
	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}