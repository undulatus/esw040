package com.pointwest.workforce.planner.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.Activity;
import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.OpportunityActivity;
import com.pointwest.workforce.planner.service.AccessService;
import com.pointwest.workforce.planner.service.OpportunityActivityService;
import com.pointwest.workforce.planner.service.OpportunityService;
import com.pointwest.workforce.planner.service.ReferenceDataService;

@RestController
public class OpportunityActivityController {
	
	@Autowired
	OpportunityActivityService opportunityActivityService;
	
	@Autowired
	OpportunityService opportunityService;
	
	@Autowired
	AccessService accessService;
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@RequestMapping(method=RequestMethod.GET, value="/opportunityactivities")
    public ResponseEntity<Object> fetchAllOpportunities() {
		List<OpportunityActivity> opportunityActivities = opportunityActivityService.fetchAllOpportunityActivities(); 
		if(opportunityActivities == null || opportunityActivities.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No Opportunity Activities retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(opportunityActivities, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/opportunityactivities/{opportunityActivityId}")
    public ResponseEntity<Object> fetchOpportunityActivity(@PathVariable Long opportunityActivityId) {
		OpportunityActivity opportunityActivity = opportunityActivityService.fetchOpportunityActivity(opportunityActivityId);
		if(opportunityActivity == null) {
			return new ResponseEntity<>(new CustomError("Opportunity Activity not found"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(opportunityActivity, HttpStatus.OK);
		}
    }
	

	@RequestMapping(method=RequestMethod.POST, value="/opportunityactivities")
    public ResponseEntity<Object> saveOpportunityActivity(@RequestBody(required=false) OpportunityActivity opportunityActivity) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		try {
			if(!accessService.hasPermissionToEdit(opportunityActivity.getOpportunityId(), username)) {
				return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid inputs"), HttpStatus.BAD_REQUEST);
		}
		
		OpportunityActivity savedOpportunityActivity = null;
		boolean isNew = false;
		/*if(opportunityActivity==null) {
			savedOpportunityActivity = opportunityActivityService.saveOpportunityActivity(new OpportunityActivity());
			isNew = true;
		} else*/ if(opportunityActivity.getOpportunityActivityId() == null) {
			savedOpportunityActivity = opportunityActivityService.saveOpportunityActivity(opportunityActivity);
			isNew = true;
		} else {
			savedOpportunityActivity = opportunityActivityService.updateOpportunityActivity(opportunityActivity, opportunityActivity.getOpportunityActivityId());
			isNew = false;
		}
		if(savedOpportunityActivity==null) {
			return new ResponseEntity<>(new CustomError("Incorrect inputs, not saved"), HttpStatus.BAD_REQUEST);
		} else {
			if(isNew) {
				int id = savedOpportunityActivity.getActivity().getActivityId();
				Activity newActivity = new Activity();
				newActivity = referenceDataService.fetchActivity(id);
				savedOpportunityActivity.setActivity(newActivity);

				return new ResponseEntity<>(savedOpportunityActivity, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(savedOpportunityActivity, HttpStatus.OK);
			}
		}
    }
	
	@RequestMapping(method=RequestMethod.PUT, value="/opportunityactivities/{opportunityActivityId}")
    public ResponseEntity<Object> updateOpportunityActivity(@RequestBody(required=true) OpportunityActivity opportunityActivity, @PathVariable Long opportunityActivityId) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		try {
			if(!accessService.hasPermissionToEditOaId(opportunityActivityId, username)) {
				return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid inputs"), HttpStatus.BAD_REQUEST);
		}
		
		OpportunityActivity savedOpportunityActivity = null;
		Long idInRequestBody = opportunityActivity.getOpportunityActivityId();
		if ( (idInRequestBody != null) && ((idInRequestBody.compareTo(opportunityActivityId)) != 0) ) {
			//unmatched object and url id's
			return new ResponseEntity<>(new CustomError("Unmatched ID's in RequestBody and URL"), HttpStatus.BAD_REQUEST);
		}
		savedOpportunityActivity = opportunityActivityService.updateOpportunityActivity(opportunityActivity, opportunityActivityId);
		if(savedOpportunityActivity==null) {
			return new ResponseEntity<>(opportunityActivity, HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(savedOpportunityActivity, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.PUT, value="/opportunityactivities")
    public ResponseEntity<Object> updateSequenceOpportunityActivity(@RequestBody(required=true) List<OpportunityActivity> opportunityActivities) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		try {
			if(!accessService.hasPermissionToEditOaId(opportunityActivities.get(0).getOpportunityActivityId(), username)) {
				return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid inputs"), HttpStatus.BAD_REQUEST);
		}
		
		return this.updateOpportunityActivities(opportunityActivities);
    }
	
	@RequestMapping(method=RequestMethod.DELETE, value="/opportunityactivities/{opportunityActivityId}")
    public ResponseEntity<Object> deleteOpportunityActivity(@RequestBody(required=false) List<OpportunityActivity> opportunityActivities,
    		@PathVariable Long opportunityActivityId) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		try {
			if(!accessService.hasPermissionToEditOaId(opportunityActivityId, username)) {
				return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid inputs"), HttpStatus.BAD_REQUEST);
		}
		
		int deleteCount = opportunityActivityService.deleteOpportunityActivity(opportunityActivityId);
		if(deleteCount > 0) {
			if(opportunityActivities != null) {
				return this.updateOpportunityActivities(opportunityActivities);
			} else {
				return new ResponseEntity<>(deleteCount, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(deleteCount, HttpStatus.BAD_REQUEST);
		}
    }
	
	private ResponseEntity<Object> updateOpportunityActivities(List<OpportunityActivity> opportunityActivities) {
		OpportunityActivity savedOpportunityActivity = null;
		for(OpportunityActivity opportunityActivity : opportunityActivities) {			
			savedOpportunityActivity = opportunityActivityService.updateOpportunityActivity(opportunityActivity, opportunityActivity.getOpportunityActivityId());
			if(savedOpportunityActivity==null) {
				break;
			}
		}
		
		if(savedOpportunityActivity==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<OpportunityActivity> savedOpportunityActivities = opportunityService.fetchOpportunity(savedOpportunityActivity.getOpportunityId()).getOpportunityActivities();
			savedOpportunityActivities.sort(Comparator.comparingInt(OpportunityActivity::getSequenceNo));
			return new ResponseEntity<>(savedOpportunityActivities, HttpStatus.OK);
		}
	}
	
	
}
