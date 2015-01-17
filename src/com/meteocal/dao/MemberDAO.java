package com.meteocal.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.meteocal.entity.Event;
import com.meteocal.entity.Member;
import com.meteocal.entity.MemberEventInvitation;

@Local
public interface MemberDAO extends Serializable{

	/**
	 * 
	 */
	
	Member addMember(Member Member);

	List<Member> getAllMembers();

	Member getMemberByID(Long l);

	Member getMemeberByEmail(String email);
	
	
	 List<Member> getAllMembersForEventExceptInvitedMembers(Event event, Member member);

	 List<Member> getAllInvitedMembersForEvent(Event event);

	List<MemberEventInvitation> getMemberEventRequests(Member member);

	MemberEventInvitation updateMemberEventInvitation(
			MemberEventInvitation memberEventInvitation);

	void delete(MemberEventInvitation memberEventInvitation);

}
