package com.pointwest.workforce.planner.domain;

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

@Entity
@Table(name="opportunity_activity")
public class OpportunityActivity {
	
	public OpportunityActivity() {
		super();
	}
	
	public OpportunityActivity(Activity activity, Long opportunityId) {
		super();
		this.activity = activity;
		this.opportunityId = opportunityId;
	}
	
	public OpportunityActivity(Activity activity, Long opportunityId, Integer sequenceNo) {
		super();
		this.activity = activity;
		this.opportunityId = opportunityId;
		this.sequenceNo = sequenceNo;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="opportunity_activity_id")
	private Long opportunityActivityId;
	
	@OneToOne
	@JoinColumn(name="activity_id")
	private Activity activity;
	
	//@ManyToOne
	//@JoinColumn(name="opportunity_id")
	private Long opportunityId;

	@Column(name="opportunity_activity_duration_week")
	private Double durationInWeeks;
	
	@Column(name="opportunity_activity_start_date")
	private Date activityStartDate;
	
	@Column(name="opportunity_activity_sequence_no")
	private Integer sequenceNo;
	
	//@OneToMany(FetchType.LAZY, mappedBy = "opportunityActivityId", cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "opportunityActivityId", cascade = CascadeType.ALL)
	private List<ResourceSpecification> resourceSpecificationList;

	public Long getOpportunityActivityId() {
		return opportunityActivityId;
	}

	public void setOpportunityActivityId(Long opportunityActivityId) {
		this.opportunityActivityId = opportunityActivityId;
	}

	public Long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(Long opportunityId) {
		this.opportunityId = opportunityId;
	}

	public Double getDurationInWeeks() {
		return durationInWeeks;
	}

	public void setDurationInWeeks(Double durationInWeeks) {
		this.durationInWeeks = durationInWeeks;
	}

	public Date getActivityStartDate() {
		return activityStartDate;
	}

	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}

	public List<ResourceSpecification> getResourceSpecificationList() {
		return resourceSpecificationList;
	}

	public void setResourceSpecificationList(List<ResourceSpecification> resourceSpecificationList) {
		this.resourceSpecificationList = resourceSpecificationList;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
}
