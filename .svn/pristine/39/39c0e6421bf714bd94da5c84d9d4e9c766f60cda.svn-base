package com.pointwest.workforce.planner.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pointwest.workforce.planner.domain.OpportunityTnlRaw;
import com.pointwest.workforce.planner.nonentity.domain.OpportunityTnl;

@Component
public class OpportunityTnlRawAdapter extends OpportunityTnl {

	@Value("${tnl.col.projectcode}")
	private String projectCode;

	@Value("${tnl.col.resource.role}")
	private String role;

	@Value("${tnl.col.resource.practice}")
	private String practice;

	@Value("${tnl.col.resource.paylevel}")
	private String payLevel;

	@Value("${tnl.col.location}")
	private String location;

	@Value("${tnl.col.resource.billable}")
	private String billable;

	@Value("${tnl.col.resource.startdate}")
	private String estimatedStartDate;

	@Value("${tnl.col.resource.enddate}")
	private String estimateEndDate;

	@Value("${tnl.col.resource.fte.balance}")
	private String balance;

	@Value("${tnl.col.daterequested}")
	private String dateRequested;

	@Value("${tnl.col.agilereq}")
	private String agileReq;

	@Value("${tnl.col.account}")
	private String account;

	@Value("${tnl.col.groupproject}")
	private String groupProject;

	@Value("${tnl.col.backfill}")
	private String backfillSupernum;

	@Value("${tnl.col.quantity}")
	private String qty;

	@Value("${tnl.col.filled}")
	private String filled;

	@Value("${tnl.col.datefilled}")
	private String dateFilled;

	@Value("${tnl.col.resource.newhire}")
	private String newHire;

	@Value("${tnl.col.resource.candidate}")
	private String candidate;

	@Value("${tnl.col.resource.status}")
	private String candidateStatus;

	@Value("${tnl.col.resource.status}")
	private String status;

	@Value("${tnl.col.resource.background}")
	private String background;

	@Value("${tnl.col.resource.comments}")
	private String comments;

	@Value("${tnl.col.resource.resume}")
	private String resume;

	@Value("${tnl.col.resource.coc}")
	private String coc;

	@Value("${tnl.col.resource.nda}")
	private String nda;

	@Value("${tnl.col.resource.dateofbirth}")
	private String dateOfBirth;

	@Value("${tnl.col.resource.sss}")
	private String sss;

	@Value("${tnl.col.resource.docs}")
	private String docs;

	@Value("${tnl.col.workbookdatasource.id}")
	private String workbookDataSourceId;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<OpportunityTnlRaw> opportunityTnlRaws;
	OpportunityTnlRaw opportunityTnlRaw = null;

	public OpportunityTnlRawAdapter() {
		super();
	}

	public List<OpportunityTnlRaw> opportunityTnlRawAdapter (List<OpportunityTnl> opportunityTnls) {

		opportunityTnlRaws = new ArrayList<OpportunityTnlRaw>();

		for (OpportunityTnl opportunityTnl : opportunityTnls) {
			Map<String, Object> dataMap = opportunityTnl.getDataMap();
			dataMap.forEach((key, value) -> {
				opportunityTnlRaw = new OpportunityTnlRaw();

				opportunityTnlRaw.setRowNo(opportunityTnl.getOpportunityTnlRow());
				opportunityTnlRaw.setDateRequested((String) dataMap.get(dateRequested));
				opportunityTnlRaw.setAgileReq((String) dataMap.get(agileReq));
				opportunityTnlRaw.setAccount((String) dataMap.get(account));
				opportunityTnlRaw.setProjectCode((String) dataMap.get(projectCode));
				opportunityTnlRaw.setGroupProject((String) dataMap.get(groupProject));
				opportunityTnlRaw.setBillable((String) dataMap.get("billableynoptionalsingle"));
				opportunityTnlRaw.setBackfillSupernum((String) dataMap.get(backfillSupernum));
				opportunityTnlRaw.setRole((String) dataMap.get(role));
				opportunityTnlRaw.setSkillSet((String) dataMap.get(practice));
				opportunityTnlRaw.setPayLevel((String) dataMap.get(payLevel));
				opportunityTnlRaw.setLocation((String) dataMap.get(location));
				opportunityTnlRaw.setQty((String) dataMap.get(qty));
				opportunityTnlRaw.setFilled((String) dataMap.get(filled));
				opportunityTnlRaw.setBalance((String) dataMap.get(balance));
				opportunityTnlRaw.setNewHire((String) dataMap.get(newHire));
				opportunityTnlRaw.setDateFilled((String) dataMap.get(dateFilled));
				opportunityTnlRaw.setEstimatedStartDate((String) dataMap.get(estimatedStartDate));
				opportunityTnlRaw.setEstimatedEndDate((String) dataMap.get(estimateEndDate));
				opportunityTnlRaw.setBackground((String) dataMap.get(background));
				opportunityTnlRaw.setCandidate((String) dataMap.get(candidate));
				opportunityTnlRaw.setCandidateStatus((String) dataMap.get(candidateStatus));
				opportunityTnlRaw.setComments((String) dataMap.get(comments));
				opportunityTnlRaw.setResume((String) dataMap.get(resume));
				opportunityTnlRaw.setContingentWorkerCodeOfConduct((String) dataMap.get(coc));
				opportunityTnlRaw.setNonDisclosureAgreement((String) dataMap.get(nda));
				opportunityTnlRaw.setDateOfBirth((String) dataMap.get(dateOfBirth));
				opportunityTnlRaw.setLast5DigitsOfSSS((String) dataMap.get(sss));
				opportunityTnlRaw.setOnsiteOnboardingDocuments((String) dataMap.get(docs));
				opportunityTnlRaw.setWorkbookDatasourceId((Long) dataMap.get(workbookDataSourceId));
			});
			
			
			opportunityTnlRaws.add(opportunityTnlRaw);
		}
		return opportunityTnlRaws;
	}

	public List<OpportunityTnlRaw> getOpportunityTnlRaws() {
		return opportunityTnlRaws;
	}

	public void setOpportunityTnlRaws(List<OpportunityTnlRaw> opportunityTnlRaws) {
		this.opportunityTnlRaws = opportunityTnlRaws;
	}
}
