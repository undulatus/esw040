package com.pointwest.workforce.planner.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.domain.OpportunityActivity;
import com.pointwest.workforce.planner.domain.ResourceSpecification;
import com.pointwest.workforce.planner.domain.Version;
import com.pointwest.workforce.planner.domain.WeeklyFTE;
import com.pointwest.workforce.planner.domain.WeeklyFTEKey;
import com.pointwest.workforce.planner.service.AccessService;
import com.pointwest.workforce.planner.service.OpportunityActivityService;
import com.pointwest.workforce.planner.service.OpportunityService;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.ResourceSpecificationService;
import com.pointwest.workforce.planner.service.TemplateDataService;
import com.pointwest.workforce.planner.service.VersionService;
import com.pointwest.workforce.planner.service.WeeklyFTEService;
import com.pointwest.workforce.planner.ui.adapter.VersionNoDataProjection;
import com.pointwest.workforce.planner.ui.domain.VersionSimplePojo;

@RestController
public class VersionController {
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@Autowired
	TemplateDataService templateDataService;

	@Autowired
	OpportunityService opportunityService;
	
	@Autowired
	OpportunityActivityService opportunityActivityService;
	
	@Autowired
	ResourceSpecificationService resourceSpecificationService;
	
	@Autowired
	WeeklyFTEService weeklyFTEService;
	
	@Autowired
	VersionService versionService;
	
	@Autowired
	AccessService accessService;
	
	@Autowired
	JsonParser parser;
	
	private static final Logger log = LoggerFactory.getLogger(VersionController.class);

