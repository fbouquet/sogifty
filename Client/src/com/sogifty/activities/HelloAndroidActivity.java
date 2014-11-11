package com.sogifty.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import com.sogifty.model.User;
import com.sogifty.model.Users;

import com.sogifty.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView.OnItemLongClickListener;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HelloAndroidActivity extends Activity {
	private static final String EMAIL = "EmailUser";
	private static final String PASSWORD = "PasswordUser";
	private ProgressDialog pDialog;
	private ListView listJson;
	private Button deleteBtn = null;
	private boolean deleteMode = false;
	private Users usersList = null;
	private ArrayAdapter<Users> adapterCheckList = null;
	private ListAdapter adapter = null;
	private List<HashMap<String, String>> liste;

	private List<String> friendsToDelete = null;
	private UserAdapter userAdapter = null;
	
	private ParserJson parser = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getActionBar();
		// add the custom view to the action bar
		// actionBar.setCustomView(R.layout.custom_menu);
		actionBar.setSubtitle("Liste des amis");
		actionBar.setTitle("Sogifti");

		friendsToDelete = new ArrayList<String>();

		listJson = (ListView) findViewById(R.id.listJson);
		listJson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				// Object listItem = FriendListView.getItemAtPosition(position);
				// view.findViewById(R.id.UserId).get

				TextView id = (TextView) view.findViewById(R.id._id);
				String idValue = id.getText().toString();

				if (deleteMode) {
					
					if (!friendsToDelete.contains(idValue))
						friendsToDelete.add(idValue);
					else
						friendsToDelete.remove(friendsToDelete.indexOf(idValue));

					Log.i("L'id est",
							((User) listJson.getAdapter().getItem(position))
									.getNom());
				} else {
					// TextView id = (TextView) view.findViewById(R.id._id);
					
					createFriendActivity(position);
				}

				userAdapter.toggleCheckBox(position);
				userAdapter.notifyDataSetChanged();
			}

		});

		listJson.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {

				UserAdapter u = (UserAdapter) parent.getAdapter();
				User o = (User) u.getItem(position);

				Toast.makeText(
						HelloAndroidActivity.this,
						"Item in position " + o.getId()
								+ " clicked", Toast.LENGTH_LONG).show();
				return true;
			}
		});

		listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		//gettingJson();
		parser = new ParserJson(this);
		//usersList = parser.gettingJson();
		
		createFalseList();
		adapter = new UserAdapter(this, usersList.getListUsers());
		listJson.setAdapter(adapter);
		
		
		userAdapter = ((UserAdapter) listJson.getAdapter());
		

		/* Toggle Delete Button Definition */
		deleteBtn = (Button) findViewById(R.id.deleteBtn);

		deleteBtn.setText(R.string.deleteModeBtnFalse);
		deleteBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				deleteMode = !deleteMode;
				if (deleteMode) {

					// createList();
					listJson.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

					// Initialization of the list of friend to delete
					friendsToDelete.clear();
				} else {
					deleteBtn.setText(R.string.deleteModeBtnFalse);
					removeCheckedElementFromList();
					listJson.setChoiceMode(ListView.CHOICE_MODE_NONE);

				}
			}
		});

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
			// Initialization of the list of friend to delete
			userAdapter.showCheckbox();
			

			break;
		case R.id.action_delete_stop:
			invalidateOptionsMenu();
			deleteMode = !deleteMode;

			Log.i("Friends to Delete", friendsToDelete.toString());
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
				String.valueOf(friendsToDelete.size()) + " amis supprimés",
				Toast.LENGTH_SHORT).show();
		friendsToDelete.clear();
	}
	
	public void createFriendActivity(int position){
		SparseBooleanArray checkedPositions = listJson
				.getCheckedItemPositions();
		Log.i("List of ids to remove", checkedPositions.toString());

		Intent intent = new Intent(HelloAndroidActivity.this,
				FriendDetailActivity.class);

		User u = (User) listJson.getAdapter().getItem(position);

		intent.putExtra("name", u.getNom());
		
		intent.putExtra("avatar", u.getAvatar());
		intent.putExtra("id", u.getId());

		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

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
