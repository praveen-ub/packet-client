package com.packet.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.packet.challenge.service.ProjectService;

@RestController
@RequestMapping("/deployment")
public class DeploymentController{
	
	@Autowired
	private ProjectService projectService;
	
	
	@RequestMapping("/project_events")
	public JsonNode getProjectEvents(){
		
		return projectService.getEvents();
	}
	
}