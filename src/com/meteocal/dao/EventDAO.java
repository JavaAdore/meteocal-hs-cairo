package com.meteocal.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.entity.WeatherStatus;
import com.meteocal.entity.WeatherType;
import com.meteocal.general.MeteocalExceltion;

@Local
public interface EventDAO extends Serializable {

	/**
	 * 
	 */

	public Event persistEvent(Event event) throws MeteocalExceltion;

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

	public List<Event> getEventsByCreator(Member creator);

	public void assignEventToMembers(Event selectedEvent, List<Member> members);

	public void deleteNotInvitedMemberForEvent(Event event, List<Member> invitedMembers);

	public void inviteNotInvitedUsers(Event event, Member currentMember);

	public void unsubscribeFromEvent(Event event, Member currentUser) throws MeteocalExceltion;

	public List<Event> getFutureEventCurrentUserInvitedIn(Member creator);

	public List<Event> getFutureEventsCreatedByMember(Member member);

	public void joinEvent(Event event, Member currentUser) throws MeteocalExceltion;

	public List<Event> getAllEventsUserInvitedIn(Member member);

	public List<Event> getAllOldEventsCreatedByUser(Member currentUser);

	//public List<Event> getFutureEvents(List<Date> dates);
	public List<Event> getEventsWithIn3Days();

	public WeatherStatus getWeatherStatusOfEvent(Event event);

	public void addOrUpdateWeatherStatus(WeatherStatus weatherStatus);

	public List<WeatherType> getAllWeatherTypes();

	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType, Member member);

	public MemberEventInvitation getMemberEventInvitation(Event event,
			Member member);

	public void addMemberInvitation(MemberEventInvitation memberEventInvitation);

	List<Event> getInterceptingEventsForCreator(Long ID, Member creator,
			Date startDate, Date endDate);

	List<Event> getInterceptingEventsForAnEvent(Event event);

	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType);
}
