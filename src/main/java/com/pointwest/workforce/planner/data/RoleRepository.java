package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	@Query(value=
			" SELECT r.org_role_id, r.org_role_name" +
			" FROM ref_org_role r" +
			" LEFT JOIN tmpl_service_type_org_role slr ON r.org_role_id = slr.org_role_id" +
			" WHERE slr.service_type_id = ?1" +
			" ORDER BY r.org_role_name ASC"
			, nativeQuery=true)
	public List<Role> findRolesByServiceTypeIdOrderByOrgRoleNameAsc(int serviceTypeId);
	
	public List<Role> findAllByOrderByRoleNameAsc();
}
