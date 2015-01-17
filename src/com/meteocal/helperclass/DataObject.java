package com.meteocal.helperclass;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class DataObject {

	@Expose
	private String cod;
	@Expose
	private Double message;
	@Expose
	private City city;
	@Expose
	private Double cnt;
	@Expose
	private java.util.List<com.meteocal.helperclass.List> list = new ArrayList<com.meteocal.helperclass.List>();

	/**
	 * 
	 * @return The cod
	 */
	public String getCod() {
		return cod;
	}

	/**
	 * 
	 * @param cod
	 *            The cod
	 */
	public void setCod(String cod) {
		this.cod = cod;
	}

	/**
	 * 
	 * @return The message
	 */
	public Double getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message
	 *            The message
	 */
	public void setMessage(Double message) {
		this.message = message;
	}

	/**
	 * 
	 * @return The city
	 */
	public City getCity() {
		return city;
	}

	/**
	 * 
	 * @param city
	 *            The city
	 */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * 
	 * @return The cnt
	 */
	public Double getCnt() {
		return cnt;
	}

	/**
	 * 
	 * @param cnt
	 *            The cnt
	 */
	public void setCnt(Double cnt) {
		this.cnt = cnt;
	}

	/**
	 * 
	 * @return The list
	 */
	public java.util.List<com.meteocal.helperclass.List> getList() {
		return list;
	}

	/**
	 * 
	 * @param list
	 *            The list
	 */
	public void setList(java.util.List<com.meteocal.helperclass.List> list) {
		this.list = list;
	}

}
