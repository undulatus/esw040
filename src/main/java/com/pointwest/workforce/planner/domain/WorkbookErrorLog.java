package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="workbook_error_log")
public class WorkbookErrorLog implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkbookErrorLog() {
		super();
	}

	public WorkbookErrorLog(Long workbookErrorLogId, String errorIdentifier,
			String errorMessage, Integer rowNumber, String columnHeader,
			Long workbookErrorRecordId) {
		super();
		this.workbookErrorLogId = workbookErrorLogId;
		this.errorIdentifier = errorIdentifier;
		this.errorMessage = errorMessage;
		this.rowNumber = rowNumber;
		this.columnHeader = columnHeader;
		this.workbookErrorRecordId = workbookErrorRecordId;
	}
	
	public WorkbookErrorLog(String errorIdentifier,
			String errorMessage, Integer rowNumber) {
		super();
		
		this.errorIdentifier = errorIdentifier;
		this.errorMessage = errorMessage;
		this.rowNumber = rowNumber;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="workbook_error_log_id")
	private Long workbookErrorLogId;
	
	@Column(name="workbook_error_log_error_identifier")
	private String errorIdentifier;
	
	@Column(name="workbook_error_log_error_message")
	private String errorMessage;
	
	@Column(name="workbook_error_log_row_number")
	private Integer rowNumber;
	
	@Column(name="workbook_error_log_column_header")
	private String columnHeader;
	
	private Long workbookErrorRecordId;

	public Long getWorkbookErrorLogId() {
		return workbookErrorLogId;
	}

	public void setWorkbookErrorLogId(Long workbookErrorLogId) {
		this.workbookErrorLogId = workbookErrorLogId;
	}

	public String getErrorIdentifier() {
		return errorIdentifier;
	}

	public void setErrorIdentifier(String errorIdentifier) {
		this.errorIdentifier = errorIdentifier;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getColumnHeader() {
		return columnHeader;
	}

	public void setColumnHeader(String columnHeader) {
		this.columnHeader = columnHeader;
	}

	public Long getWorkbookErrorRecordId() {
		return workbookErrorRecordId;
	}

	public void setWorkbookErrorRecordId(Long workbookErrorRecordId) {
		this.workbookErrorRecordId = workbookErrorRecordId;
	}

}