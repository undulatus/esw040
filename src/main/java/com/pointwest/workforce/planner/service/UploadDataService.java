package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;

public interface UploadDataService {

	public List<OpportunityTnlRaw> fetchAllOpporutnityTnlRaw();

	public List<OpportunityTnlRaw> saveOpportunityTnlRaw(List<OpportunityTnlRaw> opportunityTnlRaws);
	
}
