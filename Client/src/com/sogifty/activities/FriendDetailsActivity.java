package com.sogifty.activities;

import java.util.ArrayList;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.sogifty.R;
import com.sogifty.model.Friend;

public class FriendDetailsActivity extends Activity{
	
	static final String FIRTNAME = "firstnameFriend";
	static final String NAME = "nameFriend";
	static final String REMAININGDATE = "RemaingDate";
	static final String FONCTION = "fonction";
	static final String AGE = "age";
	static final String AVATAR = "avatar";
	static final String ID = "id";
	
	private static final String[] EXISTING_TAGS = new String[] {
        "Beau", "Fort", "Intelligent", "Gentil", "Rigolo"
    };
	
	private static final String ALREADY_IN_ERROR = "Go�t d�j� attribu�";

	TextView nameText;
	TextView firstnameText;
	TextView remaingDate;
	TextView age;
	
	private ViewPager giftPager;
    private GiftPagerAdapter giftPagerAdapter;
    private ArrayAdapter<String> autoCompleteTagsAdapter;
    private AutoCompleteTextView autoCompleteTagsTextView;
    private List<String> leftFriendTags = new ArrayList<String>();
    private List<String> rightFriendTags = new ArrayList<String>();
    private LinearLayout leftTagsLayout ;
    private LinearLayout rightTagsLayout ;
    private Friend friend;
    
    public static Intent getIntent(Context ctxt, String name, String firstname, int remainingDate, String function, int age, String avatar, int id) {
		
    	Intent newActivityIntent = new Intent(ctxt, FriendDetailsActivity.class);
		
		newActivityIntent.putExtra(FIRTNAME, firstname);
		newActivityIntent.putExtra(NAME, name);
		newActivityIntent.putExtra(REMAININGDATE, remainingDate);
		newActivityIntent.putExtra(FONCTION, function);
		newActivityIntent.putExtra(AGE, age);
		newActivityIntent.putExtra(AVATAR, avatar);
		newActivityIntent.putExtra(ID, id);
		
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
		remaingDate.setText(remaingDate.getText() + String.format("%d", friend.getRemainingDay()));
		age.setText(age.getText() + String.format("%d",friend.getAge()));
		
		
		giftPager = (ViewPager) findViewById(R.id.frienddetails_vp_giftPager);
		giftPagerAdapter = new GiftPagerAdapter(getFragmentManager());
        giftPager.setAdapter(giftPagerAdapter);
        
        leftTagsLayout= (LinearLayout) findViewById(R.id.frienddetails_l_ltags);
        rightTagsLayout= (LinearLayout) findViewById(R.id.frienddetails_l_rtags);
    	
         
        initAutoCompleteTags();
	}
	
	
	private void initFriendInformations() {
		friend =new Friend();
		Intent tmp = getIntent();
		friend.setNom(tmp.getStringExtra(NAME));
		friend.setPrenom(tmp.getStringExtra(FIRTNAME));
		friend.setRemainingDay(tmp.getIntExtra(REMAININGDATE, 0));
		friend.setFonction(tmp.getStringExtra(FONCTION));
		friend.setAge(tmp.getIntExtra(AGE, 0));
		friend.setAvatar(tmp.getStringExtra(AVATAR));
		friend.setId(tmp.getIntExtra(ID, 0));
	}


	private void initAutoCompleteTags() {
        autoCompleteTagsAdapter= new ArrayAdapter<String> (this, android.R.layout.simple_dropdown_item_1line, EXISTING_TAGS);
        autoCompleteTagsTextView = (AutoCompleteTextView) findViewById(R.id.frienddetails_actv_edittags);
        autoCompleteTagsTextView.setAdapter(autoCompleteTagsAdapter);
        
        autoCompleteTagsTextView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String tagValue = autoCompleteTagsTextView.getText().toString();
				addTag(tagValue);
				
			}
		});
	}


	protected void addTag(String tagValue) {
		
		if(leftFriendTags.contains(tagValue) || rightFriendTags.contains(tagValue)){
			displayMessage(ALREADY_IN_ERROR);
		}
		else {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			
			final LinearLayout tagLayout = new LinearLayout(this);
			lp.gravity=LinearLayout.HORIZONTAL;
			tagLayout.setLayoutParams(lp);
			
			int position =0;
			if(leftFriendTags.size()>rightFriendTags.size())
				position = 1 ;
			
			
			final TextView newTag = createAndAddTag (lp, tagValue, position);
					
			ImageButton buttonDelete = createAndAddButtonDelete(lp, position);
			
			tagLayout.addView(newTag);
			tagLayout.addView(buttonDelete);
			
			if (position == 0){
				leftTagsLayout.addView(tagLayout);
			}
			else{
				rightTagsLayout.addView(tagLayout);
			}
			
			
			buttonDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View deleteButton) {
					String tag = newTag.getText().toString();
					tagLayout.removeAllViews();
					
					if (leftFriendTags.contains(tag)){
						leftTagsLayout.removeView(tagLayout);
					}
					else{
						rightTagsLayout.removeView(tagLayout);
					}	
					deleteTag(newTag);
					
					
				}
			});
		// Clean autocompleteTextView	
		autoCompleteTagsTextView.setText("");
			
		}
	}


	protected void deleteTag(TextView newTag) {
		
		String tag = newTag.getText().toString();
		
		if (leftFriendTags.contains(tag)){
			leftFriendTags.remove(tag);
		}
		else{
			rightFriendTags.remove(tag);
		}
		
	}


	private TextView createAndAddTag(LayoutParams lp, String tagValue, int position) {
		
		TextView newTag = new TextView(this);
		newTag.setLayoutParams(lp);
		newTag.setText(tagValue);
		
		if (position == 0){
			leftFriendTags.add(tagValue);
		}
		else{
			rightFriendTags.add(tagValue);
		}
		return newTag;
	}


	private ImageButton createAndAddButtonDelete(LayoutParams lp, int position) {
		
		ImageButton buttonDelete = new ImageButton(this);
		buttonDelete.setLayoutParams(lp);
		buttonDelete.setImageResource(R.drawable.delete_button);
		return buttonDelete;
	}


	
	private void displayMessage(String message){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
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
			Intent intent = FriendDetailModificationActivity.getIntent(this, friend.getNom(), friend.getPrenom(), friend.getRemainingDay(), friend.getFonction(), friend.getAge(), friend.getAvatar(),friend.getId());
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			break;
		default:
			break;
		}
		return true;
	} 
}