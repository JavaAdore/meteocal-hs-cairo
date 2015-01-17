package com.meteocal.managedbean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class IndexBeanHandler implements Serializable {

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
	
	private Date minEndDate;

	

	@PostConstruct
	public void init() {

		loadData();
		minStartDate = new Date();
		startDate = minStartDate;
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DAY_OF_MONTH,10);
		 
		minEndDate = c.getTime();
		endDate = minEndDate;
 
	}

	private void loadData() { 
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
				eventType);
		System.out.println();

	}

	private void fixDates() {
		if (startDate.after(endDate)) {
			Date tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}

	}

	public void askToJoinEvent(Event event) {
			WebUtils.injectIntoSession(Constants.EVENT_TO_JOIN, event);
			WebUtils.fireInfoMessageAndKeep(Constants.PLEASE_SIGN_IN_OR_REGISTER, true);
			WebUtils.redirectTo(Constants.LOGIN_PAGE);	
	}
		
	public Date getMinEndDate() {
		return minEndDate;
	}

	public void setMinEndDate(Date minEndDate) {
		this.minEndDate = minEndDate;
	}
	
	
	
}
