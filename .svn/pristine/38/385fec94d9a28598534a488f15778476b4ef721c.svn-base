package com.pointwest.workforce.planner.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.SystemRoleAccess;

public interface SystemRoleAccessRepository extends CrudRepository<SystemRoleAccess, SystemRoleAccess> {

	@Query(value= 
			//" SELECT system_role, system_role_access_module, system_role_access_action," +
			" SELECT COUNT(system_role)" +
			" FROM sys_system_role_access" +
			" WHERE system_role =?1" +
			" AND system_role_access_module =?2" +
			" AND system_role_access_action =?3"
			, nativeQuery=true)
	public int countSystemRoleAllowedAccess(String systemRole, String module, String action);
	
}
