package com.meteocal.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.meteocal.general.Constants;
import com.meteocal.general.Marker;


/**
 * The persistent class for the members database table.
 * 
 */
@Entity
@Table(name="members") 
@Cacheable(false)
public class Member implements Serializable ,Marker{
	private static final long serialVersionUID = 1L;
 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long ID;

	private int active=Constants.ACTIVE;
	
	private String email;
	@Column(name="last_name")
	private String lastName;
	@Column(name="first_name")
	private String firstName;
	private String password;
	private String token;
	@Column(name="user_picture_path")
	private String userPicPath;

	//bi-directional many-to-one association to Event
	@OneToMany(mappedBy="creator")
	private List<Event> events;

	@ManyToMany(targetEntity = Member.class)
	@JoinTable(name = "MEMBER_EVENT_INVITATION", inverseJoinColumns = { @JoinColumn(name = "member_id", referencedColumnName = "ID") })
	private List<Event> invitedEvented;
	
	public Member() {
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserPicPath() {
		return userPicPath;
	}

	public void setUserPicPath(String userPicPath) {
		this.userPicPath = userPicPath;
	}

	public List<Event> getEvents() {
		return events;
	}
 
	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Event> getInvitedEvented() {
		return invitedEvented;
	}

	public void setInvitedEvented(List<Event> invitedEvented) {
		this.invitedEvented = invitedEvented;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	
	

	

	
}