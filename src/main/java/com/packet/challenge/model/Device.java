package com.packet.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class Device{
	
	
	public enum Action {
		
		
		POWER_ON("power_on"),
		POWER_OFF("power_off"),
		REBOOT("reboot"),
		REINSTALL("reinstall");
		
		private final String actionStr;
		
		public String getActionStr() {
			return actionStr;
		}

		Action(String actionStr){
			this.actionStr = actionStr;
		}
	};
	
	private String plan;
	
	@JsonProperty("operating_system")
	private String operatingSystem;
	
	private ArrayNode facility;

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public ArrayNode getFacility() {
		return facility;
	}

	public void setFacility(ArrayNode facility) {
		this.facility = facility;
	}
	
}