package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="opportunity_tnl")
public class OpportunityTnl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OpportunityTnl() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="opportunity_tnl_id")
	private Long opportunityTnlId;
	
	//# Row Number
	@Column(name="rowNo")
	private Long rowNo;
	
	//Date Requested
	@Column(name="date_requested")
	private String dateRequested;
	
	//Agile Req
	@Column(name="agile_req")
	private String agileReq;
	
	//Account
	@Column(name="account")
	private String account;
	
	//Project Code
	@Column(name="project_code")
	private String projectCode;
	
	//Group / Project
	@Column(name="group_project")
	private String groupProject;
	
	//Billable
	@Column(name="billable")
	private String billable;
	
	//Backfill / Supernum
	@Column(name="backfill_supernum")
	private String backfillSupernum;
	
	//Org Role
	@Column(name="org_role")
	private String orgRole;
	
	//Skill Set
	@Column(name="skill_set")
	private String skillSet;
	
	//Pay Level
	@Column(name="pay_level")
	private String payLevel;
	
	//Location
	@Column(name="location")
	private String location;
	
	//Qty
	@Column(name="qty")
	private String qty;
	
	//Filled
	@Column(name="filled")
	private String filled;
	
	//Balance
	@Column(name="balance")
	private String balance;
	
	//New Hire
	@Column(name="new_hire")
	private String newHire;
	
	//Date Filled
	@Column(name="date_filled")
	private String dateFilled;
	
	//Estimated Start Date
	@Column(name="estimated_start_date")
	private String estimatedStartDate;
	
	//Estimated End Date
	@Column(name="estimated_end_date")
	private String estimatedEndDate;

	//Candidate
	@Column(name="candidate")
	private String candidate;

	//Candidate Status
	@Column(name="candidate_status")
	private String candidateStatus;

	//Background
	@Column(name="background")
	private String background;

	//Comments
	@Column(name="comments")
	private String comments;

	//Resume
	@Column(name="resume")
	private String resume;

	//Contingent Worker Code of Conduct
	@Column(name="contingent_worker_code_of_conduct")
	private String contingentWorkerCodeOfConduct;

	//Non-Disclosure Agreement
	@Column(name="non_disclosure_agreement")
	private String nonDisclosureAgreement;
	
	//Date of Birth
	@Column(name="date_of_birth")
	private String dateOfBirth;

	//Last 5 digits of SSS
	@Column(name="last_5_digits_of_sss")
	private String last5DigitsOfSSS;

	//Onsite Onboarding Documents
	@Column(name="onsite_onboarding_documents")
	private String onsiteOnboardingDocuments;

}
