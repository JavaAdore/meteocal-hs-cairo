package com.meteocal.business.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.meteocal.dao.MemberDAO;
import com.meteocal.entity.Event;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@Stateless
public class MemberServiceImpl implements MemberService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	MemberDAO memberDAO;

	
	public Member addMember(Member member) throws MeteocalExceltion {
		try {
			if (getMemeberByEmail(member.getEmail()) == null) {
				return memberDAO.addMember(member);
			} else {
				throw Constants.EMAIL_ALREADY_EXISTS;

			}
		} catch (MeteocalExceltion ex) {
			throw ex;
		} catch (Exception ex) {
			throw Constants.DATABASE_ERROR;

		}
	}

	
	public List<Member> getAllMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Member getMemberByID(Long l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  Member getMemeberByEmail(String email) {
		return memberDAO.getMemeberByEmail(email);
	}

	
 
	
	@Override
	public List<Member> getAllMembersForEventExceptInvitedMembers(Event event,Member member) {
		return memberDAO.getAllMembersForEventExceptInvitedMembers(event,member);
	}

	@Override
	public List<Member> getAllInvitedMembersForEvent(Event event) {
		return memberDAO.getAllInvitedMembersForEvent(event);
	}


	@Override
	public List<MemberEventInvitation> getMemberEventRequests(Member member) {
		return memberDAO.getMemberEventRequests(member);
		
	}


	@Override
	public void rejectRequest(MemberEventInvitation memberEventInvitation) {
		memberDAO.delete(memberEventInvitation);
		
	}


	@Override
	public void acceptRequest(MemberEventInvitation memberEventInvitation) {
		memberEventInvitation.setStatus(Constants.ACCEPT_INVITATION);
		memberDAO.updateMemberEventInvitation(memberEventInvitation);
		
	}

}
