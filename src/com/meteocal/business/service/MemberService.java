package com.meteocal.business.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.meteocal.entity.Event;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;
import com.meteocal.general.MeteocalExceltion;

@Local
public interface MemberService extends Serializable{

	/**
	 * 
	 */
	
	Member addMember(Member Member) throws MeteocalExceltion;

	List<Member> getAllMembers();

	Member getMemberByID(Long l);
	
	public Member getMemeberByEmail(String email);
	

	public List<Member> getAllMembersForEventExceptInvitedMembers(Event event,Member member);

	public List<Member> getAllInvitedMembersForEvent(Event event);

	List<MemberEventInvitation> getMemberEventRequests(Member member);

	void rejectRequest(MemberEventInvitation memberEventInvitation);

	void acceptRequest(MemberEventInvitation memberEventInvitation);
}
