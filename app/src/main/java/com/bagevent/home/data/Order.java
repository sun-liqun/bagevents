package com.bagevent.home.data;

import java.io.Serializable;

import org.json.JSONObject;

public class Order implements Serializable {

	public String name;

	public String barcodes;

	
	public Order( String restName, String receiverAddr) {
		this.name = restName;
		this.barcodes = receiverAddr;
	}

}
