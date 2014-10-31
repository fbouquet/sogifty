package com.sogifty.activities;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sogifty.R;
import com.sogifty.tools.AvatarGenerator;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.sogifty.model.Gift;
import com.sogifty.model.User;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class FriendDetailActivity extends Activity {
	User user = null;
	Intent myIntent= null;
	List<Gift> gifts = null;
	
	GridView gridGifts = null;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState)  {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_friend_details);
		
			gifts = new ArrayList<Gift>();
			user = new User();
			user.setNom("Pickachu");
			
			user.setAvatar("http://www.ffworld.com/images/discu/avatars/ff7/17.jpg");
			
			if(getIntent().getStringExtra("name") != null)
				user = getUser();

			initView();
			fillListGifts();
				
	    }
	 
	 private void initView() {
		 	ActionBar actionBar = getActionBar();
		    // add the custom view to the action bar
		    //actionBar.setCustomView(R.layout.custom_menu);
		    actionBar.setSubtitle("Détail Ami");
		    actionBar.setTitle("Sogifti");
		  
		   TextView tvName = (TextView) findViewById(R.id.tvName);
		    

		    TextView tvLikes = (TextView) findViewById(R.id.tvLikes);
		    
		    EditText etName = (EditText) findViewById(R.id.etDetailName);

		    EditText etTags = (EditText) findViewById(R.id.etDetailTags);
		    
		    ImageView iv = (ImageView) findViewById(R.id.ivDetail);
			
		    tvName.setText(R.string.nameFriend);

		    tvLikes.setText(R.string.tagsFriend);
		    
		    etName.setText(user.getNom());
		    
		    etTags.setText("Sport Vêtements Sac");
		    
		    //UrlImageViewHelper.setUrlDrawable(iv, user.getAvatar());
		    try {
				iv.setImageBitmap(AvatarGenerator.generate(user.getNom(), "M", this));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
	 
	 private User getUser(){
		 User u = new User();
		 myIntent = getIntent();
		 
		 u.setNom(myIntent.getStringExtra("name"));
		 
		 
		 
		 //u.setId(Integer.parseInt(myIntent.getStringExtra("id")));
		 
		 //u.setAvatar(myIntent.getStringExtra("avatar"));
		 //u.setAvatar("http://www.ffworld.com/images/discu/avatars/ff7/19.jpg");
		 

		 
		 return u;
	 }
	 
	 private void fillListGifts(){
		Gift g = new Gift();
			
		g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
		g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
		g.setFriendId("1");
		
		g.setPrice("19");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
		g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
		g.setFriendId("2");
		
		g.setPrice("14");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
		g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
		g.setFriendId("3");
		
		g.setPrice("199");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
		g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
		g.setFriendId("2");
		
		g.setPrice("14");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
		g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
		g.setFriendId("3");
		
		g.setPrice("199");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
		g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
		g.setFriendId("1");
		
		g.setPrice("19");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
		g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
		g.setFriendId("1");
		
		g.setPrice("19");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
		g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
		g.setFriendId("2");
		
		g.setPrice("14");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
		g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
		g.setFriendId("3");
		
		g.setPrice("199");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
		g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
		g.setFriendId("2");
		
		g.setPrice("14");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
		g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
		g.setFriendId("3");
		
		g.setPrice("199");
		
		gifts.add(g);
		
		g = new Gift();
		
		g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
		g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
		g.setFriendId("1");
		
		g.setPrice("19");
		
		gifts.add(g);
		
		
		
		
		
		gridGifts = (GridView) findViewById(R.id.gridGifts);
		
		ListAdapter adapter = new GiftAdapter(this, gifts);
		gridGifts.setAdapter(adapter);
		
		gridGifts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg) {
				// Object listItem = FriendListView.getItemAtPosition(position);
				// view.findViewById(R.id.UserId).get

				String url = (String) ((Gift) gridGifts.getAdapter().getItem(position)).getUrl();
				

				
				Intent i = new Intent(Intent.ACTION_VIEW);
	            i.setData(Uri.parse(url));
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            startActivity(i);
				
			}

		});
		
		

	 }
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	      /*MenuInflater inflater = getMenuInflater();
	      
	      if(!deleteMode)
	    	  inflater.inflate(R.menu.custom_menu, menu);
	      else
	    	  inflater.inflate(R.menu.delete_mode, menu);*/
	      return true;
	    } 
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	      
	      return true;
	    }
	    
	    @Override
	    public void onBackPressed() {
	        super.onBackPressed();
	        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	    }
	    
}
