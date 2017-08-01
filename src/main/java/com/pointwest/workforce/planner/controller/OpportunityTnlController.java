package com.pointwest.workforce.planner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.OpportunityTnl;
import com.pointwest.workforce.planner.service.UploadDataService;

@RestController
public class OpportunityTnlController {
	
	@Autowired
	UploadDataService uploadDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/workbook/tnl")
    public ResponseEntity<Object> fetchAllOpportunityTnl() {
       List<OpportunityTnl> opportunityTnls = uploadDataService.fetchAllOpporutnityTnl();
       if(opportunityTnls == null) {
    	   return new ResponseEntity<>(new CustomError("No list retrieved"), HttpStatus.NOT_FOUND);
       } else {
    	   return new ResponseEntity<>(opportunityTnls, HttpStatus.OK);
       }
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/opportunities")
	public ResponseEntity<Object> saveOpportunity(@RequestBody(required = true) List<OpportunityTnl> opportunityTnls) {
		
		List<OpportunityTnl> savedOpportunityTnls = null;
		try {
			savedOpportunityTnls = uploadDataService.saveOpportunityTnl(opportunityTnls);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
		if (savedOpportunityTnls == null) {
			return new ResponseEntity<>(new CustomError("Incorrect inputs, not saved"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(savedOpportunityTnls, HttpStatus.OK);
		}
	}
	
}
