package com.meteocal.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.meteocal.general.Marker;

/**
 * The persistent class for the events database table.
 * 
 */
@Entity
@Table(name = "events")
public class Event implements Serializable, Marker, Comparable<Event> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ID;
	@Column(name = "last_available_time_to_accept_or_invite")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastAvailableTimeToAcceptOrInvite;
	private String description;
	
	@Column(name = "start_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Column(name = "end_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	@Column(name = "event_title")
	private String eventTitle;
	@Column(name = "latitude")
	private String lat;
	@Column(name = "longitude")
	private String lon; 

	// bi-directional many-to-one association to Member
	@ManyToOne
	@JoinColumn(name = "creator_ID", referencedColumnName = "ID")
	private Member creator;

	@ManyToMany(targetEntity = Member.class)
	@JoinTable(name = "MEMBER_EVENT_INVITATION", inverseJoinColumns = { @JoinColumn(name = "member_id", referencedColumnName = "ID") })
	private List<Member> InvitedMembers;

	@OneToOne(mappedBy="event",fetch=FetchType.EAGER)
	private WeatherStatus weatherStatus;

	@ManyToOne
	@JoinColumn(name = "event_type_id", referencedColumnName = "id")
	private EventType eventType;
	
	@ManyToOne
	@JoinColumn(name = "bad_weather_id", referencedColumnName = "id")
	private BadWeather badWeather;

	public Event() {
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member creator) {
		this.creator = creator;
	}

	public List<Member> getInvitedMembers() {
		return InvitedMembers;
	}

	public void setInvitedMembers(List<Member> invitedMembers) {
		InvitedMembers = invitedMembers;
	}

	public WeatherStatus getWeatherStatus() {
		return weatherStatus;
	}

	public void setWeatherStatus(WeatherStatus weatherStatus) {
		this.weatherStatus = weatherStatus;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public Date getLastAvailableTimeToAcceptOrInvite() {
		return lastAvailableTimeToAcceptOrInvite;
	}

	public void setLastAvailableTimeToAcceptOrInvite(
			Date lastAvailableTimeToAcceptOrInvite) {
		this.lastAvailableTimeToAcceptOrInvite = lastAvailableTimeToAcceptOrInvite;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

	@Override
	public int compareTo(Event o) {
		if (o != null && o.getStartDate() != null && startDate != null) {

			return o.getStartDate().compareTo(startDate);

		}

		return 0;
	}

	public BadWeather getBadWeather() {
		return badWeather;
	}

	public void setBadWeather(BadWeather badWeather) {
		this.badWeather = badWeather;
	}

}