package com.meteocal.business.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.joda.time.DateTime;

import com.general.utils.Util;
import com.google.gson.Gson;
import com.meteocal.dao.EventDAO;
import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.entity.WeatherStatus;
import com.meteocal.entity.WeatherType;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;
import com.meteocal.helperclass.DataObject;
import com.meteocal.helperclass.Main;
import com.meteocal.helperclass.Weather;

@Stateless
public class EventServiceImpl implements EventService {

	@EJB
	EventDAO eventDAO;

	@Override
	public Event addNewEvent(Event event) throws MeteocalExceltion {

		List<Event> intersectionWithEventsCreatedByMember = getInterceptingEventsForCreator(event);
		List<Event> intersectionWithEventsUserInvitedIn = getInterceptingEventsForInvited(event);
		Event eventToReturn = null;
		MeteocalExceltion exceptionToReturn = null;
		if (Util.isNotEmpty(intersectionWithEventsCreatedByMember)) {
			exceptionToReturn = new MeteocalExceltion();
			exceptionToReturn.getFeedBack().put(
					Constants.INTERSECTED_EVENTS_CREATED_BY_USER,
					intersectionWithEventsCreatedByMember);
		}
		if (Util.isNotEmpty(intersectionWithEventsUserInvitedIn)) {
			if (exceptionToReturn == null) {
				exceptionToReturn = new MeteocalExceltion();
			}
			exceptionToReturn.getFeedBack().put(
					Constants.INTERSECTION_WITH_EVENTS_USER_INVITED_IN,
					intersectionWithEventsUserInvitedIn);
		}
		if (exceptionToReturn != null) {
			exceptionToReturn.setMessageKey(Constants.INTERSECTED_EVENTS);
			throw exceptionToReturn;
		}
		try {
			eventToReturn = eventDAO.persistEvent(event);
		} catch (Exception ex) {
			throw Constants.DATABASE_ERROR;
		}
		return eventToReturn;

	}

	@Override
	public Event addNewEventWithNoChecks(Event event) throws MeteocalExceltion {
		Event eventToReturn = null;
		try {
			eventToReturn = eventDAO.persistEvent(event);
		} catch (Exception ex) {
			throw Constants.DATABASE_ERROR;
		}
		return eventToReturn;
	}

	@Override
	public List<Event> getEventsByDataAndMember(Member member, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return eventDAO.getEventsByDataAndMember(member, startDate, endDate);
	}

	@Override
	public List<Event> getEventsByDataAndMember(Event event) {
		// TODO Auto-generated method stub
		return eventDAO.getEventsByDataAndMember(event);
	}

	@Override
	public List<Event> getInterceptingEventsForCreator(Event event) {
		if(event.getID()==null){
		return eventDAO.getInterceptingEventsForCreator(event);
		}else
		{
			return eventDAO.getInterceptingEventsForAnEvent(event);

		}
	}

	@Override
	public Event updateEvent(Event event) throws MeteocalExceltion {
		return eventDAO.persistEvent(event);
	}

	@Override
	public List<Event> getInterceptingEventsForInvited(Event event) {
		// TODO Auto-generated method stub
		return eventDAO.getInterceptingEventsForInvited(event);
	}

	@Override
	public List<Event> getInterceptingEventsForCreator(Member member,
			Date startDate, Date endDate) {
		return eventDAO.getInterceptingEventsForCreator(member, startDate,
				endDate);
	}

	@Override
	public List<Event> getInterceptingEventsForInvited(Member member,
			Date startDate, Date endDate) {
		return eventDAO.getInterceptingEventsForInvited(member, startDate,
				endDate);
	}

	@Override
	public void deleteEvent(Event event) throws MeteocalExceltion {
		eventDAO.deleteEvent(event);

	}

	@Override
	public List<EventType> getEventTypes() {
		return eventDAO.getEventTypes();
	}

	@Override
	public EventType getEventTypeByID(Long ID) {

		for (EventType eventType : getEventTypes()) {
			if (eventType.getID() == ID) {
				return eventType;
			}
		}
		return null;
	}

