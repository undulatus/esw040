package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="workbook_error_summary")
public class WorkbookErrorSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WorkbookErrorSummary() {
		super();
	}

	public WorkbookErrorSummary(Integer rowsValidated, Integer rowsPassed, Integer rowsFailed) {
		super();
		this.rowsValidated = rowsValidated;
		this.rowsPassed = rowsPassed;
		this.rowsFailed = rowsFailed;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="workbook_error_summary_id")
	private Long workbookErrorSummaryId;
	
	@Column(name="workbook_error_summary_rows_validated")
	private Integer rowsValidated;
	
	@Column(name="workbook_error_summary_rows_passed")
	private Integer rowsPassed;
	
	@Column(name="workbook_error_summary_rows_failed")
	private Integer rowsFailed;
	
	@Column(name="workbook_error_summary_errors_logged")
	private Integer errorsLogged;
	
	private Long workbookErrorRecordId;

	public Long getWorkbookErrorSummaryId() {
		return workbookErrorSummaryId;
	}

	public void setWorkbookErrorSummaryId(Long workbookErrorSummaryId) {
		this.workbookErrorSummaryId = workbookErrorSummaryId;
	}

	public Integer getRowsValidated() {
		return rowsValidated;
	}

	public void setRowsValidated(Integer rowsValidated) {
		this.rowsValidated = rowsValidated;
	}

	public Integer getRowsPassed() {
		return rowsPassed;
	}

	public void setRowsPassed(Integer rowsPassed) {
		this.rowsPassed = rowsPassed;
	}

	public Integer getRowsFailed() {
		return rowsFailed;
	}

	public void setRowsFailed(Integer rowsFailed) {
		this.rowsFailed = rowsFailed;
	}

	public Integer getErrorsLogged() {
		return errorsLogged;
	}

	public void setErrorsLogged(Integer errorsLogged) {
		this.errorsLogged = errorsLogged;
	}

	public Long getWorkbookErrorRecordId() {
		return workbookErrorRecordId;
	}

	public void setWorkbookErrorRecordId(Long workbookErrorRecordId) {
		this.workbookErrorRecordId = workbookErrorRecordId;
	}

}
