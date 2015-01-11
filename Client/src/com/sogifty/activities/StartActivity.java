package com.sogifty.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.sogifty.R;
import com.sogifty.model.Config;
import com.sogifty.services.NotificationService;
import com.sogifty.tasks.GetConfigTask;
import com.sogifty.tasks.listeners.OnGetConfigTaskListener;

public class StartActivity extends Activity implements OnGetConfigTaskListener{

	private static final String USER_ID = "user_id";
	private static final String NB_GIFTS = "nb_gifts";
	private static final String TAG_LIST = "tag_list";
	private static final String NB_TAGS = "nb_tags";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new GetConfigTask(this,this).execute();

	}


	protected void startFriendListActivty() {
		Intent intent = FriendListActivity.getIntent(this);
		startActivity(intent);
	}
	protected void startConnectionActivty() {
		Intent intent = ConnectionActivity.getIntent(this);
		startActivity(intent);
	}
	private int loadUserId(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		return preferences.getInt(USER_ID, getResources().getInteger(R.integer.user_id_default));
	}
	private void saveMyConfig(Config config){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(NB_GIFTS, config.getNbGifts());
		editor.putInt(NB_TAGS, config.getNbTags());
		editor.putStringSet(TAG_LIST, config.getTags());
		editor.commit();
	}


	@Override
	public void onGetConfigFailed(String message) {
		displayMessage(message);
	}


	@Override
	public void onGetConfigComplete(Config config) {
		saveMyConfig(config);

		if(loadUserId() == getResources().getInteger(R.integer.user_id_default) 
				|| loadUserId() == getResources().getInteger(R.integer.user_http_error)){
			startConnectionActivty();
		}
		else{
			startFriendListActivty();
		}
		finish();
	}

	public void onResume() {
		super.onResume();

		System.out.println("On resume start activity");
		//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int minutes = 6*60;//prefs.getInt("interval");
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		Intent i = new Intent(this, NotificationService.class);
		
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		
		if (pi==null)
			System.out.println("Probleme lancement service");
		am.cancel(pi);
		// by my own convention, minutes <= 0 means notifications are disabled
		if (minutes > 0) {
			am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
					SystemClock.elapsedRealtime() + minutes*60*1000,
					minutes*60*1000, pi);
			System.out.println("Set alarm clock");
			
		}
	}
		
	
	private void displayMessage(String message){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}

}
