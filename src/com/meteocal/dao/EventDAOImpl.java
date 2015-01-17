package com.meteocal.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.general.utils.Util;
import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.entity.WeatherStatus;
import com.meteocal.entity.WeatherType;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@Singleton
public class EventDAOImpl extends AbstractDAO<Event> implements EventDAO {

	EntityManager entityManager = getEntityManagerFactory()
			.createEntityManager();

	public EventDAOImpl() {
		super.setEntityManager(entityManager);
	}

	private List<EventType> eventTypes;

	@Override
	public Event persistEvent(Event event) throws MeteocalExceltion {

		return super.persist(event);

	}

	@Override
	public List<Event> getEventsByDataAndMember(Member creator, Date startDate,
			Date endDate) {
		EntityTransaction transaction = null;
		List<Event> result = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"from Event where creator =:creator and startDate =:startDate and endDate=:endDate");
			query.setParameter("creator", creator);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);

			result = query.getResultList();
			transaction.commit();

		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			return result;

		}
	}

	@Override
	public List<Event> getEventsByDataAndMember(Event event) {
		if (event != null && event.getCreator() != null
				&& event.getStartDate() != null && event.getEndDate() != null) {
			return getEventsByDataAndMember(event.getCreator(),
					event.getStartDate(), event.getEndDate());
		}
		return null;
	}

	@Override
	public List<Event> getInterceptingEventsForCreator(Member creator,
			Date startDate, Date endDate) {
		EntityTransaction transaction = null;
		List<Event> result = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"select e from Event e where  e.creator =:creator and ( (e.startDate >=:startDate and e.startDate <=:endDate and e.endDate>=:endDate )  or( e.startDate <=:startDate and e.endDate >=:startDate and e.endDate<=:endDate)  or ( e.startDate >=:startDate and e.endDate <=:endDate ))");
			query.setParameter("creator", creator);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			result = query.getResultList();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			return result;
		}
	}

	@Override
	public List<Event> getInterceptingEventsForInvited(Member member,
			Date startDate, Date endDate) {
		EntityTransaction transaction = null;
		List<Event> result = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"select mei.event from MemberEventInvitation mei  , Event e where   mei.event = e and   mei.member =:member and ( (e.startDate >=:startDate and e.startDate <=:endDate and e.endDate>=:endDate )  or( e.startDate <=:startDate and e.endDate >=:startDate and e.endDate<=:endDate)  or ( e.startDate >=:startDate and e.endDate <=:endDate )) ");
			query.setParameter("member", member);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			result = query.getResultList();
			transaction.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
			return null;
		} finally {
			return result;

		}

	}

	@Override
	public void deleteEvent(Event event) throws MeteocalExceltion {

		EntityTransaction transaction = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager().createQuery(
					"delete from MemberEventInvitation  where event  =:event");
			query.setParameter("event", event);
			query.executeUpdate();

			Query query1 = getEntityManager().createQuery(
					"delete from WeatherStatus  where event  =:event");
			query1.setParameter("event", event);
			query1.executeUpdate();

			Query query2 = getEntityManager().createQuery(
					"delete from Event where ID =:event");
			query2.setParameter("event", event.getID());
			query2.executeUpdate();

			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
		}

	}

	@Override
	public List<Event> getInterceptingEventsForCreator(Event event) {
		return getInterceptingEventsForCreator(event.getCreator(),
				event.getStartDate(), event.getEndDate());
	}
	
	@Override
	public List<Event> getInterceptingEventsForAnEvent(Event event) {
		return getInterceptingEventsForCreator(event.getID(),event.getCreator(),
				event.getStartDate(), event.getEndDate());
	}


	@Override
	public List<Event> getInterceptingEventsForInvited(Event event) {
		if (event != null && event.getCreator() != null
				&& event.getStartDate() != null && event.getEndDate() != null) {
			return getInterceptingEventsForInvited(event.getCreator(),
					event.getStartDate(), event.getEndDate());
		}
		return null;
	}

	@Override
	public List<EventType> getEventTypes() {

		if (Util.isNotEmpty(eventTypes)) {
			return eventTypes;
		} else {
			Query query = getEntityManager().createQuery("from EventType");
			eventTypes = query.getResultList();
			return eventTypes;
		}

	}

	@Override
	public List<Event> getEventsByCreator(Member creator) {
		EntityTransaction transaction = null;
		List<Event> result = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}

			Query query = getEntityManager().createQuery(
					"from Event where creator =:creator and startDate >=:startDate");
			query.setParameter("creator", creator);
			query.setParameter("startDate", new Date());
			result = query.getResultList(); 

			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();

		} finally {
			return result;
		}
	}

	@Override
	public void assignEventToMembers(Event selectedEvent, List<Member> members) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteNotInvitedMemberForEvent(Event event,
			List<Member> allMembers) {
		if (allMembers != null) {
			EntityTransaction transaction = null;
			try {
				transaction = getEntityManager().getTransaction();
				if (transaction.isActive() == false) {

					transaction.begin();
				}
				Query query = null;
				if (allMembers.size() > 0) {
					query = getEntityManager()
							.createQuery(
									"delete from MemberEventInvitation inv where inv.event =:event and inv.member not in (:allMembers) ");
					query.setParameter("allMembers", allMembers);

				} else {
					query = getEntityManager()
							.createQuery(
									"delete from MemberEventInvitation inv where inv.event =:event");
				}
				query.setParameter("event", event);
				query.executeUpdate();

				transaction.commit();
			} catch (Exception ex) {
				if (transaction != null) {
					transaction.rollback();
				}
			}
		}
	}

	@Override
	public void inviteNotInvitedUsers(Event event, Member member) {
		Date invitationDate = new Date();

		MemberEventInvitation memberEventInvitation = new MemberEventInvitation();
		memberEventInvitation.setEvent(event);
		memberEventInvitation.setInvitationDate(invitationDate);
		memberEventInvitation.setMember(member);
		memberEventInvitation.setStatus(Constants.NOT_DECIDED_YET);

		EntityTransaction entityTransaction = getEntityManager()
				.getTransaction();
		try {
			if (entityTransaction.isActive() == false) {
				entityTransaction.begin();
			}
			getEntityManager().persist(memberEventInvitation);
			entityTransaction.commit();
		} catch (Exception ex) {
			entityTransaction.rollback();
			ex.printStackTrace();

		}

	}

	@Override
	public void unsubscribeFromEvent(Event event, Member member)
			throws MeteocalExceltion {

		EntityTransaction transaction = getEntityManager().getTransaction();

		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"delete from  MemberEventInvitation inv where inv.member =:member and inv.event =:event");
			query.setParameter("member", member);
			query.setParameter("event", event);
			query.executeUpdate(); 
			transaction.commit();

		} catch (Exception ex) {

			transaction.rollback();
			throw Constants.DATABASE_ERROR;
		}
	}

	@Override
	public List<Event> getFutureEventCurrentUserInvitedIn(Member member) {
		EntityTransaction transaction = getEntityManager().getTransaction();
		List<Event> result = null;
		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"select inv.event from MemberEventInvitation inv where inv.member =:member and inv.event.startDate >=:currentDate and inv.status<>:requestInvitation and inv.status<>:rejectedByMember");
			query.setParameter("member", member);
			query.setParameter("currentDate", new Date());
			query.setParameter("requestInvitation",
					Constants.REQUEST_INVITATION);
			query.setParameter("rejectedByMember",
					Constants.REJECTED_BY_MEMBERS);

			 
			
			result = query.getResultList();
			transaction.commit();
			return result;
		} catch (Exception ex) {
			transaction.rollback();
			return null;
		}
	}

	@Override
	public List<Event> getFutureEventsCreatedByMember(Member creator) {

		EntityTransaction transaction = getEntityManager().getTransaction();
		List<Event> result = null;
		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}

			Query query = getEntityManager()
					.createQuery(
							" from Event inv where creator =:creator and startDate >=:currentDate");
			query.setParameter("creator", creator);
			query.setParameter("currentDate", new Date());
			result = query.getResultList();
			transaction.commit();

		} catch (Exception ex) {
			transaction.rollback();

		}

		finally {
			return result;
		}
	}

	@Override
	public void joinEvent(Event event, Member member) throws MeteocalExceltion {
		EntityTransaction transaction = getEntityManager().getTransaction();

		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"from  MemberEventInvitation inv where inv.member =:member and inv.event =:event");
			query.setParameter("member", member);
			query.setParameter("event", event);
			List<MemberEventInvitation> result = (List<MemberEventInvitation>) query
					.getResultList();

			if (Util.isNotEmpty(result)) {
				for (MemberEventInvitation memberEventInvitation : result) {
					memberEventInvitation
							.setStatus(Constants.ACCEPT_INVITATION);

				}

			}
			transaction.commit();

		} catch (Exception ex) {

			transaction.rollback();
			throw Constants.DATABASE_ERROR;
		}

	}

	@Override
	public List<Event> getAllEventsUserInvitedIn(Member member) {
		EntityTransaction transaction = null;
		List<Event> result = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}

			Query query = getEntityManager()
					.createQuery(
							"select inv.event from MemberEventInvitation inv where inv.member =:member and inv.event.startDate <=:currentDate ");
			query.setParameter("member", member);
			query.setParameter("currentDate", new Date());
			result = query.getResultList();

			transaction.commit();

		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			return result;
		}
	}

	@Override
	public List<Event> getAllOldEventsCreatedByUser(Member creator) {
		EntityTransaction transaction = null;
		List<Event> result = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							" from Event inv where creator =:creator and startDate <=:currentDate");
			query.setParameter("creator", creator);
			query.setParameter("currentDate", new Date());
			 result = query.getResultList();
			transaction.commit();
		}catch(Exception ex){
			transaction.rollback();
		} finally {
		
			return result;
		}
	}

	/*
	 * @Override public List<Event> getFutureEvents(List<Date> dates) { Query
	 * query = getEntityManager().createQuery(
	 * " from Event inv where startDate in(=:startDate)");
	 * query.setParameter("startDate", dates); List<Event> result =
	 * query.getResultList(); return result; }
	 */

	@Override
	public List<Event> getEventsWithIn3Days() {
		List<Event> result = null;
		EntityTransaction transaction = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Calendar toDay = Calendar.getInstance();

			Calendar firstDate = Calendar.getInstance();
			firstDate.add(Calendar.DAY_OF_MONTH, 1);

			Calendar secondDate = Calendar.getInstance();
			secondDate.add(Calendar.DAY_OF_MONTH, 2);

			Calendar thirdDate = Calendar.getInstance();
			thirdDate.add(Calendar.DAY_OF_MONTH, 3);

			Query query = getEntityManager()
					.createQuery(
							" from Event inv where (inv.startDate >=:toDay  and   inv.startDate <=:thirdDate ) and inv not in (select event from WeatherStatus ) and inv.badWeather is not null");
			query.setParameter("toDay", toDay.getTime());
			query.setParameter("thirdDate", thirdDate.getTime());

			result = query.getResultList();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			return result;
		}
	}

	@Override
	public WeatherStatus getWeatherStatusOfEvent(Event event) {
		List result = null;
		EntityTransaction transaction = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager().createQuery(
					"select ws from WeatherStatus ws where ws.event =:event");
			query.setParameter("event", event);
			result = query.getResultList();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
			ex.printStackTrace();
		}
		return (WeatherStatus) (Util.isNotEmpty(result) ? result.get(0) : null);

	}

	@Override
	public void addOrUpdateWeatherStatus(WeatherStatus weatherStatus) {
		super.merge(weatherStatus);

	}

	@Override
	public List<WeatherType> getAllWeatherTypes() {
		Query query = getEntityManager().createQuery("from WeatherType");
		List<WeatherType> result = query.getResultList();
		return result;
	}

	@Override
	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType, Member member) {
		List result = null;
		EntityTransaction transaction = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}

			Query query = getEntityManager()
					.createQuery(
							"from Event e  where e.startDate>=:startDate and e.endDate<=:endDate and e.creator<>:member and e not in (select inv.event from  MemberEventInvitation inv where inv.member=:member and (inv.status <>:accepted ) ) ");
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("member", member);
			query.setParameter("accepted", Constants.ACCEPT_INVITATION);

			result = query.getResultList();
			transaction.commit();
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			transaction.rollback();
			return null;
		}

	}

	@Override
	public MemberEventInvitation getMemberEventInvitation(Event event,
			Member member) {
		EntityTransaction transaction = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}

			Query query = getEntityManager()
					.createQuery(
							"select inv from MemberEventInvitation inv where inv.event =:event and inv.member =:invitedMember");
			query.setParameter("event", event);
			query.setParameter("invitedMember", member);
			List<MemberEventInvitation> result = query.getResultList();
			if (Util.isNotEmpty(result)) {
				return result.get(0);
			}
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();

		}
		return null;
	}

	@Override
	public void addMemberInvitation(MemberEventInvitation memberEventInvitation) {
		super.merge(memberEventInvitation);

	}

	@Override
	public List<Event> getInterceptingEventsForCreator(Long ID, Member creator,
			Date startDate, Date endDate) {
		EntityTransaction transaction = null;
		List<Event> result = null;
		try {
			transaction = getEntityManager().getTransaction();
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"select e from Event e where e.ID<>:ID and  e.creator =:creator and ( (e.startDate >=:startDate and e.startDate <=:endDate and e.endDate>=:endDate )  or( e.startDate <=:startDate and e.endDate >=:startDate and e.endDate<=:endDate)  or ( e.startDate >=:startDate and e.endDate <=:endDate ))");
			query.setParameter("creator", creator);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("ID", ID);
			result = query.getResultList();
			transaction.commit();
		} catch (Exception ex) {
			transaction.rollback();
		} finally {
			return result;
		}
	}

	@Override
	public List<Event> getFutureEvents(Date startDate, Date endDate,
			EventType eventType) {
		EntityTransaction transaction = getEntityManager().getTransaction();
		List<Event> result = null;
		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							" from Event  where startDate >=:startDate and endDate <=:endDate");
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			
			 result = query.getResultList();
			 transaction.commit();
		}catch(Exception ex)
		{
			transaction.rollback();
			
		}finally
		{
			return result;
		}
	}

}
