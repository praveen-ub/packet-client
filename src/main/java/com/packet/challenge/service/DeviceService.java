package com.packet.challenge.service;

import static com.packet.challenge.AppConstants.DEVICES;
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
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.packet.challenge.controller.DeploymentController;
import com.packet.challenge.model.Device;
import com.packet.challenge.utils.RequestUtils;



@Service
public class DeviceService{
	
		
	@Autowired
	private RequestUtils requestUtils;
	
	@Value("${app.config.project_id}")
	private String PROJECT_ID;
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);
	
	public JsonNode create(Device deviceConfig) {
		
		logger.info("Entering create device");
		
		String url = requestUtils.getEndPoint(PROJECTS) + SLASH + PROJECT_ID + SLASH + DEVICES;
		HttpMethod method = HttpMethod.POST;
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers =  requestUtils.getHeaders(method);
		HttpEntity<Device> httpEntity = new HttpEntity<Device> (deviceConfig,headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, method , httpEntity, JsonNode.class);
		JsonNode responseBody = response.getBody();
		
		if(response.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
			logger.debug("Unable to create device due to {}", response.getBody());
			return null;
		}
		logger.info("Created device successfully");
		return responseBody;	
	}
	
	public JsonNode getDetails(String deviceId){

		logger.info("Going to get details of device {}", deviceId);
		
		HttpMethod method = HttpMethod.GET;
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> httpEntity = new HttpEntity<Object> (requestUtils.getHeaders(method));
		String url = requestUtils.getEndPoint(DEVICES) + SLASH + deviceId;
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, method , httpEntity, JsonNode.class);
		if(response.getStatusCode() == HttpStatus.NOT_FOUND){
			logger.debug("Device with id {} not found",deviceId);
			return null;
		}
		return response.getBody();
	}

	public String getStatus(String deviceId){

		
		logger.info("Going to get status of device {}", deviceId);
		
		JsonNode deviceDetails = getDetails(deviceId);
		if(deviceDetails!=null){
			return deviceDetails.get(STATE).asText();
		}
		return null;
	}
	
	//Does not work with project level API key
	public boolean performAction(String deviceId,  Device.Action action){
		
		logger.info("Action {} trigger on device {}",action,deviceId);
		
		HttpMethod method = HttpMethod.POST;
		RestTemplate restTemplate = new RestTemplate();
		
		HttpEntity<Object> request = new HttpEntity<Object>(requestUtils.getHeaders(method));
		String url = requestUtils.getEndPoint(DEVICES) + SLASH + deviceId +"/actions?type="+action.getActionStr();
		restTemplate.exchange(url, method, request, String.class);
		return true;
	}
	
	public boolean delete(String deviceId){
		
		logger.info("Entering to delete device {}", deviceId);
		
		HttpMethod method = HttpMethod.DELETE;
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> request = new HttpEntity<Object>(requestUtils.getHeaders(method));
		String url = requestUtils.getEndPoint(DEVICES) + SLASH + deviceId;
		restTemplate.exchange(url, method, request, String.class);
		logger.info("Deleted device {} successfully", deviceId);
		return true;
	}

}