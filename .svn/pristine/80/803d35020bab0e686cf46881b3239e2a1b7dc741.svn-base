package com.pointwest.workforce.planner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.Activity;
import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.TemplateDataService;

@RestController
public class ActivityController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@Autowired
	TemplateDataService templateDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/activities")
    public ResponseEntity<Object> fetchAllActivity() {
       List<Activity> activities = referenceDataService.fetchAllActivity();
       if(activities == null) {
    	   return new ResponseEntity<>(new CustomError("No activities retrieved"), HttpStatus.NOT_FOUND);
       } else {
    	   return new ResponseEntity<>(activities, HttpStatus.OK);
       }
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/activities/{activityId}")
    public ResponseEntity<Object> fetchActivity(@PathVariable int activityId) {
        Activity activity = referenceDataService.fetchActivity(activityId); 
		if(activity == null) {
			return new ResponseEntity<>(new CustomError("Activity not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(activity, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/servicetypes/{serviceTypeId}/activities")
    public ResponseEntity<Object> fetchActivitiesByServiceTypeId(@PathVariable int serviceTypeId) {
		List<Activity> activities = templateDataService.fetchActivitiesByServiceTypeId(serviceTypeId);
		if(activities == null) {
			return new ResponseEntity<>(new CustomError("No activities retrieved for the given service type"), HttpStatus.NOT_FOUND);
		} else {
		   return new ResponseEntity<>(activities, HttpStatus.OK);
		}
    }
	
	//implemented like this to prevent breaking integration
	@RequestMapping(method=RequestMethod.POST, value="/activities")
    public ResponseEntity<Object> saveCustomActivity(@RequestBody(required=true) String activityName, 
    		@RequestParam(required = false) Long opportunityId) {
		Activity customActivity = null;
		if(opportunityId != null) {
			customActivity = new Activity(activityName, true, opportunityId);
		} else {			
			customActivity = new Activity(activityName, true);
		}
		customActivity = referenceDataService.addActivity(customActivity);
		if(customActivity==null) {
			return new ResponseEntity<>(new CustomError("Incorrect inputs, not saved"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(customActivity, HttpStatus.CREATED);
		}
    }

}
