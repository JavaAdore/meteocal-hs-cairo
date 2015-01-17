package com.meteocal.general;

import java.io.Serializable;

public abstract interface Marker extends  Serializable{
	
	Long getID();
	void setID(Long ID);
	
}
