package com.sogifty.activities;


import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.sogifty.R;
import com.sogifty.model.Friend;
import com.sogifty.tasks.AddOrModifyFriendTask;
import com.sogifty.tasks.listeners.OnAddOrModifyFriendTaskListener;

public class FriendDetailModificationActivity extends Activity implements OnAddOrModifyFriendTaskListener{
	private static final CharSequence APPLICATION_NAME = "Sogifty";
	private static final CharSequence FRIEND_DETAIL = "Détails ami";
	private static final String EMPTY_CONNECTION_ITEMS = "Please enter at least name and birthdate";

	private static ArrayAdapter<String> autoCompleteExistingsTagsAdapter = null;

	private static final String IS_MODIFY = "is modify";	
			
	private static final String ALREADY_IN_ERROR = "Goût déjà attribué";
	private Friend friend = null;
	private EditText etName = null ;
	private EditText etFirstname = null ;
	private EditText etFunction = null ;
	private EditText etBirthdaydate = null ;
	private Button bAddTag;
	private AutoCompleteTextView autoCompleteTagsTextView;
	private List<String> leftFriendTags = new ArrayList<String>();
	private List<String> rightFriendTags = new ArrayList<String>();
	private LinearLayout leftTagsLayout ;
	private LinearLayout rightTagsLayout ;
	private boolean isModify = true;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_details_modification);

		initView();

	}

	private void initFriendAndMode() {
		friend =new Friend();
		Intent tmp = getIntent();
		friend.setNom(tmp.getStringExtra(FriendDetailsActivity.NAME));
		friend.setPrenom(tmp.getStringExtra(FriendDetailsActivity.FIRTNAME));
		friend.setRemainingDay(tmp.getIntExtra(FriendDetailsActivity.REMAININGDATE, 0));
		friend.setFonction(tmp.getStringExtra(FriendDetailsActivity.FONCTION));
		friend.setAge(tmp.getIntExtra(FriendDetailsActivity.AGE, 0));
		friend.setAvatar(tmp.getStringExtra(FriendDetailsActivity.AVATAR));
		friend.setId(tmp.getIntExtra(FriendDetailsActivity.ID, 0));
		
		isModify=tmp.getBooleanExtra(IS_MODIFY, true);
	}

	public static Intent getIntent(Context ctxt, String name, String firstname, long l, String function, int age, String avatar, int id, boolean ismodify) {

		Intent newActivityIntent = new Intent(ctxt, FriendDetailModificationActivity.class);

		newActivityIntent.putExtra(FriendDetailsActivity.FIRTNAME, firstname);
		newActivityIntent.putExtra(FriendDetailsActivity.NAME, name);
		newActivityIntent.putExtra(FriendDetailsActivity.REMAININGDATE, l);
		newActivityIntent.putExtra(FriendDetailsActivity.FONCTION, function);
		newActivityIntent.putExtra(FriendDetailsActivity.AGE, age);
		newActivityIntent.putExtra(FriendDetailsActivity.AVATAR, avatar);
		newActivityIntent.putExtra(FriendDetailsActivity.ID, id);

		newActivityIntent.putExtra(IS_MODIFY, ismodify);

		return newActivityIntent;
	}

	private void initView() {

		initFriendAndMode();
		initActionBar();

		etName = (EditText) findViewById(R.id.modify_et_name);
		etName.setText(friend.getNom());
		etFirstname = (EditText) findViewById(R.id.modify_et_firstname);
		etFirstname.setText(friend.getPrenom());
		etFunction = (EditText) findViewById(R.id.modify_et_function);
		etFunction.setText(friend.getFonction());
		etBirthdaydate = (EditText) findViewById(R.id.modify_et_birthdaydate);
//		etBirthdaydate.setText(friend.());
		ImageView ivAvatar = (ImageView) findViewById(R.id.modify_iv_avatar);

		initButtonAddTag();
		
		leftTagsLayout= (LinearLayout) findViewById(R.id.modify_l_ltags);
		rightTagsLayout= (LinearLayout) findViewById(R.id.modify_l_rtags);
	    	
		initAutoCompleteTags();
		//UrlImageViewHelper.setUrlDrawable(iv, user.getAvatar());
		//		 iv.setImageBitmap(AvatarGenerator.generate(friend.getNom(), "M", this));
	}

	private void initButtonAddTag() {
		
		bAddTag = (Button) findViewById(R.id.modify_b_addtag);
		
		bAddTag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String tagValue = autoCompleteTagsTextView.getText().toString();
				addTag(tagValue);	
			}
		});
		
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(FRIEND_DETAIL);
		actionBar.setTitle(APPLICATION_NAME);

	}


	private void initAutoCompleteTags() {
		if (autoCompleteExistingsTagsAdapter==null)
			autoCompleteExistingsTagsAdapter= new ArrayAdapter<String> (this, android.R.layout.simple_dropdown_item_1line);
		autoCompleteTagsTextView = (AutoCompleteTextView) findViewById(R.id.modify_actv_edittags);
		autoCompleteTagsTextView.setAdapter(autoCompleteExistingsTagsAdapter);

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
		if(autoCompleteExistingsTagsAdapter.getPosition(tagValue)<0)
			autoCompleteExistingsTagsAdapter.add(tagValue);
		return newTag;
	}


	private ImageButton createAndAddButtonDelete(LayoutParams lp, int position) {

		ImageButton buttonDelete = new ImageButton(this);
		buttonDelete.setLayoutParams(lp);
		buttonDelete.setImageResource(R.drawable.ic_action_cancel);
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
		inflater.inflate(R.menu.modify_menu, menu);
		return true;
	} 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_modify_ok:
			updateFriend();
			if(isModify){
				callConnectionModificationTask();
			}
			else {
				if(friend.getNom().equals("") && etBirthdaydate.getText().toString().equals("")){
					loadEmptyPopUp();
				}
				else{
					callConnectionAddTask();
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	private void updateFriend() {
		friend.setNom(etName.getText().toString());
		friend.setPrenom(etFirstname.getText().toString());
		friend.setFonction(etFunction.getText().toString());

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}

	protected void createFriendListActivity() {
		Intent intent = FriendListActivity.getIntent(this);//, friend.getNom(), etBirthdaydate.getText().toString());
		startActivity(intent);
	}
	private void callConnectionAddTask() {
		String id = String.valueOf(friend.getId());
		new AddOrModifyFriendTask(this,this,false).execute(friend.getNom(),etBirthdaydate.getText().toString(), id);
	}
	
	private void callConnectionModificationTask() {
		String id = String.valueOf(friend.getId());
		new AddOrModifyFriendTask(this,this,true).execute(friend.getNom(),"02-12-1800",id);//etBirthdaydate.getText().toString(), id);
	}
	protected void loadEmptyPopUp() {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(EMPTY_CONNECTION_ITEMS);
		AlertDialog ad = adb.create();
		ad.show();
	}

	@Override
	public void onAddOrModifyFriendComplete() {
		if(isModify)
		{
			Intent intent = FriendDetailsActivity.getIntent(this, friend.getNom(), friend.getPrenom(), friend.getRemainingDay(), friend.getFonction(), friend.getAge(), friend.getAvatar(),friend.getId());
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
		else
			createFriendListActivity();
	}

	@Override
	public void onAddOrModifyFriendFailed(String errorMessage) {
		displayMessage(errorMessage);
	}
}

