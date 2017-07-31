package com.pointwest.workforce.planner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.data.OpportunityActivityRepository;
import com.pointwest.workforce.planner.data.OpportunityCollaboratorRepository;
import com.pointwest.workforce.planner.data.OpportunityRepository;
import com.pointwest.workforce.planner.data.ResourceSpecificationRepository;
import com.pointwest.workforce.planner.data.SystemRoleAccessRepository;
import com.pointwest.workforce.planner.service.AccessService;

@Service
public class AccessServiceImpl implements AccessService {

	@Autowired
	private SystemRoleAccessRepository systemRoleAccessRepository;
	
	@Autowired
	private OpportunityCollaboratorRepository opportunityCollaboratorRepository;
	
	@Autowired
	private OpportunityRepository opportunityRepository;
	
	@Autowired
	private OpportunityActivityRepository opportunityActivityRepository;
	
	@Autowired
	private ResourceSpecificationRepository resourceSpecificationRepository;
	
	@Value("${collaborator.permission.edit}")
	private String EDIT;
	
	@Override
	public boolean isSystemRoleAllowedAccess(String systemRole, String module, String action) {
		boolean allowed = systemRoleAccessRepository.countSystemRoleAllowedAccess(systemRole, module, action) > 0 ? true : false;
		return allowed;
	}

	@Override
	public boolean hasPermission(long opportunityId, String username, String permission) {
		boolean allowed = opportunityCollaboratorRepository.countUsernameWithPermission(opportunityId, username, permission) > 0 ? true : false;
		return allowed;
	}

	@Override
	public boolean isOwner(long opportunityId, String username) {
		return opportunityRepository.countUsernameWithOpportunityId(opportunityId, username) > 0 ? true : false;
	}
	
	@Override
	public boolean hasPermissionToEdit(long opportunityId, String username) {
		//first check if owner
		boolean allowed = this.isOwner(opportunityId, username);
		//if not user check if editor
		if(!allowed) {
			allowed = opportunityCollaboratorRepository.countUsernameWithPermission(opportunityId, username, EDIT) > 0 ? true : false;
		}
		/*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Set<String> authorities= AuthorityUtils.authorityListToSet(auth.getAuthorities());
		
		for (Iterator<String> it = authorities.iterator(); it.hasNext(); ) {
	        String role = it.next();
	        
	    }*/
		
		return allowed;
	}
	
	@Override
	public boolean hasPermissionToEditOaId(long opportunityActivityId, String username) {
		long opportunityId = opportunityActivityRepository.findOpportunityId(opportunityActivityId);
		//first check if owner
		boolean allowed = this.isOwner(opportunityId, username);
		//if not user check if editor
		if(!allowed) {
			allowed = opportunityCollaboratorRepository.countUsernameWithPermission(opportunityId, username, EDIT) > 0 ? true : false;
		}
		
		return allowed;
	}
	
	@Override
	public boolean hasPermissionToEditRsId(long resourceSpecificationId, String username) {
		long opportunityId = resourceSpecificationRepository.findOpportunityId(resourceSpecificationId);
		//first check if owner
		boolean allowed = this.isOwner(opportunityId, username);
		//if not user check if editor
		if(!allowed) {
			allowed = opportunityCollaboratorRepository.countUsernameWithPermission(opportunityId, username, EDIT) > 0 ? true : false;
		}
		
		return allowed;
	}

	
}
