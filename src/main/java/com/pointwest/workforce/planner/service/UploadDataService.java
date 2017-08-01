package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.OpportunityTnl;

public interface UploadDataService {

	public List<OpportunityTnl> fetchAllOpporutnityTnl();

	public List<OpportunityTnl> saveOpportunityTnl(List<OpportunityTnl> opportunityTnl);
	
}
