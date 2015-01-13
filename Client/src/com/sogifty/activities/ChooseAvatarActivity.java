package com.sogifty.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.sogifty.R;

public class ChooseAvatarActivity extends Activity {

	public static final String RESULT = "result";
	private String drawablePicture="";
	@Override
	public void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_avatar);

		initView();
		
	}
	
	
	private void initView() {

		ImageView man = (ImageView) findViewById(R.id.chooseavatar_iv_man);
		ImageView woman = (ImageView) findViewById(R.id.chooseavatar_iv_woman);
	
		man.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				drawablePicture = "man";
				Intent returnIntent = new Intent();
				returnIntent.putExtra(RESULT,drawablePicture);
				setResult(RESULT_OK,returnIntent);
				finish();
			}
		});
		
		woman.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				drawablePicture = "woman";
				Intent returnIntent = new Intent();
				returnIntent.putExtra(RESULT,drawablePicture);
				setResult(RESULT_OK,returnIntent);
				finish();
			}
		});
		
		
	}


	public static Intent getIntent(Context ctxt){
		
		Intent intent = new Intent(ctxt, ChooseAvatarActivity.class);
		
		return intent;
	}
}
