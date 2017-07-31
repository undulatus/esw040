package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.BusinessUnit;

public interface BusinessUnitRepository extends CrudRepository<BusinessUnit, Integer> {
	public List<BusinessUnit> findAllByOrderByBusinessUnitNameAsc();
}