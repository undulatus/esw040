package com.pointwest.workforce.planner.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.data.ResourceSpecificationRepository;
import com.pointwest.workforce.planner.domain.ResourceSpecification;
import com.pointwest.workforce.planner.domain.WeeklyFTE;
import com.pointwest.workforce.planner.service.ResourceSpecificationService;
import com.pointwest.workforce.planner.util.DateUtil;

@Service
public class ResourceSpecificationServiceImpl implements ResourceSpecificationService {
	
	@Autowired
	public ResourceSpecificationRepository resourceSpecificationRepository;
	
	@Value("${month.to.week.multiplier}")
	private Integer WEEKSINMONTH;
	
	private static final Logger log = LoggerFactory.getLogger(ResourceSpecificationServiceImpl.class);

	@Override
	public List<ResourceSpecification> fetchAllResourceSpecifications() {
		List<ResourceSpecification> resourceSpecifications = new ArrayList<ResourceSpecification>(); 
		resourceSpecificationRepository.findAll().forEach(resourceSpecifications::add);
		return resourceSpecifications;
	}

	@Override
	public ResourceSpecification fetchResourceSpecification(Long resourceSpecificationId) {
		return resourceSpecificationRepository.findOne(resourceSpecificationId);
	}

	@Override
	public ResourceSpecification saveResourceSpecification(ResourceSpecification resourceSpecification) {
		return resourceSpecificationRepository.save(resourceSpecification);
	}
	
	@Override
	public ResourceSpecification updateResourceSpecification(ResourceSpecification resourceSpecification,
			Long resourceSpecificationId) {
		//if id not supplied in request body then set it
		if(resourceSpecification.getResourceSpecificationId() == null) resourceSpecification.setResourceSpecificationId(resourceSpecificationId);
		//do not save null values but set the previous values into it
		ResourceSpecification previousResourceSpecification = resourceSpecificationRepository.findOne(resourceSpecificationId);
		if(resourceSpecification.getRole() == null) resourceSpecification.setRole(previousResourceSpecification.getRole());
		if(resourceSpecification.getPractice() == null) resourceSpecification.setPractice(previousResourceSpecification.getPractice());
		if(resourceSpecification.getPayLevel() == null) resourceSpecification.setPayLevel(previousResourceSpecification.getPayLevel());
		if(resourceSpecification.isBillable() == null) resourceSpecification.setBillable(previousResourceSpecification.isBillable());
		if(resourceSpecification.getOpportunityActivityId() == null) resourceSpecification.setOpportunityActivityId(previousResourceSpecification.getOpportunityActivityId());
		//sprint 2 changes
		if(resourceSpecification.getRoleStartDate() == null) resourceSpecification.setRoleStartDate(previousResourceSpecification.getRoleStartDate());
		if(resourceSpecification.getDurationInWeeks() == null) resourceSpecification.setDurationInWeeks(previousResourceSpecification.getDurationInWeeks());
		if(resourceSpecification.getTotalFTE() == null) resourceSpecification.setTotalFTE(previousResourceSpecification.getTotalFTE());
		return resourceSpecificationRepository.save(resourceSpecification);
	}

	@Override
	public int deleteResourceSpecification(Long resourceSpecificationId) {
		int initialCount = resourceSpecificationRepository.countByResourceSpecificationId(resourceSpecificationId);
		resourceSpecificationRepository.delete(resourceSpecificationId);
		int postDeleteCount = resourceSpecificationRepository.countByResourceSpecificationId(resourceSpecificationId);
		return initialCount - postDeleteCount;
	}

	@Override
	public ResourceSpecification updateResourceSpecificationDates(Long resourceSpecificationId) {
		ResourceSpecification resourceSpecification = resourceSpecificationRepository.findOne(resourceSpecificationId);
		LocalDate startLocalDate = resourceSpecificationRepository.findOpportunityStartDate(resourceSpecificationId);
		log.debug("opportunity start localdate " + startLocalDate);
		Integer minWeek = resourceSpecificationRepository.findStartWeekOfResourceSpecification(resourceSpecificationId);
		Integer maxWeek = resourceSpecificationRepository.findEndWeekOfResourceSpecification(resourceSpecificationId);
		
		//Timestamp roleStartDate = Timestamp.valueOf(opportunityStartLocalDate.plusWeeks(minWeek - 1).atStartOfDay());
		Date roleStartDate;
		Double durationInWeeks;
		if(minWeek != null) {
			roleStartDate = DateUtil.adjustDateInclusive(startLocalDate, minWeek, WEEKSINMONTH);
			durationInWeeks = (maxWeek - minWeek) + 1.0;
		} else {
			roleStartDate = null;
			durationInWeeks = new Double(0);
		}
		
		resourceSpecification.setRoleStartDate(roleStartDate);
		resourceSpecification.setDurationInWeeks(durationInWeeks);
		
		List<WeeklyFTE> ftes = resourceSpecification.getResourceSchedule();
		BigDecimal pCurFTE = new BigDecimal("0.0");
		BigDecimal ptotalFTE = new BigDecimal("0.0").setScale(5, BigDecimal.ROUND_HALF_UP);
		
		for(WeeklyFTE fte : ftes) {
			pCurFTE = new BigDecimal(Double.toString(fte.getResourceScheduleFTE())).setScale(5, BigDecimal.ROUND_HALF_UP);
			ptotalFTE = ptotalFTE.add(pCurFTE);
		}
		ptotalFTE.setScale(5, BigDecimal.ROUND_HALF_UP);
		resourceSpecification.setTotalFTE(ptotalFTE.doubleValue());
		return resourceSpecificationRepository.save(resourceSpecification);
	}
	

	
}
