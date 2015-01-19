package com.sogifty.services;

import java.util.Iterator;
import java.util.List;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.sogifty.R;
import com.sogifty.activities.ConfigActivity;
import com.sogifty.activities.StartActivity;
import com.sogifty.model.Friend;
import com.sogifty.model.Friends;
import com.sogifty.tasks.GetFriendListServiceTask;
import com.sogifty.tasks.listeners.OnGetFriendListServiceTaskListener;

public class NotificationService extends Service implements OnGetFriendListServiceTaskListener{


	private static final String TEXT_NOTIFICATION_WEEK = "De ? dans une semaine!";
	private static final String TEXT_NOTIFICATION_DAY = "De ? demain!";
	private static final String TEXT_NOTIFICATION_DDAY = "De ? aujourd'hui!";
	private static final CharSequence NOUVEL_ANNIVERSAIRE = "Anniversaire";
	private Friends friendsList = null;
	private static final String TAG = "Notification Service";
	private static final String PLUSIEURS_ANNIVERSAIRES = "Plusieurs Anniversaires proches";
	private static final String MORE_BIRTHDAY = "Attention ? anniversaires dans la semaine!";
	private WakeLock mWakeLock;
	
	public NotificationService(){
		super();
	}
	
	/**
	 * Simply return null, since our Service will not be communicating with
	 * any other components. It just does its work silently.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * This is where we initialize. We call this when onStart/onStartCommand is
	 * called by the system. We won't do anything with the intent here, and you
	 * probably won't, either.
	 */
	private void handleIntent(Intent intent) {
		// obtain the wake lock
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
		mWakeLock.acquire();


		// do the actual work, in a separate thread
		new PollTask().execute();
	}

	private class PollTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			
			
			new GetFriendListServiceTask(NotificationService.this, NotificationService.this).execute();
			return null;
		}

		
		@Override
		protected void onPostExecute(Void result) {
			// handle your data
			stopSelf();
		}
	}

	
	@Override
	public void onStart(Intent intent, int startId) {

		handleIntent(intent);
	}

	public void createNotif() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		List<Friend> fl = friendsList.getListFriends();
		Iterator<Friend> it = fl.iterator();
		Friend f;
		int nb_birthday = 0;
		while (it.hasNext() && (f=it.next()).getRemainingDay()<7 ) {
			nb_birthday++;
			String textNotification="";
			if(f.getRemainingDay()==6 && preferences.getBoolean(ConfigActivity.NOTIF_WEEK, true))
				textNotification = TEXT_NOTIFICATION_WEEK;
			else if (f.getRemainingDay()==1 && preferences.getBoolean(ConfigActivity.NOTIF_DAY_BEFORE, true))
				textNotification = TEXT_NOTIFICATION_DAY;
			else if (f.getRemainingDay() == 0 && preferences.getBoolean(ConfigActivity.NOTIF_DDAY, true))
				textNotification = TEXT_NOTIFICATION_DDAY;
			if (textNotification!=""){
				if(nb_birthday<=1){
					textNotification = textNotification.replace("?", f.getNom()+" "+f.getPrenom());
					NotificationCompat.Builder mBuilder =
							new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.sogifti_logo)
					.setContentTitle(NOUVEL_ANNIVERSAIRE)
					.setContentText(textNotification)
					.setAutoCancel(true);

					Intent resultIntent = new Intent(this, StartActivity.class);

					TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
					stackBuilder.addParentStack(StartActivity.class);
					stackBuilder.addNextIntent(resultIntent);
					PendingIntent resultPendingIntent =
							stackBuilder.getPendingIntent(
									0,
									PendingIntent.FLAG_UPDATE_CURRENT
									);
					mBuilder.setContentIntent(resultPendingIntent);
					NotificationManager mNotificationManager =
							(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(0, mBuilder.build());
				}
				else {
					textNotification = MORE_BIRTHDAY.replace("?", String.valueOf(nb_birthday));
					NotificationCompat.Builder mBuilder =
							new NotificationCompat.Builder(this)
					.setSmallIcon(R.drawable.sogifti_logo)
					.setContentTitle(PLUSIEURS_ANNIVERSAIRES)
					.setContentText(textNotification)
					.setAutoCancel(true);

					Intent resultIntent = new Intent(this, StartActivity.class);

					TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
					stackBuilder.addParentStack(StartActivity.class);
					stackBuilder.addNextIntent(resultIntent);
					PendingIntent resultPendingIntent =
							stackBuilder.getPendingIntent(
									0,
									PendingIntent.FLAG_UPDATE_CURRENT
									);
					mBuilder.setContentIntent(resultPendingIntent);
					NotificationManager mNotificationManager =
							(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					mNotificationManager.notify(1, mBuilder.build());
				}
					
			}
			
		}
	}

	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		handleIntent(intent);
		return START_NOT_STICKY;
	}

	
	public void onDestroy() {

		super.onDestroy();
		mWakeLock.release();
	}

	@Override
	public void onGetFriendListServiceComplete(Friends friendsListparam) {
		friendsList = friendsListparam;
		if(friendsList.getListFriends() != null && friendsList.getListFriends().size() != 0){
			createNotif();
		}
		
	}

	@Override
	public void onGetFriendListServiceFailed(String message) {
	}
}
