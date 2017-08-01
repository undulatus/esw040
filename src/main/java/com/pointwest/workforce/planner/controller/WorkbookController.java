package com.pointwest.workforce.planner.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;
import com.pointwest.workforce.planner.service.UploadDataService;


@RestController
public class WorkbookController {
	
	@Autowired
	UploadDataService uploadDataService;
	
	private static final Logger log = LoggerFactory.getLogger(WorkbookController.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/workbook")
    public ResponseEntity<Object> fetchAllWorkbook() {
       return null;
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/workbook/{workbookId}")
    public ResponseEntity<Object> fetchWorkbook() {
       return null;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/gbook")
	public ResponseEntity<Object> saveGoogleWorkbook(@RequestBody(required = false) 
	@RequestParam("username") String username, @RequestParam("url") String url) {
		return null;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/workbook/tnl")
    public ResponseEntity<Object> fetchAllOpportunityTnlRaw() {
       List<OpportunityTnlRaw> opportunityTnlRaws = uploadDataService.fetchAllOpportunityTnlRaw();
       if(opportunityTnlRaws == null) {
    	   return new ResponseEntity<>(new CustomError("No list retrieved"), HttpStatus.NOT_FOUND);
       } else {
    	   return new ResponseEntity<>(opportunityTnlRaws, HttpStatus.OK);
       }
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/workbook/tnl")
	public ResponseEntity<Object> saveOpportunity(@RequestBody(required = true) List<OpportunityTnlRaw> opportunityTnlRaws) {
		
		List<OpportunityTnlRaw> savedOpportunityTnlRaws = null;
		try {
			savedOpportunityTnlRaws = uploadDataService.saveOpportunityTnlRaw(opportunityTnlRaws);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		if (savedOpportunityTnlRaws == null) {
			return new ResponseEntity<>(new CustomError("Incorrect inputs, not saved"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(savedOpportunityTnlRaws, HttpStatus.OK);
		}
	}
	
}
