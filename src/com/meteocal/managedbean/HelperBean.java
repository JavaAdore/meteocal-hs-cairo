package com.meteocal.managedbean;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.entity.Member;
import com.meteocal.general.Constants;

@ManagedBean
@ApplicationScoped
public class HelperBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getMemberImage(String str)
	{	
		try
		{
		if(Util.isNotEmpty(str)&& str instanceof String)
		{ 
			if( WebUtils.isFileExistInWar(Constants.STORE_FOLDER_NAME +"/"  +str) )
			{
			return Constants.STORE_FOLDER_NAME +"/"  +str ; 
			}else
			{
				throw Constants.EXCEPTION;

			}
			
		}else
		{ 
			throw Constants.EXCEPTION;
		} 
		}catch(Exception ex)
		{
			return Constants.STORE_FOLDER_NAME +"/" +Constants.DEFAULT_IMAGE_NAME;

		}
	}
	 
	public Member getCurrentMember()
	{
		return WebUtils.getCurrentUser();
	}
	
	
	public void validateCurrentUser()
	{
		WebUtils.validateCurrentUser();
	}

	public String getWetherIconsPath()
	{
		return "weathericons";
	}
	public String getWeatherIcon(String str)
	{	
		
		return Constants.ICONS_STORE_FOLDER_NAME +"/" +Constants.DEFAULT_IMAGE_NAME+str+".png";

	}
	
	   
	public void redirectIfLogin()
	{
		if(getCurrentMember()!=null)
		{
			WebUtils.redirectTo(Constants.HOME_PAGE);
		}
	}

}
