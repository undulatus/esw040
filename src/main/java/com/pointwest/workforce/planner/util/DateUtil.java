package com.pointwest.workforce.planner.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

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
	
	public static Integer getWeeks(LocalDate startDate, LocalDate endDate, int weeksInAMonth) {
		int numberOfWeeks = 0;
		int numberOfMonths = 0;
		//numberOfWeeks = startDate.;
		Period period = Period.between(startDate, endDate);
		numberOfMonths = period.getMonths();
		numberOfWeeks = numberOfMonths * weeksInAMonth;
		System.out.println("start date " + startDate.toString());
		System.out.println("end date " + endDate.toString());
		System.out.println("# of months " + numberOfMonths);
		System.out.println("# of weeks " + numberOfWeeks);
		return numberOfWeeks;
	}
	
	public static LocalDate stringToDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-d-yyyy");
		//String date = "16/08/2016";
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}
}
