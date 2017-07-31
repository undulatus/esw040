package com.pointwest.workforce.planner.util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtil {
	
	public static Date adjustDateInclusive(LocalDate originalDate, int addedWeeks, int weeksInAMonth) {
		int offsetMonth = (addedWeeks - 1) / weeksInAMonth;
		int offsetWeek = (addedWeeks - 1) % weeksInAMonth;
		//add offset months using standardized value
		originalDate = originalDate.plusMonths(offsetMonth);
		//add offset weeks for standardized value
		originalDate = originalDate.plusWeeks(offsetWeek);
		Date adjustedDate = Date.valueOf(originalDate);
		return adjustedDate;
	}

	public static Date adjustDateExclusive(LocalDate originalDate, int addedWeeks, int weeksInAMonth) {
		int offsetMonth = addedWeeks / weeksInAMonth;
		int offsetWeek = addedWeeks % weeksInAMonth;
		//add offset months using standardized value
		originalDate = originalDate.plusMonths(offsetMonth);
		//add offset weeks for standardized value
		originalDate = originalDate.plusWeeks(offsetWeek);
		Date adjustedDate = Date.valueOf(originalDate);
		return adjustedDate;
	}
}
