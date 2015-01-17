package com.meteocal.managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.facade.EventFacade;
import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@ManagedBean
@ViewScoped
public class HomeHandlerBean implements Serializable {

	/**
	 * 
	 */
	@EJB
	private EventFacade eventFacade;

	private static final long serialVersionUID = 1L;

	private List<Event> eventCreatedByCurrentUser;
	private List<Event> eventsCurrentUserInvietedIn;

	private List<Event> futureEvents;

	private Date startDate;

	private Date endDate;

	private EventType eventType;
	private List<EventType> eventTypes;

	private Date minStartDate;

	@PostConstruct
	public void init() {

		loadData();	
		minStartDate = new Date();
		startDate = minStartDate;
		endDate = minStartDate;
		
		Event extractedEvent = (Event)WebUtils.extractFromSession(Constants.EVENT_TO_JOIN);
		
		if(extractedEvent !=null)
		{
			if(extractedEvent.getCreator() !=null &&  ( extractedEvent.getCreator().equals(WebUtils.getCurrentUser())==false))
			{
				try {
					eventFacade.requestToJoinEvent(extractedEvent,WebUtils.getCurrentUser());
					WebUtils.injectIntoSession(Constants.EVENT_TO_JOIN, null);
					WebUtils.fireInfoMessage(Constants.REQUST_HAS_BEEN_SENT_SUCCESSFULLY); 
				} catch (MeteocalExceltion e) {
					
					WebUtils.fireErrorMessage(e.getMessageKey());
					
					
					WebUtils.injectIntoSession(Constants.EVENT_TO_JOIN, null);

					
				} 
			}else 		
			{
				WebUtils.fireInfoMessage(Constants.YOU_ARE_THE_CREATOR_OF_THE_EVENT); 
 			}
		}
		
		

	}

	private void loadData() {
		eventCreatedByCurrentUser = eventFacade
				.getFutureEventsCreatedByMember(WebUtils.getCurrentUser());
		eventsCurrentUserInvietedIn = eventFacade
				.getFutureEventCurrentUserInvitedIn(WebUtils.getCurrentUser());
		eventTypes = eventFacade.getEventTypes();
		if (Util.isNotEmpty(eventTypes)) {
			eventType = eventTypes.get(0);
		}
	}

	public List<Event> getEventCreatedByCurrentUser() {
		return eventCreatedByCurrentUser;
	}

	public void setEventCreatedByCurrentUser(
			List<Event> eventCreatedByCurrentUser) {
		this.eventCreatedByCurrentUser = eventCreatedByCurrentUser;
	}

	public List<Event> getEventsCurrentUserInvietedIn() {
		return eventsCurrentUserInvietedIn;
	}

	public void setEventsCurrentUserInvietedIn(
			List<Event> eventsCurrentUserInvietedIn) {
		this.eventsCurrentUserInvietedIn = eventsCurrentUserInvietedIn;
	}

	public void joinEvent(Event event) {
		try {

			eventFacade.joinEvent(event, WebUtils.getCurrentUser());
			WebUtils.fireInfoMessage(Constants.EVENT_ACCEPTED_ENJOY);
			WebUtils.invokeJavaScriptFunction("update()");
		} catch (MeteocalExceltion e) {
			WebUtils.fireErrorMessage(e.getMessageKey());
		}

	}

	public void deleteEvent(Event event) {
		try {
			eventFacade.deleteEvent(event);
			Util.removeFromList(eventCreatedByCurrentUser, event);
			Util.removeFromList(eventsCurrentUserInvietedIn, event);
			WebUtils.fireInfoMessage(Constants.EVENT_DELETED_SUCCESSFULLY);
			WebUtils.invokeJavaScriptFunction("update()");
			eventFacade.deleteFromQueue(event);

		} catch (MeteocalExceltion e) {
			WebUtils.fireErrorMessage(e.getMessageKey());
		}
	}

	public void unsubscribeEvent(Event event) {
		try {
			eventFacade.unsubscribeFromEvent(event, WebUtils.getCurrentUser());
			Util.removeFromList(eventsCurrentUserInvietedIn, event);

			WebUtils.fireInfoMessage(Constants.EVENT_DELETED_SUCCESSFULLY);
			WebUtils.invokeJavaScriptFunction("update()");

		} catch (Exception ex) {
			if (ex instanceof MeteocalExceltion) {
				WebUtils.fireErrorMessage(Constants.DATABASE_ERROR_MESSAGE);
			}
		}

	}

	public List<Event> getFutureEvents() {
		return futureEvents;
	}

	public void setFutureEvents(List<Event> futureEvents) {
		this.futureEvents = futureEvents;
	}

	public EventFacade getEventFacade() {
		return eventFacade;
	}

	public void setEventFacade(EventFacade eventFacade) {
		this.eventFacade = eventFacade;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public List<EventType> getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getMinStartDate() {
		return minStartDate;
	}

	public void setMinStartDate(Date minStartDate) {
		this.minStartDate = minStartDate;
	}

	public void searchFutureEvents() {
		fixDates();
		futureEvents = eventFacade.getFutureEvents(startDate, endDate,
				eventType, WebUtils.getCurrentUser());

	}

	private void fixDates() {
		if (startDate.after(endDate)) {
			Date tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}

	}

	public void askToJoinEvent(Event event) {
		try {
			eventFacade.requestToJoinEvent(event, WebUtils.getCurrentUser());
			futureEvents.remove(event);
			WebUtils.fireInfoMessage(Constants.REQUST_HAS_BEEN_SENT_SUCCESSFULLY);
		} catch (MeteocalExceltion ex) {
			if (ex.equals(Constants.REQUEST_ALREADY_SENT)) {
				futureEvents.remove(event);

			}
			WebUtils.fireInfoMessage(ex.getMessageKey());
		}

	}
	
	
	public void editEvent(Event event)
	{ 
		WebUtils.injectIntoSession(Constants.SELECTED_EVENT,event); 
		WebUtils.redirectTo(Constants.EDIT_EVENT_PAGE);
	}

	
	
	public boolean isMemberAcceptedInvitation(Event event)
	{
		return eventFacade.isMemberAcceptedEvent(WebUtils.getCurrentUser(), event);
	}
}
