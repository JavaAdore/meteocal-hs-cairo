package com.meteocal.business.facade;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.service.AlertThread;
import com.meteocal.business.service.BadWeatherService;
import com.meteocal.business.service.EventService;
import com.meteocal.business.service.MemberService;
import com.meteocal.entity.BadWeather;
import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.entity.WeatherStatus;
import com.meteocal.entity.WeatherType;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@Stateless
public class EventFacadeImpl implements EventFacade {

	@EJB
	private EventService eventService;

	@EJB
	private MemberService memberService;

	@EJB
	private BadWeatherService badWeatherService;

	@EJB
	AlertThread alertThread;

	@Override
	public Event addNewEvent(String eventTitle, Member creator,
			EventType eventType, String description, Date startDate,
			Date endDate, Date lastAvailableTimeToAcceptOrInvite, String lat,
			String lon, boolean immediate, BadWeather badWeather)
			throws MeteocalExceltion {

		// validate dates()
		if (startDate.after(endDate)) {
			MeteocalExceltion meteocalExceltion = new MeteocalExceltion();
			meteocalExceltion
					.setMessageKey(Constants.END_DATE_SHOULD_BE_AFTER_START_DATE);
			throw meteocalExceltion;
		}

		if (lastAvailableTimeToAcceptOrInvite.after(startDate)) {
			MeteocalExceltion meteocalExceltion = new MeteocalExceltion();
			meteocalExceltion
					.setMessageKey(Constants.LAST_AVAILABLE_TIME_TO_ACCEPT_SHOULD_BE_BEFORE_START_DATE);
			throw meteocalExceltion;
		}

		Event event = new Event();
		event.setCreator(creator);
		event.setLat(lat);
		event.setLon(lon);
		event.setDescription(description);
		event.setEventTitle(eventTitle);
		event.setEventType(eventType);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setLastAvailableTimeToAcceptOrInvite(lastAvailableTimeToAcceptOrInvite);
		event.setBadWeather(badWeather);

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 3);

