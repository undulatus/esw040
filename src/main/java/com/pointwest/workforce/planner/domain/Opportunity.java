package com.pointwest.workforce.planner.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="opportunity")
@DynamicUpdate
public class Opportunity extends Auditable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Opportunity() {
		super();
	}
	
	public Opportunity(Long opportunityId) {
		super();
		this.opportunityId = opportunityId;
	}

	@Id
	@Column(name="opportunity_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long opportunityId;
	
	@Column(name="opportunity_name")
	private String opportunityName;
	
	@OneToOne
	@JoinColumn(name="business_unit_id")
	private BusinessUnit businessUnit;
	
	@OneToOne
	@JoinColumn(name="service_type_id")
	private ServiceType serviceType;
	
	@Column(name="opportunity_duration_granularity")
	private String durationGranularity;
	
	@Column(name="opportunity_duration_week")
	private Double durationInWeeks;
	
	@Column(name="opportunity_start_date")
	private Date projectStartDate;
	
	/*@Column(name="opportunity_status")
	private String opportunityStatus;*/
	@OneToOne
	@JoinColumn(name="opportunity_status_id")
	private OpportunityStatus opportunityStatus;
	
	@Column(name="opportunity_document_status")
	private String documentStatus;
	
	@Column(name="opportunity_client_name")
	private String clientName;
	
	@Column(name="opportunity_project_alias")
	private String projectAlias;
	
	@Column(name="opportunity_end_date")
	private Date projectEndDate;
	
	@JoinColumn(name="username")
	private String username;
	
	
	//@OneToMany(mappedBy = "opportunityId", cascade = CascadeType.DETACH)
	@OneToMany(mappedBy = "opportunityId")
	private List<OpportunityActivity> opportunityActivities;
	
	@OneToMany(mappedBy = "key.opportunityId", cascade = CascadeType.ALL)
	private List<OpportunityCollaborator> opportunityCollaborators;
	
	//removed - will explicitly call
	/*@OneToMany(mappedBy = "opportunityId", cascade = CascadeType.ALL)
	private List<Version> versions;*/

	public Long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(Long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public String getOpportunityName() {
		return opportunityName;
	}

	public void setOpportunityName(String opportunityName) {
		this.opportunityName = opportunityName;
	}

	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public String getDurationGranularity() {
		return durationGranularity;
	}

	public void setDurationGranularity(String durationGranularity) {
		this.durationGranularity = durationGranularity;
	}

	public Double getDurationInWeeks() {
		return durationInWeeks;
	}

	public void setDurationInWeeks(Double durationInWeeks) {
		this.durationInWeeks = durationInWeeks;
	}

	public Date getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}
	
	public OpportunityStatus getOpportunityStatus() {
		return opportunityStatus;
	}

	public void setOpportunityStatus(OpportunityStatus opportunityStatus) {
		this.opportunityStatus = opportunityStatus;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProjectAlias() {
		return projectAlias;
	}

	public void setProjectAlias(String projectAlias) {
		this.projectAlias = projectAlias;
	}

	public List<OpportunityActivity> getOpportunityActivities() {
		return opportunityActivities;
	}

	public void setOpportunityActivities(List<OpportunityActivity> opportunityActivities) {
		this.opportunityActivities = opportunityActivities;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<OpportunityCollaborator> getOpportunityCollaborators() {
		return opportunityCollaborators;
	}

	public void setOpportunityCollaborators(List<OpportunityCollaborator> opportunityCollaborators) {
		this.opportunityCollaborators = opportunityCollaborators;
	}

	//removed - will explicitly call
	/*public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}*/
	
	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}
	

	@Override
	public String toString() {
		return "Opportunity [opportunityId=" + opportunityId + ", opportunityName=" + opportunityName
				+ ", businessUnit=" + businessUnit + ", serviceType=" + serviceType + ", durationGranularity="
				+ durationGranularity + ", durationInWeeks=" + durationInWeeks + ", projectStartDate="
				+ projectStartDate + ", opportunityStatus=" + opportunityStatus + ", documentStatus=" + documentStatus
				+ ", clientName=" + clientName + ", projectAlias=" + projectAlias + ", user=" + username
				+ ", opportunityActivities=" + opportunityActivities + "]";
	}

	
}
