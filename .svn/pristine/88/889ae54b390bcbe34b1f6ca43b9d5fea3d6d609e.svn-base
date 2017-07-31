package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pointwest.workforce.planner.domain.Version;
import com.pointwest.workforce.planner.domain.Version.VersionKey;
import com.pointwest.workforce.planner.ui.adapter.VersionNoDataProjection;

public interface VersionRepository extends CrudRepository<Version, VersionKey> {

	public List<VersionNoDataProjection> findByKeyOpportunityIdAndIsDeletedOrderByDateCreatedDesc(Long opportunityId, Boolean isDeleted);
	
	@Transactional
	@Modifying
	@Query(value= 
			" UPDATE version" +
			" SET" +
			" 	version_is_active = 0" +
			" WHERE opportunity_id = ?1"
			, nativeQuery=true)
	public void noActiveVersion(Long opportunityId);
	
	@Transactional
	@Modifying
	@Query(value= 
			" UPDATE version" +
			" SET" +
			" 	version_is_active = 1" +
			" WHERE version_name = ?2 AND opportunity_id = ?1"
			, nativeQuery=true)
	public void activateVersion(Long opportunityId, String versionName);
	
	@Transactional
	@Modifying
	@Query(value= 
			" UPDATE version" +
			" SET" +
			" 	version_name = ?3" +
			" WHERE version_name = ?2 AND opportunity_id = ?1"
			, nativeQuery=true)
	public void renameVersion(Long opportunityId, String versionName, String versionNewName);

}
