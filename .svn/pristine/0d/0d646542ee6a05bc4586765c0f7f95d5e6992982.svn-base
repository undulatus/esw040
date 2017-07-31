package com.pointwest.workforce.planner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.domain.OpportunityCollaborator;
import com.pointwest.workforce.planner.service.AccessService;
import com.pointwest.workforce.planner.service.OpportunityCollaboratorService;
import com.pointwest.workforce.planner.service.OpportunityService;
import com.pointwest.workforce.planner.ui.adapter.CollaboratorsSharingAdapter;
import com.pointwest.workforce.planner.ui.domain.CollaboratorsSharing;

@RestController
public class OpportunityCollaboratorController {
	
	@Autowired
	OpportunityCollaboratorService opportunityCollaboratorService;
	
	@Autowired
	OpportunityService opportunityService;
	
	@Autowired
	CollaboratorsSharingAdapter collaboratorsSharingAdapter;
	
	@Autowired
	AccessService accessService;
	
	@Value("${collaborator.permission.edit}")
	private String EDIT;
	
	@Value("${collaborator.permission.view}")
	private String VIEW;

	@RequestMapping(method=RequestMethod.GET, value="/opportunities/{opportunityId}/opportunitycollaborators")
    public ResponseEntity<Object> fetchOpportunityCollaborators(@PathVariable Long opportunityId) {
		List<OpportunityCollaborator> opportunityCollaborators = opportunityCollaboratorService.fetchOpportunityCollaborators(opportunityId);
		if(opportunityCollaborators == null || opportunityCollaborators.isEmpty()) {
			Opportunity opportunity = opportunityService.fetchOpportunity(opportunityId);
			if (opportunity == null) {
				return new ResponseEntity<>(new CustomError("Opportunity not found"), HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(new CollaboratorsSharing(opportunityId), HttpStatus.OK);
			}
		} else {
			CollaboratorsSharing collaboratorsSharing = collaboratorsSharingAdapter.transform(opportunityCollaborators, opportunityId);
			return new ResponseEntity<>(collaboratorsSharing, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.POST, value="/opportunities/{opportunityId}/opportunitycollaborators/{permission}")
    public ResponseEntity<Object> saveOpportunityCollaborator(@RequestBody(required=true) List<String> usernames, @PathVariable Long opportunityId, @PathVariable String permission) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if(!accessService.hasPermissionToEdit(opportunityId, username)) {
			return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
		}
		
		permission = permission.toUpperCase().trim(); 
		if(permission.equals(EDIT) || permission.equals(VIEW) ) {
			Opportunity opportunity = opportunityService.fetchOpportunity(opportunityId);
			if (opportunity == null) {
				return new ResponseEntity<>(new CustomError("Opportunity not found"), HttpStatus.NOT_FOUND);
			} else {
				if(usernames.isEmpty()) {
					this.opportunityCollaboratorService.deleteByOpportunityIdAndPermission(opportunityId, permission);
					return new ResponseEntity<>(new CollaboratorsSharing(opportunityId), HttpStatus.OK);
				} else {
					this.opportunityCollaboratorService.deleteByOpportunityIdAndPermission(opportunityId, permission);
					List<OpportunityCollaborator> opportunityCollaborators = this.opportunityCollaboratorService.saveOpportunityCollaborator(usernames, opportunityId, permission);
					CollaboratorsSharing collaboratorsSharing = collaboratorsSharingAdapter.transform(opportunityCollaborators, opportunityId);
					return new ResponseEntity<>(collaboratorsSharing, HttpStatus.OK);
				}
			}
		} else {
			//value mismatch
			return new ResponseEntity<>(new CustomError("Invalid permission value for collaborators"), HttpStatus.BAD_REQUEST);
		}
    }
	
	@RequestMapping(method=RequestMethod.PUT, value="/opportunities/{opportunityId}/opportunityowner")
    public ResponseEntity<Object> changeOpportunityOwner(@RequestBody(required=true) String username, @PathVariable Long opportunityId) {
		
		//2nd level checker for ownership
		String user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if(!accessService.isOwner(opportunityId, user)) {
			return new ResponseEntity<>(new CustomError("Not owner of opportunity"), HttpStatus.FORBIDDEN);
		}
		Opportunity opp = new Opportunity();
		opp.setUsername(username);
		try {
			opp = opportunityService.updateOpportunity(opp, opportunityId);
			opportunityCollaboratorService.saveOpportunityEditor(user, opportunityId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new CustomError("error in changing owner"), HttpStatus.BAD_REQUEST);
		}
    }
	
	
}
