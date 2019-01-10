package com.packet.challenge.model;


public class EnvironmentConfig{
	
	
	private boolean tearDownAfterRun;
	
	private Device deviceInfo;

	public boolean isTearDownAfterRun() {
		return tearDownAfterRun;
	}

	public void setTearDownAfterRun(boolean tearDownAfterRun) {
		this.tearDownAfterRun = tearDownAfterRun;
	}

	public Device getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(Device deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
}