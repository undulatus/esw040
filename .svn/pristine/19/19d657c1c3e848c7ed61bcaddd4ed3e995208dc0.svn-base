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
import com.pointwest.workforce.planner.service.ReferenceDataService;

@RestController
public class BusinessUnitController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/businessunits")
    public ResponseEntity<Object> fetchAllBusinessUnit() {
       List<BusinessUnit> businessUnits = referenceDataService.fetchAllBusinessUnit();
       if(businessUnits == null || businessUnits.isEmpty()) {
    	   return new ResponseEntity<>(new CustomError("No business units retrieved"), HttpStatus.NOT_FOUND);
       } else {
    	   return new ResponseEntity<>(businessUnits, HttpStatus.OK);
       }
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/businessunits/{businessUnitId}")
    public ResponseEntity<Object> fetchBusinessUnit(@PathVariable int businessUnitId) {
		BusinessUnit businessUnit = referenceDataService.fetchBusinessUnit(businessUnitId);
		if(businessUnit == null) {
			return new ResponseEntity<>(new CustomError("Business unit not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(businessUnit, HttpStatus.OK);
		}
    }
	
	//not used
	@RequestMapping(method=RequestMethod.POST, value="/businessunits")
    public void addBusinessUnit(@RequestBody BusinessUnit businessUnit) {
		referenceDataService.addBusinessUnit(businessUnit);
    }

}
