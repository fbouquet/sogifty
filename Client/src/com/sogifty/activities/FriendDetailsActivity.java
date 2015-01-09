package com.sogifty.activities;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sogifty.R;
import com.sogifty.model.Friend;
import com.sogifty.model.Gift;
import com.sogifty.model.Gifts;
import com.sogifty.tasks.GetGiftsTask;
import com.sogifty.tasks.listeners.OnGetGiftsTaskListener;

public class FriendDetailsActivity extends Activity implements OnGetGiftsTaskListener{
	
	static final String FRIEND = "Friend";

	TextView nameText;
	TextView firstnameText;
	TextView remaingDate;
	TextView age;
	TextView tagsView;
	ImageView ivAvatar;
	
	private ViewPager giftPager;
    private GiftPagerAdapter giftPagerAdapter;
    private List<Gift> gifts;
    
    private Friend friend;
    
    public static Intent getIntent(Context ctxt, Friend friend){
		
    	Intent newActivityIntent = new Intent(ctxt, FriendDetailsActivity.class);
		newActivityIntent.putExtra(FRIEND, friend);
	
    	return newActivityIntent;
	}
	
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_details);
		initFriendInformations();
		new GetGiftsTask(this, this).execute(String.valueOf(friend.getId()));
		
	}
    
    
	private void initActivity() {
		nameText = (TextView) findViewById(R.id.frienddetails_tv_name);
		firstnameText =(TextView) findViewById(R.id.frienddetails_tv_firstname);
		remaingDate =(TextView) findViewById(R.id.frienddetails_tv_RemainingDate);
		age = (TextView) findViewById(R.id.frienddetails_tv_age);
		tagsView = (TextView) findViewById(R.id.frienddetails_all_tags);
		ivAvatar = (ImageView) findViewById(R.id.frienddetails_iv_friendAvatar);
		nameText.setText(friend.getNom());
		firstnameText.setText(friend.getPrenom());
		remaingDate.setText(remaingDate.getText() + String.format("%d", ParserJson.getRemainingDay(friend.getBirthdayDate())));
		age.setText(age.getText() + String.format("%d",ParserJson.getAge(friend.getBirthdayDate())));
		if(friend.getTagsinPointString().compareTo("") != 0){
			tagsView.setText(friend.getTagsinPointString());
		}
		else{
			tagsView.setText(getResources().getString(R.string.frienddetails_no_taste));
			tagsView.setTypeface(null, Typeface.ITALIC);
		}
		initAvatar();
		giftPager = (ViewPager) findViewById(R.id.frienddetails_vp_giftPager);
		giftPagerAdapter = new GiftPagerAdapter(getFragmentManager(),new Gifts(gifts), this);
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


	protected void initAvatar () {
    
		Bitmap bitmap   = null;
        String path = friend.getAvatar();
        if (path !=null){
        	bitmap = BitmapFactory.decodeFile(path);
        	if (bitmap != null) {
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

	@Override
	public void onGetGiftsComplete(List<Gift> giftList) {
		gifts = giftList;
		initActivity();
		
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