	@Override
	public List<Event> getEventsByCreator(Member creator) {
		return eventDAO.getEventsByCreator(creator);

	}

	@Override
	public void assignEventToMembers(Event selectedEvent, List<Member> members) {
		eventDAO.assignEventToMembers(selectedEvent, members);

	}

	@Override
	public void updateEvent(Event event, List<Member> allMembers,
			List<Member> invitedMembers) {
		eventDAO.deleteNotInvitedMemberForEvent(event, allMembers);

		for (int i = allMembers.size() - 1; i >= 0; i--) {
			Member currentMember = allMembers.get(i);
			if (invitedMembers.contains(currentMember)) {
				continue;

			}
			eventDAO.inviteNotInvitedUsers(event, currentMember);

		}
	}

	@Override
	public void unsubscribeFromEvent(Event event, Member currentUser)
			throws MeteocalExceltion {

		eventDAO.unsubscribeFromEvent(event, currentUser);

	}

	@Override
	public List<Event> getFutureEventCurrentUserInvitedIn(Member creator) {

		return eventDAO.getFutureEventCurrentUserInvitedIn(creator);
	}

	@Override
	public List<Event> getFutureEventsCreatedByMember(Member member) {
		return eventDAO.getFutureEventsCreatedByMember(member);
	}

	@Override
	public void joinEvent(Event event, Member currentUser)
			throws MeteocalExceltion {
		eventDAO.joinEvent(event, currentUser);

	}

	@Override
	public List<Event> getAllEventsUserInvitedIn(Member member) {
		return eventDAO.getAllEventsUserInvitedIn(member);
	}

	@Override
	public List<Event> getAllOldEventsCreatedByUser(Member currentUser) {
		return eventDAO.getAllOldEventsCreatedByUser(currentUser);
	}

	@Override
	public WeatherStatus generateWeatherStatusForEvent(Event event) {
		try {
			String url = "http://api.openweathermap.org/data/2.5/forecast/coord?lat="
					+ event.getLat().trim()
					+ "&lon="
					+ event.getLon().trim()
					+ "&APPID=e890c3057c2446e8eb089dcc65e0107f";
			// String url =
			// "http://api.openweathermap.org/data/2.5/forecast/coord?lat=30.045951989830176&lon=31.23969736259619&APPID=e890c3057c2446e8eb089dcc65e0107f";

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);

			HttpResponse response = client.execute(request);

			System.out.println("Response Code : "
					+ response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			Gson gson = new Gson();

			DataObject data = gson
					.fromJson(result.toString(), DataObject.class);

			System.out.println(result);

			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

			String dateStr = ft.format(event.getStartDate()).split(" ")[0];
			String timeStr = ft.format(event.getStartDate()).split(" ")[1];

			Weather weather = null;
			Main main = null;
			int temp = -1;

			for (int i = 0; i < data.getList().size(); i++) {
				if (data.getList().get(i).getDtTxt().substring(0, 10)
						.equals(dateStr)) {
					Date weatherDateTime = ft.parse(data.getList().get(i)
							.getDtTxt());
					System.out.println(data.getList().get(i).getDtTxt()
							+ " ---------------------");

					DateTime dtOrg = new DateTime(event.getStartDate());

					if (dtOrg.getHourOfDay() >= 0 && dtOrg.getHourOfDay() < 3) {
						temp = i;
					} else if (dtOrg.getHourOfDay() >= 3
							&& dtOrg.getHourOfDay() < 6) {
						temp = i + 1;
					} else if (dtOrg.getHourOfDay() >= 6
							&& dtOrg.getHourOfDay() < 9) {
						temp = i + 2;
					} else if (dtOrg.getHourOfDay() >= 9
							&& dtOrg.getHourOfDay() < 12) {
						temp = i + 3;
					} else if (dtOrg.getHourOfDay() >= 12
							&& dtOrg.getHourOfDay() < 15) {
						temp = i + 4;
					} else if (dtOrg.getHourOfDay() >= 15
							&& dtOrg.getHourOfDay() < 18) {
						temp = i + 5;
					} else if (dtOrg.getHourOfDay() >= 18
							&& dtOrg.getHourOfDay() < 21) {
						temp = i + 6;
					} else if (dtOrg.getHourOfDay() >= 21
							&& dtOrg.getHourOfDay() < 24) {
						temp = i + 7;
					}
					if (i != -1) {
						weather = data.getList().get(temp).getWeather().get(0);
						main = data.getList().get(temp).getMain();
					}
				}
			}

			if (weather != null && main != null) {

				WeatherStatus weatherStatus = new WeatherStatus();
				weatherStatus.setEvent(event);
				weatherStatus.setMinDegree(main.getTempMin());
				weatherStatus.setMaxDegree(main.getTempMax());
				weatherStatus.setNameOfWeather(weather.getMain() + " ("
						+ weather.getDescription() + ")");
				weatherStatus.setWeatherIcon(weather.getIcon());
				if (data.getCity() != null
						&& data.getCity().getCountry() != null) {
					weatherStatus.setCity(data.getCity().getName());
					weatherStatus.setCountry(data.getCity().getCountry());
				}
				if (weather.getId() != null) {
					weatherStatus.setWeatherStatusCode(weather.getId());
				}
				return weatherStatus;
			} else {
				throw Constants.EXCEPTION;
			}

		} catch (Exception ex) {
			return null;

		}

	}

