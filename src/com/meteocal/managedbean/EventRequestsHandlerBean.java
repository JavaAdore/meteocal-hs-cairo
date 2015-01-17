package com.meteocal.managedbean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.velocity.convert.WebMacro;
import org.primefaces.model.UploadedFile;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.facade.EventFacade;
import com.meteocal.business.facade.MemberFacade;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@ManagedBean
@ViewScoped
public class EventRequestsHandlerBean implements Serializable {

	private static final long serialVersionUID = 1L ;
	
	@EJB
	EventFacade eventFacade;
	
	List<MemberEventInvitation> memberEventInvitation;
	
	@PostConstruct
	public void intit()
	{
		memberEventInvitation = eventFacade.getMemberEventRequests(WebUtils.getCurrentUser());
	}
	

	public List<MemberEventInvitation> getMemberEventInvitation() {
		return memberEventInvitation;
	}

	public void setMemberEventInvitation(
			List<MemberEventInvitation> memberEventInvitation) {
		this.memberEventInvitation = memberEventInvitation;
	}
	
	
	public void acceptRequest(MemberEventInvitation memberInv)
	{	
		try
		{
		eventFacade.acceptRequest(memberInv);
		Util.removeFromList(memberEventInvitation, memberInv);
		WebUtils.fireInfoMessage(Constants.REQUEST_ACCEPTED);

		}catch(MeteocalExceltion ex)
		{
			WebUtils.fireErrorMessage(ex.getMessageKey());
		}
	}
	
	public void rejectRequest(MemberEventInvitation memberInv)
	{	try
		{
		eventFacade.rejectRequest(memberInv);
		Util.removeFromList(memberEventInvitation, memberInv);
		WebUtils.fireInfoMessage(Constants.REQUEST_REJECTED);

		}catch(MeteocalExceltion ex)
		{
			WebUtils.fireErrorMessage(ex.getMessageKey());
		}

	}
	
}
