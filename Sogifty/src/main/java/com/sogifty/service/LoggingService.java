package com.sogifty.service;

import com.sogifty.dao.LoggingDAO;

public class LoggingService {
	
	private LoggingDAO loggingDAO = new LoggingDAO();
	
	public String getLoggingData() {
		return loggingDAO.getLoggingData();
	}
	
	public void clearLogs() {
		LoggingDAO.clearLogs();
	}
}
