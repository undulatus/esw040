package com.pointwest.workforce.planner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.Group;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.TemplateDataService;

@RestController
public class GroupController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@Autowired
	TemplateDataService templateDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/groups")
    public ResponseEntity<Object> fetchAllGroup() {
       List<Group> groups = referenceDataService.fetchAllGroup();
		if(groups == null || groups.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No groups retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(groups, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/groups/{groupId}")
    public ResponseEntity<Object> fetchGroup(@PathVariable int groupId) {
        Group group = referenceDataService.fetchGroup(groupId);
		if(group == null) {
			return new ResponseEntity<>(new CustomError("Group not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(group, HttpStatus.OK);
		}
    }
	

}
