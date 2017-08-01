package com.pointwest.workforce.planner.data;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.OpportunityTnl;

public interface OpportunityTnlRepository extends CrudRepository<OpportunityTnl, Long> {
	
	//public List<OpportunityTnl> save(List<OpportunityTnl> in);

}
