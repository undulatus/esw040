package com.pointwest.workforce.planner.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.domain.Activity;
import com.pointwest.workforce.planner.domain.BusinessUnit;
import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.domain.OpportunityActivity;
import com.pointwest.workforce.planner.domain.PayLevel;
import com.pointwest.workforce.planner.domain.Practice;
import com.pointwest.workforce.planner.domain.ResourceSpecification;
import com.pointwest.workforce.planner.domain.Role;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;
import com.pointwest.workforce.planner.service.MigrationService;
import com.pointwest.workforce.planner.service.OpportunityActivityService;
import com.pointwest.workforce.planner.service.OpportunityService;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.ResourceSpecificationService;

@Service
public class MigrationServiceImpl implements MigrationService {
	
	@Autowired
	OpportunityService opportunityService;	
	
	@Autowired
	ReferenceDataService referenceDataService;
	
	@Autowired
	OpportunityActivityService opportunityActivityService;
	
	@Autowired
	ResourceSpecificationService resourceSpecificationService;
	
	@Value("${tnl.col.projectcode}")
	private String projectCodeCol;
	
	@Value("${tnl.col.resource.role}")
	private String resourceRoleCol;
	
	@Value("${tnl.col.resource.practice}")
	private String resourcePracticeCol;
	
	@Value("${tnl.col.resource.paylevel}")
	private String resourcePayLevelCol;

	@Value("${tnl.col.resource.billable}")
	private String resourceIsBillableCol;

	@Value("${tnl.col.resource.startdate}")
	private String resourceStartDateCol;

	@Value("${tnl.col.resource.enddate}")
	private String resourceEndDateCol;
	
	@Value("${tnl.col.resource.fte.balance}")
	private String resourceFteBalanceCol;
	
	@Value("${tnl.def.activity.id}")
	private Long defaultActivityId;
	
	@Value("${tnl.def.serviceline.id}")
	private Long defaultServiceLineId;
	
/**
 * 
 * tnl.col.projectcode=Project Code
#tnl.col.businessunit=Business Unit
#resource spec
tnl.col.resource.role=Org Role
#Skillset (Technology/Practice)
tnl.col.resource.practice=Skill Set
tnl.col.resource.paylevel=Pay Level
#Billable? (Y/N)
tnl.col.resource.billable=Billable
#Estimated Start Date (of Resource)
tnl.col.resource.startdate=Estimated Start Date
#Estimated End Date (of Resource)
tnl.col.resource.enddate=Estimated End Date
#FTE? derive for fte this and role start date	
*/	
	
	private static final Logger log = LoggerFactory.getLogger(MigrationServiceImpl.class);
	
	private Map<String, Integer> mapBusinessUnit() {
		Map<String, Integer> buMap = new HashMap<String, Integer>();
		List<BusinessUnit> buList = referenceDataService.fetchAllBusinessUnit();
		for(BusinessUnit bu : buList) {
			buMap.put(bu.getBusinessUnitName(), bu.getBusinessUnitId());
		}
		return buMap;		
	}
	
	private Map<String, Integer> mapRole() {
		Map<String, Integer> roleMap = new HashMap<String, Integer>();
		List<Role> roleList = referenceDataService.fetchAllRole();
		for(Role role : roleList) {
			roleMap.put(role.getRoleName(), role.getRoleId());
		}
		return roleMap;		
	}
	
	private Map<String, Integer> mapPractice() {
		Map<String, Integer> practiceMap = new HashMap<String, Integer>();
		List<Practice> practiceList = referenceDataService.fetchAllPractice();
		for(Practice practice : practiceList) {
			practiceMap.put(practice.getPracticeName(), practice.getPracticeId());
		}
		return practiceMap;		
	}
	
	private Map<String, Integer> mapPayLevel() {
		Map<String, Integer> payLevelMap = new HashMap<String, Integer>();
		List<PayLevel> payLevelList = referenceDataService.fetchAllPayLevel();
		for(PayLevel payLevel: payLevelList) {
			payLevelMap.put(payLevel.getPayLevelName(), payLevel.getPayLevelId());
		}
		return payLevelMap;		
	}
	
	private Map<String, List<Map<String, Object>>> segregateOpportunities(List<Map<String, Object>> listOfEntries) {
		String projCode = null;
		List<Map<String, Object>> opportunityDataMapsContainer = null;
		Map<String, List<Map<String, Object>>> opportunityCollection = new HashMap<String, List<Map<String, Object>>>();
		
		for(Map<String, Object> rowMap : listOfEntries) {
			//bmab here
			projCode = rowMap.get(this.projectCodeCol).toString();
			if(opportunityCollection.containsKey(projCode)) {
				opportunityDataMapsContainer = opportunityCollection.get(projCode);
				opportunityDataMapsContainer.add(rowMap);
				opportunityCollection.put(projCode, opportunityDataMapsContainer);
			} else {
				opportunityDataMapsContainer = new ArrayList<Map<String, Object>>();
				opportunityDataMapsContainer.add(rowMap);
				opportunityCollection.put(projCode, opportunityDataMapsContainer);
			}
		}
		return opportunityCollection;
	}
	
	private List<Opportunity> transformToOpportunityList(Map<String, List<Map<String, Object>>> opportunityCollection) {
		Opportunity opportunity = null;
		
		Activity activity = new Activity();
		//BMAB set default activity value
		activity.setActivityId(20);

		List<OpportunityActivity> opportunityActivities = null;
		OpportunityActivity opportunityActivity = null;
		List<ResourceSpecification> resourceSpecifications = null;
		ResourceSpecification resourceSpecification = null;
		
		List<Opportunity> listOfOpportunitiesToSave = new ArrayList<Opportunity>();
		/*for (Map.Entry<String ,List<Map<Integer, Object>>> entry : opportunityCollection.entrySet()) {
			opportunity = new Opportunity();
			opportunity.setOpportunityName(entry.getKey());
			opportunityActivities = new ArrayList<OpportunityActivity>();
			resourceSpecifications = new ArrayList<ResourceSpecification>();
			
			opportunityActivity = new OpportunityActivity();
			//set defaults
			opportunityActivity.setActivity(activity);
			opportunityActivity.setSequenceNo(1);
			
			for(Map<Integer, Object> data : entry.getValue()) {
				resourceSpecification = this.processResourceSpecificationData(data);
				resourceSpecifications.add(resourceSpecification);
			}
			
			opportunityActivity.setResourceSpecificationList(resourceSpecifications);
			opportunityActivities.add(opportunityActivity);
			opportunity.setOpportunityActivities(opportunityActivities);
			listOfOpportunitiesToSave.add(opportunity);
		}*/
		System.out.println("##### NUMBER OF OPPS " + listOfOpportunitiesToSave.size());
		return listOfOpportunitiesToSave;
		
	}
	
	@Override
	public List<Opportunity> saveTnlOpportunities(Workbook tnlWorkbook) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Opportunity> deleteTnlOpportunities(Workbook tnlWorkbook) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
