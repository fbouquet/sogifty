package com.sogifty.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class InitServer implements ServletContextListener {

	private static final int INIT_DELAY		= 0;
	private static final int PERIODIC_GAP	= 1;
	private static final int NB_THREAD		= 2;
	
	private volatile ScheduledExecutorService executor  = Executors.newScheduledThreadPool(NB_THREAD);

	private static final Logger logger = Logger.getLogger(InitServer.class);

	public void contextInitialized(ServletContextEvent sce) {
		this.launchGiftService();
	}

	public void contextDestroyed(ServletContextEvent sce) {
	    final ScheduledExecutorService executor = this.executor;

	    if (executor != null) {
	        executor.shutdown();
	        this.executor = null;
	    }
	}
	
	private void launchGiftService() {
		logger.info("Beginning updating the gifts.");
		this.executor.scheduleAtFixedRate(new PeriodicGiftService(), INIT_DELAY, PERIODIC_GAP, TimeUnit.DAYS);
	}
}
