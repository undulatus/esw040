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

import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.ServiceType;
import com.pointwest.workforce.planner.service.ReferenceDataService;

@RestController
public class ServiceTypeController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/servicetypes")
    public ResponseEntity<Object> fetchAllServiceType() {
		List<ServiceType> serviceTypes = referenceDataService.fetchAllServiceType();
		if(serviceTypes == null || serviceTypes.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No Service Types retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(serviceTypes, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/servicetypes/{serviceTypeId}")
    public ResponseEntity<Object> fetchServiceType(@PathVariable int serviceTypeId) {
		ServiceType serviceType = referenceDataService.fetchServiceType(serviceTypeId);
		if(serviceType == null) {
			return new ResponseEntity<>(new CustomError("Service Type not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(serviceType, HttpStatus.OK);
		}
    }
	
	//not used
	@RequestMapping(method=RequestMethod.POST, value="/servicetypes")
    public void addServiceType(@RequestBody ServiceType serviceType) {
		referenceDataService.addServiceType(serviceType);
    }

}
