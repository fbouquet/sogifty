package com.sogifty.activities;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sogifty.R;
import com.sogifty.model.Friend;
import com.sogifty.model.Gift;
import com.sogifty.tools.AvatarGenerator;

public class FriendAdapter extends BaseAdapter {

	List<Friend> friends;
	boolean deleteMode = false;
	LayoutInflater inflater;
	SparseBooleanArray friendsChecked = null;
	Context context = null;

	boolean showCheckbox = false;
	
	public FriendAdapter(Context context,List<Friend> friends) {
		inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		Collections.sort(friends);
		this.friends = friends;
		friendsChecked = new SparseBooleanArray();
	}
	
	
	public int getCount() {
		return friends.size();
	}

	public Object getItem(int position) {
		return friends.get(position);
	}

	public long getItemId(int position) {
		return friends.get(position).getId();
	}
	
	public void initChecked() {
		friendsChecked.clear();
	}
	
	public void toggleCheckBox(int position) {
		//If the element is not in the array we put it with true value but if it is already in, we just take the opposite value
		if(friendsChecked.indexOfKey(position) < 0)
			friendsChecked.put(position, true);
		else
			friendsChecked.put(position, !friendsChecked.get(position));
	}
	
	public void showCheckbox() {
		showCheckbox = !showCheckbox;
		notifyDataSetChanged();
	}
	
	private class ViewHolder {
		LinearLayout layout;
		TextView nom;
		TextView prenom;
		TextView age;
		TextView remainingDate;
		//TextView fonction;
		TextView id;
		CheckBox cb;
		ImageView iv;
		ImageView ar;
		TextView tags;
		ImageView giftImage;
	}
	
	
	


	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.useritem, null);

			holder.nom = (TextView)convertView.findViewById(R.id.nom);
			holder.prenom = (TextView)convertView.findViewById(R.id.prenom);
			holder.age = (TextView)convertView.findViewById(R.id.age);
			holder.iv = (ImageView)convertView.findViewById(R.id.imageViewUser);
			holder.remainingDate = (TextView)convertView.findViewById(R.id.remainingDate);
			holder.tags = (TextView)convertView.findViewById(R.id.tvLikes);
			holder.giftImage = (ImageView)convertView.findViewById(R.id.imageViewGift);
			//holder.fonction = (TextView)convertView.findViewById(R.id.fonction);
			holder.cb = (CheckBox)convertView.findViewById(R.id.checkBox);
			holder.ar = (ImageView)convertView.findViewById(R.id.arrow);
			holder.layout = (LinearLayout)convertView.findViewById(R.id.main_ll_itemUserList);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		Friend f = friends.get(position);
		holder.nom.setText(f.getNom());
		holder.prenom.setText(f.getPrenom());
		holder.age.setText(String.valueOf(f.getAge()));
		holder.remainingDate.setText("J-"+f.getRemainingDay());
		holder.tags.setText("Préférences : \n - sport \n - cuisine \n - intelligent \n - musculation");
	
		if(position == 0){

			holder.tags.setVisibility(CheckBox.VISIBLE);
			holder.giftImage.setVisibility(CheckBox.VISIBLE);
		}
		else{
			holder.tags.setVisibility(CheckBox.GONE);
			holder.giftImage.setVisibility(CheckBox.GONE);
		}
		//holder.fonction.setText("Fonction : "+ String.valueOf(users.get(position).getFonction()));
		//holder.cb.setChecked(usersChecked.get(position));
		if(showCheckbox){
			holder.cb.setVisibility(CheckBox.VISIBLE);
			holder.ar.setVisibility(CheckBox.GONE);
		}
		else{
			holder.cb.setVisibility(CheckBox.GONE);
			holder.ar.setVisibility(CheckBox.VISIBLE);
		}
		
		/*String imgUrl = users.get(position).getAvatar();
		
		
		if(imgUrl != null){
			Log.i("URL", imgUrl);
			UrlImageViewHelper.setUrlDrawable(holder.iv, imgUrl);
			//UrlImageViewHelper.loadUrlDrawable(context, imgUrl);
			
			//Ne marche pas
			//holder.iv.setImageBitmap(UrlImageViewHelper.getCachedBitmap(imgUrl));
		}
		else
			holder.iv.setImageResource(android.R.drawable.ic_menu_gallery);
		*/
		
//		try {
//			holder.iv.setImageBitmap(AvatarGenerator.generate(f.getNom(), f.getGender() ,context));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		
		return convertView;
	}
	
	
	 private List<Gift> createGifts(){
		 
		 	List<Gift> gifts = new ArrayList<Gift>();
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
	
			return gifts;
	 }
	 
	 public void onClick(View arg){
		 
	 }
}
