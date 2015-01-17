package com.meteocal.general;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MeteocalExceltion extends Exception  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageKey;
	private Map feedBack = new HashMap();
	
	public MeteocalExceltion(String messageKey) {
		this.messageKey = messageKey;
	}

	public MeteocalExceltion() {
		// TODO Auto-generated constructor stub
	}

	public String getMessageKey() {
		return messageKey;
	}

	public MeteocalExceltion setMessageKey(String messageKey) {
		this.messageKey = messageKey;
		return this;
	}

	public Map getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(Map feedBack) {
		this.feedBack = feedBack;
	}

	
	
}
