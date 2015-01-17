package com.meteocal.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.facade.EventFacade;
import com.meteocal.business.facade.MemberFacade;
import com.meteocal.entity.Event;
import com.meteocal.entity.Member;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@ManagedBean
@ViewScoped
public class EventInvitationHandlerBean implements Serializable {

	/**
	 * 
	 */
	@EJB
	private EventFacade eventFacade;

	@EJB
	private MemberFacade memberFacade;
	private static final long serialVersionUID = 1L;

	private List<Event> eventsCreatedByMember ;
	
	private List<Member> eventInvitedMembers;
	private List<Member> allMembersExceptWhoInvited;
	private Event selectedEvent ;
	private DualListModel<Member> membersModel;

	
	@PostConstruct
	public void init() {
		loadData();
		WebUtils.validateCurrentUser();

		
	}


	private void loadData() {

		eventsCreatedByMember = eventFacade.getEventsByCreator(WebUtils.getCurrentUser());
		
		if(Util.isNotEmpty(eventsCreatedByMember))
		{
			selectedEvent = eventsCreatedByMember.get(0);
			
			refreshModels(selectedEvent);
			
		}else
		{
			membersModel = new DualListModel(); 
		}
				
	}


	private void refreshModels(Event selectedEvent) {
		eventInvitedMembers = memberFacade.getAllInvitedMembersForEvent(selectedEvent);
		allMembersExceptWhoInvited = memberFacade.getAllMembersForEventExceptInvitedMembers(selectedEvent);
		membersModel = new DualListModel<Member>(allMembersExceptWhoInvited, eventInvitedMembers);
 
		
	}


	public List<Event> getEventsCreatedByMember() {
		return eventsCreatedByMember;
	}


	public void setEventsCreatedByMember(List<Event> eventsCreatedByMember) {
		this.eventsCreatedByMember = eventsCreatedByMember;
	}


	public Event getSelectedEvent() {
		return selectedEvent;
	}


	public void setSelectedEvent(Event selectedEvent) {
		this.selectedEvent = selectedEvent;
	}


	public DualListModel<Member> getMembersModel() {
		return membersModel;
	}

 
	public void setMembersModel(DualListModel<Member> membersModel) {
		this.membersModel = membersModel;
	}

	public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        Member member;
        for(Object object : event.getItems()) {
              
         
				member = (Member)object;
			
            builder.append( member.getLastName() +" "+ member.getFirstName() ).append("\n");
 
        	
        }
          
        WebUtils.fireDetailsIntoMessage(Constants.INVITE_MEMBERS_TRANSFORMED,builder.toString());
    }  

	
	public void eventChanged()
	{
		refreshModels(selectedEvent);
	}
	
	public void save()
	{  
		 selectedEvent = Util.<Event>extractMarkerFromList(eventsCreatedByMember, selectedEvent);
		try {
			eventFacade.updateEvent(selectedEvent  ,  membersModel.getTarget() , eventInvitedMembers );
			WebUtils.fireInfoMessage(Constants.GENERAL_MODIFICATES_HAS_BEEN_SAVED);
			refreshModels(selectedEvent);
		} catch (MeteocalExceltion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	}

	
	
}
