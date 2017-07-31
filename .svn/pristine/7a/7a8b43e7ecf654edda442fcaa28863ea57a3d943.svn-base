package com.pointwest.workforce.planner.data;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.OpportunityActivity;

public interface OpportunityActivityRepository extends CrudRepository<OpportunityActivity, Long> {

	public int countByOpportunityActivityId(Long opportunityActivityId);
	
	@Query(value= 
		" SELECT o.opportunity_id" +
		" FROM opportunity_activity oa" +
		" LEFT JOIN opportunity o ON oa.opportunity_id = o.opportunity_id" +
		" WHERE oa.opportunity_activity_id =?1"
		, nativeQuery=true)
	public long findOpportunityId(Long opportunityActivityId);
	
	@Query(value= 
		" SELECT o.opportunity_start_date" +
		" FROM opportunity_activity oa" +
		" LEFT JOIN opportunity o ON oa.opportunity_id = o.opportunity_id" +
		" WHERE oa.opportunity_activity_id =?1"
		, nativeQuery=true)
	public LocalDate findOpportunityStartDate(Long opportunityActivityId);

	@Query(value= 
		" SELECT oa.opportunity_activity_id, oa.opportunity_id, oa.activity_id, oa.opportunity_activity_duration_week," +
		" oa.opportunity_activity_start_date, opportunity_activity_sequence_no" +
		" FROM resource_specification rs" +
		" LEFT JOIN opportunity_activity oa ON rs.opportunity_activity_id = oa.opportunity_activity_id " +
		" WHERE resource_specification_id = ?1"
		, nativeQuery=true)
	public OpportunityActivity findOpportunityActivityOfResourceSpecificationId(Long resourceSpecificationId);
	
	@Query(value= 
		" SELECT MIN(rsc.resource_schedule_week_number)" +
		" FROM resource_schedule rsc" +
		" WHERE rsc.resource_specification_id" +
		" IN" +
		" (SELECT rsp.resource_specification_id" +
		" FROM resource_specification rsp" +
		" WHERE opportunity_activity_id =?1)"
		, nativeQuery=true)
	public Integer findStartWeekOfOpportunityActivity(Long opportunityActivityId);
	
	@Query(value= 
		" SELECT MAX(rsc.resource_schedule_week_number)" +
		" FROM resource_schedule rsc" +
		" WHERE rsc.resource_specification_id" +
		" IN" +
		" (SELECT rsp.resource_specification_id" +
		" FROM resource_specification rsp" +
		" WHERE opportunity_activity_id =?1)"
		, nativeQuery=true)
	public Integer findEndWeekOfOpportunityActivity(Long opportunityActivityId);

}
