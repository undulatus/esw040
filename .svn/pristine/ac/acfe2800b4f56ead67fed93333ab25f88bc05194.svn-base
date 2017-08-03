package com.pointwest.workforce.planner.service.impl;

import java.time.LocalDate;
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
import com.pointwest.workforce.planner.domain.WeeklyFTE;
import com.pointwest.workforce.planner.domain.WeeklyFTEKey;
import com.pointwest.workforce.planner.nonentity.domain.OpportunityTnl;
import com.pointwest.workforce.planner.nonentity.domain.Workbook;
import com.pointwest.workforce.planner.service.MigrationService;
import com.pointwest.workforce.planner.service.OpportunityActivityService;
import com.pointwest.workforce.planner.service.OpportunityService;
import com.pointwest.workforce.planner.service.ReferenceDataService;
import com.pointwest.workforce.planner.service.ResourceSpecificationService;
import com.pointwest.workforce.planner.service.WeeklyFTEService;
import com.pointwest.workforce.planner.util.DateUtil;


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
	
	@Autowired
	WeeklyFTEService weeklyFTEService;
	
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
//from
	@Value("${tnl.col.resource.startdate}")
	private String resourceStartDateCol;

	@Value("${tnl.col.resource.enddate}")
	private String resourceEndDateCol;
	
	@Value("${tnl.col.resource.fte.balance}")
	private String resourceFteBalanceCol;
