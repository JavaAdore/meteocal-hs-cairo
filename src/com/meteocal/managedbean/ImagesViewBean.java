package com.meteocal.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.gmap.GMap;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;

import com.general.utils.Util;
import com.general.utils.WebUtils;
import com.meteocal.business.facade.EventFacade;
import com.meteocal.business.service.AlertThread;
import com.meteocal.entity.BadWeather;
import com.meteocal.entity.Event;
import com.meteocal.entity.EventType;
import com.meteocal.entity.Member;
import com.meteocal.general.Constants;
import com.meteocal.general.MeteocalExceltion;

@ManagedBean
@ApplicationScoped
public class ImagesViewBean implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> images;
     
	    @PostConstruct
	    public void init() {
	        images = new ArrayList<String>();
	        for (int i = 1; i <= 7; i++) {
	            images.add("image" + i + ".jpg");
	        }
	    } 
	  
	    public List<String> getImages() {
	        return images;
	    }
}