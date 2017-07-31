package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pointwest.workforce.planner.domain.OpportunityCollaborator;
import com.pointwest.workforce.planner.domain.OpportunityCollaborator.OpportunityCollaboratorKey;

public interface OpportunityCollaboratorRepository extends CrudRepository<OpportunityCollaborator, OpportunityCollaboratorKey> {

	@Query(value= 
			//" SELECT system_role, system_role_access_module, system_role_access_action," +
			" SELECT count(username)" +
			" FROM opportunity_collaborator" +
			" WHERE opportunity_id = ?1" +
			" AND username = ?2" +
			" AND opportunity_collaborator_permission = ?3"
			, nativeQuery=true)
	public int countUsernameWithPermission(long opportunityId, String username, String permission);
	
	@Query(value= 
			//" SELECT system_role, system_role_access_module, system_role_access_action," +
			" SELECT count(username)" +
			" FROM opportunity_collaborator" +
			" WHERE opportunity_id = " +
				" (SELECT o.opportunity_id" +
				" FROM opportunity_activity oa" +
				" LEFT JOIN opportunity o ON oa.opportunity_id = o.opportunity_id" +
				" WHERE oa.opportunity_activity_id =?1)" +
			" AND username = ?2" +
			" AND opportunity_collaborator_permission = ?3"
			, nativeQuery=true)
	public int countUsernameWithPermissionOaId(long opportunityActivityId, String username, String permission);
	
	@Query(value= 
			//" SELECT system_role, system_role_access_module, system_role_access_action," +
			" SELECT count(username)" +
			" FROM opportunity_collaborator" +
			" WHERE opportunity_id = " +
				" (SELECT o.opportunity_id" +
				" FROM resource_specification rsp" +
				" LEFT JOIN opportunity_activity oa ON rsp.opportunity_activity_id = oa.opportunity_activity_id" +
				" LEFT JOIN opportunity o ON oa.opportunity_id = o.opportunity_id" +
				" WHERE resource_specification_id =?1)" +
			" AND username = ?2" +
			" AND opportunity_collaborator_permission = ?3"
			, nativeQuery=true)
	public int countUsernameWithPermissionRsId(long opportunityActivityId, String username, String permission);
	
	public int countByKeyOpportunityId(Long opportunityId);
	
	public List<OpportunityCollaborator> findOpportunityCollaboratorsByKeyOpportunityId(Long opportunityId);
	
	@Modifying
	@Transactional
	@Query(value= 
		" DELETE FROM opportunity_collaborator " +
		" WHERE opportunity_id = ?1 " +
		" AND opportunity_collaborator_permission = ?2"
		, nativeQuery=true)
	public void deleteByKeyOpportunityIdAndPermission(Long opportunityId, String permission);
	
}
