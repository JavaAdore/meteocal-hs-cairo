package com.meteocal.helperclass;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

	@Expose
	private Double dt;
	@Expose
	private Main main;
	@Expose
	private java.util.List<Weather> weather = new ArrayList<Weather>();
	@Expose
	private Clouds clouds;
	@Expose
	private Wind wind;
	@Expose
	private Sys sys;
	@SerializedName("dt_txt")
	@Expose
	private String dtTxt;
	@Expose
	private Rain rain;

	/**
	 * 
	 * @return The dt
	 */
	public Double getDt() {
		return dt;
	}

	/**
	 * 
	 * @param dt
	 *            The dt
	 */
	public void setDt(Double dt) {
		this.dt = dt;
	}

	/**
	 * 
	 * @return The main
	 */
	public Main getMain() {
		return main;
	}

	/**
	 * 
	 * @param main
	 *            The main
	 */
	public void setMain(Main main) {
		this.main = main;
	}

	/**
	 * 
	 * @return The weather
	 */
	public java.util.List<Weather> getWeather() {
		return weather;
	}

	/**
	 * 
	 * @param weather
	 *            The weather
	 */
	public void setWeather(java.util.List<Weather> weather) {
		this.weather = weather;
	}

	/**
	 * 
	 * @return The clouds
	 */
	public Clouds getClouds() {
		return clouds;
	}

	/**
	 * 
	 * @param clouds
	 *            The clouds
	 */
	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	/**
	 * 
	 * @return The wind
	 */
	public Wind getWind() {
		return wind;
	}

	/**
	 * 
	 * @param wind
	 *            The wind
	 */
	public void setWind(Wind wind) {
		this.wind = wind;
	}

	/**
	 * 
	 * @return The sys
	 */
	public Sys getSys() {
		return sys;
	}

	/**
	 * 
	 * @param sys
	 *            The sys
	 */
	public void setSys(Sys sys) {
		this.sys = sys;
	}

	/**
	 * 
	 * @return The dtTxt
	 */
	public String getDtTxt() {
		return dtTxt;
	}

	/**
	 * 
	 * @param dtTxt
	 *            The dt_txt
	 */
	public void setDtTxt(String dtTxt) {
		this.dtTxt = dtTxt;
	}

	/**
	 * 
	 * @return The rain
	 */
	public Rain getRain() {
		return rain;
	}

	/**
	 * 
	 * @param rain
	 *            The rain
	 */
	public void setRain(Rain rain) {
		this.rain = rain;
	}

}
