package com.pointwest.workforce.planner.controller;

import java.util.List;

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

import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;
import com.pointwest.workforce.planner.service.MigrationService;
import com.pointwest.workforce.planner.service.OpportunityService;
import com.pointwest.workforce.planner.service.UploadDataService;
import com.pointwest.workforce.planner.ui.adapter.WorkbookAdapter;

@RestController
public class MigrationController {
	
	@Value("${tnl.col.projectcode}")
	private String projectCodeCol;
	
	@Value("${tnl.col.groupproject}")
	private String opportunityNameCol;
	
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
	
	@Autowired
	UploadDataService uploadDataService;
	
	@Autowired
	WorkbookAdapter workbookAdapter;
	
	private static final Logger log = LoggerFactory.getLogger(MigrationController.class);
	
	@RequestMapping(method = RequestMethod.POST, value = "/migration")
	public ResponseEntity<Object> migrateData(@RequestBody(required = true) Long workbookDataSourceId) {
		
/*	
		//MOCK DATA
		Workbook workbook = new Workbook();
		List<OpportunityTnl> opList = new ArrayList<OpportunityTnl>();
		OpportunityTnl opportunityTnl = null;
		Map<String, Object> dataMap = null;
		//##
		opportunityTnl = new OpportunityTnl();
		dataMap = new HashMap<String, Object>();
		dataMap.put(projectCodeCol, "prj01");
		dataMap.put(opportunityNameCol, "oppy numba 1");
		dataMap.put(resourceRoleCol, "Team/Technical Lead");
		dataMap.put(resourcePracticeCol, "Java");
		dataMap.put(resourcePayLevelCol, "SE3");
		dataMap.put(resourceIsBillableCol, "y");
		dataMap.put(resourceStartDateCol, "09-21-2017");
		dataMap.put(resourceEndDateCol, "12-21-2018");
		dataMap.put(resourceFteBalanceCol, "1");
		dataMap.put(workbookDataSourceIdCol, "1");
		opportunityTnl.setDataMap(dataMap);
		opList.add(opportunityTnl);
		//##
		//##
		opportunityTnl = new OpportunityTnl();
		dataMap = new HashMap<String, Object>();
		dataMap.put(projectCodeCol, "prj01");
		dataMap.put(opportunityNameCol, "oppy numba 1");
		dataMap.put(resourceRoleCol, "QA Analyst");
		dataMap.put(resourcePracticeCol, "Java");
		dataMap.put(resourcePayLevelCol, "SE4");
		dataMap.put(resourceIsBillableCol, "y");
		dataMap.put(resourceStartDateCol, "10-21-2017");
		dataMap.put(resourceEndDateCol, "12-21-2018");
		dataMap.put(resourceFteBalanceCol, "3");
		dataMap.put(workbookDataSourceIdCol, "1");
		opportunityTnl.setDataMap(dataMap);
		opList.add(opportunityTnl);
		//##
		//##
		opportunityTnl = new OpportunityTnl();
		dataMap = new HashMap<String, Object>();
		dataMap.put(projectCodeCol, "prj02");
		dataMap.put(opportunityNameCol, "oppy numba 2");
		dataMap.put(resourceRoleCol, "Developer");
		dataMap.put(resourcePracticeCol, "Java");
		dataMap.put(resourcePayLevelCol, "SE3");
		dataMap.put(resourceIsBillableCol, "y");
		dataMap.put(resourceStartDateCol, "11-21-2017");
		dataMap.put(resourceEndDateCol, "12-21-2018");
		dataMap.put(resourceFteBalanceCol, "1");
		dataMap.put(workbookDataSourceIdCol, "1");
		opportunityTnl.setDataMap(dataMap);
		opList.add(opportunityTnl);
		//##
		
		//#
		workbookDataSourceId = Long.valueOf("1");
		//#
*/		
		List<OpportunityTnlRaw> opportunityTnlRaws = uploadDataService.fetchOpportunityTnlRaw(workbookDataSourceId);
		
		Workbook workbook = workbookAdapter.getWorkbook(opportunityTnlRaws);
		//delete opportunities uploaded from the same source
		List<Opportunity> opportunitiesToDelete = opportunityService.fetchOpportunitiesByWorkbookDataSourceId(workbookDataSourceId);
		for(Opportunity opportunity : opportunitiesToDelete) {
			opportunityService.deleteOpportunity(opportunity.getOpportunityId());
			log.debug("delete during upload purging opportunity " + opportunity.getOpportunityId());
		}
		//insert opportunity entries
		List<Opportunity> opportunities = migrationService.saveTnlOpportunities(workbook);
		
		if(opportunities == null) {
			return new ResponseEntity<>(new CustomError("migration failed"), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>(opportunities, HttpStatus.OK);
		}
	}
	
}
