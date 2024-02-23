package com.kwsp.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "WDGPAR")
@TypeAlias("")

public class WDGPAR {
	 @Id
	 private ObjectId id; // Use ObjectId for id field
    private String WDPRNM;
    private String WDPITM;
    private String WDTVAL;
    
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getWDPRNM() {
		return WDPRNM;
	}
	public void setWDPRNM(String wDPRNM) {
		WDPRNM = wDPRNM;
	}
	public String getWDPITM() {
		return WDPITM;
	}
	public void setWDPITM(String wDPITM) {
		WDPITM = wDPITM;
	}
	public String getWDTVAL() {
		return WDTVAL;
	}
	public void setWDTVAL(String wDTVAL) {
		WDTVAL = wDTVAL;
	}
}