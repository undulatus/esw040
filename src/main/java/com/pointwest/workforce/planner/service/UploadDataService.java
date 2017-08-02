package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;
import com.pointwest.workforce.planner.domain.WorkbookDataSource;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;

public interface UploadDataService {

	public List<OpportunityTnlRaw> fetchAllOpportunityTnlRaw();

	public List<OpportunityTnlRaw> saveOpportunityTnlRaw(List<OpportunityTnlRaw> opportunityTnlRaws);

	public WorkbookDataSource saveGoogleWorkbook(String workbookUrl);
	
	public Workbook extractGoogleWorkbook(WorkbookDataSource workbookDataSource);
}
