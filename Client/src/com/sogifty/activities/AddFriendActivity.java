package com.sogifty.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.sogifty.R;
import com.sogifty.tasks.AddFriendTask;
import com.sogifty.tasks.listeners.OnAddFriendTaskListener;

public class AddFriendActivity extends Activity implements OnAddFriendTaskListener{
	private static final String EMPTY_CONNECTION_ITEMS = "Please enter name and birthdate";
	
	Button addButton;
	EditText nameText;
	EditText birthdateText;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_friend);
		initializeActivity();
		setButtonListener();
	}
	
	private void initializeActivity() {
		addButton = (Button) findViewById(R.id.add_friend_b_add);
		nameText = (EditText) findViewById(R.id.add_friend_et_name);
		birthdateText = (EditText) findViewById(R.id.add_friend_et_birthdate);			
	}
	
	
	private void setButtonListener() {
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(nameText.getText().toString().equals("") && birthdateText.getText().toString().equals("")){
					loadEmptyPopUp();
				}
				else{
					callConnectionTask();
				}
			}
		
		});
	}
	protected void createFriendListActivity() {
		Intent intent = FriendListActivity.getIntent(this, nameText.getText().toString(), birthdateText.getText().toString());
		startActivity(intent);
	}
	private void callConnectionTask() {
		new AddFriendTask(this,this).execute(nameText.getText().toString(),birthdateText.getText().toString());
	}
	protected void loadEmptyPopUp() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(EMPTY_CONNECTION_ITEMS);
		AlertDialog ad = adb.create();
		ad.show();
	}

	@Override
	public void onAddFriendComplete() {
		createFriendListActivity();
	}

	@Override
	public void onAddFriendFailed(String errorMessage) {
		displayMessage(errorMessage);
	}
	private void displayMessage(String message){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}

	public static Intent getIntent(Context ctxt){
		Intent newActivityIntent = new Intent(ctxt, AddFriendActivity.class);
		return newActivityIntent;
	}

}
