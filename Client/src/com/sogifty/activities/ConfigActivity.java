package com.sogifty.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sogifty.R;

public class ConfigActivity extends Activity {

	
	private static final String CONFIG_NOTIF = "Configuration des notifications:";

	private static final CharSequence CONFIG_NOTIF_WEEK = "  Prévenir une semaine avant";

	private static final CharSequence CONFIG_NOTIF_DAY = "  Prévenir un jour avant";

	private static final CharSequence CONFIG_NOTIF_JJ = "  Prévenir le jour même";

	public static final String NOTIF_DDAY = "dday";

	public static final String NOTIF_DAY_BEFORE = "dbefore";

	public static final String NOTIF_WEEK = "week";

	SparseBooleanArray configChecked = null;

	@Override
	public void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);

		initConfig();
		initView();
	}
	
	
	private void initConfig() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		configChecked = new SparseBooleanArray();
		configChecked.put(0, preferences.getBoolean(NOTIF_DDAY, true));
		configChecked.put(1, preferences.getBoolean(NOTIF_DAY_BEFORE, true));
		configChecked.put(2, preferences.getBoolean(NOTIF_WEEK, true));
	}


	private void initView() {
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		final SharedPreferences.Editor editor = preferences.edit();
		
		LinearLayout globalLayout = (LinearLayout) findViewById(R.id.config_l_layout);
		
		TextView tvConfigurationNotif = new TextView(this);
		tvConfigurationNotif.setText(CONFIG_NOTIF);
		globalLayout.addView(tvConfigurationNotif);
		
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(20, 20, 0, 0);

		final LinearLayout configLayout = new LinearLayout(this);
		lp.gravity=LinearLayout.HORIZONTAL;
		configLayout.setLayoutParams(lp);

		TextView tvConfigurationNotifJJ = new TextView(this);
		tvConfigurationNotifJJ.setText(CONFIG_NOTIF_JJ);
		final CheckBox ckBox = new CheckBox(this);
		ckBox.setChecked(configChecked.get(0));
		configLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ckBox.setChecked(!configChecked.get(0));
				configChecked.put(0, !configChecked.get(0));
				editor.putBoolean(NOTIF_DDAY, configChecked.get(0));
				editor.commit();
			}
		});
		
		configLayout.addView(ckBox);
		configLayout.addView(tvConfigurationNotifJJ);
		
		globalLayout.addView(configLayout);
		
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp1.setMargins(20, 20, 0, 0);

		final LinearLayout configLayout1 = new LinearLayout(this);
		lp1.gravity=LinearLayout.HORIZONTAL;
		configLayout1.setLayoutParams(lp1);

		TextView tvConfigurationNotifDay = new TextView(this);
		tvConfigurationNotifDay.setText(CONFIG_NOTIF_DAY);
		final CheckBox ckBox1 = new CheckBox(this);
		ckBox1.setChecked(configChecked.get(1));
		
		configLayout1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ckBox1.setChecked(!configChecked.get(1));
				configChecked.put(1, !configChecked.get(1));
				editor.putBoolean(NOTIF_DAY_BEFORE, configChecked.get(1));
				editor.commit();
				}
		});
		
		configLayout1.addView(ckBox1);
		configLayout1.addView(tvConfigurationNotifDay);
		
		globalLayout.addView(configLayout1);
		
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp2.setMargins(20, 20, 0, 0);

		final LinearLayout configLayout2 = new LinearLayout(this);
		lp2.gravity=LinearLayout.HORIZONTAL;
		configLayout2.setLayoutParams(lp2);

		TextView tvConfigurationNotifWeek = new TextView(this);
		tvConfigurationNotifWeek.setText(CONFIG_NOTIF_WEEK);
		final CheckBox ckBox2 = new CheckBox(this);
		ckBox2.setChecked(configChecked.get(2));
		
		configLayout2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ckBox2.setChecked(!configChecked.get(2));
				configChecked.put(2, !configChecked.get(2));
				editor.putBoolean(NOTIF_WEEK, configChecked.get(2));
				editor.commit();
			}
		});
		
		configLayout2.addView(ckBox2);
		configLayout2.addView(tvConfigurationNotifWeek);
		
		globalLayout.addView(configLayout2);
		
	}



	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.modify_menu, menu);
		return true;
	} 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_modify_ok:
			super.onBackPressed();
			break;
		default:
			break;
		}
		return true;
	}

	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	public static Intent getIntent(Context ctxt){
		
		Intent intent = new Intent(ctxt, ConfigActivity.class);
		
		return intent;
	}
}