//end from
	@Value("${tnl.def.activity.id}")
	private Integer defaultActivityId;
	
	@Value("${tnl.def.serviceline.id}")
	private Integer defaultServiceLineId;
	
	@Value("${tnl.def.durationgranularity}")
	private String defaultDurationGranularity;
	
	@Value("${month.to.week.multiplier}")
	private Integer WEEKSINMONTH;
	
	private Map<String, BusinessUnit> businessUnitMap = null;
	
	private Map<String, Role> roleMap = null;
	
	private Map<String, Practice> practiceMap = null;
	
	private Map<String, PayLevel> payLevelMap = null;
	
	private static final Logger log = LoggerFactory.getLogger(MigrationServiceImpl.class);
	
	private Map<String, BusinessUnit> mapBusinessUnit() {
		Map<String, BusinessUnit> buMap = new HashMap<String, BusinessUnit>();
		List<BusinessUnit> buList = referenceDataService.fetchAllBusinessUnit();
		for(BusinessUnit bu : buList) {
			buMap.put(bu.getBusinessUnitName(), bu);
		}
		return buMap;		
	}
	
	private Map<String, Role> mapRole() {
		Map<String, Role> roleMap = new HashMap<String, Role>();
		List<Role> roleList = referenceDataService.fetchAllRole();
		for(Role role : roleList) {
			roleMap.put(role.getRoleName(), role);
		}
		return roleMap;		
	}
	
	private Map<String, Practice> mapPractice() {
		Map<String, Practice> practiceMap = new HashMap<String, Practice>();
		List<Practice> practiceList = referenceDataService.fetchAllPractice();
		for(Practice practice : practiceList) {
			practiceMap.put(practice.getPracticeName(), practice);
		}
		return practiceMap;		
	}
	
	private Map<String, PayLevel> mapPayLevel() {
		Map<String, PayLevel> payLevelMap = new HashMap<String, PayLevel>();
		List<PayLevel> payLevelList = referenceDataService.fetchAllPayLevel();
		for(PayLevel payLevel: payLevelList) {
			payLevelMap.put(payLevel.getPayLevelName(), payLevel);
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
		activity.setActivityId(this.defaultActivityId.intValue());

		List<OpportunityActivity> opportunityActivities = null;
		OpportunityActivity opportunityActivity = null;
		List<ResourceSpecification> resourceSpecifications = null;
		ResourceSpecification resourceSpecification = null;
		
		List<Opportunity> listOfOpportunitiesToSave = new ArrayList<Opportunity>();
		for (Map.Entry<String ,List<Map<String, Object>>> entry : opportunityCollection.entrySet()) {
			//BMAB TO DO CREATE PROJ CODE IN OPP OBJECT set here
			opportunity = new Opportunity();
			opportunity.setOpportunityName(entry.getKey());
			opportunity.setDurationGranularity(this.defaultDurationGranularity);
			opportunityActivities = new ArrayList<OpportunityActivity>();
			resourceSpecifications = new ArrayList<ResourceSpecification>();
			
			opportunityActivity = new OpportunityActivity();
			//set defaults
			opportunityActivity.setActivity(activity);
			opportunityActivity.setSequenceNo(1);
			
			for(Map<String, Object> data : entry.getValue()) {
				if(isEntryForSaving(data)) {
					resourceSpecification = this.processResourceSpecificationData(data);
					resourceSpecification.setResourceSchedule(this.processFTESchedule(data));
					resourceSpecifications.add(resourceSpecification);
				} else {
					//next iteration
				}
			}
			
			opportunityActivity.setResourceSpecificationList(resourceSpecifications);
			opportunityActivities.add(opportunityActivity);
			opportunity.setOpportunityActivities(opportunityActivities);
			listOfOpportunitiesToSave.add(opportunity);
		}
		log.debug("##### NUMBER OF OPPS from upload" + listOfOpportunitiesToSave.size());
		return listOfOpportunitiesToSave;
		
	}
	
	private boolean isEntryForSaving(Map<String, Object> data) {
		//balance > 0 is valid
		boolean result = false;
		Double balance = Double.valueOf( (String) data.get(this.resourceFteBalanceCol) );
		if(balance > 0) {
			result = true;
		}
		return result;
	}
	
	private ResourceSpecification processResourceSpecificationData(Map<String, Object> data) {
		ResourceSpecification resourceSpecification = new ResourceSpecification();
		
		String roleName = (String) data.get(this.resourceRoleCol);
		Role role = roleMap.get(roleName);
		resourceSpecification.setRole(role);
		
		String payLevelName = (String) data.get(this.resourcePayLevelCol);
		PayLevel payLevel = payLevelMap.get(payLevelName);
		resourceSpecification.setPayLevel(payLevel);
		
		String practiceName = (String) data.get(this.resourcePracticeCol);
		Practice practice = practiceMap.get(practiceName);
		resourceSpecification.setPractice(practice);
		
		String isBillableString = (String) data.get(this.resourceIsBillableCol);
		isBillableString = isBillableString.toLowerCase().trim();
		if(isBillableString.equals("y") || isBillableString.equals("yes") || isBillableString.equals("1") || isBillableString.equals("true")) { 
			resourceSpecification.setBillable(true);
		} else { 
			resourceSpecification.setBillable(false);
		}
		
		return resourceSpecification ;
	}
	
	private List<WeeklyFTE> processFTESchedule(Map<String, Object> data) {
		List<WeeklyFTE> weeklyFTEs = new ArrayList<WeeklyFTE>();
		WeeklyFTE weeklyFTE = null;
		Integer durationInWeeks = null;
		try {
			Double fteValue = Double.valueOf( (String) data.get(this.resourceFteBalanceCol) );
			String startDateString = (String) data.get(this.resourceStartDateCol);
			String endDateString = (String) data.get(this.resourceEndDateCol);
			LocalDate startDate = DateUtil.stringToDate(startDateString);
			LocalDate endDate = DateUtil.stringToDate(endDateString);
			durationInWeeks = DateUtil.getWeeks(startDate, endDate, WEEKSINMONTH);
			
			for(long i = 1 ; i <= durationInWeeks; i++) {
				//arbitrary resource spec id will be handled in saving
				weeklyFTE = new WeeklyFTE(i, i, fteValue);
				weeklyFTEs.add(weeklyFTE);
				log.debug("an fte val entry " + weeklyFTE.getResourceScheduleFTE());
			}
			
		} catch(Exception e) {
			log.debug(" no entries for fte may not be valid ");
			weeklyFTE = null;
		}
		log.debug("upload fte size " + weeklyFTEs.size());
		return weeklyFTEs;
	}
	
	
	@Override
	public List<Opportunity> saveTnlOpportunities(Workbook tnlWorkbook) {
		//init references
		this.businessUnitMap = this.mapBusinessUnit();
		this.roleMap = this.mapRole();
		this.practiceMap = this.mapPractice();
		this.payLevelMap = this.mapPayLevel();
		
		List<Map<String,Object>> listOfEntries = new ArrayList<Map<String,Object>>();
		for(OpportunityTnl entry : tnlWorkbook.getOpportunityTnl()) {
			listOfEntries.add(entry.getDataMap());
		}
		Map<String, List<Map<String, Object>>> opportunityCollection = this.segregateOpportunities(listOfEntries);
		List<Opportunity> opportunitiesToSave = this.transformToOpportunityList(opportunityCollection);
		List<Opportunity> savedOpportunities = this.saveData(opportunitiesToSave);
		
		return savedOpportunities;
	}
	
	private List<Opportunity> saveData(List<Opportunity> opportunities) {
		List<Opportunity> savedOpportunities = new ArrayList<Opportunity>();
		Opportunity savedOpportunity = null;
		OpportunityActivity savedOpportunityActivity = null;
		ResourceSpecification savedResourceSpecification = null;
		Long opportunityId = null;
		Long opportunityActivityId = null;
		List<OpportunityActivity> oppa = null;
		List<ResourceSpecification> ress = null;
		List<WeeklyFTE> fteWeeks = null;
		WeeklyFTEKey key = null;
		for(Opportunity opportunity : opportunities) {
			oppa = opportunity.getOpportunityActivities();
			opportunity.setOpportunityActivities(null);
			savedOpportunity = opportunityService.saveOpportunity(opportunity);
			opportunityId = savedOpportunity.getOpportunityId();
			log.debug("upload saved opp id : " + opportunityId);
			for(OpportunityActivity oppAct : oppa) {				
				ress = oppAct.getResourceSpecificationList();
				oppAct.setResourceSpecificationList(null);
				oppAct.setOpportunityId(opportunityId);
				savedOpportunityActivity = opportunityActivityService.saveOpportunityActivity(oppAct);
				opportunityActivityId = savedOpportunityActivity.getOpportunityActivityId();
				log.debug("upload oppAct act id " + savedOpportunityActivity.getActivity().getActivityId());
				log.debug("upload oppAct transact id " + opportunityActivityId);
				for(ResourceSpecification resSpec : ress) {
					resSpec.setOpportunityActivityId(opportunityActivityId);
					fteWeeks = resSpec.getResourceSchedule();
					resSpec.setResourceSchedule(null);
					savedResourceSpecification = resourceSpecificationService.saveResourceSpecification(resSpec);
					log.debug("upload resSpec id " + savedResourceSpecification.getResourceSpecificationId());
					for(WeeklyFTE fteWeek : fteWeeks) {
						key = fteWeek.getKey();
						key.setResourceSpecificationId(savedResourceSpecification.getResourceSpecificationId());
						fteWeek.setKey(key);
						weeklyFTEService.saveWeeklyFTE(fteWeek);
						log.debug("trying to save fte.. " + key.getResourceSpecificationId());
					}
				}
			}
			savedOpportunities.add(opportunityService.fetchOpportunity(opportunityId));
				
		}
		return savedOpportunities;
	}

	@Override
	public List<Opportunity> deleteTnlOpportunities(Workbook tnlWorkbook) {
		
		return null;
	}
	
}
