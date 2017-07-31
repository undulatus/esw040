package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ref_business_unit")
public class BusinessUnit implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessUnit() {
		super();
	}

	@Id
	@Column(name="business_unit_id")
	private int businessUnitId;
	
	@Column(name="business_unit_name")
	private String businessUnitName;

	public int getBusinessUnitId() {
		return businessUnitId;
	}

	public void setBusinessUnitId(int businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	public String getBusinessUnitName() {
		return businessUnitName;
	}

	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}

	
}
