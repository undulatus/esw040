package com.pointwest.workforce.planner.nonentity.domain;

import java.io.Serializable;
import java.util.Map;

public class OpportunityTnl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OpportunityTnl() {
		super();
	}
	
	private Long opportunityTnlId;
	
	private Long opportunityTnlRow;
	
	private Map<String,Object> dataMap;

	public Long getOpportunityTnlId() {
		return opportunityTnlId;
	}

	public void setOpportunityTnlId(Long opportunityTnlId) {
		this.opportunityTnlId = opportunityTnlId;
	}

	public Long getOpportunityTnlRow() {
		return opportunityTnlRow;
	}

	public void setOpportunityTnlRow(Long opportunityTnlRow) {
		this.opportunityTnlRow = opportunityTnlRow;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	
}
