package com.meteocal.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.general.utils.Util;
import com.meteocal.entity.Event;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.general.Constants;

@Singleton
public class MemberDAOImpl extends AbstractDAO<Member> implements MemberDAO {

	EntityManager entityManager = getEntityManagerFactory()
			.createEntityManager();

	public MemberDAOImpl() {
		super.setEntityManager(entityManager);
	}

	public Member addMember(Member Member) {

		return super.persist(Member);
	}

	public List<Member> getAllMembers() {
		// TODO Auto-generated method stub
		return super.findAll(Member.class);
	}

	public Member getMemberByID(Long ID) {
		return super.getEntityByID(Member.class, ID);
	}

	public Member getMemeberByEmail(String email) {
		EntityTransaction transaction = getEntityManager().getTransaction();
		List<Member> result = new ArrayList();
		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager().createQuery(
					"from Member where lower(email) like :email");
			query.setParameter("email", email.toLowerCase());
			result = query.getResultList();
			Util.isNotEmpty(result);
			return Util.isNotEmpty(result) ? result.get(0) : null;
		} finally {
			transaction.commit();
		}

	}

	@Override
	public List<Member> getAllMembersForEventExceptInvitedMembers(Event event,Member creator) {
		EntityTransaction transaction = getEntityManager().getTransaction();
		List<Member> result = new ArrayList();
		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"select m from Member m    where m.ID not in ( select inv.member.ID from MemberEventInvitation inv where inv.event =:event ) and m <> :creator");
			query.setParameter("event", event);
			query.setParameter("creator", creator);
			result = query.getResultList();
			return result;

		} finally {
			transaction.commit();
		}
	}

	@Override
	public List<Member> getAllInvitedMembersForEvent(Event event) {
		EntityTransaction transaction = getEntityManager().getTransaction();
		List<Member> result = new ArrayList();
		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
					.createQuery(
							"select mei.member from MemberEventInvitation mei  where mei.event =:event ");
			query.setParameter("event", event);
			result = query.getResultList();
			transaction.commit();
			

		} catch(Exception ex)
		{
			transaction.rollback();
		}finally
		{
			return result;
		}
	}

	@Override
	public List<MemberEventInvitation> getMemberEventRequests(Member creator) {
		EntityTransaction transaction = getEntityManager().getTransaction();
		List<MemberEventInvitation> result = new ArrayList();
		try {
			if (transaction.isActive() == false) {
				transaction.begin();
			}
			Query query = getEntityManager()
	 				.createQuery(
							"select inv  from MemberEventInvitation inv where  inv.event.creator =:creator and inv.status=:requestInvitation ");
			query.setParameter("creator", creator);
			query.setParameter("requestInvitation", Constants.REQUEST_INVITATION);
			result = query.getResultList();
			transaction.commit();

		} catch(Exception ex)
		{
			transaction.rollback();
		}finally
		{
			return result;
		}
		
	}
	@Override
	public  MemberEventInvitation updateMemberEventInvitation(MemberEventInvitation memberEventInvitation)
	{
		return super.merge(memberEventInvitation);
		
	}
	@Override
	public void delete(MemberEventInvitation memberEventInvitation)
	{
		 super.delete(memberEventInvitation);
	}

}
