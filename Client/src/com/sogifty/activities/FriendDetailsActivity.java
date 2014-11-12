package com.sogifty.activities;

import com.sogifty.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class FriendDetailsActivity extends Activity{
	
	private static final String FIRTNAME = "firstnameFriend";
	private static final String NAME = "nameFriend";
	private static final String REMAININGDATE = "RemaingDate";
	TextView nameText;
	TextView firstnameText;
	TextView remaingDate;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_details);
		initActivity();
	}

	private void initActivity() {
		nameText = (TextView) findViewById(R.id.frienddetails_tv_name);
		firstnameText =(TextView) findViewById(R.id.frienddetails_tv_firstname);
		remaingDate =(TextView) findViewById(R.id.frienddetails_tv_RemainingDate);
	}
	


	public static Intent getIntent(Context ctxt, String name, String firstname, int remainingDate) {
		Intent newActivityIntent = new Intent(ctxt, FriendDetailsActivity.class);
		newActivityIntent.putExtra(FIRTNAME, firstname);
		newActivityIntent.putExtra(NAME, name);
		newActivityIntent.putExtra(REMAININGDATE, remainingDate);
    	return newActivityIntent;
	}
	
	
}
