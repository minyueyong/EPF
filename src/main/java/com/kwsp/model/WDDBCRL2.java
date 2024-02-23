package com.kwsp.model;

import java.util.Date;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WDDBCRL2")
@TypeAlias("")
public class WDDBCRL2 {

	String WDSTS8;

	public String getWDSTS8() {
		return WDSTS8;
	}

	public void setWDSTS8(String wDSTS8) {
		WDSTS8 = wDSTS8;
	}

	
}