	/**
	 * @param opportunityId
	 * @return list of versions ordered by creation date descending, data not included
	 */
	@RequestMapping(method=RequestMethod.GET, value="opportunities/{opportunityId}/versions")
	public ResponseEntity<Object> fetchOpportunityVersions(@PathVariable Long opportunityId) {
		
        List<VersionNoDataProjection> versions = versionService.fetchVersions(opportunityId, false);
       
		if( versions == null || versions.isEmpty()) {
			return new ResponseEntity<>(new CustomError("Versions not found for supplied opportunity id"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(versions, HttpStatus.OK);
		}
	}
	
	/**
	 * @param opportunityId
	 * @param versionName
	 * @return an opportunity version
	 */
	@RequestMapping(method=RequestMethod.GET, value="opportunities/{opportunityId}/versions/{versionName}")
    public ResponseEntity<Object> fetchVersion(@PathVariable Long opportunityId, @PathVariable String versionName) {
		ObjectMapper mapper = new ObjectMapper();
		Version version = null;
		Opportunity opportunity = null;
		Version.VersionKey key = new Version.VersionKey();
		key.setOpportunityId(opportunityId);
		key.setVersionName(versionName);
		try {
	        version = versionService.fetchOpportunityVersion(key);
	        if(version == null) {
				return new ResponseEntity<>(new CustomError("Version not found"), HttpStatus.NOT_FOUND);
			} else {			
				opportunity = mapper.readValue(version.getVersionData(), Opportunity.class);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new CustomError("Version corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new CustomError("Version corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new CustomError("Bad inputs"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new CustomError("Bad inputs"), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(opportunity, HttpStatus.OK);
    }
	
	
	/**
	 * @param versionName
	 * @param opportunityId
	 * @return saved opportunity version based on parameters
	 */
	@RequestMapping(method=RequestMethod.POST, value="/opportunities/{opportunityId}/versions")
    public ResponseEntity<Object> saveVersion(@RequestBody(required=true) VersionSimplePojo versionSimple, @PathVariable Long opportunityId) {

		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if(!accessService.hasPermissionToEdit(opportunityId, username)) {
			return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData;
		Opportunity opportunity = opportunityService.fetchOpportunity(opportunityId);		
		if(opportunity == null) {
			return new ResponseEntity<>(new CustomError("Opportunity not found"), HttpStatus.NOT_FOUND);
		} else {
			try {				
				//PREVENT SAVING TO EXISTING
				Version.VersionKey key = new Version.VersionKey();
				key.setOpportunityId(opportunityId);
				key.setVersionName(versionSimple.getVersionName());
				Version existingVersion = versionService.fetchOpportunityVersion(key);
				
			    if(existingVersion != null) {
			    	return new ResponseEntity<>(new CustomError("This version name already exists please use update"), HttpStatus.BAD_REQUEST);
			    }
			    
			    jsonData = mapper.writeValueAsString(opportunity);
			    Version version = new Version(opportunityId, versionSimple.getVersionName(), versionSimple.getVersionDescription(), jsonData, true, true, false);
			    version.setUsername(username);
				versionService.saveVersion(version);
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return new ResponseEntity<>(new CustomError("Error in saving version"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(opportunity, HttpStatus.OK);
		}
	}
	
	/**
	 * @param versionName
	 * @param opportunityId
	 * @return saved opportunity version based on parameters
	 * used for renaming and tagging as deleted version
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/opportunities/{opportunityId}/versions")
    public ResponseEntity<Object> updateVersion(@RequestBody(required=true) VersionSimplePojo versionSimple, @PathVariable Long opportunityId, 
    		 @RequestParam(required=false) Boolean isDeleted) {

		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if(!accessService.hasPermissionToEdit(opportunityId, username)) {
			return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
		}
		
		Opportunity opportunity = opportunityService.fetchOpportunity(opportunityId);		
		Version updatedVersion = null;
		
		if(opportunity == null) {
			return new ResponseEntity<>(new CustomError("Opportunity not found"), HttpStatus.NOT_FOUND);
		} else {
			Version version = new Version(opportunityId ,versionSimple.getVersionName());
			String versionNewName = versionSimple.getVersionNewName();
			
			if(isDeleted != null && isDeleted) {
				updatedVersion = versionService.tagVersionAsDeleted(version);
			} else {
				if(versionNewName != null) {
					updatedVersion = versionService.renameVersion(version, versionNewName);
					//return new ResponseEntity<>(version, HttpStatus.OK);
				} else {
					String jsonData;
					ObjectMapper mapper = new ObjectMapper();
					try {
						jsonData = mapper.writeValueAsString(opportunity);
						version.setVersionData(jsonData);
						updatedVersion = versionService.updateVersion(version);
					} catch (JsonProcessingException e) {
						log.debug("error parsing opportunity json");
						updatedVersion = null;
					}
					
				}
			}
		}
		if(updatedVersion == null) {
			return new ResponseEntity<>(new CustomError("Error in updating deleting version. Version might not exist"), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		return new ResponseEntity<>(updatedVersion, HttpStatus.OK);
	}
	
	
	/**
	 * @param opportunityId
	 * @param versionName
	 * @return an opportunity reverted to its selected version values
	 */
	@PreAuthorize("hasAnyRole('MANAGER', 'BUSINESS_LEAD')")
	@RequestMapping(method=RequestMethod.POST, value="opportunities/{opportunityId}/versions/{versionName}/revert")
    public ResponseEntity<Object> revertVersion(@PathVariable Long opportunityId, @PathVariable String versionName) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		if(!accessService.isOwner(opportunityId, username)) {
			return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		Version.VersionKey vKey = new Version.VersionKey();
		vKey.setOpportunityId(opportunityId);
		vKey.setVersionName(versionName);
        Version version = versionService.fetchOpportunityVersion(vKey);
        
        Opportunity currentOpportunity = null;
        Opportunity versionedOpportunity = null;
		try {
			versionedOpportunity = mapper.readValue(version.getVersionData(), Opportunity.class);
			currentOpportunity = opportunityService.fetchOpportunity(opportunityId);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new CustomError("Version corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new CustomError("Version corrupted"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new CustomError("Bad inputs"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new CustomError("Bad inputs"), HttpStatus.BAD_REQUEST);
		}
		Long opportunityActivityId;
		for (OpportunityActivity opportunityActivity : currentOpportunity.getOpportunityActivities()) {
			opportunityActivityId = opportunityActivity.getOpportunityActivityId();
			int result = opportunityActivityService.deleteOpportunityActivity(opportunityActivityId);
			log.debug("delete id " + opportunityActivityId + " result : " + result);
		}
		//bmab check for refactoring
		versionedOpportunity.setOpportunityCollaborators(currentOpportunity.getOpportunityCollaborators());
		OpportunityActivity transAct;
		ResourceSpecification transRes;
		List<ResourceSpecification> ress;
		List<WeeklyFTE> weeks;
		WeeklyFTEKey key = null;
		log.debug("act opp id " + versionedOpportunity.getOpportunityId());
		for(OpportunityActivity act : versionedOpportunity.getOpportunityActivities()) {
			act.setOpportunityId(versionedOpportunity.getOpportunityId());
			ress = act.getResourceSpecificationList();
			act.setResourceSpecificationList(null);
			transAct = opportunityActivityService.saveOpportunityActivity(act);
			log.debug("res oppact id " + transAct.getOpportunityActivityId());
			for(ResourceSpecification res : ress) {
				res.setResourceSpecificationId(null);
				res.setOpportunityActivityId(transAct.getOpportunityActivityId());
				weeks = res.getResourceSchedule();
				res.setResourceSchedule(null);
				transRes = resourceSpecificationService.saveResourceSpecification(res);
				log.debug("week resspec id " + transRes.getResourceSpecificationId());
				for(WeeklyFTE week : weeks) {
					key = week.getKey();
					key.setResourceSpecificationId(transRes.getResourceSpecificationId());
					week.setKey(key);
					weeklyFTEService.saveWeeklyFTE(week);
				}
			}
		}
		versionedOpportunity.setOpportunityActivities(null);
		versionedOpportunity = opportunityService.saveOpportunity(versionedOpportunity);
		
		if (versionedOpportunity == null) {
			currentOpportunity = opportunityService.saveOpportunity(currentOpportunity);
			return new ResponseEntity<>(new CustomError("Error version not applied"), HttpStatus.BAD_REQUEST);
		} else {
			//sprint 2 set as active version
			versionService.activateVersion(opportunityId, versionName);
			return new ResponseEntity<>(versionedOpportunity, HttpStatus.OK);
		}
	}
	
    
}
