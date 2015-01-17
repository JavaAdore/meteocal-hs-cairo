package com.meteocal.helperclass;

import com.google.gson.annotations.Expose;

public class Sys {

	@Expose
	private String pod;

	/**
	 * 
	 * @return The pod
	 */
	public String getPod() {
		return pod;
	}

	/**
	 * 
	 * @param pod
	 *            The pod
	 */
	public void setPod(String pod) {
		this.pod = pod;
	}

}
