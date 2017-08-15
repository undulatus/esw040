package com.pointwest.workforce.planner.data;

import org.springframework.data.repository.CrudRepository;

import com.pointwest.workforce.planner.domain.WorkbookErrorRecord;

public interface WorkbookErrorRecordRepository extends CrudRepository<WorkbookErrorRecord, Long> {
	
	public WorkbookErrorRecord findByWorkbookDataSourceWorkbookDataSourceId(Long workbookErrorRecord);
	
}
