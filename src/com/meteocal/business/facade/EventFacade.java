package com.meteocal.business.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.meteocal.entity.BadWeather;
import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.entity.WeatherType;
import com.meteocal.general.MeteocalExceltion;


@Local
public interface EventFacade  {
	

	Event addNewEvent(String eventTitle, Member creator, EventType eventType,
			String description, Date startDate, Date endDate,
			Date lastAvailableTimeToAcceptOrInvite, String lat, String lon , boolean immediate, BadWeather badWeather)
			throws MeteocalExceltion;
	List<EventType> getEventTypes();
	List<Event> getEventsByCreator(Member member);
	void assignEventToMembers(Event selectedEvent , List<Member> members) throws MeteocalExceltion;
	void updateEvent(Event selectedEvent, List<Member> invitedMembers, List<Member> list) throws MeteocalExceltion;
	void deleteEvent(Event event) throws MeteocalExceltion;
	void unsubscribeFromEvent(Event event, Member currentUser) throws MeteocalExceltion;
	List<Event> getFutureEventCurrentUserInvitedIn(Member currentUser);
	List<Event> getFutureEventsCreatedByMember(Member currentUser);
	void joinEvent(Event event, Member currentUser)throws MeteocalExceltion;
	List<Event> getAllOldEventsCreatedByUser(Member currentUser);
	List<Event> getAllEventsUserInvitedIn(Member currentUser);
	public boolean assignWeatherForEvent(Event event);
	List<Event> getEventsWithIn3Days();
	void deleteFromQueue(Event event);
	List<WeatherType> getAllWeatherTypes();
	void addNewBadWeather(Member currentUser, WeatherType weatherType,
			String name, double minDegree, double maxDegree) throws MeteocalExceltion;
	List<BadWeather> getMemberBadWeathers();
	List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType, Member member);
	void requestToJoinEvent(Event event, Member currentUser) throws MeteocalExceltion;
	List<MemberEventInvitation> getMemberEventRequests(Member member);
	void acceptRequest(MemberEventInvitation memberEventInvitation)throws MeteocalExceltion;
	void rejectRequest(MemberEventInvitation memberEventInvitation)throws MeteocalExceltion;
	Event editEvent(Long id, String eventTitle, Member currentMember,
			EventType eventType, String description, Date startDate,
			Date endDate, Date lastAvailableTimeToAcceptOrInvite, String lat, 
			String lon, boolean processAnyWay, BadWeather badWeather)throws MeteocalExceltion;
	List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType);
	
	boolean isMemberAcceptedEvent(Member member , Event event);
	
}
