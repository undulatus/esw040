package com.pointwest.workforce.planner.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="resource_schedule")
public class WeeklyFTE implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WeeklyFTE() {
		super();
	}
	
	/**
	 * constructor wich limits fte to 5 decimals.
	 * @param resourceSpecificationId
	 * @param resourceScheduleWeekNumber
	 * @param resourceScheduleFTE
	 */
	public WeeklyFTE(Long resourceSpecificationId, Long resourceScheduleWeekNumber, Double resourceScheduleFTE) {
		super();
		this.key = new WeeklyFTEKey(resourceSpecificationId, resourceScheduleWeekNumber);
		this.setResourceScheduleFTE(resourceScheduleFTE);
	}
	
	@EmbeddedId
	private WeeklyFTEKey key;
	
	@Column(name="resource_schedule_fte")
	private double resourceScheduleFTE;

	public WeeklyFTEKey getKey() {
		return key;
	}

	public void setKey(WeeklyFTEKey key) {
		this.key = key;
	}

	public double getResourceScheduleFTE() {
		return resourceScheduleFTE;
	}

	public void setResourceScheduleFTE(double resourceScheduleFTE) {
		
		//this.resourceScheduleFTE = resourceScheduleFTE;
		BigDecimal fte = new BigDecimal(Double.toString(resourceScheduleFTE)).setScale(5, BigDecimal.ROUND_HALF_UP);
		String sFte = fte.toString();
		this.resourceScheduleFTE = Double.parseDouble(sFte);
		
	}
	

}
