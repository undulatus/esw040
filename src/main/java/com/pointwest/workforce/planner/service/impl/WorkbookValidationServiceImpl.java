package com.pointwest.workforce.planner.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.constants.WorkbookExceptionConstants;
import com.pointwest.workforce.planner.controller.OpportunityController;
import com.pointwest.workforce.planner.domain.WorkbookErrorLog;
import com.pointwest.workforce.planner.domain.WorkbookErrorRecord;
import com.pointwest.workforce.planner.domain.WorkbookErrorSummary;
import com.pointwest.workforce.planner.nonentity.domain.OpportunityTnl;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;
import com.pointwest.workforce.planner.service.WorkbookValidationService;
import com.pointwest.workforce.planner.util.DateUtil;

@Service
public class WorkbookValidationServiceImpl implements WorkbookValidationService {

	private static final Logger log = LoggerFactory.getLogger(OpportunityController.class);

	public WorkbookErrorRecord checkWorkbookForExceptions(Workbook workbook) {

		WorkbookErrorRecord workbookErrorRecord = new WorkbookErrorRecord();
		WorkbookErrorSummary workbookErrorSummary = new WorkbookErrorSummary();
		List<WorkbookErrorLog> workbookErrorLogList = new ArrayList<WorkbookErrorLog>();
		
		boolean result = true;
		// CHECK IF REQUIRED COLUMN IS MISSING
		List<String> worksheetColumns = new ArrayList<String>();
		workbook.getOpportunityTnl().get(0).getDataMap()
				.forEach((columnHeader, v) -> worksheetColumns.add(columnHeader));
		List<String> missingColumns = WorkbookExceptionConstants.REQUIRED_COLUMN_LIST.stream()
				.filter(elem -> !worksheetColumns.contains(elem)).collect(Collectors.toList());
		int errorCount = 0;
		int rowsFailed = 0;
		int rowCount = 0;

		// IF REQUIRED COLUMNS ARE PRESENT
		if (missingColumns.size() == 0) {
			log.info("Form validated; Required columns are present");
			for (OpportunityTnl entry : workbook.getOpportunityTnl()) {
				for (Map.Entry<String, Object> columnKey : entry.getDataMap().entrySet()) {

					log.info("CHECKING: " + columnKey.getKey());

					// CHECK IF REQUIRED FIELD IS EMPTY
					if (WorkbookExceptionConstants.REQUIRED_COLUMN_LIST.contains(columnKey.getKey())
							&& (columnKey.getValue() == null || columnKey.getValue().equals(""))) {
						WorkbookErrorLog workbookErrorLogMissingReq = new WorkbookErrorLog();
						log.info(WorkbookExceptionConstants.ERROR_MESSAGE_REQUIRED_FIELD_EMPTY);
						log.info("REQUIRED FIELD WITH EMPTY VALUE: " + columnKey.getKey());
						log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
						workbookErrorLogMissingReq.setColumnHeader(columnKey.getKey());
						workbookErrorLogMissingReq.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_REQUIRED_FIELD_EMPTY);
						workbookErrorLogMissingReq.setRowNumber(entry.getOpportunityTnlRow().intValue());
						workbookErrorLogList.add(workbookErrorLogMissingReq);
						result = false;
						errorCount++;
					} else if (!WorkbookExceptionConstants.REQUIRED_COLUMN_LIST.contains(columnKey.getKey())
							&& (columnKey.getValue() == null || columnKey.getValue().equals(""))) {
						continue;
					} else {
						Map.Entry<Integer, List<WorkbookErrorLog>> returnKey = testValidations(columnKey, entry, errorCount).entrySet().iterator().next();
						errorCount = returnKey.getKey().intValue();
						
						if(returnKey.getValue().size() > 0){
							workbookErrorRecord.setWorkbookErrorLogList(returnKey.getValue());
							workbookErrorLogList.addAll(returnKey.getValue());
						}
						
					}
				}
				if(errorCount>0){
					rowsFailed++;
				}
				rowCount = entry.getOpportunityTnlRow().intValue();
			}
			log.info("ERROR COUNT: " + errorCount);
			log.info("ROW COUNT: " + rowCount);
			log.info("ROWS PASSED: " + (rowCount - rowsFailed));
			log.info("ROWS FAILED: " + rowsFailed);
			
			int rowsPassed = rowCount - rowsFailed;
			
			workbookErrorSummary.setErrorsLogged(errorCount);
			workbookErrorSummary.setRowsFailed(rowsFailed);
			workbookErrorSummary.setRowsPassed(rowsPassed);
			workbookErrorSummary.setErrorsLogged(errorCount);
			workbookErrorSummary.setRowsValidated(rowCount);
			
			workbookErrorRecord.setWorkbookErrorSummary(workbookErrorSummary);
			workbookErrorRecord.setWorkbookErrorLogList(workbookErrorLogList);
			
			if(errorCount>0){
				result = false;

			}
		// IF A REQUIRED COLUMN IS MISSING
		} else {
			log.info(WorkbookExceptionConstants.ERROR_MESSAGE_REQUIRED_COLUMN_MISSING);
			log.info("MISSING COLUMNS: " + missingColumns);
			result = false;
			String csvMissingColumns = String.join(",", missingColumns);
			workbookErrorRecord.setMissingColumns(csvMissingColumns);
		}
		
