package com.sogifty.activities;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.Gravity;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
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
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_FILE = 2;
	protected static final int PICK_FROM_APPLICATION = 3;

	private static ArrayAdapter<String> autoCompleteExistingsTagsAdapter = null;

	private static final String IS_MODIFY = "is modify";	

	private static final String ALREADY_IN_ERROR = "Goût déjà attribué";
	private static final String TAGS = "tag_list";
	private static final Set<String> DEFAULT_TAGS = null;
	private static final String NB_TAGS_MAX = "nb_tags";
	private static final int NB_TAGS_MAX_DEFAULT = 10;
	protected static final String TOO_MUCH_TAG = "Vous avez atteint le nombre maximum de tags";
	private static final String WOMAN = "woman";
	private static final String MAN = "man";

	private Friend friend = null;
	private EditText etName = null ;
	private EditText etFirstname = null ;
	private DatePicker dpBirthdaydate = null ;
	private ImageView ivAvatar;
	private Button bAddTag;
	private Button bModifyAvatar;
	private Uri avatarUri;
	private AutoCompleteTextView autoCompleteTagsTextView;
	private List<String> leftFriendTags = new ArrayList<String>();
	private List<String> rightFriendTags = new ArrayList<String>();
	private LinearLayout leftTagsLayout ;
	private LinearLayout rightTagsLayout ;
	private boolean isModify = true;
	private int nbTagsMax;


	@Override
	public void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_details_modification);

		initView();

	}

	private void initFriendAndMode() {
		Intent tmp = getIntent();
		friend = (Friend) tmp.getSerializableExtra(FriendDetailsActivity.FRIEND);
		if (friend != null && friend.getAvatar()!=null)
			avatarUri = Uri.parse(friend.getAvatar());
		isModify=tmp.getBooleanExtra(IS_MODIFY, true);
	}

	public static Intent getIntent(Context ctxt, Friend friend, boolean ismodify){

		Intent newActivityIntent = new Intent(ctxt, FriendDetailModificationActivity.class);

		newActivityIntent.putExtra(FriendDetailsActivity.FRIEND, friend );
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
		dpBirthdaydate = (DatePicker) findViewById(R.id.modify_dp_birthdaydate);
		if (friend.getBirthdayDate()!=null){
			String date = friend.getBirthdayDate();
			String[] dmy = date.split("-");
			dpBirthdaydate.updateDate(Integer.parseInt(dmy[2]), Integer.parseInt(dmy[1]), Integer.parseInt(dmy[0]));
		}
		ivAvatar = (ImageView) findViewById(R.id.modify_iv_avatar);

		initButtonModifyAvatar();
		initButtonAddTag();

		leftTagsLayout= (LinearLayout) findViewById(R.id.modify_l_ltags);
		rightTagsLayout= (LinearLayout) findViewById(R.id.modify_l_rtags);

		initAutoCompleteTags();
		initTagsList();
		initAvatar();
	}

	private void initAvatar () {

		Bitmap bitmap   = null;
		String path = friend.getAvatar();
		if (path !=null){
			if (path.equals(WOMAN) || path.equals(MAN)){
    			if (path.equals(MAN)){
    				InputStream is = getResources().openRawResource(R.drawable.man);
    				bitmap = BitmapFactory.decodeStream(is);
    			}
    			else if (path.equals(WOMAN)){
    				InputStream is = getResources().openRawResource(R.drawable.woman);
    				bitmap = BitmapFactory.decodeStream(is);
    							}
    		}
        	else {
        		bitmap = BitmapFactory.decodeFile(path);
        	}
			if(bitmap!=null){
				int rotate = getOrientation(path);
				int imgHeight = bitmap.getHeight();
				int imgWidth = bitmap.getWidth();
				if(imgHeight<imgWidth){
					bitmap = changeImgOrientation(bitmap, rotate);
					imgHeight = bitmap.getHeight();
					imgWidth = bitmap.getWidth();
				}
				float fixedHeight = dpToPx(155);
				float rate = fixedHeight/imgHeight;
				Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(imgWidth*rate), Math.round(fixedHeight), true);
				ivAvatar.setImageBitmap(resizedBitmap);
			}
		}

	}

	private void initButtonModifyAvatar() {

		final String [] items           = new String [] {"From Camera", "From SD Card","From Application's Avatar"};
		ArrayAdapter<String> adapter  = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
		AlertDialog.Builder builder     = new AlertDialog.Builder(this);

		builder.setTitle("Select Image");
		builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int item ) {
				if (item == 0) {
					Intent intent    = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File file        = new File(Environment.getExternalStorageDirectory(),
							"tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
					avatarUri = Uri.fromFile(file);

					try {
						intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, avatarUri);
						intent.putExtra("return-data", true);

						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (Exception e) {
						e.printStackTrace();
					}

					dialog.cancel();
				} else if(item == 1){
					Intent intent = new Intent();

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}
				else{
					Intent intent = ChooseAvatarActivity.getIntent(FriendDetailModificationActivity.this);

					startActivityForResult(intent, PICK_FROM_APPLICATION);
				}
			}
		} );

		final AlertDialog dialog = builder.create();

		bModifyAvatar = (Button) findViewById(R.id.modify_b_avatar);
		bModifyAvatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) return;
		Bitmap bitmap   = null;
		String path     = "";
		int rotate = 0;
		if (requestCode == PICK_FROM_FILE) {
			avatarUri = data.getData();
			path = getRealPathFromURI(avatarUri); //from Gallery

			if (path == null)
				path = avatarUri.getPath(); //from File Manager

			if (path != null){
				bitmap  = BitmapFactory.decodeFile(path);
				rotate = getOrientation(path);
			}
		} else if (requestCode == PICK_FROM_APPLICATION){
			path = data.getStringExtra(ChooseAvatarActivity.RESULT);
			if (path.equals(MAN)){
				InputStream is = this.getResources().openRawResource(R.drawable.man);
				bitmap = BitmapFactory.decodeStream(is);
			}
			else if (path.equals(WOMAN)){
				InputStream is = this.getResources().openRawResource(R.drawable.woman);
				bitmap = BitmapFactory.decodeStream(is);
							}
			if (bitmap == null){
				displayMessage("No such image!");
				return;
			}
		}
		else {
			path    = avatarUri.getPath();
			bitmap  = BitmapFactory.decodeFile(path);
			rotate = getOrientation(path);
		}
		friend.setAvatar(path);
		int imgHeight = bitmap.getHeight();
		int imgWidth = bitmap.getWidth();
		if(imgHeight<imgWidth){
			bitmap = changeImgOrientation(bitmap, rotate);
			imgHeight = bitmap.getHeight();
			imgWidth = bitmap.getWidth();
		}
		float fixedHeight = dpToPx(155);
		float rate = fixedHeight/imgHeight;
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(imgWidth*rate), Math.round(fixedHeight), true);
		ivAvatar.setImageBitmap(resizedBitmap);
	}

	private int getOrientation(String path) {
		int orientation = 0 ;
		try {
			orientation = (new ExifInterface(path)).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				return -90;
			case ExifInterface.ORIENTATION_ROTATE_180:
				return 180;
			case ExifInterface.ORIENTATION_ROTATE_90:
				return 90;
			default:
				return 0;
			}
		} 
		catch (IOException e) {

			e.printStackTrace();
			return 0;
		}
	}

	private Bitmap changeImgOrientation(Bitmap bitmap, int rotate) {
		Matrix matrix = new Matrix();
		matrix.postRotate(rotate);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}


	private int dpToPx(int dp)
	{
		float density = getApplicationContext().getResources().getDisplayMetrics().density;
		return Math.round((float)dp * density);
	}
	public String getRealPathFromURI(Uri contentUri) {
		String [] proj      = {MediaStore.Images.Media.DATA};
		Cursor cursor       = managedQuery( contentUri, proj, null, null,null);

		if (cursor == null) return null;

		int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		cursor.moveToFirst();

		return cursor.getString(column_index);
	}



	private void initTagsList() {
		if(friend.getTags() != null)
			for (int i=0;i<friend.getTags().size();++i) {
				addTag(friend.getTags().get(i));
			}
	}

	private void initButtonAddTag() {
		nbTagsMax = loadNbTagsMax();
		bAddTag = (Button) findViewById(R.id.modify_b_addtag);

		bAddTag.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(leftFriendTags.size()+rightFriendTags.size() >= nbTagsMax){
					displayMessage(TOO_MUCH_TAG);
				}
				else{
					String tagValue = autoCompleteTagsTextView.getText().toString();
					addTag(tagValue);
				}
			}


		});

	}
	private int loadNbTagsMax() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		return preferences.getInt(NB_TAGS_MAX, NB_TAGS_MAX_DEFAULT);
	}
	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle(FRIEND_DETAIL);
		actionBar.setTitle(APPLICATION_NAME);

	}


	private void initAutoCompleteTags() {
		if (autoCompleteExistingsTagsAdapter==null){
			autoCompleteExistingsTagsAdapter = loadTags();
			//autoCompleteExistingsTagsAdapter= new ArrayAdapter<String> (this, android.R.layout.simple_dropdown_item_1line);
		}
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


	private ArrayAdapter<String> loadTags() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		Set<String> tags = preferences.getStringSet(TAGS, DEFAULT_TAGS);
		ArrayAdapter<String> tagsArrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_dropdown_item_1line);
		tagsArrayAdapter.addAll(tags);
		return tagsArrayAdapter;
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

			LinearLayout.LayoutParams lpTextView = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			final TextView newTag = createAndAddTag (lpTextView, tagValue, position);
			LinearLayout.LayoutParams lpButton = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			ImageButton buttonDelete = createAndAddButtonDelete(lpButton, position);

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
		lp.gravity = Gravity.CENTER;
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


	@SuppressLint("ResourceAsColor")
	private ImageButton createAndAddButtonDelete(LayoutParams lp, int position) {

		ImageButton buttonDelete = new ImageButton(this);
		buttonDelete.setLayoutParams(lp);
		buttonDelete.setImageResource(R.drawable.ic_action_cancel);
		buttonDelete.setScaleType(ScaleType.CENTER_INSIDE);
		buttonDelete.setBackgroundColor(getResources().getColor(R.color.globalBackground));
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
				if(friend.getNom().equals("")){
					loadEmptyPopUp();
				}
				else{
					callConnectionModificationTask();
				}
			}
			else {
				if(friend.getNom().equals("")){
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
		String tmp ="";
		tmp = etName.getText().toString();
		friend.setNom(tmp);
		tmp=etFirstname.getText().toString();
		friend.setPrenom(tmp);
		tmp = dpBirthdaydate.getDayOfMonth() + "-" + dpBirthdaydate.getMonth() + "-" + dpBirthdaydate.getYear();
		friend.setBirthdayDate(tmp);
		List<String> tags = new ArrayList<String>(leftFriendTags);
		tags.addAll(rightFriendTags);
		friend.setTags(tags);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}

	protected void createFriendListActivity() {
		Intent intent = FriendListActivity.getIntent(this);
		startActivity(intent);
	}
	private void callConnectionAddTask() {

		String id = String.valueOf(friend.getId());
		List<String> tags = new ArrayList<String>(leftFriendTags);
		tags.addAll(rightFriendTags);
		new AddOrModifyFriendTask(this,this,false)
		.execute(friend.getNom(),friend.getPrenom(), friend.getBirthdayDate(), id, friend.getTagsinJsonString(), friend.getAvatar());
	}

	private void callConnectionModificationTask() {
		String id = String.valueOf(friend.getId());
		List<String> tags = new ArrayList<String>(leftFriendTags);
		tags.addAll(rightFriendTags);
		new AddOrModifyFriendTask(this,this,true).
		execute(friend.getNom(),friend.getPrenom(),friend.getBirthdayDate(),id,friend.getTagsinJsonString(),friend.getAvatar());
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
			Intent intent = FriendDetailsActivity.getIntent(this, friend);
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

