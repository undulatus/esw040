package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.OpportunityCollaborator;

public interface OpportunityCollaboratorService {

	public List<OpportunityCollaborator> fetchOpportunityCollaborators(Long opportunityId);
	
	public List<OpportunityCollaborator> saveOpportunityCollaborator(List<String> usernames, Long opportunityId, String permission);
	
	public int deleteByOpportunityIdAndPermission(Long opportunityId, String permission);

	public OpportunityCollaborator saveOpportunityEditor(String username, Long opportunityId);

}
