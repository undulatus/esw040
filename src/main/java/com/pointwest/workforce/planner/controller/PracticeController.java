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
import com.pointwest.workforce.planner.domain.Practice;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.TemplateDataService;

@RestController
public class PracticeController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@Autowired
	TemplateDataService templateDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/practices")
    public ResponseEntity<Object> fetchAllPractice() {
		List<Practice> practices = referenceDataService.fetchAllPractice();
		if(practices == null || practices.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No Practices retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(practices, HttpStatus.OK);
		}
    }
	
	
	@RequestMapping(method=RequestMethod.GET, value="/practices/{practiceId}")
    public ResponseEntity<Object> fetchPractice(@PathVariable int practiceId) {
		Practice practice = referenceDataService.fetchPractice(practiceId);
		if(practice == null) {
			return new ResponseEntity<>(new CustomError("Practice not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(practice, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/roles/{roleId}/practices")
    public ResponseEntity<Object> fetchPracticesByRoleId(@PathVariable int roleId) {
		List<Practice> practices = templateDataService.fetchPracticesByRoleId(roleId);
		if(practices == null || practices.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No Practices retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(practices, HttpStatus.OK);
		}
    }

}
