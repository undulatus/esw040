package com.pointwest.workforce.planner.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.OrganizationRole;
import com.pointwest.workforce.planner.security.JWTDecoderService;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.TemplateDataService;

@RestController
public class OrganizationRoleController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@Autowired
	TemplateDataService templateDataService;
	
	@Autowired
	JWTDecoderService decoderService;
	
	@Value("${token.request.header}")
	private String tokenHeader;
	
	private static final Logger log = LoggerFactory.getLogger(OrganizationRoleController.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/orgroles")
    public ResponseEntity<Object> fetchAllOrgRole(HttpServletRequest request) {
		//to be made a filter
		try {
			String token = request.getHeader(tokenHeader);
			log.debug("token recieved " + token);
			if(decoderService.isValidToken(token)) {
				List<OrganizationRole> orgRoles = referenceDataService.fetchAllOrgRole();
				log.debug("tokenuser : " + decoderService.getTokenUser(token).toString());
				if(orgRoles == null || orgRoles.isEmpty()) {
					return new ResponseEntity<>(new CustomError("No Organization Roles retrieved"), HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<>(orgRoles, HttpStatus.OK);
				}
				
			} else {
				return new ResponseEntity<>(new CustomError("Invalid Token"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid Token"), HttpStatus.UNAUTHORIZED);
		}
		
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/orgroles/{orgroleId}")
    public ResponseEntity<Object> fetchOrgRole(@PathVariable int orgroleId) {
		OrganizationRole orgRole = referenceDataService.fetchOrgRole(orgroleId);
       if(orgRole == null) {
			return new ResponseEntity<>(new CustomError("Organization Role not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(orgRole, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/servicetypes/{serviceTypeId}/orgroles")
    public ResponseEntity<Object> fetchOrgRolesByServiceTypeId(@PathVariable int serviceTypeId) {
       List<OrganizationRole> orgRoles = templateDataService.fetchOrgRolesByServiceTypeId(serviceTypeId);
		if(orgRoles == null) {
			return new ResponseEntity<>(new CustomError("No organization roles retrieved for the given service type"), HttpStatus.NOT_FOUND);
		} else {
		   return new ResponseEntity<>(orgRoles, HttpStatus.OK);
		}
    }

}
