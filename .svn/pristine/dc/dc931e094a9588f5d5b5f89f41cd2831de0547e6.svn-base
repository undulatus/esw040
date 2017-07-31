package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ref_activity")
public class Activity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Activity() {
		super();
	}
	
	public Activity(String activityName, Boolean isCustom) {
		this.activityName = activityName;
		this.isCustom = isCustom;
	}
	
	public Activity(String activityName, Boolean isCustom, Long opportunityId) {
		this.activityName = activityName;
		this.isCustom = isCustom;
		this.opportunityId = opportunityId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="activity_id")
	private Integer activityId;
	
	@Column(name="activity_name")
	private String activityName;
	
	@Column(name="activity_is_custom", columnDefinition="INT(1)")
	private Boolean isCustom;
	
	@Column(name="opportunity_id")
	private Long opportunityId;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Boolean isCustom() {
		return isCustom;
	}

	public void setCustom(Boolean isCustom) {
		this.isCustom = isCustom;
	}

	public Long getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(Long opportunityId) {
		this.opportunityId = opportunityId;
	}
	
	
}
