package com.packet.challenge.service;

import org.springframework.http.HttpEntity;
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

}