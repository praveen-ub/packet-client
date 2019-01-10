package com.packet.challenge.controller;

import java.io.IOException;

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
import com.packet.challenge.service.ProjectService;


@RestController
@RequestMapping("/deployment")
public class DeploymentController{
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private ProjectService projectService;
		
	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);
	
	@RequestMapping(method=RequestMethod.POST, value="run_acceptance_testing")
	public boolean runUserAcceptanceTesting(@RequestBody EnvironmentConfig config) throws InterruptedException, IOException{
		
		/*    Sample Input: t1.small.x86, Ubuntu 16.04 LTS, Amsterdam | any
		{
			"tearDownAfterRun":true,
			"deviceInfo":{
				"hostname":"staging-server",
				"plan":"e69c0169-4726-46ea-98f1-939c9e8a3607", 
				"operating_system":"1b9b78e3-de68-466e-ba00-f2123e89c112", 
				"facility":["8e6470b3-b75e-47d1-bb93-45b225750975","any"], 
			}
		} 
		 */
		
				
		logger.info("Starting to run acceptance testing");
		logger.info("Should enviroment has to be destroyed post run?::{}", config.isTearDownAfterRun());
			
	    JsonNode deviceDetails = deviceService.create(config.getDeviceInfo());
	    if(deviceDetails == null){
	    	return false; 
	    }
	    
	    String deviceId = deviceDetails.get("id").asText();
	    logger.info("Starting to poll device {} for 'active' status",deviceId);
	    long timerStart = System.currentTimeMillis();
	    //Wait until 5 mins to see if server deployment is successful
	    while(true){ 
	    	
	    	long timeElapsed = getTimeElapsedInMinutes(timerStart, System.currentTimeMillis());
	    	if(timeElapsed >= 5){	
	    		return false; //deployment failed
	    	}
	    	if("active".equalsIgnoreCase(deviceService.getStatus(deviceId))){
	    		logger.info("Device {} is now active", deviceId);
	    		break;
	    	}
	    	Thread.sleep(30*1000); //check status again after a minute
	    }
		
	    /* 
	     * Now that the device is active, deploy code and start the app servers
	     * once the app servers are started, run the test cases against the url
	     * <device_ip>:<port>
	     */
	    //Pausing for time taken for the app deployment & testing scripts to complete  
	    Thread.sleep(2*60*1000);
	    
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
	
	@RequestMapping(method=RequestMethod.GET, value="/project_keys")
	public JsonNode getProjectSSHKeys(){
		
		return projectService.getSSHKeys();
	}
	
	private long getTimeElapsedInMinutes (long startTime, long endTime){
		
		return ((startTime-endTime)/1000*60);
		
	}
	
}