package com.pointwest.workforce.planner.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointwest.workforce.planner.domain.CustomError;
import com.pointwest.workforce.planner.domain.Opportunity;
import com.pointwest.workforce.planner.domain.OpportunityActivity;
import com.pointwest.workforce.planner.domain.ResourceSpecification;
import com.pointwest.workforce.planner.domain.WeeklyFTE;
import com.pointwest.workforce.planner.domain.WeeklyFTEKey;
import com.pointwest.workforce.planner.service.AccessService;
import com.pointwest.workforce.planner.service.OpportunityActivityService;
import com.pointwest.workforce.planner.service.OpportunityService;
import com.pointwest.workforce.planner.service.ResourceSpecificationService;
import com.pointwest.workforce.planner.service.WeeklyFTEService;

@RestController
public class FTEController {
	
	@Autowired
	WeeklyFTEService weeklyFTEService;
	
	@Autowired
	OpportunityService opportunityService;
	
	@Autowired
	OpportunityActivityService opportunityActivityService;
	
	@Autowired
	ResourceSpecificationService resourceSpecificationService;
	
	@Autowired
	AccessService accessService;
	
	@Value("${month.to.week.multiplier}")
	private Long WEEKSINMONTH;
	
	@Value("${granularity.week}")
	private String WEEKLY;
	
	@Value("${granularity.month}")
	private String MONTHLY;
	
	private static final Logger log = LoggerFactory.getLogger(FTEController.class);
	
