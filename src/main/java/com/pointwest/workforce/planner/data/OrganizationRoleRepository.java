package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.OrganizationRole;

public interface OrganizationRoleRepository extends CrudRepository<OrganizationRole, Integer> {

	@Query(value=
			" SELECT o.org_role_id, o.org_role_name" +
			" FROM ref_org_role o" +
			" LEFT JOIN tmpl_service_type_org_role slor ON o.org_role_id = slor.org_role_id" +
			" WHERE slor.service_type_id = ?1" +
			" ORDER BY o.org_role_name ASC"
			, nativeQuery=true)
	public List<OrganizationRole> findOrgRolesByServiceTypeIdOrderByOrgRoleNameAsc(int serviceTypeId);
	
	public List<OrganizationRole> findAllByOrderByOrgRoleNameAsc();
}
