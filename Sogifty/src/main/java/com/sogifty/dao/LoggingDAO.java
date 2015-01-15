package com.sogifty.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LoggingDAO {
	
	private static String loggingData = new String();
	
	private final static String HTML_HEADER = "<!doctype html><html lang=\"fr\"><head><meta charset=\"utf-8\"><title>Sogifty server logging</title></head><body>";
	private final static String HTML_FOOTER = "</body></html>";
	private final static String NEW_LINE	= "<br />";
	
	private static Calendar lastDate = null;
	
	public String getLoggingData() {
		return HTML_HEADER + loggingData + HTML_FOOTER;
	}
	
	public static void addLoggingData(String data) {
		
		// Print the date if we did not before or if this is a new day
		if(lastDate == null) {
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
	
	private static String getFormatedTime() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		
		// For the seconds before 10, print a 0 before (02 instead of 2)
		String seconds = String.valueOf(cal.get(Calendar.SECOND));
		if(seconds.length() == 1) {
			seconds = "0" + seconds;
		}
		
		return "[" + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + seconds + "]";
	}
	
	private static String getDate(Calendar cal) {
		return "<strong>" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.YEAR) + "</strong>" + NEW_LINE;
	}
}
