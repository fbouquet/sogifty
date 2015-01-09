package com.sogifty.activities;

import com.sogifty.R;
import com.sogifty.model.Config;
import com.sogifty.tasks.ConnectionTask;
import com.sogifty.tasks.GetConfigTask;
import com.sogifty.tasks.listeners.OnGetConfigTaskListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

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
	private void displayMessage(String message){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}

}
