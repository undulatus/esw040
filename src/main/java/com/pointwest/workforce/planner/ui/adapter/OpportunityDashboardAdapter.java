package com.pointwest.workforce.planner.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.pointwest.workforce.planner.ui.domain.OpportunityDashboard;

public class OpportunityDashboardAdapter extends OpportunityDashboard {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<OpportunityDashboard> opportunityDashboards;
	
	public OpportunityDashboardAdapter() {
		super();
	}
	
	public OpportunityDashboardAdapter(List<OpportunityDashboardProjection> opportunityDashboardProjections) {
		this.opportunityDashboards = new ArrayList<OpportunityDashboard>();
		OpportunityDashboard opportunityDashboard = null;
		for(OpportunityDashboardProjection entry : opportunityDashboardProjections) {
			opportunityDashboard = new OpportunityDashboard(
					entry.getOpportunityId(),
					entry.getOpportunityName(),
					entry.getBusinessUnitName(),
					entry.getServiceTypeName(),
					entry.getProjectStartDate(),
					entry.getOpportunityStatus(),
					entry.getDocumentStatus(),
					entry.getClientName(),
					entry.getOpportunityCollaborators(),
					entry.getLastModifiedDate(),
					entry.getUsername());
			this.opportunityDashboards.add(opportunityDashboard);
		}
	}
	
	public OpportunityDashboardAdapter(List<OpportunityDashboardProjection> opportunityDashboardProjections, String username) {
		this.opportunityDashboards = new ArrayList<OpportunityDashboard>();
		OpportunityDashboard opportunityDashboard = null;
		for(OpportunityDashboardProjection entry : opportunityDashboardProjections) {
			opportunityDashboard = new OpportunityDashboard(
					entry.getOpportunityId(),
					entry.getOpportunityName(),
					entry.getBusinessUnitName(),
					entry.getServiceTypeName(),
					entry.getProjectStartDate(),
					entry.getOpportunityStatus(),
					entry.getDocumentStatus(),
					entry.getClientName(),
					entry.getOpportunityCollaborators(),
					entry.getLastModifiedDate(),
					entry.getUsername());
			opportunityDashboard.setUserPermission(username);
			this.opportunityDashboards.add(opportunityDashboard);
		}
	}

	public List<OpportunityDashboard> getOpportunityDashboards() {
		return opportunityDashboards;
	}

	public void setOpportunityDashboards(List<OpportunityDashboard> opportunityDashboards) {
		this.opportunityDashboards = opportunityDashboards;
	}
	
	
}
