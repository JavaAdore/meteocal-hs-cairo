package com.meteocal.business.facade;

import java.util.List;

import javax.ejb.Local;

import com.meteocal.entity.Event;
import com.meteocal.entity.Member;
import com.meteocal.general.MeteocalExceltion;


@Local
public interface MemberFacade  {
	

	List<Member> getAllMembersForEventExceptInvitedMembers(Event event);
	List<Member> getAllInvitedMembersForEvent(Event event);

	List<Member> getAllMembers();

	Member getMemberByID(Long l);
	
	Member login(String email , String password)throws MeteocalExceltion ;
	
	Member register(String firstName, String lastName, String email, String password, String confirmPassword, String pictureURL) throws MeteocalExceltion ;
	

	
}
