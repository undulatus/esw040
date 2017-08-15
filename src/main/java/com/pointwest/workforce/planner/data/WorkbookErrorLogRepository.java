package com.pointwest.workforce.planner.data;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.WorkbookErrorLog;

public interface WorkbookErrorLogRepository extends CrudRepository<WorkbookErrorLog, Long> {
	
	
}
