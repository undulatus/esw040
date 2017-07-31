package com.pointwest.workforce.planner.service;

import java.sql.Date;
import java.util.List;

import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.ui.adapter.OpportunityDashboardProjection;

public interface OpportunityService {

	public List<Opportunity> fetchAllOpportunities();

	public Opportunity fetchOpportunity(long opportunityId);

	public Opportunity saveOpportunity(Opportunity opportunity);

	public Opportunity updateOpportunity(Opportunity opportunity, Long opportunityId) throws Exception;

	public List<Opportunity> fetchOpportunityList();
	
	public int lockOpportunity(long opportunityId, boolean lock);
	
	public List<Opportunity> fetchNotOwnedOpportunitiesByUsername(String username);
	
	public List<OpportunityDashboardProjection> fetchOpportunitiesByUsernameAndStatusNot(String username, String documentStatus);

	public List<OpportunityDashboardProjection> fetchSharedOpportunitiesByUsernameAndStatusNot(String username, String documentStatus);
	
	public Date deriveOpportunityEndDate(Opportunity opportunity);

	public boolean isUsernameOwner(long opportunityId, String username);

	public int deleteOpportunity(long opportunityId);
	
	public int publishOpportunity(long opportunityId);
	
}
