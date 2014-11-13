package com.sogifty.activities;

import com.sogifty.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class StartActivity extends Activity{
	
	private static final String USER_ID = "user_id";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(loadUserId() == getResources().getInteger(R.integer.user_id_default) 
				|| loadUserId() == getResources().getInteger(R.integer.user_id_error)){
			startConnectionActivty();
		}
		else{
			startFriendListActivty();
		}
		
	
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

}
