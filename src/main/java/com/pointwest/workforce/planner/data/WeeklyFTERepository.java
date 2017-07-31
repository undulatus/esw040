package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pointwest.workforce.planner.domain.WeeklyFTE;
import com.pointwest.workforce.planner.domain.WeeklyFTEKey;

public interface WeeklyFTERepository extends CrudRepository<WeeklyFTE, WeeklyFTEKey> {
	
	public int countByKey(WeeklyFTEKey weeklyFTEKey);
	
	public List<WeeklyFTE> findWeeklyFTEsByKeyResourceSpecificationId(Long resourceSpecificationId);
	
	@Modifying
	@Transactional
	@Query(value= 
		" DELETE FROM resource_schedule" +
		" WHERE resource_specification_id IN" +
		" (SELECT rs.resource_specification_id" +
		" FROM resource_specification rs" +
		" LEFT JOIN opportunity_activity oa ON rs.opportunity_activity_id = oa.opportunity_activity_id" + 
		" WHERE oa.opportunity_id = ?1)"
		, nativeQuery=true)
	public void deleteFTEsByOpportunityId(Long opportunityId);
	
	@Query(value= 
		" SELECT COUNT(rsc.resource_schedule_FTE)" +
		" FROM resource_schedule rsc" +
		" WHERE rsc.resource_specification_id IN" +
		" (SELECT rs.resource_specification_id" +
		" FROM resource_specification rs" +
		" LEFT JOIN opportunity_activity oa ON rs.opportunity_activity_id = oa.opportunity_activity_id" + 
		" WHERE oa.opportunity_id = ?1)"
		, nativeQuery=true)
	public int countFTEsByOpportunityId(Long opportunityId);
}