		workbookErrorRecord.setValidationPassed(result);
		log.info("Number of Errors" + errorCount);
		
		return workbookErrorRecord;
	}
	
	public static Map<Integer, List<WorkbookErrorLog>> testValidations(Entry<String, Object> columnKey, OpportunityTnl entry, int errorCount) {
		
		Map<Integer, List<WorkbookErrorLog>> validationResult = new LinkedHashMap<Integer, List<WorkbookErrorLog>>();
		List<WorkbookErrorLog> workbookErrorLog = new ArrayList<>();
		
		// CHARACTER LIMIT
		if ((columnKey.getValue().toString().length() > WorkbookExceptionConstants.CHARACTER_LIMIT) && 
				!WorkbookExceptionConstants.DISABLE_CHARACTER_COUNT.contains(columnKey.getKey())) {
			WorkbookErrorLog workbookErrorLogCharacterLimit= new WorkbookErrorLog();
			log.info(WorkbookExceptionConstants.ERROR_MESSAGE_CHARACTER_LIMIT_EXCEEDED);
			log.info("FIELD WITH EXCEEDING VALUE: " + columnKey.getKey());
			log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
			workbookErrorLogCharacterLimit.setColumnHeader(columnKey.getKey());
			workbookErrorLogCharacterLimit.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_CHARACTER_LIMIT_EXCEEDED);
			workbookErrorLogCharacterLimit.setRowNumber(entry.getOpportunityTnlRow().intValue());
			workbookErrorLog.add(workbookErrorLogCharacterLimit);
			errorCount++;
		}

		// INVALID DATA TYPE DOUBLE
		// IF VALUE IS NOT A NUMERIC
		if (WorkbookExceptionConstants.DOUBLE_DATA_TYPE_COLUMN_LIST.contains(columnKey.getKey()) && ((!NumberUtils
				.isNumber(columnKey.getValue().toString())
				|| (NumberUtils.isNumber(columnKey.getValue().toString()) && (columnKey.getValue().toString()
						.substring(columnKey.getValue().toString().length() - 1)
						.equalsIgnoreCase(WorkbookExceptionConstants.VALID_NUMBER_CHARACTER_CONSTRAINT_1)
						|| columnKey.getValue().toString().substring(columnKey.getValue().toString().length() - 1)
								.equalsIgnoreCase(WorkbookExceptionConstants.VALID_NUMBER_CHARACTER_CONSTRAINT_2)))))) {
			WorkbookErrorLog workbookErrorLogInvalidDataType= new WorkbookErrorLog();
			log.info(WorkbookExceptionConstants.ERROR_MESSAGE_INVALID_DATA_TYPE
					+ WorkbookExceptionConstants.ERROR_MESSAGE_INVALID_DATA_DOUBLE_EXPECTED);
			log.info("FIELD WITH INVALID DATA FORMAT: " + columnKey.getKey());
			log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
			workbookErrorLogInvalidDataType.setColumnHeader(columnKey.getKey());
			workbookErrorLogInvalidDataType.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_INVALID_DATA_DOUBLE_EXPECTED);
			workbookErrorLogInvalidDataType.setRowNumber(entry.getOpportunityTnlRow().intValue());
			workbookErrorLog.add(workbookErrorLogInvalidDataType);
			errorCount++;
			
		// IF VALUE IS A NUMERIC
		} else if (WorkbookExceptionConstants.DOUBLE_DATA_TYPE_COLUMN_LIST.contains(columnKey.getKey())
				&& NumberUtils.isNumber(columnKey.getValue().toString())) {

			// DECIMAL PRECISION
			String text = Double.toString(NumberUtils.toDouble(columnKey.getValue().toString()));
			int integerPlaces = text.indexOf('.');
			int decimalPlaces = text.length() - integerPlaces - 1;

			if (decimalPlaces > WorkbookExceptionConstants.MINIMUM_ACCEPTABLE_DECIMAL_PLACES) {
				WorkbookErrorLog workbookErrorLogInvalidNumericPrecision = new WorkbookErrorLog();
				log.info(WorkbookExceptionConstants.ERROR_MESSAGE_INVALID_NUMERIC_PRECISION);
				log.info("FIELD WITH INVALID PRECISION: " + columnKey.getKey());
				log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
				workbookErrorLogInvalidNumericPrecision.setColumnHeader(columnKey.getKey());
				workbookErrorLogInvalidNumericPrecision.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_INVALID_NUMERIC_PRECISION);
				workbookErrorLogInvalidNumericPrecision.setRowNumber(entry.getOpportunityTnlRow().intValue());
				workbookErrorLog.add(workbookErrorLogInvalidNumericPrecision);
				errorCount++;
			}

		}

		// INVALID DATA TYPE DATE
		if (WorkbookExceptionConstants.DATE_DATA_TYPE_COLUMN_LIST.contains(columnKey.getKey())
				&& !DateUtil.checkIfStringIsValidDate(columnKey.getValue().toString(), "MM-d-yyyy")
				&& (columnKey.getValue() != null)) {
			WorkbookErrorLog workbookErrorLogDateInvalid = new WorkbookErrorLog();
			log.info("DATE VALUE: " + columnKey.getValue().toString());
			log.info(WorkbookExceptionConstants.ERROR_MESSAGE_INVALID_DATA_TYPE
					+ WorkbookExceptionConstants.ERROR_MESSAGE_INVALID_DATA_DATE_EXPECTED);
			log.info("FIELD WITH INVALID DATE: " + columnKey.getKey());
			log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
			workbookErrorLogDateInvalid.setColumnHeader(columnKey.getKey());
			workbookErrorLogDateInvalid.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_INVALID_DATA_DATE_EXPECTED);
			workbookErrorLogDateInvalid.setRowNumber(entry.getOpportunityTnlRow().intValue());
			workbookErrorLog.add(workbookErrorLogDateInvalid);
			errorCount++;
		}

		// VALUE NOT IN PREDEFINEDLIST
		if (WorkbookExceptionConstants.PREDEFINED_COLUMN_LIST.contains(columnKey.getKey())) {
			WorkbookErrorLog workbookErrorLogPredefinedList = new WorkbookErrorLog();
			switch (columnKey.getKey()) {
			case WorkbookExceptionConstants.COLUMN_2:
				if (!WorkbookExceptionConstants.BUSINESS_UNIT_PREDEFINED_LIST.contains(columnKey.getValue())) {
					log.info(WorkbookExceptionConstants.ERROR_MESSAGE_VALUE_NOT_IN_PREDEFINED_LIST);
					log.info("FIELD WITH INVALID VALUE: " + columnKey.getKey());
					log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
					workbookErrorLogPredefinedList.setColumnHeader(columnKey.getKey());
					workbookErrorLogPredefinedList.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_VALUE_NOT_IN_PREDEFINED_LIST);
					workbookErrorLogPredefinedList.setRowNumber(entry.getOpportunityTnlRow().intValue());
					workbookErrorLog.add(workbookErrorLogPredefinedList);
					errorCount++;
				}
				break;
			case WorkbookExceptionConstants.COLUMN_4:
				if (!WorkbookExceptionConstants.PROJECT_ROLE_PREDEFINED_LIST.contains(columnKey.getValue())) {
					log.info(WorkbookExceptionConstants.ERROR_MESSAGE_VALUE_NOT_IN_PREDEFINED_LIST);
					log.info("FIELD WITH INVALID VALUE: " + columnKey.getKey());
					log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
					workbookErrorLogPredefinedList.setColumnHeader(columnKey.getKey());
					workbookErrorLogPredefinedList.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_VALUE_NOT_IN_PREDEFINED_LIST);
					workbookErrorLogPredefinedList.setRowNumber(entry.getOpportunityTnlRow().intValue());
					workbookErrorLog.add(workbookErrorLogPredefinedList);
					errorCount++;
				}
				break;
			case WorkbookExceptionConstants.COLUMN_10:
				if (!WorkbookExceptionConstants.PAY_LEVEL_PREDEFINED_LIST.contains(columnKey.getValue())) {
					log.info(WorkbookExceptionConstants.ERROR_MESSAGE_VALUE_NOT_IN_PREDEFINED_LIST);
					log.info("FIELD WITH INVALID VALUE: " + columnKey.getKey());
					log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
					workbookErrorLogPredefinedList.setColumnHeader(columnKey.getKey());
					workbookErrorLogPredefinedList.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_VALUE_NOT_IN_PREDEFINED_LIST);
					workbookErrorLogPredefinedList.setRowNumber(entry.getOpportunityTnlRow().intValue());
					workbookErrorLog.add(workbookErrorLogPredefinedList);
					errorCount++;
				}
				break;
			case WorkbookExceptionConstants.COLUMN_3:
				if (!WorkbookExceptionConstants.Y_OR_N_ONLY.contains(columnKey.getValue())) {
					log.info(WorkbookExceptionConstants.ERROR_MESSAGE_Y_OR_N_ONLY);
					log.info("FIELD WITH INVALID VALUE: " + columnKey.getKey());
					log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
					workbookErrorLogPredefinedList.setColumnHeader(columnKey.getKey());
					workbookErrorLogPredefinedList.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_Y_OR_N_ONLY);
					workbookErrorLogPredefinedList.setRowNumber(entry.getOpportunityTnlRow().intValue());
					workbookErrorLog.add(workbookErrorLogPredefinedList);
					errorCount++;
				}
				break;
			}
		}

			// MULTIPLE ENTRIES IN SINGLE ONLY ENTRY FIELD
			List<String> multipleEntryList = Arrays
					.asList(columnKey.getValue().toString().split("[,;][ ]*"));
			if ((!WorkbookExceptionConstants.MULTIPLE_ENTRY_COLUMN_LIST.contains(columnKey.getKey())
					&& multipleEntryList.size() > 1) && WorkbookExceptionConstants.REQUIRED_FOR_MIGRATION_COLUMN_LIST.contains(columnKey.getKey())) {
				WorkbookErrorLog workbookErrorLogMultipleEntry = new WorkbookErrorLog();
				log.info(WorkbookExceptionConstants.ERROR_MESSAGE_SINGLE_ENTRY_FIELD_ONLY);
				log.info("FIELD WITH INVALID VALUE: " + columnKey.getKey());
				log.info("ROW NUMBER: " + entry.getOpportunityTnlRow());
				workbookErrorLogMultipleEntry.setColumnHeader(columnKey.getKey());
				workbookErrorLogMultipleEntry.setErrorMessage(WorkbookExceptionConstants.ERROR_MESSAGE_SINGLE_ENTRY_FIELD_ONLY);
				workbookErrorLogMultipleEntry.setRowNumber(entry.getOpportunityTnlRow().intValue());
				workbookErrorLog.add(workbookErrorLogMultipleEntry);
				errorCount++;
			}
		
			validationResult.put(new Integer(errorCount), workbookErrorLog);
			
		return validationResult;
	}
}
