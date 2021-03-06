package com.pointwest.workforce.planner.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;

public interface OpportunityTnlRawRepository extends CrudRepository<OpportunityTnlRaw, Long> {
	
	@Transactional
	public List<OpportunityTnlRaw> deleteByWorkbookDatasourceId(Long workbookDatasourceId);
	
	public List<OpportunityTnlRaw> findByWorkbookDatasourceId(Long workbookDatasourceId);
	
}
