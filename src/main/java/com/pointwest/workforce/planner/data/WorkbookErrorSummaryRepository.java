package com.pointwest.workforce.planner.data;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.WorkbookErrorSummary;

public interface WorkbookErrorSummaryRepository extends CrudRepository<WorkbookErrorSummary, Long> {
	
	
}
