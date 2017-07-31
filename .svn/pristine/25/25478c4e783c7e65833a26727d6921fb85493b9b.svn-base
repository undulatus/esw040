package com.pointwest.workforce.planner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.data.OpportunityCollaboratorRepository;
import com.pointwest.workforce.planner.domain.OpportunityCollaborator;
import com.pointwest.workforce.planner.service.OpportunityCollaboratorService;

@Service
public class OpportunityCollaboratorServiceImpl implements OpportunityCollaboratorService {
	
	@Autowired
	public OpportunityCollaboratorRepository opportunityCollaboratorRepository;
	
	private static final Logger log = LoggerFactory.getLogger(OpportunityCollaboratorServiceImpl.class);
	
	@Value("${collaborator.permission.edit}")
	private String EDIT;

	@Value("${collaborator.permission.view}")
	private String VIEW;
	
	@Override
	public List<OpportunityCollaborator> fetchOpportunityCollaborators(Long opportunityId) {
		List<OpportunityCollaborator> opportunityCollaborators = new ArrayList<OpportunityCollaborator>(); 
		opportunityCollaboratorRepository.findOpportunityCollaboratorsByKeyOpportunityId(opportunityId)
		.forEach(opportunityCollaborators::add);
		log.debug("opportunityId: " + opportunityId + " collaborators count = " + opportunityCollaborators.size());
		return opportunityCollaborators;
	}

	@Override
	public List<OpportunityCollaborator> saveOpportunityCollaborator(List<String> usernames, Long opportunityId,
			String permission) {
		List<OpportunityCollaborator> opportunityCollaborators = new ArrayList<OpportunityCollaborator>();
		for(String username : usernames) {
			opportunityCollaborators.add(new OpportunityCollaborator(username, opportunityId, permission));
		}
		return (List<OpportunityCollaborator>) opportunityCollaboratorRepository.save(opportunityCollaborators); 
	}
	
	@Override
	public OpportunityCollaborator saveOpportunityEditor(String username, Long opportunityId) {
		OpportunityCollaborator opportunityCollaborator = new OpportunityCollaborator(username, opportunityId, EDIT);
		if(opportunityCollaboratorRepository.countUsernameWithPermission(opportunityId, username, EDIT) == 0) {
			if(opportunityCollaboratorRepository.countUsernameWithPermission(opportunityId, username, VIEW) == 0) {				
				opportunityCollaborator = opportunityCollaboratorRepository.save(opportunityCollaborator);
				log.debug("collaborator had no existing permission but now saved as editor");
			} else {
				OpportunityCollaborator.OpportunityCollaboratorKey key = new OpportunityCollaborator.OpportunityCollaboratorKey();
				key.setUsername(username);
				key.setOpportunityId(opportunityId);
				opportunityCollaboratorRepository.delete(key);
				opportunityCollaborator = opportunityCollaboratorRepository.save(opportunityCollaborator);
				log.debug("collaborator changed from viewer to editor");
			}
		}
		return opportunityCollaborator; 
	}

	@Override
	public int deleteByOpportunityIdAndPermission(Long opportunityId, String permission) {
		int initialCount = opportunityCollaboratorRepository.countByKeyOpportunityId(opportunityId);
		log.debug("collaborator init count " + initialCount);
		opportunityCollaboratorRepository.deleteByKeyOpportunityIdAndPermission(opportunityId, permission);
		int postDeleteCount = opportunityCollaboratorRepository.countByKeyOpportunityId(opportunityId);
		log.debug("collaborator post delete count " + postDeleteCount);
		return initialCount - postDeleteCount;
	}

	
}
