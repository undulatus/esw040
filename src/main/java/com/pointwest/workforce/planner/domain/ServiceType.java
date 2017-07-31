package com.pointwest.workforce.planner.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ref_service_type")
public class ServiceType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceType() {
		super();
	}
	
	public ServiceType(Integer serviceTypeId) {
		super();
		this.serviceTypeId = serviceTypeId;
	}

	@Id
	@Column(name="service_type_id")
	private Integer serviceTypeId;
	
	@Column(name="service_type_name")
	private String serviceTypeName;

	public Integer getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getServiceTypeName() {
		return serviceTypeName;
	}

	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	
	
}
