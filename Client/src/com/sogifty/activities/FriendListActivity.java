package com.sogifty.activities;



import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sogifty.R;
import com.sogifty.model.Friend;
import com.sogifty.model.Friends;
import com.sogifty.model.Gift;
import com.sogifty.tasks.DeleteFriendTask;
import com.sogifty.tasks.GetFriendListTask;
import com.sogifty.tasks.GetGiftsTask;
import com.sogifty.tasks.listeners.OnDeleteFriendTaskListener;
import com.sogifty.tasks.listeners.OnGetFriendListTaskListener;
import com.sogifty.tasks.listeners.OnGetGiftsTaskListener;

public class FriendListActivity extends Activity implements OnGetFriendListTaskListener, OnDeleteFriendTaskListener, OnGetGiftsTaskListener{
	private static String FRIEND_LIST = "Liste des amis";
	private static String APPLICATION_NAME = "Sogifti";
	private static final String FRIENDS_DELETED = " amis supprimés";
	private static final String ITEM_POSITION = "Item in position " ;
	private static final String CLICKED = " clicked";
	private static final String EMAIL = "EmailUser";
	private static final String PASSWORD = "PasswordUser";
	private static final String USER_ID = "user_id";
	
	
	private ListView listJson;
	private TextView id;
	private Button deleteBtn = null;
	
	
	private boolean deleteMode = false;
	private Friends friendsList = null;
	private ListAdapter adapter = null;
	private List<Integer> friendsToDelete = null;
	private FriendAdapter userAdapter = null;
	private List<Gift> firstFriendGifts = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initActionBar();
		
		initLayout();
		
		//createListView();
		new GetFriendListTask(FriendListActivity.this, this).execute();
		
		listJson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
			
				TextView id = (TextView) findViewById(R.id._id);
			
				//String idValue = id.getText().toString();

				if (deleteMode) {
					deleteFriend(adapter,position);
				} 
				else {
					createFriendDetailsActivity(position);
				}

				userAdapter.toggleCheckBox(position);
				userAdapter.notifyDataSetChanged();
			}

		
		});

		listJson.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				longClickFonction(parent,position);
				return true;
			}

			
		});

		
		
//		deleteBtn.setText(R.string.deleteModeBtnFalse);
//		deleteBtn.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View view) {
//
//				deleteMode = !deleteMode;
//
//				if (deleteMode) {
//					listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//					friendsToDelete.clear();
//				} else {
//					//deleteBtn.setText(R.string.deleteModeBtnFalse);
//					removeCheckedElementFromList();
//					listJson.setChoiceMode(ListView.CHOICE_MODE_NONE);
//				}
//			}
//		});
		

	}

	
