package com.pointwest.workforce.planner.service;

public interface AccessService {

	public boolean isSystemRoleAllowedAccess(String systemRole, String module, String action);
	
	public boolean hasPermission(long opportunityId, String username, String permission);

	public boolean hasPermissionToEdit(long opportunityId, String username);

	public boolean hasPermissionToEditOaId(long opportunityActivityId, String username);

	public boolean hasPermissionToEditRsId(long resourceSpecificationId, String username);

	public boolean isOwner(long opportunityId, String username);
	
}
