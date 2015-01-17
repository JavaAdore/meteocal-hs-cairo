package com.meteocal.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.meteocal.business.service.MemberService;
import com.meteocal.dao.EventDAO;
import com.meteocal.entity.Event;



@ManagedBean
@ViewScoped
public class TestBean implements Serializable {

	/**
	 * 
	 */

	@EJB
	MemberService memberService;
	 
	@EJB EventDAO eventDAO;
	private static final long serialVersionUID = 1L;

	public void execute()  { 
		try{
		
			List<Event> e = eventDAO.getEventsWithIn3Days();

		System.out.println("===========================");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	
}
