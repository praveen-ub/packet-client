package com.packet.challenge.utils;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JacksonUtils{
	
	
	public JsonNode convertStringToJson(String jsonString){
		
		try{
			ObjectMapper mapper = new ObjectMapper();
		    JsonNode json = mapper.readTree(jsonString);
		    return json;
		}
		catch(IOException ioe){
			System.out.println("Exception occured while parsing json string");
		}
		return null;
	}
	
}