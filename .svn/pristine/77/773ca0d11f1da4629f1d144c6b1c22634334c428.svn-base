package com.pointwest.workforce.planner.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="opportunity")
@DynamicUpdate
public class OpportunityEntity {
	
	public OpportunityEntity() {
		super();
	}
	public OpportunityEntity(long opportunityId, String documentStatus) {
		this.opportunityId = opportunityId;
		this.documentStatus = documentStatus;
	}

	@Id
	@Column(name="opportunity_id")
	private long opportunityId;
	
	@Column(name="opportunity_document_status")
	private String documentStatus;

	public long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	

}