//	private void createListView() {
//		listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//		createFalseList();
//		//friendsList = new GetFriendListTask(FriendListActivity.this).execute(loadUserId());
//		adapter = new FriendAdapter(this, friendsList.getListFriends());
//		listJson.setAdapter(adapter);
//		userAdapter = ((FriendAdapter) listJson.getAdapter());
//	}


	private void initLayout() {
		friendsToDelete = new ArrayList<Integer>();
		listJson = (ListView) findViewById(R.id.listJson);
		deleteBtn = (Button) findViewById(R.id.deleteBtn);
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(FRIEND_LIST);
		actionBar.setTitle(APPLICATION_NAME);

	}
	@Override
	public void onGetFriendListComplete(Friends friendsListparam) {
		friendsList = friendsListparam;
		if(friendsList.getListFriends() != null && friendsList.getListFriends().size() != 0){
			new GetGiftsTask(this, this).execute(String.valueOf(friendsList.getListFriends().get(0).getId()));
		}
		else{
			if(friendsList.getListFriends().isEmpty())
				displayMessage(getResources().getString(R.string.friendlist_no_friend));
		}
//		listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//		//createFalseList();
//		if(friendsList.getListFriends().isEmpty())
//			displayMessage("you have no friend, add one");
//		adapter = new FriendAdapter(this, friendsList.getListFriends(), firstFriendGifts);
//		listJson.setAdapter(adapter);
//		userAdapter = ((FriendAdapter) listJson.getAdapter());
	}
	
	@Override
	public void onGetFriendListFailed(String message) {
		displayMessage(message);
	}
	


	@Override
	public void onGetGiftsComplete(List<Gift> giftList) {
		firstFriendGifts = giftList;
		listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		//createFalseList();
		adapter = new FriendAdapter(this, friendsList.getListFriends(), firstFriendGifts);
		listJson.setAdapter(adapter);
		userAdapter = ((FriendAdapter) listJson.getAdapter());
	}


	@Override
	public void onGetGiftsFailed(String message) {
		displayMessage(message);
	}


	
	

	private void deleteFriend(AdapterView<?> parent,int position) {
		FriendAdapter f = (FriendAdapter) parent.getAdapter();
		Friend o = (Friend) f.getItem(position);
		int idValue = o.getId();
//		Toast.makeText(
//				FriendListActivity.this,
//				ITEM_POSITION + o.getId()
//						+ CLICKED, Toast.LENGTH_LONG).show();
		if (!friendsToDelete.contains(idValue)){
			friendsToDelete.add(idValue);
			System.out.println(idValue);
		}
		else
			friendsToDelete.remove(friendsToDelete.indexOf(idValue));

//		Log.i("id is",
//				((User) listJson.getAdapter().getItem(position))
//						.getNom());
	}

	public void createFriendDetailsActivity(int position){
		SparseBooleanArray checkedPositions = listJson
				.getCheckedItemPositions();
		//Log.i(ID_TO_REMOVE_LIST, checkedPositions.toString());
		Friend f = (Friend) listJson.getAdapter().getItem(position);

		Intent intent = FriendDetailsActivity.getIntent(this, f);// f.getNom(), f.getPrenom(), f.getRemainingDay(), f.getFonction(), f.getAge(), f.getAvatar(),f.getId());
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

	}
	protected void createAddFriendActivity() {
		Intent intent = FriendDetailModificationActivity.getIntent(this,new Friend(), false);// "", "", 0, "", 0, "", -1, false);
		startActivity(intent);
	}
	protected void createFriendListActivity() {
		Intent intent = FriendListActivity.getIntent(this);
		startActivity(intent);
	}
	
	private void longClickFonction(AdapterView<?> parent,int position) {
		//utility to determine
		FriendAdapter f = (FriendAdapter) parent.getAdapter();
		Friend o = (Friend) f.getItem(position);

		Toast.makeText(
				FriendListActivity.this,
				ITEM_POSITION + o.getId()
						+ CLICKED, Toast.LENGTH_LONG).show();
	}

	
	public static Intent getIntent(Context ctxt, String email, String password) {
		Intent newActivityIntent = new Intent(ctxt, FriendListActivity.class);
		newActivityIntent.putExtra(EMAIL, email);
    	newActivityIntent.putExtra(PASSWORD, password);
    	return newActivityIntent;
	}
	public static Intent getIntent(Context ctxt){
		Intent newActivityIntent = new Intent(ctxt, FriendListActivity.class);
		return newActivityIntent;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		if (!deleteMode)
			inflater.inflate(R.menu.custom_menu, menu);
		else
			inflater.inflate(R.menu.delete_mode, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_delete:
			deleteMode = !deleteMode;
			userAdapter.initChecked();
			friendsToDelete.clear();
			invalidateOptionsMenu();
			userAdapter.showCheckbox();
			

			break;
		case R.id.action_delete_stop:
			removeCheckedElementFromList();
		
			break;
		case R.id.action_add:
			createAddFriendActivity();
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			break;
		default:
			break;
		}
		return true;
	} 

	private void removeCheckedElementFromList() {
		new DeleteFriendTask(this, this).execute(friendsToDelete);

	}
	
		

	
	private void displayMessage(String message) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}


	@Override
	public void deleteFriendComplete() {
		createFriendListActivity();
	}


	@Override
	public void deleteFriendFailed(String errorMessage) {
		displayMessage(errorMessage);
		createFriendListActivity();
	}

	

	
}
