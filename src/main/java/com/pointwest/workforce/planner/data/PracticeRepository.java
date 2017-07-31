package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.Practice;

public interface PracticeRepository extends CrudRepository<Practice, Integer> {
	
	@Query(value=
			" SELECT p.practice_id, p.practice_name" +
			" FROM ref_practice p" +
			" LEFT JOIN tmpl_role_practice rp ON p.practice_id = rp.practice_id" +
			" WHERE rp.role_id = ?1" +
			" ORDER BY p.practice_name ASC"
			, nativeQuery=true)
	public List<Practice> findPracticesByRoleIdOrderByPracticeNameAsc(int roleId);
	
	public List<Practice> findAllByOrderByPracticeNameAsc();

}
