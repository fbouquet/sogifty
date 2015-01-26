package com.sogifty.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LoggingDAO {

	private static final String BOOTSTRAPCDN_URL = "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css";
	private static final String PAGE_TITLE = "SoGifty server logs";

	private static final String HTML_FOOTER = "\t\t</div>\n" + "\t</body>\n</html>";

	private static final String LOG_TABLE_PREFIX = "\t\t\t<table class=\"table table-striped table-hover table-condensed\">\n" +
			"\t\t\t\t<tr>\n" +
			"\t\t\t\t\t<th>Time</th>\n" +
			"\t\t\t\t\t<th>Log</th>\n" +
			"\t\t\t\t</tr>\n";

	private static final String LOG_TABLE_POSTFIX = "\t\t\t</table>\n";
	
	private static final String NO_LOGS_MESSAGE = "\t\t\t<h2 class=\"text-center\">Oops! There are no logs yet.</h2>\n"; // Displayed if no logs are available

	private static final Integer SAFETY_MARGIN = 512;
	private static final Integer LOGGING_STRING_LIMIT_LENGTH = Integer.MAX_VALUE - SAFETY_MARGIN;

	private static int refreshInterval = 5;
	private static String HTML_HEADER_BEGIN = "<!doctype html>\n" +
			"<html lang=\"fr\">\n" +
			"\t<head>\n" +
			"\t\t<meta http-equiv=\"refresh\" content=\"";
	private static String HTML_HEADER_END = "\">\n" +
			"\t\t<meta charset=\"utf-8\">\n" +
			"\t\t<link href=\"" + BOOTSTRAPCDN_URL + "\" rel=\"stylesheet\">\n" +
			"\t\t<title>" + PAGE_TITLE + "</title>\n" +
			"\t</head>\n" +
			"\t<body>\n" +
			"\t\t<div class=\"container\">\n" +
			"\t\t\t<div class=\"jumbotron\">\n" +
			"\t\t\t\t<h1 class=\"text-center\">" + PAGE_TITLE + "</h1>\n" +
			"\t\t\t</div>\n";
	
	private static String loggingData = new String(); // Holds the variable content of the log page's HTML code
	private static Calendar lastDate = null;

	public static void clearLogs() {
		loggingData = new String();
	}
	
	public static void changeRefreshFrequency(int frequencyInSeconds) {
		refreshInterval = frequencyInSeconds;
	}

	public String getLoggingData() {
		// If there are no logs, return an HTML page saying it
		if (loggingData == null || loggingData.isEmpty()) {
			return buildHTMLHeader() + NO_LOGS_MESSAGE + HTML_FOOTER;
		}
		// Else return an HTML page displaying the logs
		return buildHTMLHeader() + loggingData + HTML_FOOTER;
	}

	public static void addLoggingData(String data) {
		// If the String if too long, clear the data
		if (loggingData.length() >= LOGGING_STRING_LIMIT_LENGTH) {
			LoggingDAO.clearLogs();
		}

		// Print the date if we did not before
		if (lastDate == null || loggingData == null || loggingData.isEmpty()) {
			lastDate = new GregorianCalendar();
			lastDate.setTime(new Date());
			loggingData = LoggingDAO.getDate(lastDate) + LOG_TABLE_PREFIX + getLogTableRow(data) + LOG_TABLE_POSTFIX;
		} else {
			Calendar actualDate = new GregorianCalendar();
			actualDate.setTime(new Date());
			// Or if this is a new day
			if (LoggingDAO.getDate(lastDate).compareTo(LoggingDAO.getDate(actualDate)) != 0) {
				loggingData = LoggingDAO.getDate(actualDate) + LOG_TABLE_PREFIX + getLogTableRow(data) + LOG_TABLE_POSTFIX + loggingData;
				lastDate = actualDate;
			} else { // If the date has already been added this day
				// Insert the line after the first header row, so that the logs are in reverse chronological order
				int afterTableHeaderRow = loggingData.indexOf(LOG_TABLE_PREFIX) + LOG_TABLE_PREFIX.length();
				loggingData = loggingData.substring(0, afterTableHeaderRow) + getLogTableRow(data) + loggingData.substring(afterTableHeaderRow);
			}
		}
	}

	private String buildHTMLHeader() {
		return HTML_HEADER_BEGIN + Integer.toString(refreshInterval) + HTML_HEADER_END;
	}
	
	private static String getLogTableRow(String data) {
		return new StringBuilder("\t\t\t\t<tr>\n")
		.append("\t\t\t\t\t<td>").append(getFormatedTime()).append("</td>\n")
		.append("\t\t\t\t\t<td>").append(data).append("</td>\n")
		.append("\t\t\t\t</tr>\n")
		.toString();
	}

	private static String getFormatedTime() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		return new StringBuilder(printableTimeElement(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)))).append(":")
				.append(printableTimeElement(String.valueOf(cal.get(Calendar.MINUTE)))).append(":")
				.append(printableTimeElement(String.valueOf(cal.get(Calendar.SECOND))))
				.toString();
	}

	private static String getDate(Calendar cal) {
		return new StringBuilder("\t\t\t<h2 class=\"text-center\">")
		.append(printableTimeElement(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))).append("/")
		.append(printableTimeElement(String.valueOf(cal.get(Calendar.MONTH) + 1))).append("/")
		.append(printableTimeElement(String.valueOf(cal.get(Calendar.YEAR))))
		.append("</h2>\n")
		.toString();
	}

	/***
	 * For time elements before 10, print a '0' before (e.g.: 02 instead of 2)
	 * @param e : the time element
	 * @return the printable time element
	 */
	private static String printableTimeElement(String e) {
		return e.length() == 1 ? e = "0" + e : e;
	}
}
