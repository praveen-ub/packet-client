package com.packet.challenge.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.packet.challenge.config.PacketConfig;

@Service
public class ProjectService{
	
	
	public JsonNode getEvents(){
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Auth-Token", PacketConfig.AUTH_TOKEN); //Move string literals to constants
		HttpEntity<String> httpEntity = new HttpEntity<String> ("headers",headers);
		String url = PacketConfig.PROJECTS_ENDPOINT+"/"+PacketConfig.PROJECT_ID+"/events";
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JsonNode.class);
		return response.getBody();
	}
	
}