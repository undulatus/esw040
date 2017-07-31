package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.Version;
import com.pointwest.workforce.planner.domain.Version.VersionKey;
import com.pointwest.workforce.planner.ui.adapter.VersionNoDataProjection;

public interface VersionService {

	public Version saveVersion(Version version);

	public List<VersionNoDataProjection> fetchVersions(Long opportunityId, Boolean isDeleted);
	
	public Version fetchOpportunityVersion(VersionKey key);

	public Version updateVersion(Long opportunityId, String versionName, String versionNewName, String versionDescription, String versionData, Boolean isDeleted);
	
	public Version updateVersion(Version version);
	
	public Version renameVersion(Version version, String versionNewName);
	
	public Version tagVersionAsDeleted(Version version);

	public void activateVersion(Long opportunityId, String versionName);
	
	/**
	 * This will discard any current data for the opportunity with the opportunityId
	 * and load the opportunity from the version (with versionId)
	 *//*	
	public Version fetchAndApplyOpportunityVersion(Long versionId);*/
	
}
