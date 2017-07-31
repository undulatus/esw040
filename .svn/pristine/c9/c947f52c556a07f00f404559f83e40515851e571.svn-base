package com.pointwest.workforce.planner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.BusinessUnit;
import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.OpportunityStatus;
import com.pointwest.workforce.planner.service.ReferenceDataService;

@RestController
public class OpportunityStatusController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/opportunitystatuses")
    public ResponseEntity<Object> fetchAllOpportunityStatuses() {
       List<OpportunityStatus> opportunityStatuses = referenceDataService.fetchAllOpportunityStatus();
       if(opportunityStatuses == null || opportunityStatuses.isEmpty()) {
    	   return new ResponseEntity<>(new CustomError("No opportunity status retrieved"), HttpStatus.NOT_FOUND);
       } else {
    	   return new ResponseEntity<>(opportunityStatuses, HttpStatus.OK);
       }
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/opportunitystatuses/{opportunityStatusId}")
    public ResponseEntity<Object> fetchOpportunityStatus(@PathVariable int opportunityStatusId) {
		OpportunityStatus opportunityStatus = referenceDataService.fetchOpportunityStatus(opportunityStatusId);
		if(opportunityStatus == null) {
			return new ResponseEntity<>(new CustomError("Opportunity Status not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(opportunityStatus, HttpStatus.OK);
		}
    }
	

}