	@Override
	public void addOrUpdateWeatherStatus(Event event,
			WeatherStatus weatherStatus) throws MeteocalExceltion {
		WeatherStatus ws = eventDAO.getWeatherStatusOfEvent(event);

		if (ws != null) {
			weatherStatus.setID(ws.getID());
		}

		eventDAO.addOrUpdateWeatherStatus(weatherStatus);

	}

	@Override
	public List<Event> getEventsWithIn3Days() {
		return eventDAO.getEventsWithIn3Days();
	}

	List<WeatherType> eventTypes;

	@Override
	public List<WeatherType> getallWeatherTypes() {

		if (eventTypes == null) {
			eventTypes = eventDAO.getAllWeatherTypes();
		}
		return eventTypes;
	}

	@Override
	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType, Member member) {

		return eventDAO.getFutureEvents(startDate, endDate, eventType, member);
	}

	@Override
	public void requestToJoinEvent(Event event, Member member)
			throws MeteocalExceltion {
		MemberEventInvitation memberEventInvitation = eventDAO
				.getMemberEventInvitation(event, member);
		if (memberEventInvitation == null) {
			memberEventInvitation = new MemberEventInvitation();
			memberEventInvitation.setMember(member);
			memberEventInvitation.setEvent(event);
			memberEventInvitation.setStatus(Constants.REQUEST_INVITATION);
			try {
				eventDAO.addMemberInvitation(memberEventInvitation);
			} catch (Exception ex) {
				throw Constants.DATABASE_ERROR;
			}

		} else {
			if (memberEventInvitation.getStatus() != null
					&& memberEventInvitation.getStatus().intValue() == Constants.ACCEPT_INVITATION) {
				throw Constants.REQUEST_ALREADY_ACCEPTED;
			}

			if (memberEventInvitation.getStatus() != null
					&& memberEventInvitation.getStatus().intValue() == Constants.REQUEST_INVITATION) {
				throw Constants.REQUEST_ALREADY_SENT;
			}

		}

	}

	@Override
	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType) {
		return eventDAO.getFutureEvents( startDate,  endDate,
				 eventType);
	}

	@Override
	public boolean isMemberAcceptEvent(Member member, Event event) {
			
		MemberEventInvitation result = eventDAO.getMemberEventInvitation(event, member);
		if(result !=null)
		{
			return result.getStatus() !=null && result.getStatus().intValue()==Constants.ACCEPT_INVITATION;
		}else
		{
			return false;
		}
		
		
	}
	
	

}
