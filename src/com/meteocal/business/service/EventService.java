package com.meteocal.business.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.entity.WeatherStatus;
import com.meteocal.entity.WeatherType;
import com.meteocal.general.MeteocalExceltion;

@Local
public interface EventService {

	public Event addNewEvent(Event event) throws MeteocalExceltion;
	
	public Event addNewEventWithNoChecks(Event event) throws MeteocalExceltion;

	public Event updateEvent(Event event) throws MeteocalExceltion;

	public List<Event> getEventsByDataAndMember(Member member, Date startDate,
			Date endDate);

	public List<Event> getEventsByDataAndMember(Event event);

	public List<Event> getInterceptingEventsForCreator(Event event);

	public List<Event> getInterceptingEventsForInvited(Event event);

	public List<Event> getInterceptingEventsForCreator(Member member,
			Date startDate, Date endDate);

	public List<Event> getInterceptingEventsForInvited(Member member,
			Date startDate, Date endDate);

	public void deleteEvent(Event event) throws MeteocalExceltion;
	
	public List<EventType> getEventTypes();

	public EventType getEventTypeByID(Long valueOf);

	public List<Event> getEventsByCreator(Member creator);

	public void assignEventToMembers(Event selectedEvent, List<Member> members);

	public void updateEvent(Event event, List<Member> invitedMembers,
			List<Member> allMembers);

	public void unsubscribeFromEvent(Event event, Member currentUser) throws MeteocalExceltion;

	public List<Event> getFutureEventCurrentUserInvitedIn(Member creator);

	public List<Event> getFutureEventsCreatedByMember(Member member);

	public void joinEvent(Event event, Member currentUser) throws MeteocalExceltion;

	public List<Event> getAllEventsUserInvitedIn(Member member);

	public List<Event> getAllOldEventsCreatedByUser(Member currentUser);
	
	public WeatherStatus generateWeatherStatusForEvent(Event event);
	
	public void addOrUpdateWeatherStatus(Event event ,WeatherStatus weatherStatus) throws  MeteocalExceltion;

	public List<Event> getEventsWithIn3Days();

	public List<WeatherType> getallWeatherTypes();

	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType, Member member);

	public void requestToJoinEvent(Event event, Member member) throws MeteocalExceltion;

	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType);

	public boolean isMemberAcceptEvent(Member member, Event event);

 

}
