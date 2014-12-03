package com.sogifty.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.sogifty.R;
import com.sogifty.model.Friend;

public class FriendDetailsActivity extends Activity{
	
	static final String FRIEND = "Friend";

	TextView nameText;
	TextView firstnameText;
	TextView remaingDate;
	TextView age;
	
	private ViewPager giftPager;
    private GiftPagerAdapter giftPagerAdapter;
    
    private Friend friend;
    
    public static Intent getIntent(Context ctxt, Friend friend){//String name, String firstname, long l, String function, int age, String avatar, int id) {
		
    	Intent newActivityIntent = new Intent(ctxt, FriendDetailsActivity.class);
		newActivityIntent.putExtra(FRIEND, friend);
	
    	return newActivityIntent;
	}
	
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_details);
		initActivity();
	}
    
    
	private void initActivity() {
		
		initFriendInformations();
	       
		nameText = (TextView) findViewById(R.id.frienddetails_tv_name);
		firstnameText =(TextView) findViewById(R.id.frienddetails_tv_firstname);
		remaingDate =(TextView) findViewById(R.id.frienddetails_tv_RemainingDate);
		age = (TextView) findViewById(R.id.frienddetails_tv_age);
		
		nameText.setText(friend.getNom());
		firstnameText.setText(friend.getPrenom());
		remaingDate.setText(remaingDate.getText() + String.format("%d", ParserJson.getRemainingDay(friend.getBirthdayDate())));
		age.setText(age.getText() + String.format("%d",ParserJson.getAge(friend.getBirthdayDate())));
		
		
		giftPager = (ViewPager) findViewById(R.id.frienddetails_vp_giftPager);
		giftPagerAdapter = new GiftPagerAdapter(getFragmentManager());
        giftPager.setAdapter(giftPagerAdapter);
        
       
         
	}
	
	
	private void initFriendInformations() {
		Intent tmp = getIntent();
		friend = (Friend) tmp.getSerializableExtra(FRIEND);
	}

	@Override
	public void onBackPressed() {
		createFriendListActivity();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.friendetails_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_modify:
			Intent intent = FriendDetailModificationActivity.getIntent(this, friend, true); 
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			break;
		default:
			break;
		}
		return true;
	} 

	protected void createFriendListActivity() {
		Intent intent = FriendListActivity.getIntent(this);
		startActivity(intent);
	}
}