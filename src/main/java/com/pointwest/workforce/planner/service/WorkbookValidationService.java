package com.pointwest.workforce.planner.service;

import com.pointwest.workforce.planner.domain.WorkbookErrorRecord;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;

public interface WorkbookValidationService {

	public WorkbookErrorRecord checkWorkbookForExceptions(Workbook workbook);
}
