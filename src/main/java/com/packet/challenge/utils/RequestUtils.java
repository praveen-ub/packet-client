package com.packet.challenge.utils;

import static com.packet.challenge.AppConstants.ACCEPT;
import static com.packet.challenge.AppConstants.AUTH_TOKEN_STRING;
import static com.packet.challenge.AppConstants.CONTENT_TYPE;
import static com.packet.challenge.AppConstants.JSON;
import static com.packet.challenge.AppConstants.SLASH;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class RequestUtils{
	
	@Value("${app.config.base_url}")
	private String BASE_URL;
	
	@Value("${app.config.auth_token}")
	private String AUTH_TOKEN;

	public MultiValueMap<String, String> getHeaders(HttpMethod method) {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(AUTH_TOKEN_STRING, AUTH_TOKEN);
		if (method == HttpMethod.POST) {
			headers.add(CONTENT_TYPE, JSON);
			headers.add(ACCEPT, JSON);
		}
		return headers;
	}

	public String getEndPoint(String resourceName) {

		return BASE_URL + SLASH + resourceName;
	}
	
	public String getBaseUrl(){
		
		return BASE_URL;
	}
}