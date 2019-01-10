package com.packet.challenge.service;

import static com.packet.challenge.AppConstants.PROJECTS;
import static com.packet.challenge.AppConstants.SLASH;
import static com.packet.challenge.AppConstants.KEYS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.packet.challenge.utils.RequestUtils;

@Service
public class ProjectService{
	
	@Autowired
	private RequestUtils requestUtils;
	
	@Value("${app.config.project_id}")
	private String PROJECT_ID;
	
	/*
	 * This method could be useful to get ssh keys to login to devices created in the staging environment
	 */
	public JsonNode getSSHKeys(){
		
		HttpMethod method = HttpMethod.GET;
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> headers =  requestUtils.getHeaders(method);
		HttpEntity<Object> httpEntity = new HttpEntity<Object> (headers);
		String url = requestUtils.getEndPoint(PROJECTS) + SLASH + PROJECT_ID + SLASH + KEYS;
		ResponseEntity<JsonNode> response = restTemplate.exchange(url, method, httpEntity, JsonNode.class);
		return response.getBody();
	}
	
}