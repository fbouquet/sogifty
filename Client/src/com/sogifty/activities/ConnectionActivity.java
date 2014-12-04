package com.sogifty.activities;

import com.sogifty.R;
import com.sogifty.tasks.ConnectionTask;
import com.sogifty.tasks.listeners.OnConnectionTaskListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConnectionActivity extends Activity implements OnConnectionTaskListener{
	
	private static final String EMPTY_CONNECTION_ITEMS = "Please enter email and password";
	private static final String USER_ID = "user_id";
	private static final CharSequence USER_NOT_EXISTING = "User not found";
		
	Button connectButton;
	Button subscribeButton;
	EditText emailText;
	EditText passwordText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_connection);
		initializeActivity();
		setButtonListener();
		setSubscriptionButtonListener();
	}
	
	private void initializeActivity() {
		connectButton = (Button) findViewById(R.id.connection_b_connection);
		subscribeButton = (Button) findViewById(R.id.connection_b_subscription);
		emailText = (EditText) findViewById(R.id.connection_et_email);
		passwordText = (EditText) findViewById(R.id.connection_et_password);			
	}
	
	
	private void setButtonListener() {
		connectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(emailText.getText().toString().equals("") && passwordText.getText().toString().equals("")){
					loadEmptyPopUp();
				}
				else{
					callConnectionTask();
				}
			}

			

			
		});
	}
	private void setSubscriptionButtonListener() {
		subscribeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
					createSubscriptionActivity();
			}
		});
	}
	

	protected void createFriendListActivity() {
		Intent intent = FriendListActivity.getIntent(this, emailText.getText().toString(), passwordText.getText().toString());
		startActivity(intent);
	}
	protected void createSubscriptionActivity() {
		Intent intent = SubscriptionActivity.getIntent(this);
		startActivity(intent);
	}
	private void callConnectionTask() {
		new ConnectionTask(this,this).execute(emailText.getText().toString(),passwordText.getText().toString());
	}
	protected void loadEmptyPopUp() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(EMPTY_CONNECTION_ITEMS);
		AlertDialog ad = adb.create();
		ad.show();
	}
	protected void loadNoUserPopUp() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(USER_NOT_EXISTING);
		AlertDialog ad = adb.create();
		ad.show();
	}
	public static Intent getIntent(Context ctxt){
		Intent newActivityIntent = new Intent(ctxt, ConnectionActivity.class);
		return newActivityIntent;
	}

	@Override
	public void onConnectionFailed(String message) {
		displayMessage(message);
	}
	private void displayMessage(String message){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}
	private void saveMyId(Integer resultId) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(USER_ID, resultId);
		editor.commit();
	}
	
	@Override
	public void onConnectionComplete(int userId) {
		saveMyId(userId);
		createFriendListActivity();
	}

}
