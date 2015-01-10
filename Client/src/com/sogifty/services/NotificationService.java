package com.sogifty.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.sogifty.R;
import com.sogifty.activities.StartActivity;
import com.sogifty.model.Friend;
import com.sogifty.model.Friends;
import com.sogifty.tasks.GetFriendListServiceTask;
import com.sogifty.tasks.GetFriendListTask;
import com.sogifty.tasks.listeners.OnGetFriendListServiceTaskListener;
import com.sogifty.tasks.listeners.OnGetFriendListTaskListener;

public class NotificationService extends Service implements OnGetFriendListServiceTaskListener{


	private static final String TEXT_NOTIFICATION_WEEK = "De ? dans une semaine!";
	private static final String TEXT_NOTIFICATION_DAY = "De ? demain!";
	private static final String TEXT_NOTIFICATION_DDAY = "De ? aujourd'hui!";
	private static final CharSequence NOUVEL_ANNIVERSAIRE = "Anniversaire";
	private Friends friendsList = null;
	private static final String TAG = "Notification Service";
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

//		// check the global background data setting
//		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//		if (!cm.getBackgroundDataSetting()) {
//			stopSelf();
//			return;
//		}

		// do the actual work, in a separate thread
		new PollTask().execute();
	}

	private class PollTask extends AsyncTask<Void, Void, Void> {
		/**
		 * This is where YOU do YOUR work. There's nothing for me to write here
		 * you have to fill this in. Make your HTTP request(s) or whatever it is
		 * you have to do to get your updates in here, because this is run in a
		 * separate thread
		 * @return 
		 */
		@Override
		protected Void doInBackground(Void... params) {
			
			// do stuff
			new GetFriendListServiceTask(NotificationService.this, NotificationService.this).execute();
			return null;
		}

		/**
		 * In here you should interpret whatever you fetched in doInBackground
		 * and push any notifications you need to the status bar, using the
		 * NotificationManager. I will not cover this here, go check the docs on
		 * NotificationManager.
		 *
		 * What you HAVE to do is call stopSelf() after you've pushed your
		 * notification(s). This will:
		 * 1) Kill the service so it doesn't waste precious resources
		 * 2) Call onDestroy() which will release the wake lock, so the device
		 *    can go to sleep again and save precious battery.
		 */
		@Override
		protected void onPostExecute(Void result) {
			// handle your data
			stopSelf();
		}
	}

	/**
	 * This is deprecated, but you have to implement it if you're planning on
	 * supporting devices with an API level lower than 5 (Android 2.0).
	 */
	@Override
	public void onStart(Intent intent, int startId) {

		handleIntent(intent);
	}

	public void createNotif() {

		Friend f = friendsList.getListFriends().get(0);
		if (f!=null){
			String textNotification="";
			if(f.getRemainingDay()==6)
				textNotification = TEXT_NOTIFICATION_WEEK;
			else if (f.getRemainingDay()==1)
				textNotification = TEXT_NOTIFICATION_DAY;
			else if (f.getRemainingDay() == 0)
				textNotification = TEXT_NOTIFICATION_DDAY;
			if (textNotification!=""){
				
				textNotification = textNotification.replace("?", f.getNom()+" "+f.getPrenom());
				NotificationCompat.Builder mBuilder =
						new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.sogifti_logo)
				.setContentTitle(NOUVEL_ANNIVERSAIRE)
				.setContentText(textNotification)
				.setAutoCancel(true);
				
				// Creates an explicit intent for an Activity in your app
				Intent resultIntent = new Intent(this, StartActivity.class);

				// The stack builder object will contain an artificial back stack for the
				// started Activity.
				// This ensures that navigating backward from the Activity leads out of
				// your application to the Home screen.
				TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
				// Adds the back stack for the Intent (but not the Intent itself)
				stackBuilder.addParentStack(StartActivity.class);
				// Adds the Intent that starts the Activity to the top of the stack
				stackBuilder.addNextIntent(resultIntent);
				PendingIntent resultPendingIntent =
						stackBuilder.getPendingIntent(
								0,
								PendingIntent.FLAG_UPDATE_CURRENT
								);
				mBuilder.setContentIntent(resultPendingIntent);
				NotificationManager mNotificationManager =
						(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				// mId allows you to update the notification later on.
				mNotificationManager.notify(0, mBuilder.build());
			}
		}
	}

	/**
	 * This is called on 2.0+ (API level 5 or higher). Returning
	 * START_NOT_STICKY tells the system to not restart the service if it is
	 * killed because of poor resource (memory/cpu) conditions.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		handleIntent(intent);
		return START_NOT_STICKY;
	}

	/**
	 * In onDestroy() we release our wake lock. This ensures that whenever the
	 * Service stops (killed for resources, stopSelf() called, etc.), the wake
	 * lock will be released.
	 */
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
