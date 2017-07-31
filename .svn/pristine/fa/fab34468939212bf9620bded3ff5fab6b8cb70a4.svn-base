package com.pointwest.workforce.planner.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ref_pay_level")
public class PayLevel {

	public PayLevel() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="pay_level_id")
	private int payLevelId;
	
	@Column(name="pay_level_name")
	private String payLevelName;

	public int getPayLevelId() {
		return payLevelId;
	}

	public void setPayLevelId(int payLevelId) {
		this.payLevelId = payLevelId;
	}

	public String getPayLevelName() {
		return payLevelName;
	}

	public void setPayLevelName(String payLevelName) {
		this.payLevelName = payLevelName;
	}
	
}
