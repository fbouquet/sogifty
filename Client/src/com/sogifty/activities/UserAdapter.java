package com.sogifty.activities;



import java.io.IOException;
import java.util.List;

import com.sogifty.tools.AvatarGenerator;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.sogifty.model.User;

import com.sogifty.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
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
		TextView nom;
		TextView prenom;
		TextView age;
		//TextView fonction;
		TextView id;
		CheckBox cb;
		ImageView iv;
		ImageView ar;
	}
	
	
	


	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.useritem, null);

			holder.nom = (TextView)convertView.findViewById(R.id.nom);

			holder.age = (TextView)convertView.findViewById(R.id.age);
			//holder.fonction = (TextView)convertView.findViewById(R.id.fonction);
			holder.id = (TextView)convertView.findViewById(R.id._id);
			holder.cb = (CheckBox)convertView.findViewById(R.id.checkBox);
			holder.iv = (ImageView)convertView.findViewById(R.id.imageViewUser);
			holder.ar = (ImageView)convertView.findViewById(R.id.arrow);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		User u = users.get(position);

		holder.nom.setText(u.getNom());
		//holder.prenom.setText("Prénom : "+ users.get(position).getPrenom());
		//holder.age.setText("Age : "+ String.valueOf(users.get(position).getAge()));
		//holder.fonction.setText("Fonction : "+ String.valueOf(users.get(position).getFonction()));
		holder.id.setText(String.valueOf(u.getId()));
		holder.cb.setChecked(usersChecked.get(position));
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
	
	

	
	
}
