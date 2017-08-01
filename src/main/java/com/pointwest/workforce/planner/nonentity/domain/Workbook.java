package com.pointwest.workforce.planner.nonentity.domain;

import java.io.Serializable;
import java.util.List;
public class Workbook implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Workbook() {
		super();
	}
	
	private List<OpportunityTnl> opportunityTnl;

	public List<OpportunityTnl> getOpportunityTnl() {
		return opportunityTnl;
	}

	public void setOpportunityTnl(List<OpportunityTnl> opportunityTnl) {
		this.opportunityTnl = opportunityTnl;
	}
	
	
}