		if (immediate) {

			event = eventService.addNewEventWithNoChecks(event);

			if (Util.isDateBetween(event.getStartDate(), new Date(),
					c.getTime())) {
				alertThread.registerOnQueue(event);
			}
			return event;

		} else {

			event = eventService.addNewEvent(event);
			if (Util.isDateBetween(event.getStartDate(), new Date(),
					c.getTime())) {
				alertThread.registerOnQueue(event);
			}
			return event;

		}
	}

	@Override
	public List<EventType> getEventTypes() {
		return eventService.getEventTypes();
	}

	@Override
	public List<Event> getEventsByCreator(Member creator) {
		return eventService.getEventsByCreator(creator);
	}

	@Override
	public void assignEventToMembers(Event selectedEvent, List<Member> members)
			throws MeteocalExceltion {
		eventService.assignEventToMembers(selectedEvent, members);

	}

	@Override
	public void updateEvent(Event event, List<Member> invitedMembers,
			List<Member> allMembers) throws MeteocalExceltion {
		eventService.updateEvent(event, invitedMembers, allMembers);

	}

	@Override
	public void deleteEvent(Event event) throws MeteocalExceltion {

		eventService.deleteEvent(event);
	}

	@Override
	public void unsubscribeFromEvent(Event event, Member currentUser)
			throws MeteocalExceltion {
		eventService.unsubscribeFromEvent(event, currentUser);

	}

	@Override
	public List<Event> getFutureEventCurrentUserInvitedIn(Member creator) {
		return eventService.getFutureEventCurrentUserInvitedIn(creator);
	}

	@Override
	public List<Event> getFutureEventsCreatedByMember(Member member) {
		return eventService.getFutureEventsCreatedByMember(member);
	}

	@Override
	public void joinEvent(Event event, Member currentUser)
			throws MeteocalExceltion {
		eventService.joinEvent(event, currentUser);

	}

	@Override
	public List<Event> getAllOldEventsCreatedByUser(Member currentUser) {
		return eventService.getAllOldEventsCreatedByUser(currentUser);
	}

	@Override
	public List<Event> getAllEventsUserInvitedIn(Member member) {
		return eventService.getAllEventsUserInvitedIn(member);
	}

	@Override
	public boolean assignWeatherForEvent(Event event) {
		try {
			WeatherStatus weatherStatus = eventService
					.generateWeatherStatusForEvent(event);

			if (weatherStatus != null) {

				if (event.getBadWeather() != null
						&& event.getBadWeather().getWeatherType() != null
						&& event.getBadWeather().getWeatherType().getIcon() != null
						&& weatherStatus.getWeatherIcon() != null) {

					if (weatherStatus
							.getWeatherIcon()
							.replace("n", "d")
							.equalsIgnoreCase(
									event.getBadWeather().getWeatherType()
											.getIcon())) {
						if (weatherStatus.getMinDegree() != null
								&& weatherStatus.getMaxDegree() != null) {
							if (event.getBadWeather().getMinDegree() >= weatherStatus
									.getMinDegree()
									&& event.getBadWeather().getMaxDegree() <= weatherStatus
											.getMaxDegree()) {
								weatherStatus
										.setIsBadWeather(Constants.IS_BAD_WEATHER);
							}
						}
					}

				}
				eventService.addOrUpdateWeatherStatus(event, weatherStatus);

				return true;
			} else {
				throw Constants.EXCEPTION;
			}

		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<Event> getEventsWithIn3Days() {
		return eventService.getEventsWithIn3Days();
	}

	@Override
	public void deleteFromQueue(Event event) {
		if (event == null && event.getStartDate() != null) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 3);
			if (Util.isDateBetween(event.getStartDate(), new Date(),
					c.getTime())) {
				alertThread.removeFromQueue(event);
			}

		}

	}

	@Override
	public List<WeatherType> getAllWeatherTypes() {
		return eventService.getallWeatherTypes();
	}

	@Override
	public void addNewBadWeather(Member currentUser, WeatherType weatherType,
			String name, double minDegree, double maxDegree)
			throws MeteocalExceltion {

		try {
			BadWeather badWeather = new BadWeather();
			badWeather.setBadWeatherCreator(currentUser);
			badWeather.setWeatherType(weatherType);
			badWeather.setName(name);
			badWeather.setMaxDegree(maxDegree);
			badWeather.setMinDegree(minDegree);
			badWeatherService.addBadWeather(badWeather);
		} catch (Exception ex) {
			throw Constants.DATABASE_ERROR;
		}

	}

	@Override
	public List<BadWeather> getMemberBadWeathers() {
		return badWeatherService.getUserBadWeathers(WebUtils.getCurrentUser());
	}

	@Override
	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType, Member member) {
		return eventService.getFutureEvents(startDate, endDate, eventType,
				member);
	}

	@Override
	public void requestToJoinEvent(Event event, Member member)
			throws MeteocalExceltion {

		eventService.requestToJoinEvent(event, member);

	}

	@Override
	public List<MemberEventInvitation> getMemberEventRequests(Member member) {

		return memberService.getMemberEventRequests(member);

	}

	@Override
	public void acceptRequest(MemberEventInvitation memberEventInvitation)
			throws MeteocalExceltion {
		try {
			memberService.acceptRequest(memberEventInvitation);
		} catch (Exception ex) {
			throw Constants.DATABASE_ERROR;
		}

	}

	@Override
	public void rejectRequest(MemberEventInvitation memberEventInvitation)
			throws MeteocalExceltion {
		try {
			memberService.rejectRequest(memberEventInvitation);
		} catch (Exception ex) {
			throw Constants.DATABASE_ERROR;
		}

	}

	@Override
	public Event editEvent(Long id, String eventTitle, Member creator,
			EventType eventType, String description, Date startDate,
			Date endDate, Date lastAvailableTimeToAcceptOrInvite, String lat,
			String lon, boolean immediate, BadWeather badWeather)
			throws MeteocalExceltion {
		// validate dates()
		if (startDate.after(endDate)) {
			MeteocalExceltion meteocalExceltion = new MeteocalExceltion();
			meteocalExceltion
					.setMessageKey(Constants.END_DATE_SHOULD_BE_AFTER_START_DATE);
			throw meteocalExceltion;
		}

		if (lastAvailableTimeToAcceptOrInvite.after(startDate)) {
			MeteocalExceltion meteocalExceltion = new MeteocalExceltion();
			meteocalExceltion
					.setMessageKey(Constants.LAST_AVAILABLE_TIME_TO_ACCEPT_SHOULD_BE_BEFORE_START_DATE);
			throw meteocalExceltion;
		}

		Event event = new Event();
		event.setID(id);
		event.setCreator(creator);
		event.setLat(lat);
		event.setLon(lon);
		event.setDescription(description);
		event.setEventTitle(eventTitle);
		event.setEventType(eventType);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setLastAvailableTimeToAcceptOrInvite(lastAvailableTimeToAcceptOrInvite);
		event.setBadWeather(badWeather);

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 3);

		if (immediate) {

			event = eventService.addNewEventWithNoChecks(event);

			if (Util.isDateBetween(event.getStartDate(), new Date(),
					c.getTime())) {
				alertThread.registerOnQueue(event);
			}
			return event;

		} else {

			event = eventService.addNewEvent(event);
			if (Util.isDateBetween(event.getStartDate(), new Date(),
					c.getTime())) {
				alertThread.registerOnQueue(event);
			}
			return event;

		}

	}

	@Override
	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType) {
		return eventService.getFutureEvents(startDate, endDate, eventType);
	}

	@Override
	public boolean isMemberAcceptedEvent(Member member, Event event) {
		return eventService.isMemberAcceptEvent(member,event );
	}

}
