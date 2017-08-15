package com.pointwest.workforce.planner.controller;

import java.util.ArrayList;
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
import com.pointwest.workforce.planner.domain.WorkbookDataSource;
import com.pointwest.workforce.planner.domain.WorkbookErrorLog;
import com.pointwest.workforce.planner.domain.WorkbookErrorRecord;
import com.pointwest.workforce.planner.domain.WorkbookErrorSummary;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;
import com.pointwest.workforce.planner.service.MigrationService;
import com.pointwest.workforce.planner.service.OpportunityService;
import com.pointwest.workforce.planner.service.UploadDataService;
import com.pointwest.workforce.planner.service.WorkbookValidationErrorDataService;
import com.pointwest.workforce.planner.ui.adapter.WorkbookAdapter;

@RestController
public class MigrationController {
	
	@Value("${workbook.migration.status.not-migrated}")
	private String migrationStatusNonMigrated;
	
	@Value("${workbook.migration.status.migrated}")
	private String migrationStatusMigrated;
	
	@Value("${workbook.migration.status.cancelled}")
	private String migrationStatusCancelled;
	
	@Value("${workbook.migration.status.ongoing}")
	private String migrationStatusOngoing;
	
	@Value("${workbook.migration.status.failed}")
	private String migrationStatusFailed;
	
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
		WorkbookDataSource workbookDataSource = null;
		List<Opportunity> opportunities = null;
		try {
			workbookDataSource = uploadDataService.fetchWorkbookDataSource(workbookDataSourceId);
			
			uploadDataService.updateMigrationStatus(workbookDataSource, migrationStatusOngoing);
			String owner = workbookDataSource.getOwner();
			Integer businessUnitId = workbookDataSource.getBusinessUnit().getBusinessUnitId();
		
			
			List<OpportunityTnlRaw> opportunityTnlRaws = uploadDataService.fetchOpportunityTnlRaw(workbookDataSourceId);
			
			Workbook workbook = workbookAdapter.getWorkbook(opportunityTnlRaws);
			//delete opportunities uploaded from the same source
			List<Opportunity> opportunitiesToDelete = opportunityService.fetchOpportunitiesByWorkbookDataSourceId(workbookDataSourceId);
			for(Opportunity opportunity : opportunitiesToDelete) {
				opportunityService.deleteOpportunity(opportunity.getOpportunityId());
				log.debug("migration.. purging opportunity with id " + opportunity.getOpportunityId());
			}
			//insert opportunity entries
			if(workbook.getOpportunityTnl().size() < 1) {
				uploadDataService.updateMigrationStatus(workbookDataSource, migrationStatusFailed);
			} else {				
				opportunities = migrationService.saveTnlOpportunities(workbook, owner, businessUnitId);
			}
		} catch(Exception e) {
			log.error("migration failed");
			log.error(e.getMessage());
			e.printStackTrace();
			uploadDataService.updateMigrationStatus(workbookDataSource, migrationStatusFailed);
			return new ResponseEntity<>(new CustomError("migration failed"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(opportunities == null) {
			uploadDataService.updateMigrationStatus(workbookDataSource, migrationStatusFailed);
			return new ResponseEntity<>(new CustomError("migration failed"), HttpStatus.BAD_REQUEST);
		} else {
			uploadDataService.updateMigrationStatus(workbookDataSource, migrationStatusMigrated);
			return new ResponseEntity<>(opportunities, HttpStatus.OK);
		}
	}
	
	@Autowired
	public WorkbookValidationErrorDataService workbookValidationErrorDataService; 
	
	@RequestMapping(method = RequestMethod.POST, value = "/logerror")
	public ResponseEntity<Object> testErrorLog(@RequestBody(required = false) WorkbookErrorRecord workbookErrorR) {
		WorkbookErrorRecord workbookErrorRecord = new WorkbookErrorRecord();
		workbookErrorRecord.setMissingColumns("miss,one");
		
		WorkbookErrorLog elog = new WorkbookErrorLog("er1", "miss col 1", 3);
		WorkbookErrorSummary esum = new WorkbookErrorSummary(4, 3, 1);
		List<WorkbookErrorLog> elogList = new ArrayList<WorkbookErrorLog>();
		elogList.add(elog);
		elog = new WorkbookErrorLog("er2", "miss col 2", 4);
		elogList.add(elog);
		
		workbookErrorRecord.setWorkbookErrorLogList(elogList);
		workbookErrorRecord.setWorkbookErrorSummary(esum);
		try {
			log.debug("start saving error log");
			WorkbookErrorRecord wer = workbookValidationErrorDataService.saveWorkbookErrorRecord(workbookErrorRecord);
			return new ResponseEntity<Object>(wer, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/viewerror")
	public ResponseEntity<Object> testErrorLogView() {
		
		try {
			log.debug("start viewing error log");
			WorkbookErrorRecord wer = workbookValidationErrorDataService.fetchWorkbookErrorRecordByWorkbookDataSourceId(new Long(129));
			return new ResponseEntity<Object>(wer, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
