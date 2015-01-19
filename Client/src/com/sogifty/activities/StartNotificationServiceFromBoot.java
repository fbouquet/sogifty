package com.sogifty.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.sogifty.services.NotificationService;

public class StartNotificationServiceFromBoot extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		int minutes = 1;
	    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    Intent i = new Intent(context, NotificationService.class);
	    PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
	    am.cancel(pi);
	    if (minutes > 0) {
	        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
	            SystemClock.elapsedRealtime() + minutes*60*1000,
	            minutes*60*1000, pi);    
	    }

	}

}
