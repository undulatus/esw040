package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WeeklyFTEKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WeeklyFTEKey() {
		super();
	}
	
	public WeeklyFTEKey(Long resourceSpecificationId, Long resourceScheduleWeekNumber) {
		super();
		this.resourceSpecificationId = resourceSpecificationId;
		this.resourceScheduleWeekNumber = resourceScheduleWeekNumber;
	}
	
	@Column(name="resource_specification_id")
	private Long resourceSpecificationId;
	
	@Column(name="resource_schedule_week_number")
	private Long resourceScheduleWeekNumber;

	public Long getResourceSpecificationId() {
		return resourceSpecificationId;
	}

	public void setResourceSpecificationId(Long resourceSpecificationId) {
		this.resourceSpecificationId = resourceSpecificationId;
	}

	public Long getResourceScheduleWeekNumber() {
		return resourceScheduleWeekNumber;
	}

	public void setResourceScheduleWeekNumber(Long resourceScheduleWeekNumber) {
		this.resourceScheduleWeekNumber = resourceScheduleWeekNumber;
	}
	
	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        WeeklyFTEKey that = (WeeklyFTEKey) o;
        if (resourceSpecificationId != null? !resourceSpecificationId.equals(that.getResourceSpecificationId()) : that.getResourceSpecificationId() != null) return false;
        if (resourceScheduleWeekNumber != null? !resourceScheduleWeekNumber.equals(that.getResourceScheduleWeekNumber()) : that.getResourceScheduleWeekNumber() != null) return false;
        return true;
    }

	@Override
    public int hashCode() {
        int result;
        result = (resourceSpecificationId != null? resourceSpecificationId.hashCode() : 0);
        result = 31 * result + (resourceScheduleWeekNumber !=null? resourceScheduleWeekNumber.hashCode() : 0);
        return result;
    }
	
}
