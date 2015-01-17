package com.meteocal.managedbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import com.general.utils.WebUtils;
import com.meteocal.general.Constants;

@ManagedBean
@ApplicationScoped
public class MainMenuHandlerBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
    private MenuModel model;

	@PostConstruct
	public void init() {

		model = new DefaultMenuModel();
	    DefaultMenuItem home = new DefaultMenuItem(WebUtils.extractFromBundle(Constants.MAIN_MENU));
	    home.setUrl("home.xhtml");
	    home.setIcon("resources/images/home_page.png");
	    
	    
	    DefaultMenuItem addEvent = new DefaultMenuItem(WebUtils.extractFromBundle(Constants.ADD_NEW_EVENT));
	    addEvent.setUrl("addevent.xhtml");
	    addEvent.setIcon("resources/images/add_event.png");
	    
	    DefaultMenuItem addBadWeather = new DefaultMenuItem(WebUtils.extractFromBundle(Constants.ADD_BAD_WEATHER));
	    addBadWeather.setUrl("addBadWeather.xhtml"); 
	    addBadWeather.setIcon("resources/images/bad_weather.png");
	   
	    DefaultMenuItem manageEvent = new DefaultMenuItem(WebUtils.extractFromBundle(Constants.MANAGE_EVENT));
	    manageEvent.setUrl("invitemembers.xhtml");
	    manageEvent.setIcon("resources/images/manage_events.png");


	    DefaultMenuItem viewRequests = new DefaultMenuItem(WebUtils.extractFromBundle(Constants.VIEW_REQUESTS));
	    viewRequests.setUrl("viewRequests.xhtml");
	    viewRequests.setIcon("resources/images/requests.png");
	    
	    model.addElement(home);
	    model.addElement(addEvent);
	    model.addElement(addBadWeather);
	    model.addElement(manageEvent);
	    model.addElement(viewRequests);

	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}

}
