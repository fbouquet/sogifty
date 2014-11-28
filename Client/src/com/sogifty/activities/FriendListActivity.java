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
import com.sogifty.tasks.GetFriendListTask;
import com.sogifty.tasks.listeners.OnGetFriendListTaskListener;

public class FriendListActivity extends Activity implements OnGetFriendListTaskListener{
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
			
				String idValue = id.getText().toString();

				if (deleteMode) {
					deleteFriend(idValue,position);
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

		
		
		deleteBtn.setText(R.string.deleteModeBtnFalse);
		deleteBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				deleteMode = !deleteMode;

				if (deleteMode) {
					listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
					friendsToDelete.clear();
				} else {
					deleteBtn.setText(R.string.deleteModeBtnFalse);
					removeCheckedElementFromList();
					listJson.setChoiceMode(ListView.CHOICE_MODE_NONE);
				}
			}
		});
		

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
	public void onGetFriendListComplete(Friends friendsList) {
		listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		//createFalseList();
		if(friendsList.getListFriends().isEmpty())
			displayMessage("you have no friend, add one");
		adapter = new FriendAdapter(this, friendsList.getListFriends());
		listJson.setAdapter(adapter);
		userAdapter = ((FriendAdapter) listJson.getAdapter());
	}
	
	@Override
	public void onGetFriendListFailed(String message) {
		displayMessage(message);
	}
	
	
	
	

	private void deleteFriend(String idValue, int position) {
		if (!friendsToDelete.contains(idValue))
			friendsToDelete.add(Integer.parseInt(idValue));
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

		Intent intent = FriendDetailsActivity.getIntent(this, f.getNom(), f.getPrenom(), f.getRemainingDay(), f.getFonction(), f.getAge(), f.getAvatar(),f.getId());
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

	}
	protected void createAddFriendActivity() {
		Intent intent = FriendDetailModificationActivity.getIntent(this, "", "", 0, "", 0, "", -1, false);
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
			userAdapter.initChecked();
			friendsToDelete.clear();
			invalidateOptionsMenu();
			deleteMode = !deleteMode;
			userAdapter.showCheckbox();
			

			break;
		case R.id.action_delete_stop:
			invalidateOptionsMenu();
			deleteMode = !deleteMode;

			//Log.i("Friends to Delete", friendsToDelete.toString());
			removeCheckedElementFromList();
			userAdapter.showCheckbox();
			userAdapter.initChecked();
			
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

		for (Integer id : friendsToDelete) {
			List<Friend> f = friendsList.getListFriends();
			for (int i = 0; i < f.size(); i++) {
				if (f.get(i).getId() == id) {
					f.remove(i);
				}
			}
		}

		userAdapter.notifyDataSetChanged();
		Toast.makeText(this,
				String.valueOf(friendsToDelete.size()) + FRIENDS_DELETED,
				Toast.LENGTH_SHORT).show();
		friendsToDelete.clear();
	}
	
		
//	public void createFalseList(){
//		friendsList = new Friends();
//		
//		Friend f = new Friend();
//		f.setAvatar("u");
//		f.setFonction("b");
//		f.setGender("male");
//		f.setId(Integer.parseInt("0"));
//		f.setNom("Villalba");
//		f.setPrenom("Léo");
//		f.setAge(21);
//		f.setRemainingDay(Integer.parseInt("3"));
//		friendsList.addFriend(f);
//		
//		Friend v = new Friend();
//		v.setAvatar("blabl");
//		v.setFonction("b");
//		v.setGender("b");
//		v.setId(Integer.parseInt("1"));
//		v.setAge(22);
//		v.setNom("Sagardia");
//		v.setPrenom("Elorri");
//		v.setRemainingDay(Integer.parseInt("8"));
//		friendsList.addFriend(v);
//		
//		Friend w = new Friend();
//		w.setAvatar("blabl");
//		w.setFonction("b");
//		w.setGender("b");
//		w.setId(Integer.parseInt("2"));
//		w.setAge(22);
//		w.setNom("Folliot");
//		w.setPrenom("Thomas");
//		w.setRemainingDay(Integer.parseInt("144"));
//		friendsList.addFriend(w);
//		
//		Friend x = new Friend();
//		x.setAvatar("blabl");
//		x.setFonction("b");
//		x.setGender("b");
//		x.setId(Integer.parseInt("3"));
//		x.setAge(1000);
//		x.setNom("JOMARD");
//		x.setPrenom("ARnauuuud");
//		x.setRemainingDay(Integer.parseInt("70000"));
//		friendsList.addFriend(x);
//		
//		Friend y = new Friend();
//		y.setAvatar("blabl");
//		y.setFonction("b");
//		y.setGender("b");
//		y.setId(Integer.parseInt("5"));
//		y.setAge(2);
//		y.setNom("Jouuu");
//		y.setPrenom("Valouuuuuuve");
//		y.setRemainingDay(Integer.parseInt("4"));
//		friendsList.addFriend(y);
//		
//		Friend z = new Friend();
//		z.setAvatar("blabl");
//		z.setFonction("b");
//		z.setGender("b");
//		z.setId(Integer.parseInt("4"));
//		z.setAge(27);
//		z.setNom("Bouquet");
//		z.setPrenom("Fleur");
//		z.setRemainingDay(Integer.parseInt("80"));
//		friendsList.addFriend(z);
//		
//		Friend e = new Friend();
//		e.setAvatar("blabl");
//		e.setFonction("b");
//		e.setGender("b");
//		e.setId(Integer.parseInt("6"));
//		e.setAge(22);
//		e.setNom("Garbage");
//		e.setPrenom("Yvonne");
//		e.setRemainingDay(Integer.parseInt("57"));
//		friendsList.addFriend(e);
//		
//		
//	}
	
	private int loadUserId(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		return preferences.getInt(USER_ID, getResources().getInteger(R.integer.user_id_default));
	}
	private void displayMessage(String message) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}


	

	
}
