package com.pointwest.workforce.planner.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.mortbay.log.Log;

public class DateUtil {

	public static Date adjustDateInclusive(LocalDate originalDate, int addedWeeks, int weeksInAMonth) {
		int offsetMonth = (addedWeeks - 1) / weeksInAMonth;
		int offsetWeek = (addedWeeks - 1) % weeksInAMonth;
		// add offset months using standardized value
		originalDate = originalDate.plusMonths(offsetMonth);
		// add offset weeks for standardized value
		originalDate = originalDate.plusWeeks(offsetWeek);
		Date adjustedDate = Date.valueOf(originalDate);
		return adjustedDate;
	}

	public static Date adjustDateExclusive(LocalDate originalDate, int addedWeeks, int weeksInAMonth) {
		int offsetMonth = addedWeeks / weeksInAMonth;
		int offsetWeek = addedWeeks % weeksInAMonth;
		// add offset months using standardized value
		originalDate = originalDate.plusMonths(offsetMonth);
		// add offset weeks for standardized value
		originalDate = originalDate.plusWeeks(offsetWeek);
		Date adjustedDate = Date.valueOf(originalDate);
		return adjustedDate;
	}

	public static Integer getWeeks(LocalDate startDate, LocalDate endDate, int weeksInAMonth) {
		int numberOfWeeks = 0;
		int numberOfMonths = 0;
		// numberOfWeeks = startDate.;
		try {
			Period period = Period.between(startDate, endDate);
			numberOfMonths = period.getMonths();
			numberOfWeeks = numberOfMonths * weeksInAMonth;
			System.out.println("start date " + startDate.toString());
			System.out.println("end date " + endDate.toString());
			System.out.println("# of months " + numberOfMonths);
			System.out.println("# of weeks " + numberOfWeeks); 
		} catch(Exception e) {
			Log.debug("no duration since no date for this entry ");
			numberOfWeeks = 0;
		}
		return numberOfWeeks;
	}

	public static LocalDate stringToDate(String date) {
		LocalDate localDate = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-d-yyyy");
			// String date = "16/08/2016";
			localDate = LocalDate.parse(date, formatter);
		} catch(Exception e) {
			Log.debug("no dates for this entry ");
			localDate = null;
		}
		return localDate;
	}

	public static boolean checkIfStringIsValidDate(String dateToValidate, String dateFormat) {
		if (dateToValidate == null) {
			return false;
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat, Locale.ENGLISH);
			@SuppressWarnings("unused")
			LocalDate dates = LocalDate.parse(dateToValidate, formatter);
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			sdf.setLenient(false);
			java.util.Date date = sdf.parse(dateToValidate);
			date.getTime();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
