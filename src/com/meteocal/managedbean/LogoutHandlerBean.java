package com.meteocal.managedbean;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.general.utils.WebUtils;

@ManagedBean
@ApplicationScoped
public class LogoutHandlerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */


	public void logout()
	{
		WebUtils.invalidateSession();
		WebUtils.redirectTo("login.xhtml");
	}

}
