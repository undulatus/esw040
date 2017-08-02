package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;

public interface MigrationService {

	public List<Opportunity> saveTnlOpportunities(Workbook tnlWorkbook);
	
	public List<Opportunity> deleteTnlOpportunities(Workbook tnlWorkbook);
	
}
