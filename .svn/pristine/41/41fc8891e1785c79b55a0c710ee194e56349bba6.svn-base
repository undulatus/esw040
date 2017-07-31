package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.WeeklyFTE;
import com.pointwest.workforce.planner.domain.WeeklyFTEKey;

public interface WeeklyFTEService {

	public List<WeeklyFTE> fetchAllWeeklyFTEs();
	
	public List<WeeklyFTE> fetchWeeklyFTE(Long resourceSpecificationId);

	public WeeklyFTE fetchWeeklyFTE(WeeklyFTEKey key);

	public WeeklyFTE saveWeeklyFTE(WeeklyFTE weeklyFTE);
	
	public WeeklyFTE updateWeeklyFTE(WeeklyFTE weeklyFTE, WeeklyFTEKey weeklyFTEKey);
	
	public int deleteWeeklyFTE(WeeklyFTEKey weeklyFTEKey) throws Exception;
	
	public int deleteWeeklyFTEbyOpportunityId(Long opportunityId) throws Exception;

}
