package com.pointwest.workforce.planner.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ref_org_role")
public class OrganizationRole {
	
	public OrganizationRole() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="org_role_id")
	private int orgRoleId;
	
	@Column(name="org_role_name")
	private String orgRoleName;

	public int getOrgRoleId() {
		return orgRoleId;
	}

	public void setOrgRoleId(int orgRoleId) {
		this.orgRoleId = orgRoleId;
	}

	public String getOrgRoleName() {
		return orgRoleName;
	}

	public void setOrgRoleName(String orgRoleName) {
		this.orgRoleName = orgRoleName;
	}

	
}
