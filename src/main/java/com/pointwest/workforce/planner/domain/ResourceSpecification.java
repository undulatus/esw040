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
@Table(name="resource_specification")
public class ResourceSpecification {
	
	public ResourceSpecification() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="resource_specification_id")
	private Long resourceSpecificationId;
	
	@OneToOne
	@JoinColumn(name="role_id")
	private Role role;
	
	@OneToOne
	@JoinColumn(name="practice_id")
	private Practice practice;
	
	@OneToOne
	@JoinColumn(name="pay_level_id")
	private PayLevel payLevel;
	
	@Column(name="resource_specification_is_billable",columnDefinition="INT(1)")
	private Boolean isBillable;
	
//	@ManyToOne
//	@JoinColumn(name="opportunity_activity_id")
	private long opportunityActivityId;
	
	@Column(name="resource_specification_start_date")
	private Date roleStartDate;
	
	@Column(name="resource_specification_duration_week")
	private Double durationInWeeks;
	
	@Column(name="resource_specification_total_fte")
	private Double totalFTE;
	
	@OneToMany(mappedBy = "key.resourceSpecificationId", cascade = CascadeType.ALL)
	private List<WeeklyFTE> resourceSchedule;
	

	public Long getResourceSpecificationId() {
		return resourceSpecificationId;
	}

	public void setResourceSpecificationId(Long resourceSpecificationId) {
		this.resourceSpecificationId = resourceSpecificationId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Practice getPractice() {
		return practice;
	}

	public void setPractice(Practice practice) {
		this.practice = practice;
	}

	public PayLevel getPayLevel() {
		return payLevel;
	}

	public void setPayLevel(PayLevel payLevel) {
		this.payLevel = payLevel;
	}

	public Boolean isBillable() {
		return isBillable;
	}

	public void setBillable(Boolean isBillable) {
		this.isBillable = isBillable;
	}

	public Long getOpportunityActivityId() {
		return opportunityActivityId;
	}

	public void setOpportunityActivityId(Long opportunityActivityId) {
		this.opportunityActivityId = opportunityActivityId;
	}

	public List<WeeklyFTE> getResourceSchedule() {
		return resourceSchedule;
	}

	public void setResourceSchedule(List<WeeklyFTE> resourceSchedule) {
		this.resourceSchedule = resourceSchedule;
	}

	public Date getRoleStartDate() {
		return roleStartDate;
	}

	public void setRoleStartDate(Date roleStartDate) {
		this.roleStartDate = roleStartDate;
	}

	public Double getDurationInWeeks() {
		return durationInWeeks;
	}

	public void setDurationInWeeks(Double durationInWeeks) {
		this.durationInWeeks = durationInWeeks;
	}

	public Double getTotalFTE() {
		return totalFTE;
	}

	public void setTotalFTE(Double totalFTE) {
		this.totalFTE = totalFTE;
	}
	
	
}
