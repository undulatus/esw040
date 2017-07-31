package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.OpportunityStatus;

public interface OpportunityStatusRepository extends CrudRepository<OpportunityStatus, Integer> {
	public List<OpportunityStatus> findAllByOrderByOpportunityStatusNameAsc();
}