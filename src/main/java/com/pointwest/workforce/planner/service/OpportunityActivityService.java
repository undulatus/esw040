package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.Activity;
import com.pointwest.workforce.planner.domain.OpportunityActivity;

public interface OpportunityActivityService {

	public List<OpportunityActivity> fetchAllOpportunityActivities();

	public OpportunityActivity fetchOpportunityActivity(Long opportunityActivityId);

	public OpportunityActivity saveOpportunityActivity(OpportunityActivity opportunityActivity);
	
	public OpportunityActivity updateOpportunityActivity(OpportunityActivity opportunityActivity, Long opportunityActivityId);
	
	public OpportunityActivity updateOpportunityActivityDates(Long resourceSpecificationId);

	public int deleteOpportunityActivity(Long opportunityActivityId);
	
	public List<OpportunityActivity> saveOpportunityActivity(List<Activity> activities, Long opportunityId);
	
}
