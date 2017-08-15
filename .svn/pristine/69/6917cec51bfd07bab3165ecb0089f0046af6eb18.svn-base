package com.pointwest.workforce.planner.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.data.WorkbookErrorLogRepository;
import com.pointwest.workforce.planner.data.WorkbookErrorRecordRepository;
import com.pointwest.workforce.planner.data.WorkbookErrorSummaryRepository;
import com.pointwest.workforce.planner.domain.WorkbookErrorLog;
import com.pointwest.workforce.planner.domain.WorkbookErrorRecord;
import com.pointwest.workforce.planner.domain.WorkbookErrorSummary;
import com.pointwest.workforce.planner.service.WorkbookValidationErrorDataService;

@Service
public class WorkbookValidationErrorDataServiceImpl implements WorkbookValidationErrorDataService {

	@Autowired
	private WorkbookErrorLogRepository workbookErrorLogRepository;
	
	@Autowired
	private WorkbookErrorRecordRepository workbookErrorRecordRepository;
	
	@Autowired
	private WorkbookErrorSummaryRepository workbookErrorSummaryRepository;

	private static final Logger log = LoggerFactory.getLogger(WorkbookValidationErrorDataServiceImpl.class);
	
	@Override
	public WorkbookErrorRecord saveWorkbookErrorRecord(WorkbookErrorRecord workbookErrorRecord) {
		WorkbookErrorRecord savedWorkbookErrorRecord = null;
		List<WorkbookErrorLog> workbookErrorLogList = null;
		WorkbookErrorSummary workbookErrorSummary = null;
		workbookErrorLogList = workbookErrorRecord.getWorkbookErrorLogList();
		workbookErrorSummary = workbookErrorRecord.getWorkbookErrorSummary();
		workbookErrorRecord.setWorkbookErrorLogList(null);
		workbookErrorRecord.setWorkbookErrorSummary(null);
		Long workbookErrorRecordId = null;
		
		try {
			savedWorkbookErrorRecord = workbookErrorRecordRepository.save(workbookErrorRecord);
			workbookErrorRecordId = savedWorkbookErrorRecord.getWorkbookErrorRecordId();
			for(WorkbookErrorLog wel : workbookErrorLogList) {
				wel.setWorkbookErrorRecordId(workbookErrorRecordId);
				workbookErrorLogRepository.save(wel);
			}
			workbookErrorSummary.setWorkbookErrorRecordId(workbookErrorRecordId);
			workbookErrorSummaryRepository.save(workbookErrorSummary);
			savedWorkbookErrorRecord = workbookErrorRecordRepository.findOne(workbookErrorRecordId);

		} catch(Exception e) {
			log.error(e.getMessage());
			//rethrow 
		}
		return savedWorkbookErrorRecord;
	}

	@Override
	public WorkbookErrorRecord fetchWorkbookErrorRecord(Long workbookErrorRecordId) {
		WorkbookErrorRecord workbookErrorRecord = null;
		try {
			workbookErrorRecord = workbookErrorRecordRepository.findOne(workbookErrorRecordId);
		} catch(Exception e) {
			log.error(e.getMessage());
			//rethrow
		}
		return workbookErrorRecord;
	}
	
	@Override
	public WorkbookErrorRecord fetchWorkbookErrorRecordByWorkbookDataSourceId(Long workbookDataSourceId) {
		WorkbookErrorRecord workbookErrorRecord = null;
		try {
			workbookErrorRecord = workbookErrorRecordRepository.findByWorkbookDataSourceWorkbookDataSourceId(workbookDataSourceId);
		} catch(Exception e) {
			log.error(e.getMessage());
			//rethrow
		}
		return workbookErrorRecord;
	}
	
	
}
