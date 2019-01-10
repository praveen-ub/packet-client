package com.packet.challenge.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.packet.challenge.service.DeviceService;
import com.packet.challenge.service.ProjectService;

@RestController
@RequestMapping("/deployment")
public class DeploymentController{
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private DeviceService deviceService;
	
	
	@RequestMapping("/project_events")
	public JsonNode getProjectEvents(){
		
		return projectService.getEvents();
	}
	
	public boolean startUserAcceptanceTesting(boolean retainEnvironment) throws IOException{
		
		/*
		 * Plan - t1x small, OS: Ubuntu 16.04 LTS, facility: Amsterdam | Any
		 */
		
		String sampleDeviceConfig = "{\"plan\": \"e69c0169-4726-46ea-98f1-939c9e8a3607\", "
									+ "\"operating_system\": \"1b9b78e3-de68-466e-ba00-f2123e89c112\""
									+ "\"facility\":[\"8e6470b3-b75e-47d1-bb93-45b225750975\", \"any\"]}"
									+ "\"tags\":[\"jan9-staging\"]";
		
		//Externalise this to JacksonUtils
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode deviceConfig = mapper.readTree(sampleDeviceConfig);
	    deviceService.createDevice(deviceConfig);
	  
//	    while(true){
//	    		
//	    		//keep polling for device status
//	    }
		
		//create device with sample config
		//poll device status to see if its ready
		//if ready, deploy code and start the app servers
		//collect device information like IP
		//once server is started, trigger acceptance testing using the device's ip
		//Once acceptance testing is done, mail the report
		//Tear down the environment is retainEnvironment is false
		//else power off the server and return the device details for futher access
		
		//Handle provisioning error
		//Handle de-provisioning error
		
		return true;
		
	}
	
	
	
}