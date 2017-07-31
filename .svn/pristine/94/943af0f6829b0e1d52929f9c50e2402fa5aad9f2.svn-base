package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.ServiceType;

public interface ServiceTypeRepository extends CrudRepository<ServiceType, Integer> {
	
	public List<ServiceType> findAllByOrderByServiceTypeNameAsc();

}
