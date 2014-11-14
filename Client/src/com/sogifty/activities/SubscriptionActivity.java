package com.sogifty.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sogifty.R;
import com.sogifty.tasks.SubscriptionTask;
import com.sogifty.tasks.listeners.OnSubscriptionTaskListener;

public class SubscriptionActivity extends Activity implements OnSubscriptionTaskListener {
	
	private static final String PASSWORDS_DO_NOT_CORRESPOND = "Passwords do not correspond";
	private static final String EMPTY_INSCRIPTION_ITEMS = "Please enter email and password";
	private static final String USER_ALREADY_IN_DB = "The email has already been used";
	private static final String USER_ID = "user_id";
	Button inscriptionButton;
	EditText emailText;
	EditText passwordText;
	EditText confirmText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		initializeActivity();
		setButtonListener();
	}
	
	private void initializeActivity() {
		inscriptionButton = (Button) findViewById(R.id.inscription_b_inscription);
		emailText = (EditText) findViewById(R.id.inscription_et_email);
		passwordText = (EditText) findViewById(R.id.inscription_et_password);		
		confirmText = (EditText) findViewById(R.id.inscription_et_confirmation);
	}
	
	private void setButtonListener(){
		inscriptionButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(emailText.getText().toString().equals("") && passwordText.getText().toString().equals("")){
					showPopUpEmpty();
				}else if(!passwordCorresponds()){
					showPopUpPasswordError();
				} else {
					addUserToDB();
					
				}
				
			}
		});
	}
	
	private boolean passwordCorresponds(){
		return (passwordText.getText().toString().equals(confirmText.getText().toString()));
	}
	private void displayMessage(String message){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}
		
	private void showPopUpPasswordError(){
		displayMessage(PASSWORDS_DO_NOT_CORRESPOND);
	}
	
	private void showPopUpEmpty(){
		displayMessage(EMPTY_INSCRIPTION_ITEMS);
	}
	
	private void showPopUpUser(){
		displayMessage(USER_ALREADY_IN_DB);
	}
	
	private void startFriendListActivity(){
		Intent intent = FriendListActivity.getIntent(this, emailText.getText().toString(), passwordText.getText().toString());
		startActivity(intent);
	}
	
	private void addUserToDB(){
		new SubscriptionTask(this, this).execute(emailText.getText().toString(),passwordText.getText().toString());
	}
	
	
	private void saveMyId(Integer resultId) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(USER_ID, resultId);
		editor.commit();
	}
	
	
	public static Intent getIntent(Context ctxt){
		Intent newActivityIntent = new Intent(ctxt, SubscriptionActivity.class);
		return newActivityIntent;
	}

	@Override
	public void onSubscriptionFailed(String message) {
		displayMessage(message);
	}

	@Override
	public void onSubscriptionComplete(int userId) {
		saveMyId(userId);
		startFriendListActivity();
	}
}
