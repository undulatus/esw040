package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ref_opportunity_status")
public class OpportunityStatus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OpportunityStatus() {
		super();
	}

	@Id
	@Column(name="opportunity_status_id")
	private int opportunityStatusId;
	
	@Column(name="opportunity_status_name")
	private String opportunityStatusName;

	@Column(name="opportunity_status_desc")
	private String opportunityStatusDesc;

	@Column(name="opportunity_status_state")
	private String opportunityStatusState;

	public int getOpportunityStatusId() {
		return opportunityStatusId;
	}

	public void setOpportunityStatusId(int opportunityStatusId) {
		this.opportunityStatusId = opportunityStatusId;
	}

	public String getOpportunityStatusName() {
		return opportunityStatusName;
	}

	public void setOpportunityStatusName(String opportunityStatusName) {
		this.opportunityStatusName = opportunityStatusName;
	}

	public String getOpportunityStatusDesc() {
		return opportunityStatusDesc;
	}

	public void setOpportunityStatusDesc(String opportunityStatusDesc) {
		this.opportunityStatusDesc = opportunityStatusDesc;
	}

	public String getOpportunityStatusState() {
		return opportunityStatusState;
	}

	public void setOpportunityStatusState(String opportunityStatusState) {
		this.opportunityStatusState = opportunityStatusState;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
