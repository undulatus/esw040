package com.pointwest.workforce.planner.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.data.ActivityRepository;
import com.pointwest.workforce.planner.data.BusinessUnitRepository;
import com.pointwest.workforce.planner.data.GroupRepository;
import com.pointwest.workforce.planner.data.OpportunityStatusRepository;
import com.pointwest.workforce.planner.data.OrganizationRoleRepository;
import com.pointwest.workforce.planner.data.PayLevelRepository;
import com.pointwest.workforce.planner.data.PracticeRepository;
import com.pointwest.workforce.planner.data.RoleRepository;
import com.pointwest.workforce.planner.data.ServiceTypeRepository;
import com.pointwest.workforce.planner.domain.Activity;
import com.pointwest.workforce.planner.domain.BusinessUnit;
import com.pointwest.workforce.planner.domain.Group;
import com.pointwest.workforce.planner.domain.OpportunityStatus;
import com.pointwest.workforce.planner.domain.OrganizationRole;
import com.pointwest.workforce.planner.domain.PayLevel;
import com.pointwest.workforce.planner.domain.Practice;
import com.pointwest.workforce.planner.domain.Role;
import com.pointwest.workforce.planner.domain.ServiceType;
import com.pointwest.workforce.planner.service.ReferenceDataService;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {

		@Autowired
		public BusinessUnitRepository businessUnitRepository;
		
		@Autowired
		public ServiceTypeRepository serviceTypeRepository;
		
		@Autowired
		public ActivityRepository activityRepository;
		
		@Autowired
		public GroupRepository groupRepository;
		
		@Autowired
		public PayLevelRepository paylevelRepository;
		
		@Autowired
		public PracticeRepository practiceRepository;
		
		@Autowired
		public RoleRepository roleRepository;
		
		@Autowired
		public OrganizationRoleRepository orgRoleRepository;
		
		@Autowired
		public OpportunityStatusRepository opportunityStatusRepository;
		
		private static final Logger log = LoggerFactory.getLogger(ReferenceDataServiceImpl.class);
		
		@Override
		public List<BusinessUnit> fetchAllBusinessUnit() {
			log.debug("MCI >> fetchAllBusinessUnit");
			List<BusinessUnit> businessUnits = (List<BusinessUnit>) businessUnitRepository.findAllByOrderByBusinessUnitNameAsc();
			log.debug("MCO >> fetchAllBusinessUnit");
			return businessUnits;
		}
		
		@Override
		public BusinessUnit fetchBusinessUnit(int businessUnitId) {
			log.debug("MCI >> fetchBusinessUnit with id : " + businessUnitId);
			BusinessUnit businessUnit = businessUnitRepository.findOne(businessUnitId);
			log.debug("MCO >> fetchBusinessUnit with id : " + businessUnitId);
			return businessUnit;
		}
		
		@Override
		public int addBusinessUnit(BusinessUnit businessUnit) {
			log.debug("MCI >> addBusinessUnit");
			BusinessUnit saved = businessUnitRepository.save(businessUnit);
			int result = saved != null ?  1 : 0;
			log.debug("MCO >> addBusinessUnit result " + result);
			return result;
		}

		@Override
		public List<ServiceType> fetchAllServiceType() {
			log.debug("MCI >> fetchAllServiceType");
			List<ServiceType> serviceTypes = (List<ServiceType>) serviceTypeRepository.findAllByOrderByServiceTypeNameAsc();
			log.debug("MCO >> fetchAllServiceType");
			return serviceTypes;
			
		}

		@Override
		public ServiceType fetchServiceType(int serviceTypeId) {
			log.debug("MCI >> fetchServiceType with id: " + serviceTypeId);
			ServiceType serviceType = serviceTypeRepository.findOne(serviceTypeId);
			log.debug("MCI >> fetchServiceType with id: " + serviceTypeId);
			return serviceType;
		}

		@Override
		public int addServiceType(ServiceType serviceType) {
			log.debug("MCI >> addServiceType");
			ServiceType saved = serviceTypeRepository.save(serviceType);
			int result = saved != null ?  1 : 0;
			log.debug("MCO >> addServiceType result " + result);
			return result;
		}

		@Override
		public List<Activity> fetchAllActivity() {
			log.debug("MCI >> fetchAllActivity");
			List<Activity> activities = (List<Activity>) activityRepository.findAll();
			log.debug("MCO >> fetchAllActivity");
			return activities;
		}

		@Override
		public Activity fetchActivity(int activityId) {
			log.debug("MCI >> fetchActivity with id: " + activityId);
			Activity activity = activityRepository.findOne(activityId);
			log.debug("MCO >> fetchActivity with id: " + activityId);
			return activity;
		}

		@Override
		public Activity addActivity(Activity activity) {
			log.debug("MCI >> addActivity");
			Activity savedActivity = activityRepository.save(activity);
			boolean result = savedActivity != null ?  true : false;
			log.debug("MCO >> addActivity saved? : " + result);
			return savedActivity;
		}

		@Override
		public List<Group> fetchAllGroup() {
			log.debug("MCI >> fetchAllGroup");
			List<Group> groups = (List<Group>) groupRepository.findAll();
			log.debug("MCO >> fetchAllGroup");
			return groups;
		}

		@Override
		public Group fetchGroup(int groupId) {
			log.debug("MCI >> fetchGroup with id: " + groupId);
			Group group = groupRepository.findOne(groupId);
			log.debug("MCO >> fetchGroup with id: " + groupId);
			return group;
		}

		@Override
		public List<PayLevel> fetchAllPayLevel() {
			log.debug("MCI >> fetchAllPayLevel");
			List<PayLevel> payLevels = (List<PayLevel>) paylevelRepository.findAll();
			log.debug("MCO >> fetchAllPayLevel");
			return payLevels;
		}

		@Override
		public PayLevel fetchPayLevel(int payLevelId) {
			log.debug("MCI >> fetchPayLevel with id: " + payLevelId);
			PayLevel payLevel = paylevelRepository.findOne(payLevelId);
			log.debug("MCO >> fetchPayLevel with id: " + payLevelId);
			return payLevel;
		}

		@Override
		public List<Practice> fetchAllPractice() {
			log.debug("MCI >> fetchAllPractice");
			List<Practice> practices = (List<Practice>) practiceRepository.findAllByOrderByPracticeNameAsc();
			log.debug("MCO >> fetchAllPractice");
			return practices;
		}

		@Override
		public Practice fetchPractice(int practiceId) {
			log.debug("MCI >> fetchPractice with id: " + practiceId);
			Practice practice = practiceRepository.findOne(practiceId);
			log.debug("MCO >> fetchPractice with id: " + practiceId);
			return practice;
		}

		@Override
		public List<Role> fetchAllRole() {
			log.debug("MCI >> fetchAllRole");
			List<Role> roles = (List<Role>) roleRepository.findAllByOrderByRoleNameAsc();
			log.debug("MCO >> fetchAllRole");
			return roles;
		}

		@Override
		public Role fetchRole(int roleId) {
			log.debug("MCI >> fetchRole with id: " + roleId);
			Role role = roleRepository.findOne(roleId);
			log.debug("MCI >> fetchRole with id: " + roleId);
			return role;
		}

		@Override
		public List<OrganizationRole> fetchAllOrgRole() {
			log.debug("MCI >> fetchAllOrgRole with id: ");
			List<OrganizationRole> orgRole = (List<OrganizationRole>) orgRoleRepository.findAllByOrderByOrgRoleNameAsc();
			log.debug("MCI >> fetchAllOrgRole with id: ");
			return orgRole;
		}

		@Override
		public OrganizationRole fetchOrgRole(int orgRoleId) {
			log.debug("MCI >> fetchRole with id: " + orgRoleId);
			OrganizationRole orgRole = orgRoleRepository.findOne(orgRoleId);
			log.debug("MCI >> fetchRole with id: " + orgRoleId);
			return orgRole;
		}
		
		@Override
		public List<OpportunityStatus> fetchAllOpportunityStatus() {
			log.debug("MCI >> fetchAllOpportunityStatus");
			List<OpportunityStatus> opportunityStatus = (List<OpportunityStatus>) opportunityStatusRepository.findAllByOrderByOpportunityStatusNameAsc();
			log.debug("MCO >> fetchAllOpportunityStatus");
			return opportunityStatus;
		}
		
		@Override
		public OpportunityStatus fetchOpportunityStatus(int opportunityStatusId) {
			log.debug("MCI >> fetchOpportunityStatus with id : " + opportunityStatusId);
			OpportunityStatus opportunityStatus = opportunityStatusRepository.findOne(opportunityStatusId);
			log.debug("MCO >> fetchOpportunityStatus with id : " + opportunityStatusId);
			return opportunityStatus;
		}
		
		
	}

