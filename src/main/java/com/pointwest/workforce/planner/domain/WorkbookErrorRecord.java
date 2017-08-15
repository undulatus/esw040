package com.pointwest.workforce.planner.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="workbook_error_record")
public class WorkbookErrorRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkbookErrorRecord() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="workbook_error_record_id")
	private Long workbookErrorRecordId;
	
	@OneToMany(mappedBy = "workbookErrorRecordId")
	private List<WorkbookErrorLog> workbookErrorLogList;
	
	@OneToOne
	@JoinColumn(name="workbookDatasourceId")
	private WorkbookDataSource workbookDataSource;

	@OneToOne
	@JoinColumn(name="workbookErrorRecordId")
	private WorkbookErrorSummary workbookErrorSummary;
	
	@Column(name="workbook_error_record_missing_columns")
	private String missingColumns;
	
	@Column(name="workbook_error_record_is_missing_columns", columnDefinition="INT(1)")
	private boolean isMissingColumns;
	
	@Column(name="workbook_error_record_is_validation_passed", columnDefinition="INT(1)")
	private boolean isValidationPassed;

	public Long getWorkbookErrorRecordId() {
		return workbookErrorRecordId;
	}

	public void setWorkbookErrorRecordId(Long workbookErrorRecordId) {
		this.workbookErrorRecordId = workbookErrorRecordId;
	}

	public List<WorkbookErrorLog> getWorkbookErrorLogList() {
		return workbookErrorLogList;
	}

	public void setWorkbookErrorLogList(List<WorkbookErrorLog> workbookErrorLogList) {
		this.workbookErrorLogList = workbookErrorLogList;
	}

	public WorkbookDataSource getWorkbookDataSource() {
		return workbookDataSource;
	}

	public void setWorkbookDataSource(WorkbookDataSource workbookDataSource) {
		this.workbookDataSource = workbookDataSource;
	}

	public WorkbookErrorSummary getWorkbookErrorSummary() {
		return workbookErrorSummary;
	}

	public void setWorkbookErrorSummary(WorkbookErrorSummary workbookErrorSummary) {
		this.workbookErrorSummary = workbookErrorSummary;
	}

	public String getMissingColumns() {
		return missingColumns;
	}

	public void setMissingColumns(String missingColumns) {
		this.missingColumns = missingColumns;
	}

	public boolean isMissingColumns() {
		return isMissingColumns;
	}

	public void setMissingColumns(boolean isMissingColumns) {
		this.isMissingColumns = isMissingColumns;
	}

	public boolean isValidationPassed() {
		return isValidationPassed;
	}

	public void setValidationPassed(boolean isValidationPassed) {
		this.isValidationPassed = isValidationPassed;
	}


}
