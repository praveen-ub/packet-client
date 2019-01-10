package com.packet.challenge.service;

import static com.packet.challenge.AppConstants.DEVICES;
import static com.packet.challenge.AppConstants.EVENTS;
import static com.packet.challenge.AppConstants.PROJECTS;
import static com.packet.challenge.AppConstants.SLASH;
import static com.packet.challenge.AppConstants.STATE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.packet.challenge.controller.DeploymentController;
import com.packet.challenge.model.Device;
import com.packet.challenge.utils.RequestUtils;



@Component
public class DeviceService{
	
	@Value("${config.project_id}")
	private String PROJECT_ID;
	
	@Autowired
	private RequestUtils requestUtils;
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);
	
	public JsonNode create(Device deviceConfig){
		
		logger.info("Entering create device");
		
		HttpMethod method = HttpMethod.POST;
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Device> httpEntity = new HttpEntity<Device> (deviceConfig, requestUtils.getHeaders(method));
		String url = requestUtils.getEndPoint(PROJECTS) + SLASH + PROJECT_ID + SLASH + DEVICES;
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JsonNode.class);
		JsonNode responseBody = response.getBody();
		if(responseBody.get("errors")!=null){
			logger.debug("Unable to create device due to {}", response.getBody());
			return null;
		}
		return responseBody;	
	}
	
	public JsonNode getDetails(String deviceId){

		
		logger.info("Going to get details of device {}", deviceId);
		
		//handle device not found
		HttpMethod method = HttpMethod.GET;
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> httpEntity = new HttpEntity<Object> (requestUtils.getHeaders(method));
		String url = requestUtils.getEndPoint(DEVICES) + SLASH + deviceId + SLASH + EVENTS;
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JsonNode.class);
		if(response.getStatusCode() == HttpStatus.NOT_FOUND){
			return null;
		}
		return response.getBody();
	}

	public String getStatus(String deviceId){

		
		logger.info("Going to get status of device {}", deviceId);
		
		JsonNode deviceDetails = getDetails(deviceId);
		if(deviceDetails!=null){
			return deviceDetails.get(0).get(STATE).asText();
		}
		return null;
	}
	
	public boolean performAction(String deviceId,  Device.Action action){
		
		logger.info("Action {} trigger on device {}",action,deviceId);
		
		HttpMethod method = HttpMethod.GET;
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<Object> request = new HttpEntity<Object>(requestUtils.getHeaders(method));
		String url = requestUtils.getEndPoint(DEVICES) + SLASH + deviceId +"/actions?type="+action;
		restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		return true;
	}
	
	public boolean delete(String deviceId){
		
		logger.info("Entering to delete device {}", deviceId);
		
		HttpMethod method = HttpMethod.GET;
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> request = new HttpEntity<Object>(requestUtils.getHeaders(method));
		String url = requestUtils.getEndPoint(DEVICES) + SLASH + deviceId;
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
		return true;
	}

}