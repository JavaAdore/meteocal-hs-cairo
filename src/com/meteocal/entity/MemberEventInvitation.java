package com.meteocal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.meteocal.general.Marker;

/**
 * The persistent class for the memberinevent database table.
 * 
 */
@Entity
@Table(name = "MEMBER_EVENT_INVITATION")
public class MemberEventInvitation implements Serializable, Marker {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ID;

	private Integer status;

	@Column(name = "InvitationDate")
	private Date invitationDate;
	@Column(name = "ACCEPT_OR_DECLINE_DATE")
	private Date acceptOrDeclineDate;

	// bi-directional many-to-one association to Event
	@ManyToOne
	@JoinColumn(name = "events_id", referencedColumnName = "ID")
	private Event event;

	// bi-directional many-to-one association to Member
	@ManyToOne
	@JoinColumn(name = "members_id", referencedColumnName = "ID")
	private Member member;

	@Column(name = "seen")
	private Integer seen = 0;

	@Column(name = "request_invitation")
	private Integer requestInvitation;

	public MemberEventInvitation() {
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getInvitationDate() {
		return invitationDate;
	}

	public void setInvitationDate(Date invitationDate) {
		this.invitationDate = invitationDate;
	}

	public Date getAcceptOrDeclineDate() {
		return acceptOrDeclineDate;
	}

	public void setAcceptOrDeclineDate(Date acceptOrDeclineDate) {
		this.acceptOrDeclineDate = acceptOrDeclineDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getSeen() {
		return seen;
	}

	public void setSeen(Integer seen) {
		this.seen = seen;
	}

	public boolean hasSeen() {
		return seen != 0;
	}

	public Integer getRequestInvitation() {
		return requestInvitation;
	}

	public void setRequestInvitation(Integer requestInvitation) {
		this.requestInvitation = requestInvitation;
	}

}