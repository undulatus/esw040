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
import com.pointwest.workforce.planner.domain.Role;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.TemplateDataService;

@RestController
public class RoleController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@Autowired
	TemplateDataService templateDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/roles")
    public ResponseEntity<Object> fetchAllRole() {
		List<Role> roles = referenceDataService.fetchAllRole();
		if(roles == null || roles.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No Roles retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(roles, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/roles/{roleId}")
    public ResponseEntity<Object> fetchRole(@PathVariable int roleId) {
       Role role = referenceDataService.fetchRole(roleId);
       if(role == null) {
			return new ResponseEntity<>(new CustomError("Role not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(role, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/servicetypes/{serviceTypeId}/roles")
    public ResponseEntity<Object> fetchRolesByServiceTypeId(@PathVariable int serviceTypeId) {
       List<Role> roles = templateDataService.fetchRolesByServiceTypeId(serviceTypeId);
		if(roles == null) {
			return new ResponseEntity<>(new CustomError("No roles retrieved for the given service type"), HttpStatus.NOT_FOUND);
		} else {
		   return new ResponseEntity<>(roles, HttpStatus.OK);
		}
    }

}
