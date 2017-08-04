package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	@Query(value=
			" SELECT r.role_id, r.role_name" +
			" FROM ref_role r" +
			" LEFT JOIN tmpl_service_type_role slr ON r.role_id = slr.role_id" +
			" WHERE slr.service_type_id = ?1" +
			" ORDER BY r.role_name ASC"
			, nativeQuery=true)
	public List<Role> findRolesByServiceTypeIdOrderByRoleNameAsc(int serviceTypeId);
	
	public List<Role> findAllByOrderByRoleNameAsc();
}
