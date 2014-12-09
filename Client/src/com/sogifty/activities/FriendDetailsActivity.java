package com.sogifty.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.sogifty.model.Gift;
import com.sogifty.tasks.GetGiftsTask;
import com.sogifty.tasks.listeners.OnGetGiftsTaskListener;

public class FriendDetailsActivity extends Activity implements OnGetGiftsTaskListener{
	
	static final String FRIEND = "Friend";

	TextView nameText;
	TextView firstnameText;
	TextView remaingDate;
	TextView age;
	TextView tagsView;
	
	private ViewPager giftPager;
    private GiftPagerAdapter giftPagerAdapter;
    private List<Gift> gifts;
    
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
		new GetGiftsTask(this, this);
		initActivity();
	}
    
    
	private void initActivity() {
		
		initFriendInformations();
	       
		nameText = (TextView) findViewById(R.id.frienddetails_tv_name);
		firstnameText =(TextView) findViewById(R.id.frienddetails_tv_firstname);
		remaingDate =(TextView) findViewById(R.id.frienddetails_tv_RemainingDate);
		age = (TextView) findViewById(R.id.frienddetails_tv_age);
		tagsView = (TextView) findViewById(R.id.frienddetails_all_tags);
		
		nameText.setText(friend.getNom());
		firstnameText.setText(friend.getPrenom());
		remaingDate.setText(remaingDate.getText() + String.format("%d", ParserJson.getRemainingDay(friend.getBirthdayDate())));
		age.setText(age.getText() + String.format("%d",ParserJson.getAge(friend.getBirthdayDate())));
		tagsView.setText(friend.getTagsinPointString());
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


	@Override
	public void onGetGiftsComplete(List<Gift> giftList) {
		gifts = giftList;
	}


	@Override
	public void onGetGiftsFailed(String message) {
		displayMessage(message);
	}
	
	private void displayMessage(String message) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}
}