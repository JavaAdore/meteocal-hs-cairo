package com.general.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NoLoggedUserException extends RuntimeException  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messageKey;
	private Map feedBack = new HashMap();
	
	public NoLoggedUserException(String messageKey) {
		this.messageKey = messageKey;
	}

	public NoLoggedUserException() {
		// TODO Auto-generated constructor stub
	}

	public String getMessageKey() {
		return messageKey;
	}

	public NoLoggedUserException setMessageKey(String messageKey) {
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
