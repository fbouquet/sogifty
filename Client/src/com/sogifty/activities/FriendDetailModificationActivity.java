package com.sogifty.activities;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.sogifty.R;
import com.sogifty.model.Friend;

public class FriendDetailModificationActivity extends Activity {
	private static final CharSequence APPLICATION_NAME = "Sogifty";
	private static final CharSequence FRIEND_DETAIL = "Détails ami";
	private Friend friend = null;
	private EditText etName = null ;
	private EditText etFirstname = null ;
	private EditText etFunction = null ;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState)  {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_friend_details_modification);
			
			initView();
			
	    }
	 
	 private void initFriend() {
		friend =new Friend();
		Intent tmp = getIntent();
		friend.setNom(tmp.getStringExtra(FriendDetailsActivity.NAME));
		friend.setPrenom(tmp.getStringExtra(FriendDetailsActivity.FIRTNAME));
		friend.setRemainingDay(tmp.getIntExtra(FriendDetailsActivity.REMAININGDATE, 0));
		friend.setFonction(tmp.getStringExtra(FriendDetailsActivity.FONCTION));
		friend.setAge(tmp.getIntExtra(FriendDetailsActivity.AGE, 0));
		friend.setAvatar(tmp.getStringExtra(FriendDetailsActivity.AVATAR));
		friend.setId(tmp.getIntExtra(FriendDetailsActivity.ID, 0));
	}

	public static Intent getIntent(Context ctxt, String name, String firstname, int remainingDate, String function, int age, String avatar, int id) {
			
	    	Intent newActivityIntent = new Intent(ctxt, FriendDetailModificationActivity.class);
			
			newActivityIntent.putExtra(FriendDetailsActivity.FIRTNAME, firstname);
			newActivityIntent.putExtra(FriendDetailsActivity.NAME, name);
			newActivityIntent.putExtra(FriendDetailsActivity.REMAININGDATE, remainingDate);
			newActivityIntent.putExtra(FriendDetailsActivity.FONCTION, function);
			newActivityIntent.putExtra(FriendDetailsActivity.AGE, age);
			newActivityIntent.putExtra(FriendDetailsActivity.AVATAR, avatar);
			newActivityIntent.putExtra(FriendDetailsActivity.ID, id);
			
	    	return newActivityIntent;
		}
	 
	 private void initView() {

		 initFriend();
		 initActionBar();
		 
		 etName = (EditText) findViewById(R.id.modify_et_name);
		 etName.setText(friend.getNom());
		 etFirstname = (EditText) findViewById(R.id.modify_et_firstname);
		 etFirstname.setText(friend.getPrenom());
		 etFunction = (EditText) findViewById(R.id.modify_et_function);
		 etFunction.setText(friend.getFonction());
//		 EditText etBirthdaydate = (EditText) findViewById(R.id.modify_et_birthdaydate);
//		 etBirthdaydate.setText(friend.getBirthdaydate());
		 ImageView ivAvatar = (ImageView) findViewById(R.id.modify_iv_avatar);

		 
		 //UrlImageViewHelper.setUrlDrawable(iv, user.getAvatar());
//		 iv.setImageBitmap(AvatarGenerator.generate(friend.getNom(), "M", this));
	 }

	 private void initActionBar() {
		 ActionBar actionBar = getActionBar();
		 actionBar.setSubtitle(FRIEND_DETAIL);
		 actionBar.setTitle(APPLICATION_NAME);

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
				Intent intent = FriendDetailsActivity.getIntent(this, friend.getNom(), friend.getPrenom(), friend.getRemainingDay(), friend.getFonction(), friend.getAge(), friend.getAvatar(),friend.getId());
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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

}




//private void fillListGifts(){
//	Gift g = new Gift();
//		
//	g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
//	g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
//	g.setFriendId("1");
//	
//	g.setPrice("19");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
//	g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
//	g.setFriendId("2");
//	
//	g.setPrice("14");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
//	g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
//	g.setFriendId("3");
//	
//	g.setPrice("199");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
//	g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
//	g.setFriendId("2");
//	
//	g.setPrice("14");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
//	g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
//	g.setFriendId("3");
//	
//	g.setPrice("199");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
//	g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
//	g.setFriendId("1");
//	
//	g.setPrice("19");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
//	g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
//	g.setFriendId("1");
//	
//	g.setPrice("19");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
//	g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
//	g.setFriendId("2");
//	
//	g.setPrice("14");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
//	g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
//	g.setFriendId("3");
//	
//	g.setPrice("199");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
//	g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
//	g.setFriendId("2");
//	
//	g.setPrice("14");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
//	g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
//	g.setFriendId("3");
//	
//	g.setPrice("199");
//	
//	gifts.add(g);
//	
//	g = new Gift();
//	
//	g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
//	g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
//	g.setFriendId("1");
//	
//	g.setPrice("19");
//	
//	gifts.add(g);
//	
//	
//	
//	
//	
//	gridGifts = (GridView) findViewById(R.id.gridGifts);
//	
//	ListAdapter adapter = new GiftAdapter(this, gifts);
//	gridGifts.setAdapter(adapter);
//	
//	gridGifts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//		public void onItemClick(AdapterView<?> adapter, View view,
//				int position, long arg) {
//			// Object listItem = FriendListView.getItemAtPosition(position);
//			// view.findViewById(R.id.UserId).get
//
//			String url = (String) ((Gift) gridGifts.getAdapter().getItem(position)).getUrl();
//			
//
//			
//			Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(i);
//			
//		}
//
//	});
//	
//	
//
// }
