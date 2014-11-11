package com.sogifty.activities;

import java.util.ArrayList;
import java.util.List;





import com.sogifty.model.User;
import com.sogifty.model.Users;
import com.sogifty.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.System;
import android.widget.AdapterView.OnItemLongClickListener;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HelloAndroidActivity extends Activity {
	private static String FRIEND_LIST = "Liste des amis";
	private static String APPLICATION_NAME = "Sogifti";
	private static String NAME = "name";
	private static String AVATAR = "avatar";
	private static String ID = "id";
	private static final String FRIENDS_DELETED = " amis supprimés";
	private static final String ITEM_POSITION = "Item in position " ;
	private static final String CLICKED = " clicked";
	private static final String EMAIL = "EmailUser";
	private static final String PASSWORD = "PasswordUser";

	private ProgressDialog pDialog;
	private ListView listJson;
	private TextView id;
	private Button deleteBtn = null;
	
	private boolean deleteMode = false;
	private Users usersList = null;
	private ListAdapter adapter = null;
	private List<String> friendsToDelete = null;
	private UserAdapter userAdapter = null;
	
	private ParserJson parser = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initActionBar();
		
		initLayout();
		
		createListView();
		
		
		listJson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				String idValue = id.getText().toString();

				if (deleteMode) {
					deleteFriend(idValue,position);
				} 
				else {
					createFriendActivity(position);
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

	
	private void initLayout() {
		friendsToDelete = new ArrayList<String>();
		listJson = (ListView) findViewById(R.id.listJson);
		id = (TextView) findViewById(R.id._id);
		deleteBtn = (Button) findViewById(R.id.deleteBtn);

	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(FRIEND_LIST);
		actionBar.setTitle(APPLICATION_NAME);

	}
	
	private void createListView() {
		listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		//gettingJson();
		String test = "{'user': {'name' : 'elorri'	}}";
		ParserJson js = new ParserJson(test);
		
		//java.lang.System.out.println(parser);
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(js.executeParse());
		AlertDialog ad = adb.create();
		ad.show();
		//usersList = parser.gettingJson();
		
		createFalseList();
		adapter = new UserAdapter(this, usersList.getListUsers());
		listJson.setAdapter(adapter);
		
		
		userAdapter = ((UserAdapter) listJson.getAdapter());
	}

	
	private void deleteFriend(String idValue, int position) {
		if (!friendsToDelete.contains(idValue))
			friendsToDelete.add(idValue);
		else
			friendsToDelete.remove(friendsToDelete.indexOf(idValue));

//		Log.i("id is",
//				((User) listJson.getAdapter().getItem(position))
//						.getNom());
	}

	public void createFriendActivity(int position){
		SparseBooleanArray checkedPositions = listJson
				.getCheckedItemPositions();
		//Log.i(ID_TO_REMOVE_LIST, checkedPositions.toString());

		Intent intent = new Intent(HelloAndroidActivity.this,
				FriendDetailActivity.class);

		User u = (User) listJson.getAdapter().getItem(position);

		intent.putExtra(NAME, u.getNom());
		
		intent.putExtra(AVATAR, u.getAvatar());
		intent.putExtra(ID, u.getId());

		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

	}
	
	private void longClickFonction(AdapterView<?> parent,int position) {
		//utility to determine
		UserAdapter u = (UserAdapter) parent.getAdapter();
		User o = (User) u.getItem(position);

		Toast.makeText(
				HelloAndroidActivity.this,
				ITEM_POSITION + o.getId()
						+ CLICKED, Toast.LENGTH_LONG).show();
	}

	
	public static Intent getIntent(Context ctxt, String email, String password) {
		Intent newActivityIntent = new Intent(ctxt, HelloAndroidActivity.class);
		newActivityIntent.putExtra(EMAIL, email);
    	newActivityIntent.putExtra(PASSWORD, password);
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
			Intent intent = new Intent(HelloAndroidActivity.this,
					FriendDetailActivity.class);

			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			break;
		default:
			break;
		}
		return true;
	}

	private void removeCheckedElementFromList() {

		for (String id : friendsToDelete) {
			List<User> u = usersList.getListUsers();
			for (int i = 0; i < u.size(); i++) {
				if (u.get(i).getId() == id) {
					u.remove(i);
				}
			}
		}

		userAdapter.notifyDataSetChanged();
		Toast.makeText(this,
				String.valueOf(friendsToDelete.size()) + FRIENDS_DELETED,
				Toast.LENGTH_SHORT).show();
		friendsToDelete.clear();
	}
	
		
	public void createFalseList(){
		User u = new User();
		usersList = new Users();
		u.setAvatar("u");
		u.setFonction("b");
		u.setGender("male");
		u.setId("0");
		u.setNom("Villalba");
		u.setPrenom("Léo");
		u.setAge(21);
		u.setRemainingDay("3");
		usersList.addUser(u);
		
		User v = new User();
		v.setAvatar("blabl");
		v.setFonction("b");
		v.setGender("b");
		v.setId("1");
		v.setAge(22);
		v.setNom("Sagardia");
		v.setPrenom("Elorri");
		v.setRemainingDay("8");
		usersList.addUser(v);
		
		User w = new User();
		w.setAvatar("blabl");
		w.setFonction("b");
		w.setGender("b");
		w.setId("2");
		w.setAge(22);
		w.setNom("Folliot");
		w.setPrenom("Thomas");
		w.setRemainingDay("144");
		usersList.addUser(w);
		
		User x = new User();
		x.setAvatar("blabl");
		x.setFonction("b");
		x.setGender("b");
		x.setId("3");
		x.setAge(1000);
		x.setNom("JOMARD");
		x.setPrenom("ARnauuuud");
		x.setRemainingDay("70000");
		usersList.addUser(x);
		
		User y = new User();
		y.setAvatar("blabl");
		y.setFonction("b");
		y.setGender("b");
		y.setId("5");
		y.setAge(2);
		y.setNom("Jouuu");
		y.setPrenom("Valouuuuuuve");
		y.setRemainingDay("4");
		usersList.addUser(y);
		
		User z = new User();
		z.setAvatar("blabl");
		z.setFonction("b");
		z.setGender("b");
		z.setId("4");
		z.setAge(27);
		z.setNom("Bouquet");
		z.setPrenom("Fleur");
		z.setRemainingDay("80");
		usersList.addUser(z);
		
		User e = new User();
		e.setAvatar("blabl");
		e.setFonction("b");
		e.setGender("b");
		e.setId("6");
		e.setAge(22);
		e.setNom("Garbage");
		e.setPrenom("Yvonne");
		e.setRemainingDay("57");
		usersList.addUser(e);
		
		
	}

	
}
