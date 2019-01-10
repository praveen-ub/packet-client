package com.packet.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.packet.challenge.model.Device;
import com.packet.challenge.model.EnvironmentConfig;
import com.packet.challenge.service.DeviceService;


@RestController
@RequestMapping("/deployment")
public class DeploymentController{
	
	@Autowired
	private DeviceService deviceService;
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);
	
	@RequestMapping(method=RequestMethod.POST, value="run_acceptance_testing")
	public boolean runUserAcceptanceTesting(@RequestBody EnvironmentConfig config) throws InterruptedException{
		
		logger.info("Starting to run acceptance testing");
		logger.info("Should enviroment has to be retained post run?::{}", config.isTearDownAfterRun());
			
	    JsonNode deviceDetails = deviceService.create(config.getDeviceInfo());
	    if(deviceDetails == null){
	    	return false; 
	    }
	    
	    String deviceId = deviceDetails.get("id").asText();
	    logger.info("Starting to poll device {} for 'active' status",deviceId);
	    long timerStart = System.currentTimeMillis();
	    //Wait until 5 mins to see if server deployment is successful
	    while(getTimeElapsedInMinutes(timerStart, System.currentTimeMillis()) < 5){ 
	    	
	    	if("active".equalsIgnoreCase(deviceService.getStatus(deviceId))){
	    		break;
	    	}
	    	Thread.sleep(60*1000); //check status again after a minute
	    }
		
	    /* 
	     * Now that the device is active, deploy code and start the app servers
	     * once the app servers are started, run the test cases against the url
	     * <device_ip>:<port>
	     */
	    //Pausing for time taken for the app deployment & testing scripts to complete  
	    Thread.sleep(5*60*1000);
	    
	    if(config.isTearDownAfterRun()){
	    	logger.info("Deleting device {}",deviceId);
	    	deviceService.delete(deviceId);
	    }	
	    else{
	    	logger.info("Powering off device {}",deviceId);
	    	deviceService.performAction(deviceId, Device.Action.POWER_OFF);
	    }
		return true;
	}
	
	private long getTimeElapsedInMinutes (long startTime, long endTime){
		
		return ((startTime-endTime)/1000*60);
		
	}
	
}