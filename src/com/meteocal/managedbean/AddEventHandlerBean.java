package com.meteocal.managedbean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.gmap.GMap;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.facade.EventFacade;
import com.meteocal.business.service.AlertThread;
import com.meteocal.entity.BadWeather;
import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@ManagedBean
@ViewScoped
public class AddEventHandlerBean implements Serializable {

	@EJB
	private EventFacade eventFacade;

	@EJB
	AlertThread alertThread;

	private static final long serialVersionUID = 1L;
	GMap gmap;

	private String eventTitle;
	private Date startDate;
	private Date endDate;
	private String description;
	private LatLng latlng;
	private Date lastAvailableTimeToAcceptOrInvite;

	private List<EventType> eventTypes;
	private EventType eventType;
	private Date today = new Date();

	boolean setBadWeather = true;

	public Date getToday() {
		return today;
	}

	List<Event> intersectionWithEventsCreatedByMember;
	List<Event> intersectionWithEventsUserInvitedIn;
	List<BadWeather> badWeathers;
	BadWeather badWeather;

	@PostConstruct
	public void init() {

		eventTypes = eventFacade.getEventTypes();
		if (Util.isNotEmpty(eventTypes)) {
			eventType = eventTypes.get(0);
		}
		badWeathers = eventFacade.getMemberBadWeathers();
		if (Util.isNotEmpty(badWeathers)) {
			badWeather = badWeathers.get(0);
		}
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void onStateChange(StateChangeEvent event) {
		LatLngBounds bounds = event.getBounds();
		int zoomLevel = event.getZoomLevel();

	}

	public void onPointSelect(PointSelectEvent event) {
		latlng = event.getLatLng();

	}

	public LatLng getLatlng() {
		return latlng;
	}

	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}

	public GMap getGmap() {
		return gmap;
	}

	public void setGmap(GMap gmap) {
		this.gmap = gmap;
	}

	public void addEvent(boolean processAnyWay) {
		String lat = null;
		String lon = null;

		if (latlng == null) {
			lat = Util.getLat(gmap.getCenter());
			lon = Util.getLon(gmap.getCenter());
		} else {
			lat = String.valueOf(latlng.getLat());
			lon = String.valueOf(latlng.getLng());
		}

		Member currentMember = WebUtils.getCurrentUser();

		try {
			eventFacade.addNewEvent(eventTitle, currentMember, eventType,
					description, startDate, endDate,
					lastAvailableTimeToAcceptOrInvite, lat, lon, processAnyWay,
					badWeather);
			if (processAnyWay) {
				WebUtils.invokeJavaScriptFunction("PF('interceptionDialog').hide()");
			}

			WebUtils.fireInfoMessage(Constants.ADD_EVENT_EVENT_ADDED_SUCCESSFULLY);

		} catch (MeteocalExceltion ex) {

			if (ex.getMessageKey().equals(Constants.INTERSECTED_EVENTS)) {
				// initializing intercepting lists
				intersectionWithEventsCreatedByMember = (List<Event>) ex
						.getFeedBack().get(
								Constants.INTERSECTED_EVENTS_CREATED_BY_USER);
				intersectionWithEventsUserInvitedIn = (List<Event>) ex
						.getFeedBack()
						.get(Constants.INTERSECTION_WITH_EVENTS_USER_INVITED_IN);
				;
				WebUtils.invokeJavaScriptFunction("update();PF('interceptionDialog').show()");

			} else {
				WebUtils.fireErrorMessage(ex.getMessageKey());
			}

		}

	}

	public void deleteEvent(Event event) {
		try {
			eventFacade.deleteEvent(event);
			Util.removeFromList(intersectionWithEventsCreatedByMember, event);
			Util.removeFromList(intersectionWithEventsUserInvitedIn, event);
			WebUtils.fireInfoMessage(Constants.EVENT_DELETED_SUCCESSFULLY);
			eventFacade.deleteFromQueue(event);
		} catch (MeteocalExceltion e) {
			WebUtils.fireErrorMessage(e.getMessageKey());
		}
	}

	public void unsubscribeEvent(Event event) {
		try {
			eventFacade.unsubscribeFromEvent(event, WebUtils.getCurrentUser());
			Util.removeFromList(intersectionWithEventsCreatedByMember, event);
			Util.removeFromList(intersectionWithEventsUserInvitedIn, event);
			WebUtils.fireInfoMessage(Constants.EVENT_DELETED_SUCCESSFULLY);
		} catch (Exception ex) {
			if (ex instanceof MeteocalExceltion) {
				WebUtils.fireErrorMessage(Constants.DATABASE_ERROR_MESSAGE);
			}
		}

	}

	public List<EventType> getEventTypes() {
		return eventTypes;
	}

	public void setEventTypes(List<EventType> eventTypes) {
		this.eventTypes = eventTypes;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Date getLastAvailableTimeToAcceptOrInvite() {
		return lastAvailableTimeToAcceptOrInvite;
	}

	public void setLastAvailableTimeToAcceptOrInvite(
			Date lastAvailableTimeToAcceptOrInvite) {
		this.lastAvailableTimeToAcceptOrInvite = lastAvailableTimeToAcceptOrInvite;
	}

	public List<Event> getIntersectionWithEventsCreatedByMember() {
		return intersectionWithEventsCreatedByMember;
	}

	public void setIntersectionWithEventsCreatedByMember(
			List<Event> intersectionWithEventsCreatedByMember) {
		this.intersectionWithEventsCreatedByMember = intersectionWithEventsCreatedByMember;
	}

	public List<Event> getIntersectionWithEventsUserInvitedIn() {
		return intersectionWithEventsUserInvitedIn;
	}

	public void setIntersectionWithEventsUserInvitedIn(
			List<Event> intersectionWithEventsUserInvitedIn) {
		this.intersectionWithEventsUserInvitedIn = intersectionWithEventsUserInvitedIn;
	}

	public List<BadWeather> getBadWeathers() {
		return badWeathers;
	}

	public void setBadWeathers(List<BadWeather> badWeathers) {
		this.badWeathers = badWeathers;
	}

	public BadWeather getBadWeather() {
		return badWeather;
	}

	public void setBadWeather(BadWeather badWeather) {
		this.badWeather = badWeather;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public boolean isSetBadWeather() {
		return setBadWeather;
	}

	public void setSetBadWeather(boolean setBadWeather) {
		this.setBadWeather = setBadWeather;
	}

	public void selectionChange() {
		if (badWeather != null) {
			badWeather = Util.extractMarkerFromList(badWeathers, badWeather);
			if (badWeather != null) {
				WebUtils.fireExactInfoMessage(badWeather.getWeatherType()
						.getName(), String.format(" ( min:%s , max : %s)",
						badWeather.getMinDegree(), badWeather.getMaxDegree()));
			}
		}
	}

}
