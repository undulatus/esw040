package com.pointwest.workforce.planner.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.pointwest.workforce.planner.data.WeeklyFTERepository;
import com.pointwest.workforce.planner.domain.WeeklyFTE;
import com.pointwest.workforce.planner.domain.WeeklyFTEKey;
import com.pointwest.workforce.planner.service.WeeklyFTEService;

@Service
public class WeeklyFTEServiceImpl implements WeeklyFTEService {
	
	@Autowired
	public WeeklyFTERepository weeklyFTERepository;
	
	private static final Logger log = LoggerFactory.getLogger(WeeklyFTEServiceImpl.class);

	@Override
	public List<WeeklyFTE> fetchAllWeeklyFTEs() {
		List<WeeklyFTE> weeklyFTEs = new ArrayList<WeeklyFTE>(); 
		weeklyFTERepository.findAll().forEach(weeklyFTEs::add);
		return weeklyFTEs;
	}

	@Override
	public WeeklyFTE fetchWeeklyFTE(WeeklyFTEKey key) {
		return weeklyFTERepository.findOne(key);
	}
	
	@Override
	public List<WeeklyFTE> fetchWeeklyFTE(Long resourceSpecificationId) {
		return weeklyFTERepository.findWeeklyFTEsByKeyResourceSpecificationId(resourceSpecificationId);
	}

	@Override
	public WeeklyFTE saveWeeklyFTE(WeeklyFTE weeklyFTE) {
		return weeklyFTERepository.save(weeklyFTE);
	}
	
	@Override
	public WeeklyFTE updateWeeklyFTE(WeeklyFTE weeklyFTE,
			WeeklyFTEKey weeklyFTEKey) {
		//if id not supplied in request body then set it
		if(weeklyFTE.getKey() == null) weeklyFTE.setKey(weeklyFTEKey);
		//do not save null values but set the previous values into it
		//WeeklyFTE previousWeeklyFTE = weeklyFTERepository.findOne(weeklyFTEKey);
		//if(weeklyFTE.getResourceScheduleFTE() == null) weeklyFTE.setActivity(previousWeeklyFTE.getActivity());
		
		return weeklyFTERepository.save(weeklyFTE);
	}

	@Override
	public int deleteWeeklyFTE(WeeklyFTEKey weeklyFTEKey) throws Exception {
		int initialCount = 0;
		int postDeleteCount = 0;
		try {
			initialCount = weeklyFTERepository.countByKey(weeklyFTEKey);
			weeklyFTERepository.delete(weeklyFTEKey);
			postDeleteCount = weeklyFTERepository.countByKey(weeklyFTEKey);
		} catch(EmptyResultDataAccessException erda) {
			//this is a handled exception
			log.debug("a delete request already does not exist");
		} catch(Exception e)	{
			throw new Exception("server error");
		}
		return initialCount - postDeleteCount;
	}

	@Override
	public int deleteWeeklyFTEbyOpportunityId(Long opportunityId) throws Exception {
		int initialCount = 0;
		int postDeleteCount = 0;
		try {
			initialCount = weeklyFTERepository.countFTEsByOpportunityId(opportunityId);
			weeklyFTERepository.deleteFTEsByOpportunityId(opportunityId);
			postDeleteCount = weeklyFTERepository.countFTEsByOpportunityId(opportunityId);
		} catch(EmptyResultDataAccessException erda) {
			//this is a handled exception
			log.debug("a delete request already does not exist");
		} catch(Exception e)	{
			throw new Exception("server error");
		}
		return initialCount - postDeleteCount;
	}
}
	

