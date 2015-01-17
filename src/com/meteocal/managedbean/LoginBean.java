package com.meteocal.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.general.utils.WebUtils;
import com.meteocal.business.facade.MemberFacade;
import com.meteocal.business.service.AlertThread;
import com.meteocal.entity.Member;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;



@ManagedBean
@ViewScoped
public class LoginBean implements Serializable{

	/**
	 * 
	 */
	// Construct alert thread bean 
	@EJB AlertThread alertThread;

	@EJB 
	MemberFacade memberFacade;
	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	
	@PostConstruct
	public void init(){
		alertThread.startThread();
		if(WebUtils.getCurrentUser()!=null)
		{
			WebUtils.redirectTo(Constants.HOME_PAGE);
		} 
		
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void login(){
		try {
			System.out.println("===========================\n\n\n========================");
			Member member = 	memberFacade.login(email , password);
			WebUtils.fireInfoMessage( WebUtils.perpareWelcomeMessage(member.getFirstName()) );
			
			WebUtils.injectIntoSession( Constants.CURRENT_LOGGED_USER , member );
				
			WebUtils.redirectTo(Constants.HOME_PAGE);
			
		} catch (MeteocalExceltion e) {
			WebUtils.fireErrorMessage(e.getMessageKey());
		}
		
	}
}
