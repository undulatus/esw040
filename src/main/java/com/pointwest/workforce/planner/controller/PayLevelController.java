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
import com.pointwest.workforce.planner.domain.PayLevel;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.TemplateDataService;

@RestController
public class PayLevelController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@Autowired
	TemplateDataService templateDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/paylevels")
    public ResponseEntity<Object> fetchAllPayLevel() {
		List<PayLevel> payLevels = referenceDataService.fetchAllPayLevel();
		if(payLevels == null || payLevels.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No Pay Levels retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(payLevels, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/paylevels/{payLevelId}")
    public ResponseEntity<Object> fetchActivity(@PathVariable int payLevelId) {
		PayLevel payLevel = referenceDataService.fetchPayLevel(payLevelId);
		if(payLevel == null) {
			return new ResponseEntity<>(new CustomError("Pay Level not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(payLevel, HttpStatus.OK);
		}
    }
	

}
