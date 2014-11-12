package com.sogifty.activities;


import com.sogifty.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ConnectionActivity extends Activity{
	

	Button connectButton;
	EditText emailText;
	EditText passwordText;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_connection);
		initializeActivity();
		setButtonListener();
	}
	
	private void initializeActivity() {
		connectButton = (Button) findViewById(R.id.connection_b_connection);
		emailText = (EditText) findViewById(R.id.connection_et_email);
		passwordText = (EditText) findViewById(R.id.connection_et_password);			
	}
	
	
	private void setButtonListener() {
		connectButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				createFriendListActivty();				
			}
		});
	}
	
	protected void createFriendListActivty() {
		Intent intent = FriendListActivity.getIntent(this, emailText.getText().toString(), passwordText.getText().toString());
		startActivity(intent);
	}

}
