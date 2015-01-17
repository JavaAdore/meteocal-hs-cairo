package com.meteocal.business.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.service.MemberService;
import com.meteocal.entity.Event;
import com.meteocal.entity.Member;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@Stateless
public class MemberFacadeImpl implements MemberFacade {

	@EJB
	private MemberService memberService;

	
	@Override
	public List<Member> getAllMembersForEventExceptInvitedMembers(Event event) {
		return memberService.getAllMembersForEventExceptInvitedMembers(event,WebUtils.getCurrentUser());
	}

	@Override
	public List<Member> getAllInvitedMembersForEvent(Event event) {
		
		return memberService.getAllInvitedMembersForEvent(event);
	}

	

	@Override
	public List<Member> getAllMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member getMemberByID(Long l) {
		// TODO Auto-generated method stub
		return null;
	}


	public Member login(String email, String password) throws MeteocalExceltion {

		Member member = memberService.getMemeberByEmail(email);
		if (member == null) {
			throw Constants.invalidEmail;
		} else {
			if (member.getPassword() != null
					&& member.getPassword().equals(Util.encrypt(password))) {
				return member;

			} else {
				throw Constants.invalidPassword;
			}
		}
	}


	@Override
	public Member register(String firstName, String lastName, String email,
			String password, String confirmPassword, String userPicPath)
			throws MeteocalExceltion {
	
		
		Member member = new Member();
		member.setEmail(email);
		member.setFirstName(firstName);
		member.setLastName(lastName);
		member.setUserPicPath(userPicPath);
		member.setPassword(Util.encrypt(password));
		member = memberService.addMember(member);
		return member;

	}
}
