package com.packet.challenge.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.packet.challenge.config.PacketConfig;

@Service
public class DeviceService{
	
	public JsonNode createDevice(JsonNode deviceConfig){
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-Auth-Token", PacketConfig.AUTH_TOKEN);
		headers.add("Content-Type", "application/json");
		HttpEntity<JsonNode> httpEntity = new HttpEntity<JsonNode> (deviceConfig,headers);
		String url = PacketConfig.PROJECTS_ENDPOINT+"/"+PacketConfig.PROJECT_ID+"/devices";
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JsonNode.class);
		return response.getBody();	
	}
	
	public JsonNode getDeviceDetails(String deviceId){

		//handle device not found
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Auth-Token", PacketConfig.AUTH_TOKEN); //Move string literals to constants
		HttpEntity<String> httpEntity = new HttpEntity<String> ("headers",headers);
		String url = PacketConfig.DEVICES_ENDPOINT+"/"+deviceId+"/events";
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JsonNode.class);
		return response.getBody();
	}

	public String getDeviceStatus(String deviceId){

		//handle device not found
		JsonNode deviceDetails = getDeviceDetails(deviceId);
		return deviceDetails.get(0).get("state").asText();
	}
	
	public boolean performAction(String deviceId, String action){
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-Auth-Token", PacketConfig.AUTH_TOKEN);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		String url = PacketConfig.DEVICES_ENDPOINT+"/"+deviceId+"/actions?type="+action;
		restTemplate.exchange(url, HttpMethod.POST, request, String.class);
		return true;
	}
	
	
	public boolean deleteDevice(String deviceId){
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("X-Auth-Token", PacketConfig.AUTH_TOKEN);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		String url = PacketConfig.DEVICES_ENDPOINT+"/"+deviceId;
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
		return true;
	}

}