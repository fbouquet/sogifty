package com.sogifty.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LoggingDAO {
	
	private static String loggingData = new String();
	
	private final static String HTML_HEADER = "<!doctype html><html lang=\"fr\"><head><meta charset=\"utf-8\"><title>Sogifty server logging</title></head><body>";
	private final static String HTML_FOOTER = "</body></html>";
	private final static String NEW_LINE	= "<br />";

	private static final Integer SAFETY_MARGIN = 200;
	private final static Integer LIMIT_STRING_LENGTH = Integer.MAX_VALUE - SAFETY_MARGIN;
	
	private static Calendar lastDate = null;
	
	public String getLoggingData() {
		return HTML_HEADER + loggingData + HTML_FOOTER;
	}
	
	public static void addLoggingData(String data) {
		
		// If the String if too long, clear the data
		if(loggingData.length() >= LIMIT_STRING_LENGTH) {
			LoggingDAO.clearLogs();
		}
		
		// Print the date if we did not before or if this is a new day
		if(lastDate == null || loggingData == null || loggingData.isEmpty()) {
			lastDate = new GregorianCalendar();
			lastDate.setTime(new Date());
			loggingData += LoggingDAO.getDate(lastDate);
		} else {
			Calendar actualDate = new GregorianCalendar();
			actualDate.setTime(new Date());
			if(LoggingDAO.getDate(lastDate).compareTo(LoggingDAO.getDate(actualDate)) != 0) {
				loggingData += LoggingDAO.getDate(actualDate);
				lastDate = actualDate;
			}
		}
		
		loggingData += LoggingDAO.getFormatedTime() + " " + data + NEW_LINE;
	}
	
	public static void clearLogs() {
		loggingData = new String();
	}
	
	private static String getFormatedTime() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		
		return "[" +	printableTimeElement(String.valueOf(cal.get(Calendar.HOUR_OF_DAY))) + ":" + 
						printableTimeElement(String.valueOf(cal.get(Calendar.MINUTE))) + ":" + 
						printableTimeElement(String.valueOf(cal.get(Calendar.SECOND))) + "]";
	}
	
	private static String getDate(Calendar cal) {
		return "<strong>" + printableTimeElement(String.valueOf(cal.get(Calendar.DAY_OF_MONTH))) + "/" +
							printableTimeElement(String.valueOf(cal.get(Calendar.MONTH) + 1)) + "/" + 
							printableTimeElement(String.valueOf(cal.get(Calendar.YEAR))) + "</strong>" + NEW_LINE;
	}
	
	/*
	 * For time elements before 10, print a 0 before (02 instead of 2) 
	 */
	private static String printableTimeElement(String e) {
		return e.length() == 1 ? e = "0" + e : e;
	}
}
