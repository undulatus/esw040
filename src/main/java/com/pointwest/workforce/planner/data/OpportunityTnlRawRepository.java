package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;

public interface OpportunityTnlRawRepository extends CrudRepository<OpportunityTnlRaw, Long> {
	
	public List<OpportunityTnlRaw> findByWorkbookDatasourceId(Long workbookDatasourceId);
	
}