	@RequestMapping(method=RequestMethod.GET, value="/ftes")
    public ResponseEntity<Object> fetchAllWeeklyFTE() {
       List<WeeklyFTE> weeklyFTEs = weeklyFTEService.fetchAllWeeklyFTEs();
       if(weeklyFTEs == null || weeklyFTEs.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No Weekly FTEs retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(weeklyFTEs, HttpStatus.OK);
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/resourcespecifications/{resourceSpecificationId}/ftes/{resourceScheduleWeekNumber}")
    public ResponseEntity<Object> fetchWeeklyFTE(@PathVariable Long resourceSpecificationId, @PathVariable Long resourceScheduleWeekNumber) {
		if( !(resourceSpecificationId instanceof Long) || !(resourceScheduleWeekNumber instanceof Long) ) {
			return new ResponseEntity<>(new CustomError("Invalid Id's"), HttpStatus.BAD_REQUEST);
		} else if(resourceSpecificationId <= 0 || resourceScheduleWeekNumber <= 0) {
			return new ResponseEntity<>(new CustomError("Invalid Id's"), HttpStatus.BAD_REQUEST);
		}
		else {
			WeeklyFTEKey key = new WeeklyFTEKey(resourceSpecificationId, resourceScheduleWeekNumber);
			WeeklyFTE weeklyFTE = weeklyFTEService.fetchWeeklyFTE(key);
			if(weeklyFTE == null) {
				return new ResponseEntity<>(new CustomError("No weekly fte entry"), HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(weeklyFTE, HttpStatus.OK);
			}
		}
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/resourcespecifications/{resourceSpecificationId}/ftes")
    public ResponseEntity<Object> fetchWeeklyFTEByResourceSpecification(@PathVariable Long resourceSpecificationId) {
		List<WeeklyFTE> weeklyFTEs = weeklyFTEService.fetchWeeklyFTE(resourceSpecificationId);
		if(weeklyFTEs == null || weeklyFTEs.isEmpty()) {
			return new ResponseEntity<>(new CustomError("No weekly FTEs retrieved"), HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(weeklyFTEs, HttpStatus.OK);
		}
    }
	
	private List<Long> getWeeksInMonth(long monthNumber, long multiplier) {
		long min = ((monthNumber - 1) * multiplier) + 1;
		long max = monthNumber * multiplier;
		
		List<Long> weekNumbers = new ArrayList<Long>();
		for(long i = min; i <= max; i ++) {
			weekNumbers.add(i);
		}
		return weekNumbers;
	}
	
	//bmab candidate for regrouping
	private void datePostUpdateProcessing(Long resourceSpecificationId) {
		//handle role start date, duration, total fte all in weeks 
		ResourceSpecification resourceSpecification = resourceSpecificationService.updateResourceSpecificationDates(resourceSpecificationId);
		//handle activity start date, duration in weeks
		OpportunityActivity opportunityActivity = opportunityActivityService.updateOpportunityActivityDates(resourceSpecificationId);
		log.debug("updated resource dates : " + resourceSpecification.getRoleStartDate() + " , " + resourceSpecification.getDurationInWeeks());
		log.debug("updated activity dates : " + opportunityActivity.getActivityStartDate() + " , " + opportunityActivity.getDurationInWeeks());
	}

	@RequestMapping(method=RequestMethod.POST, value="/resourcespecifications/{resourceSpecificationId}/ftes/{granularity}/{monthOrWeekNumber}")
    public ResponseEntity<Object> saveWeeklyFTE(@PathVariable Long resourceSpecificationId, @PathVariable String granularity, @PathVariable Long monthOrWeekNumber, 
    		@RequestBody(required=true) Double FTE) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		try {
			if(!accessService.hasPermissionToEditRsId(resourceSpecificationId, username)) {
				return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid inputs"), HttpStatus.BAD_REQUEST);
		}
		
		WeeklyFTE savedWeeklyFTE = null;
		if( !(resourceSpecificationId instanceof Long) || !(monthOrWeekNumber instanceof Long) ) {
			return new ResponseEntity<>(new CustomError("Invalid Id's"), HttpStatus.BAD_REQUEST);
		} else if(resourceSpecificationId <= 0 || monthOrWeekNumber <= 0) {
			return new ResponseEntity<>(new CustomError("Invalid Id's"), HttpStatus.BAD_REQUEST);
		} else if( !(FTE instanceof Double) ) {
			return new ResponseEntity<>(new CustomError("Invalid Body"), HttpStatus.BAD_REQUEST);
		} else if(FTE < 0) {
			return new ResponseEntity<>(new CustomError("Invalid Body"), HttpStatus.BAD_REQUEST);
		} else {
			if(granularity.equals(WEEKLY)) {
				savedWeeklyFTE = weeklyFTEService.saveWeeklyFTE(new WeeklyFTE(resourceSpecificationId, monthOrWeekNumber, FTE));
				//sprint 2
				datePostUpdateProcessing(resourceSpecificationId);
				if(savedWeeklyFTE==null) {
					return new ResponseEntity<>(new CustomError("not saved FTE in week " + monthOrWeekNumber), HttpStatus.BAD_REQUEST);
				} else {
					//return new ResponseEntity<>(savedWeeklyFTE, HttpStatus.OK);
					//sprint 2 hardening
					return new ResponseEntity<>(resourceSpecificationService.fetchResourceSpecification(resourceSpecificationId), HttpStatus.OK);
				}
			}
			else if(granularity.equals(MONTHLY)) {
				List<Long> weeks = getWeeksInMonth(monthOrWeekNumber, WEEKSINMONTH);
				for(Long week : weeks) {
					savedWeeklyFTE = weeklyFTEService.saveWeeklyFTE(new WeeklyFTE(resourceSpecificationId, week, FTE));
					//sprint 2
					//datePostUpdateProcessing(resourceSpecificationId);
					if(savedWeeklyFTE==null)  {
						return new ResponseEntity<>(new CustomError("not saved FTE in week number " + week), HttpStatus.BAD_REQUEST);
					}
				}
				//sprint 2
				datePostUpdateProcessing(resourceSpecificationId);
				//return new ResponseEntity<>(savedWeeklyFTE, HttpStatus.OK);
				//sprint 2 hardening
				return new ResponseEntity<>(resourceSpecificationService.fetchResourceSpecification(resourceSpecificationId), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new CustomError("Invalid url"), HttpStatus.BAD_REQUEST);
			}
		}
    }
	
	
	@RequestMapping(method=RequestMethod.PUT, value="/resourcespecifications/{resourceSpecificationId}/ftes/{granularity}/{monthOrWeekNumber}")
    public ResponseEntity<Object> updateWeeklyFTE(@PathVariable Long resourceSpecificationId, @PathVariable String granularity, @PathVariable Long monthOrWeekNumber, 
    		@RequestBody(required=true) Double FTE) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		try {
			if(!accessService.hasPermissionToEditRsId(resourceSpecificationId, username)) {
				return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid inputs"), HttpStatus.BAD_REQUEST);
		}
		
		WeeklyFTE savedWeeklyFTE = null;
		if( !(resourceSpecificationId instanceof Long) || !(monthOrWeekNumber instanceof Long) ) {
			return new ResponseEntity<>(new CustomError("Invalid Id's"), HttpStatus.BAD_REQUEST);
		} else if(resourceSpecificationId <= 0 || monthOrWeekNumber <= 0) {
			return new ResponseEntity<>(new CustomError("Invalid Id's"), HttpStatus.BAD_REQUEST);
		} else if( !(FTE instanceof Double) ) {
			return new ResponseEntity<>(new CustomError("Invalid Body"), HttpStatus.BAD_REQUEST);
		} else if(FTE < 0) {
			return new ResponseEntity<>(new CustomError("Invalid Body"), HttpStatus.BAD_REQUEST);
		} else {
			if(granularity.equals(WEEKLY)) {
				if(FTE == 0) {
					savedWeeklyFTE = new WeeklyFTE(resourceSpecificationId, monthOrWeekNumber, FTE);
					int deleteCount = 0;
					try {
						deleteCount = weeklyFTEService.deleteWeeklyFTE(savedWeeklyFTE.getKey());
					} catch (Exception e) {
						return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
					}
					if(deleteCount == 0) {
						savedWeeklyFTE = null; 
					}
				} else {
					savedWeeklyFTE = weeklyFTEService.saveWeeklyFTE(new WeeklyFTE(resourceSpecificationId, monthOrWeekNumber, FTE));
				}
				
				//sprint 2
				datePostUpdateProcessing(resourceSpecificationId);
				if(savedWeeklyFTE==null) {
					return new ResponseEntity<>(new CustomError("not saved FTE in week " + monthOrWeekNumber), HttpStatus.BAD_REQUEST);
				} else {
					//return new ResponseEntity<>(savedWeeklyFTE, HttpStatus.OK);
					//sprint 2 hardening
					return new ResponseEntity<>(resourceSpecificationService.fetchResourceSpecification(resourceSpecificationId), HttpStatus.OK);
				}
			}
			else if(granularity.equals(MONTHLY)) {
				List<Long> weeks = getWeeksInMonth(monthOrWeekNumber, WEEKSINMONTH);
				int deleteCount = 0;
				WeeklyFTEKey key = null;
				for(Long week : weeks) {
					
					if(FTE == 0) {
						//savedWeeklyFTE = new WeeklyFTE(resourceSpecificationId, week, FTE);
						key = new WeeklyFTEKey(resourceSpecificationId, week);
						
						try {
							deleteCount = deleteCount + weeklyFTEService.deleteWeeklyFTE(key);
							//deleteCount = weeklyFTEService.deleteWeeklyFTE(key);
							log.debug("KEY FTE : " + key.getResourceScheduleWeekNumber() + " res id " + key.getResourceSpecificationId());
						} catch (Exception e) {
							return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
						}
						/*if(deleteCount == 0) {
							return new ResponseEntity<>(new CustomError("not deleted FTE after trying delete at week " + week), HttpStatus.BAD_REQUEST);
						}*/
					} else {
						savedWeeklyFTE = weeklyFTEService.saveWeeklyFTE(new WeeklyFTE(resourceSpecificationId, week, FTE));
						if(savedWeeklyFTE==null)  {
							return new ResponseEntity<>(new CustomError("not saved FTE in week number " + week), HttpStatus.BAD_REQUEST);
						}
					}
				}
				//sprint 2
				datePostUpdateProcessing(resourceSpecificationId);
				if(deleteCount > 0) {
					//return new ResponseEntity<>(deleteCount, HttpStatus.OK);
					//sprint 2 hardening
					log.debug("deleted fte count : " + deleteCount );
					return new ResponseEntity<>(resourceSpecificationService.fetchResourceSpecification(resourceSpecificationId), HttpStatus.OK);
				}
				//return new ResponseEntity<>(savedWeeklyFTE, HttpStatus.OK);
				//sprint 2 hardening
				return new ResponseEntity<>(resourceSpecificationService.fetchResourceSpecification(resourceSpecificationId), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new CustomError("Invalid url"), HttpStatus.BAD_REQUEST);
			}
		}
    }
	
	@RequestMapping(method=RequestMethod.DELETE, value= "/resourcespecifications/{resourceSpecificationId}/ftes/{granularity}/{monthOrWeekNumber}")
    public ResponseEntity<Object> deleteWeeklyFTE(@PathVariable Long resourceSpecificationId, @PathVariable String granularity, @PathVariable Long monthOrWeekNumber) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		try {
			if(!accessService.hasPermissionToEditRsId(resourceSpecificationId, username)) {
				return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid inputs"), HttpStatus.BAD_REQUEST);
		}
		
		int deleteCount = 0;
		try {
			if( !(resourceSpecificationId instanceof Long) || !(monthOrWeekNumber instanceof Long) ) {
				return new ResponseEntity<>(new CustomError("Invalid Id's"), HttpStatus.BAD_REQUEST);
			} else if(resourceSpecificationId <= 0 || monthOrWeekNumber <= 0) {
				return new ResponseEntity<>(new CustomError("Invalid Id's"), HttpStatus.BAD_REQUEST);
			} else {
				if(granularity.equals(WEEKLY)) {
					WeeklyFTEKey key = new WeeklyFTEKey(resourceSpecificationId, monthOrWeekNumber);
					deleteCount = weeklyFTEService.deleteWeeklyFTE(key);
					//sprint 2
					datePostUpdateProcessing(resourceSpecificationId);
				}
				else if(granularity.equals(MONTHLY)) {
					List<Long> weeks = getWeeksInMonth(monthOrWeekNumber, WEEKSINMONTH);
					WeeklyFTEKey key;
					for(Long week : weeks) {
						key = new WeeklyFTEKey(resourceSpecificationId, week);
						deleteCount = deleteCount + weeklyFTEService.deleteWeeklyFTE(key);
					}
					//sprint 2
					datePostUpdateProcessing(resourceSpecificationId);
				} else {
					return new ResponseEntity<>(new CustomError("Invalid url"), HttpStatus.BAD_REQUEST);
				}
			}
			
			if(deleteCount == 1 || deleteCount == WEEKSINMONTH.intValue()) {
				//return new ResponseEntity<>(deleteCount, HttpStatus.OK);
				//sprint 2 hardening
				log.debug("delete success w/ count : " + deleteCount);
				return new ResponseEntity<>(resourceSpecificationService.fetchResourceSpecification(resourceSpecificationId), HttpStatus.OK);
			} else {
				log.debug("deleted ftes : " + deleteCount);
				return new ResponseEntity<>(resourceSpecificationService.fetchResourceSpecification(resourceSpecificationId), HttpStatus.OK);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@RequestMapping(method=RequestMethod.DELETE, value= "/opportunities/{opportunityId}/ftes")
    public ResponseEntity<Object> deleteWeeklyFTE(@PathVariable Long opportunityId) {
		
		//2nd level checker for editing permission
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		try {
			if(!accessService.hasPermissionToEdit(opportunityId, username)) {
				return new ResponseEntity<>(new CustomError("Not allowed to edit this opportunity"), HttpStatus.FORBIDDEN);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(new CustomError("Invalid inputs"), HttpStatus.BAD_REQUEST);
		}
		
		int deleteCount = 0;
		try {
			deleteCount = weeklyFTEService.deleteWeeklyFTEbyOpportunityId(opportunityId);
		} catch(Exception e) {
			Opportunity opportunity = opportunityService.fetchOpportunity(opportunityId);
			if(opportunity == null) {
				return new ResponseEntity<>(new CustomError("Opportunity not found"), HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(new CustomError(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		if(deleteCount > 0) {
			return new ResponseEntity<>(deleteCount, HttpStatus.OK);
		} else {
			//nothing deleted
			log.debug("no existing ftes to delete");
			return new ResponseEntity<>(deleteCount, HttpStatus.OK);
		}
	}
	
	
	
}
