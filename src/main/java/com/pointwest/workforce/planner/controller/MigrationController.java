package com.pointwest.workforce.planner.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.nonentity.domain.OpportunityTnl;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;
import com.pointwest.workforce.planner.service.MigrationService;
import com.pointwest.workforce.planner.service.OpportunityService;

@RestController
public class MigrationController {
	
	@Value("${tnl.col.projectcode}")
	private String projectCodeCol;
	
	@Value("${tnl.col.resource.role}")
	private String resourceRoleCol;
	
	@Value("${tnl.col.resource.practice}")
	private String resourcePracticeCol;
	
	@Value("${tnl.col.resource.paylevel}")
	private String resourcePayLevelCol;

	@Value("${tnl.col.resource.billable}")
	private String resourceIsBillableCol;
//from
	@Value("${tnl.col.resource.startdate}")
	private String resourceStartDateCol;

	@Value("${tnl.col.resource.enddate}")
	private String resourceEndDateCol;
	
	@Value("${tnl.col.resource.fte.balance}")
	private String resourceFteBalanceCol;
//end from
	@Value("${tnl.col.workbookdatasource.id}")
	private String workbookDataSourceIdCol;
	
	@Autowired
	MigrationService migrationService;
	
	@Autowired
	OpportunityService opportunityService;
	
	private static final Logger log = LoggerFactory.getLogger(MigrationController.class);
	
	/*@RequestMapping(method=RequestMethod.GET, value="/workbook/raw/tnl")
    public ResponseEntity<Object> fetchAllOpportunityTnlRaw() {
       List<OpportunityTnlRaw> opportunityTnlRaws = uploadDataService.fetchAllOpportunityTnlRaw();
       if(opportunityTnlRaws == null) {
    	   return new ResponseEntity<>(new CustomError("No list retrieved"), HttpStatus.NOT_FOUND);
       } else {
    	   return new ResponseEntity<>(opportunityTnlRaws, HttpStatus.OK);
       }
    }*/
	
	@RequestMapping(method = RequestMethod.POST, value = "/migration")
	public ResponseEntity<Object> migrateData(@RequestBody(required = false) Long workbookDataSourceId) {
		//@PathVariable int workbookDataSourceId
		//use identifier here
		//migrationService.deleteTnlOpportunities(identifier);
		
		Workbook workbook = new Workbook();
		List<OpportunityTnl> opList = new ArrayList<OpportunityTnl>();
		OpportunityTnl opportunityTnl = null;
		Map<String, Object> dataMap = null;
		//##
		opportunityTnl = new OpportunityTnl();
		dataMap = new HashMap<String, Object>();
		dataMap.put(projectCodeCol, "prj01");
		dataMap.put(resourceRoleCol, "Team/Technical Lead");
		dataMap.put(resourcePracticeCol, "Java");
		dataMap.put(resourcePayLevelCol, "SE 3");
		dataMap.put(resourceIsBillableCol, "y");
		dataMap.put(resourceStartDateCol, "08-21-2017");
		dataMap.put(resourceEndDateCol, "10-21-2018");
		dataMap.put(resourceFteBalanceCol, "1");
		dataMap.put(workbookDataSourceIdCol, "1");
		opportunityTnl.setDataMap(dataMap);
		opList.add(opportunityTnl);
		//##
		//##
		opportunityTnl = new OpportunityTnl();
		dataMap = new HashMap<String, Object>();
		dataMap.put(projectCodeCol, "prj01");
		dataMap.put(resourceRoleCol, "QA Analyst");
		dataMap.put(resourcePracticeCol, "Java");
		dataMap.put(resourcePayLevelCol, "SE 3");
		dataMap.put(resourceIsBillableCol, "y");
		dataMap.put(resourceStartDateCol, "08-21-2017");
		dataMap.put(resourceEndDateCol, "11-21-2018");
		dataMap.put(resourceFteBalanceCol, "3");
		dataMap.put(workbookDataSourceIdCol, "1");
		opportunityTnl.setDataMap(dataMap);
		opList.add(opportunityTnl);
		//##
		//##
		opportunityTnl = new OpportunityTnl();
		dataMap = new HashMap<String, Object>();
		dataMap.put(projectCodeCol, "prj02");
		dataMap.put(resourceRoleCol, "Developer");
		dataMap.put(resourcePracticeCol, "Java");
		dataMap.put(resourcePayLevelCol, "SE 3");
		dataMap.put(resourceIsBillableCol, "y");
		dataMap.put(resourceStartDateCol, "08-21-2017");
		dataMap.put(resourceEndDateCol, "12-21-2018");
		dataMap.put(resourceFteBalanceCol, "1");
		dataMap.put(workbookDataSourceIdCol, "1");
		opportunityTnl.setDataMap(dataMap);
		opList.add(opportunityTnl);
		//##
		
		//#
		workbookDataSourceId = Long.valueOf("1");
		//#
		
		workbook.setOpportunityTnl(opList);
		
		//delete opportunities uploaded from the same source
		List<Opportunity> opportunitiesToDelete = opportunityService.fetchOpportunitiesByWorkbookDataSourceId(workbookDataSourceId);
		for(Opportunity opportunity : opportunitiesToDelete) {
			opportunityService.deleteOpportunity(opportunity.getOpportunityId());
			log.debug("delete during upload purging opportunity " + opportunity.getOpportunityId());
		}
		//insert opportunity entries
		List<Opportunity> opportunities = migrationService.saveTnlOpportunities(workbook);
		
		return new ResponseEntity<>(opportunities, HttpStatus.OK);
	}
	
}
