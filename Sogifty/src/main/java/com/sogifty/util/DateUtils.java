package com.sogifty.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class DateUtils {
	public static int getDateDiff(Date date1, Date date2) {
		return Days.daysBetween(new DateTime(date1), new DateTime(date2)).getDays();
	}
}
