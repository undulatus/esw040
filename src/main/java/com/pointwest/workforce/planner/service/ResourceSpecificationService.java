package com.pointwest.workforce.planner.service;

import java.util.List;

import com.pointwest.workforce.planner.domain.ResourceSpecification;

public interface ResourceSpecificationService {

	public List<ResourceSpecification> fetchAllResourceSpecifications();

	public ResourceSpecification fetchResourceSpecification(Long resourceSpecificationId);

	public ResourceSpecification saveResourceSpecification(ResourceSpecification resourceSpecification);
	
	public ResourceSpecification updateResourceSpecification(ResourceSpecification resourceSpecification, Long resourceSpecificationId);
	
	public ResourceSpecification updateResourceSpecificationDates(Long resourceSpecificationId);
	
	public int deleteResourceSpecification(Long resourceSpecificationId);
	
}
