package com.pointwest.workforce.planner.data;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.ResourceSpecification;

public interface ResourceSpecificationRepository extends CrudRepository<ResourceSpecification, Long> {

	public int countByResourceSpecificationId(Long opportunityActivityId);
	
	@Query(value= 
		" SELECT o.opportunity_id" +
		" FROM resource_specification rsp" +
		" LEFT JOIN opportunity_activity oa ON rsp.opportunity_activity_id = oa.opportunity_activity_id" +
		" LEFT JOIN opportunity o ON oa.opportunity_id = o.opportunity_id" +
		" WHERE resource_specification_id =?1"
		, nativeQuery=true)
	public long findOpportunityId(Long resourceSpecificationId);
	
	@Query(value= 
		" SELECT o.opportunity_start_date" +
		" FROM resource_specification rsp" +
		" LEFT JOIN opportunity_activity oa ON rsp.opportunity_activity_id = oa.opportunity_activity_id" +
		" LEFT JOIN opportunity o ON oa.opportunity_id = o.opportunity_id" +
		" WHERE resource_specification_id =?1"
		, nativeQuery=true)
	public LocalDate findOpportunityStartDate(Long resourceSpecificationId);
	
	@Query(value= 
		" SELECT MIN(resource_schedule_week_number)" +
		" FROM resource_schedule" +
		" WHERE resource_specification_id =?1"
		, nativeQuery=true)
	public Integer findStartWeekOfResourceSpecification(Long resourceSpecificationId);
	
	@Query(value= 
		" SELECT MAX(resource_schedule_week_number)" +
		" FROM resource_schedule" +
		" WHERE resource_specification_id =?1"
		, nativeQuery=true)
	public Integer findEndWeekOfResourceSpecification(Long resourceSpecificationId);
	

}
