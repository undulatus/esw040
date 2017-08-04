package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;
import com.pointwest.workforce.planner.domain.WorkbookDataSource;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;

public interface UploadDataService {

	public List<OpportunityTnlRaw> fetchOpportunityTnlRaw(Long workbookDataSourceId);
	
	public WorkbookDataSource fetchGBookByUrl(String workbookUrl);
	
	public List<WorkbookDataSource> fetchAllWorkbookDataSources();

	public List<OpportunityTnlRaw> fetchAllOpportunityTnlRaw();

	public List<OpportunityTnlRaw> saveOpportunityTnlRaw(List<OpportunityTnlRaw> opportunityTnlRaws);

	public WorkbookDataSource saveGoogleWorkbook(String workbookUrl, String validationStatus);
	
	public void updateStartUploadValidationStatus(WorkbookDataSource workbookDataSource);

	public Workbook extractGoogleWorkbook(WorkbookDataSource workbookDataSource, Long workbookDataSourceId) throws Exception;
}
