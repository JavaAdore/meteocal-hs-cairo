package com.meteocal.business.service;

import java.util.PriorityQueue;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import com.meteocal.business.facade.EventFacade;
import com.meteocal.entity.Event;

/**
 * 
 * @author orcl
 */
@Singleton
public class AlertThread extends Thread {

	@EJB
	EventFacade eventFacade;

	Queue<Event> eventsQueue = new PriorityQueue<Event>();
	boolean started;
	@PostConstruct
	public void init() {

		InitializeQueue();
		
	}

	
	
	
	public void run() {
		boolean result = false;
		for (;;) {
			try {
			

				Event e = eventsQueue.peek();
				if (e != null) {
					result = eventFacade.assignWeatherForEvent(e);
				}
				if (result) {
					eventsQueue.poll();
					System.out
							.println("Thread Successfully completed mession ");
					result=false;
				}
				sleep(2000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void InitializeQueue() {

		eventsQueue.addAll(eventFacade.getEventsWithIn3Days());
	}
	
	public void startThread()
	{
		if(!started)
		{
			start(); 
			started = true;
		}
	}
	
	
	public void registerOnQueue(Event event)
	{
		eventsQueue.add(event);
	}
	public void removeFromQueue(Event event)
	{
		eventsQueue.remove(event);
	}

	
}
