package com.sogifty.activities;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.sogifty.tools.AvatarGenerator;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.sogifty.model.Gift;
import com.sogifty.model.User;
import com.sogifty.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {

	List<User> users;
	boolean deleteMode = false;
	LayoutInflater inflater;
	SparseBooleanArray usersChecked = null;
	Context context = null;

	boolean showCheckbox = false;
	
	public UserAdapter(Context context,List<User> users) {
		inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		Collections.sort(users);
		this.users = users;
		usersChecked = new SparseBooleanArray();
	}
	
	
	public int getCount() {
		return users.size();
	}

	public Object getItem(int position) {
		return users.get(position);
	}

	public long getItemId(int position) {
		return Integer.parseInt(users.get(position).getId());
	}
	
	public void initChecked() {
		usersChecked.clear();
	}
	
	public void toggleCheckBox(int position) {
		//If the element is not in the array we put it with true value but if it is already in, we just take the opposite value
		if(usersChecked.indexOfKey(position) < 0)
			usersChecked.put(position, true);
		else
			usersChecked.put(position, !usersChecked.get(position));
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
		GridView giftsGrid;
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
			holder.giftsGrid = (GridView)convertView.findViewById(R.id.main_gridGifts);
			//holder.fonction = (TextView)convertView.findViewById(R.id.fonction);
			holder.cb = (CheckBox)convertView.findViewById(R.id.checkBox);
			holder.ar = (ImageView)convertView.findViewById(R.id.arrow);
			holder.layout = (LinearLayout)convertView.findViewById(R.id.main_ll_itemUserList);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		User u = users.get(position);
		holder.nom.setText(u.getNom());
		holder.prenom.setText(u.getPrenom());
		holder.age.setText(String.valueOf(u.getAge()));
		holder.remainingDate.setText("J-"+u.getRemainingDay());
		holder.tags.setText("Préférences : \n - sport \n - cuisine \n - intelligent \n - musculation");
		ListAdapter adapter = new GiftAdapter(this.context, createGifts());
		holder.giftsGrid.setAdapter(adapter);
		
		if(position == 0){

			convertView.setOnClickListener(new AdapterView.OnClickListener() {
				

				@Override
				public void onClick(View v) {
				
					Intent intent = new Intent(context,
							FriendDetailActivity.class);

					User u = users.get(0);
					intent.putExtra("name", u.getNom());
					
					intent.putExtra("avatar", u.getAvatar());
					intent.putExtra("id", u.getId());

					context.startActivity(intent);
					
				}
			});
			
			holder.tags.setVisibility(CheckBox.VISIBLE);
			holder.giftsGrid.setVisibility(CheckBox.VISIBLE);
		}
		else{
			holder.tags.setVisibility(CheckBox.GONE);
			holder.giftsGrid.setVisibility(CheckBox.GONE);
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
		
		try {
			holder.iv.setImageBitmap(AvatarGenerator.generate(u.getNom(), u.getGender() ,context));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
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
