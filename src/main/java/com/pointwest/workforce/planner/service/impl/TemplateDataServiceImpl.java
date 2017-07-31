package com.pointwest.workforce.planner.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.data.ActivityRepository;
import com.pointwest.workforce.planner.data.OrganizationRoleRepository;
import com.pointwest.workforce.planner.data.PracticeRepository;
import com.pointwest.workforce.planner.data.RoleRepository;
import com.pointwest.workforce.planner.domain.Activity;
import com.pointwest.workforce.planner.domain.OrganizationRole;
import com.pointwest.workforce.planner.domain.Practice;
import com.pointwest.workforce.planner.domain.Role;
import com.pointwest.workforce.planner.service.TemplateDataService;

@Service
public class TemplateDataServiceImpl implements TemplateDataService {

		@Autowired
		public ActivityRepository activityRepository;
		
		@Autowired
		public PracticeRepository practiceRepository;
		
		@Autowired
		public RoleRepository roleRepository;
		
		@Autowired
		public OrganizationRoleRepository orgRoleRepository;
		
		private static final Logger log = LoggerFactory.getLogger(TemplateDataServiceImpl.class);

		@Override
		public List<Activity> fetchActivitiesByServiceTypeId(int serviceTypeId) {
			log.debug("MCI >> fetchActivitiesByServiceTypeId with serviceTypeId: " + serviceTypeId);
			List<Activity> activities = (List<Activity>) activityRepository.findActivitiesByServiceTypeId(serviceTypeId);
			log.debug("MCO >> fetchActivitiesByServiceTypeId with serviceTypeId: " + serviceTypeId);
			return activities;
		}

		//
		@Override
		public List<Role> fetchRolesByServiceTypeId(int serviceTypeId) {
			log.debug("MCI >> fetchRolesByServiceTypeId with serviceTypeId: " + serviceTypeId);
			List<Role> roles = (List<Role>) roleRepository.findRolesByServiceTypeIdOrderByOrgRoleNameAsc(serviceTypeId);
			log.debug("MCO >> fetchRolesByServiceTypeId with serviceTypeId: " + serviceTypeId);
			return roles;
		}

		
		@Override
		public List<Practice> fetchPracticesByRoleId(int roleId) {
			log.debug("MCI >> fetchPracticesByRoleId with roleId: " + roleId);
			List<Practice> practices = (List<Practice>) practiceRepository.findPracticesByRoleIdOrderByPracticeNameAsc(roleId);
			log.debug("MCO >> fetchPracticesByRoleId with roleId: " + roleId);
			return practices;
		}

		@Override
		public List<OrganizationRole> fetchOrgRolesByServiceTypeId(int serviceTypeId) {
			log.debug("MCI >> fetchOrgRolesByServiceTypeId with serviceTypeId: " + serviceTypeId);
			List<OrganizationRole> orgRoles = (List<OrganizationRole>) orgRoleRepository.findOrgRolesByServiceTypeIdOrderByOrgRoleNameAsc(serviceTypeId);
			log.debug("MCO >> fetchOrgRolesByServiceTypeId with serviceTypeId: " + serviceTypeId);
			return orgRoles;
		}
		
		
}

