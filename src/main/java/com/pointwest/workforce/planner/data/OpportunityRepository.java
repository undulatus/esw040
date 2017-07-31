package com.pointwest.workforce.planner.data;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.ui.adapter.OpportunityDashboardProjection;

public interface OpportunityRepository extends CrudRepository<Opportunity, Long> {

	@Query(value= 
			" SELECT opportunity_id," +
			" opportunity_name," +
			" opportunity_project_alias," +
			" business_unit_id," +
			" service_type_id," +
			" opportunity_start_date," +
			" opportunity_duration_granularity," +
			" opportunity_duration_week," +
			" opportunity_status," +
			" opportunity_client_name," +
			" opportunity_creator_username," +
			" opportunity_document_status" +
			" FROM opportunity"
			, nativeQuery=true)
	public List<Opportunity> findOpportunityList();
	
	@Query(value= " UPDATE opportunity" +
		" SET" +
		" opportunity_document_status = ?2" +
		" WHERE opportunity_id = ?1"
		, nativeQuery=true)
	public void updateOpportunityDocumentStatus(long opportunityId, String documentStatus);
	
	@Query(value= 
	" SELECT opportunity_id," +
	" opportunity_name," +
	" opportunity_project_alias," +
	" business_unit_id," +
	" service_type_id," +
	" opportunity_start_date," +
	" opportunity_duration_granularity," +
	" opportunity_duration_week," +
	" opportunity_status," +
	" opportunity_client_name," +
	" o.username," +
	" opportunity_document_status" +
	" FROM opportunity o" +
	" LEFT JOIN user u ON o.username = u.username" +
	" WHERE o.username != ?1"
	, nativeQuery=true)
	public List<Opportunity> findNotOwnedOpportunitiesByUsername(String username);
	
	
	/*@Query(value= 
	" SELECT new com.pointwest.workforce.planner.ui.domain.OpportunityDashboard(" +
		"o.opportunity_id," +
	    " o.opportunity_name," +
	    //" o.business_unit_id," +
	    //" bu.business_unit_name," +
	    //" o.service_type_id," +
		" o.opportunity_start_date," +
		" o.opportunity_status," +
		" o.opportunity_document_status," +
		" o.opportunity_client_name)" +
		//" o.username" +
		//" o.opportunity_last_update," +
		//" oc.opportunity_collaborator_permission" +
	" FROM opportunity o" +
	" LEFT JOIN ref_business_unit bu ON o.business_unit_id = bu.business_unit_id" +
	" LEFT JOIN opportunity_collaborator oc ON o.opportunity_id = oc.opportunity_id" +
	" WHERE o.username != ?1" +
	" AND oc.username = ?1"
	//, nativeQuery=true)
	)
	public List<OpportunityDashboardProjector> findSharedOpportunitiesByUsername(String username);*/
	
	/**
	 * DEPRECATED - bmab
	*/
	@Query(value= 
	" SELECT " +
		" o.opportunity_id," +
	    " o.opportunity_name," +
	    " o.business_unit_id," +
	    " bu.business_unit_name," +
	    " o.service_type_id," +
		" o.opportunity_start_date," +
		" o.opportunity_status," +
		" o.opportunity_document_status," +
		" o.opportunity_client_name" +
		" o.username" +
		" o.opportunity_last_update," +
		" oc.opportunity_collaborator_permission" +
	" FROM opportunity o" +
	" LEFT JOIN ref_business_unit bu ON o.business_unit_id = bu.business_unit_id" +
	" LEFT JOIN opportunity_collaborator oc ON o.opportunity_id = oc.opportunity_id" +
	" WHERE o.username != ?1" +
	" AND oc.username = ?1"
	, nativeQuery=true)
	public List<OpportunityDashboardProjection> findSharedOpportunitiesByUsername(String username);
	
	public List<OpportunityDashboardProjection> findByUsernameAndDocumentStatusNotOrderByLastModifiedDateDesc(String username, String documentStatus);
	
	public List<OpportunityDashboardProjection> findByOpportunityCollaboratorsKeyUsernameAndDocumentStatusNotOrderByLastModifiedDateDesc(String username, String documentStatus);
	
	@Query(value= 
		" SELECT count(username)" +
		" FROM opportunity" +
		" WHERE opportunity_id = ?1" +
		" AND username = ?2" 
		, nativeQuery=true)
	public int countUsernameWithOpportunityId(long opportunityId, String username);
	
	
	@Query(value= 
		" SELECT rsp.resource_specification_id" +
		" FROM opportunity o " +
		" RIGHT JOIN opportunity_activity oa ON o.opportunity_id = oa.opportunity_id" +
		" RIGHT JOIN resource_specification rsp ON oa.opportunity_activity_id = rsp.opportunity_activity_id" +
		" WHERE o.opportunity_id =?1"
		, nativeQuery=true)
	public List<BigInteger> findResourceSpecificationIdsUnderOpportunityId(Long opportunityId);
	
}
