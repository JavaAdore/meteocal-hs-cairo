package com.meteocal.view.converter;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.meteocal.business.service.EventService;
import com.meteocal.general.Constants;
import com.meteocal.general.Marker;

@ManagedBean
@ApplicationScoped
public class GenericMarkedEntityConverter implements Converter {

	@EJB
	EventService eventService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Object targetedObject = ((UISelectOne) component).getValue();
		if (targetedObject == null
				|| (targetedObject instanceof Marker) == false) {
			throw Constants.CONVERSION_EXCEPTION;
		}	
		Class targetedClass = targetedObject.getClass();
		try {
			Marker marker= (Marker) targetedClass.newInstance();
			marker.setID(Long.valueOf(value));
			return marker;
			 
		} catch (Exception e) {
			e.printStackTrace();
			throw Constants.CONVERSION_EXCEPTION;

		}
			
		
		

	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if(value!=null)
		{
		return ((Marker) value).getID() + "";
		}else
		{
			return ""; 
		}

	}

}
