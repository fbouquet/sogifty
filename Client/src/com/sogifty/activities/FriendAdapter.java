package com.sogifty.activities;



import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
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
import com.sogifty.tasks.DownloadImageTask;

public class FriendAdapter extends BaseAdapter {

	private static final int IO_BUFFER_SIZE = 50;
	private static final String TAG = null;
	private static final String WOMAN = "woman";
	private static final String MAN = "man";
	List<Friend> friends;
	List<Gift> gifts = null;
	boolean deleteMode = false;
	LayoutInflater inflater;
	SparseBooleanArray friendsChecked = null;
	Context context = null;

	boolean showCheckbox = false;

	public FriendAdapter(Context context,List<Friend> friends, List<Gift> gifts) {
		inflater = (LayoutInflater)
				context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		Collections.sort(friends);
		this.friends = friends;
		this.gifts = gifts;
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
		TextView id;
		CheckBox cb;
		ImageView iv;
		ImageView ar;
		TextView tags;
		ImageView giftImage;
		TextView giftPrice;
		public TextView giftName;
	}





	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.useritem, null);
			initHolder(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		setFriendItem(holder, position);



		return convertView;
	}
	private void setFriendItem(ViewHolder holder, int position) {
		Friend f = friends.get(position);
		holder.nom.setText(f.getNom());
		holder.prenom.setText(f.getPrenom());
		holder.age.setText(String.valueOf(f.getAge()));
		holder.remainingDate.setText("J-"+f.getRemainingDay());
		initAvatar(f,holder);
		if(f.getTagsinPointString().compareTo("") != 0){
			holder.tags.setText(f.getTagsinPointString());
		}
		else{
			holder.tags.setText(context.getResources().getString(R.string.frienddetails_no_taste));
			holder.tags.setTypeface(null, Typeface.ITALIC);
		}

		if(position == 0){
			holder.tags.setVisibility(CheckBox.VISIBLE);
			if (gifts != null && gifts.size() != 0){
				//int i = (int)(Math.random())%gifts.size();
				int i = 0;
				System.out.println("0"+gifts.get(i).getImgUrl());
				new DownloadImageTask(holder.giftImage)
				.execute(gifts.get(i).getImgUrl());
				holder.giftPrice.setText(gifts.get(i).getPrice());
				holder.giftName.setText(gifts.get(i).getName());

			}
			else{
				holder.giftImage.setVisibility(IGNORE_ITEM_VIEW_TYPE);
			}
			holder.giftImage.setVisibility(CheckBox.VISIBLE);
		}
		else{
			holder.tags.setVisibility(CheckBox.GONE);
			holder.giftImage.setVisibility(CheckBox.GONE);
		}
		//holder.fonction.setText("Fonction : "+ String.valueOf(users.get(position).getFonction()));
		holder.cb.setChecked(friendsChecked.get(position));
		if(showCheckbox){
			holder.cb.setVisibility(CheckBox.VISIBLE);
			holder.ar.setVisibility(CheckBox.GONE);
		}
		else{
			holder.cb.setVisibility(CheckBox.GONE);
			holder.ar.setVisibility(CheckBox.VISIBLE);
		}
	}


	private void initAvatar (Friend friend, ViewHolder holder) {

		Bitmap bitmap   = null;
		String path = friend.getAvatar();
		if (path !=null){
			if (path.equals(WOMAN) || path.equals(MAN)){
				if (path.equals(MAN)){
					InputStream is = context.getResources().openRawResource(R.drawable.man);
					bitmap = BitmapFactory.decodeStream(is);
				}
				else if (path.equals(WOMAN)){
					InputStream is = context.getResources().openRawResource(R.drawable.woman);
					bitmap = BitmapFactory.decodeStream(is);
				}
			}
			else {
				bitmap = BitmapFactory.decodeFile(path);
			}
			if (bitmap != null){
				int imgHeight = bitmap.getHeight();
				int imgWidth = bitmap.getWidth();
				int rotate = getOrientation(path);
				if(imgHeight<imgWidth){
					bitmap = changeImgOrientation(bitmap, rotate);
					imgHeight = bitmap.getHeight();
					imgWidth = bitmap.getWidth();
				}
				float fixedWidth = dpToPx(70);
				float rate = fixedWidth/imgWidth;
				Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, Math.round(fixedWidth), Math.round(imgHeight*rate), true);
				holder.iv.setImageBitmap(resizedBitmap);
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
		float density = context.getResources().getDisplayMetrics().density;
		return Math.round((float)dp * density);
	}


	private void initHolder(ViewHolder holder, View convertView) {
		holder.nom = (TextView)convertView.findViewById(R.id.nom);
		holder.prenom = (TextView)convertView.findViewById(R.id.prenom);
		holder.age = (TextView)convertView.findViewById(R.id.age);
		holder.iv = (ImageView)convertView.findViewById(R.id.imageViewUser);
		holder.remainingDate = (TextView)convertView.findViewById(R.id.remainingDate);
		holder.tags = (TextView)convertView.findViewById(R.id.tvLikes);
		holder.giftImage = (ImageView)convertView.findViewById(R.id.imageViewGift);
		holder.giftPrice = (TextView)convertView.findViewById(R.id.useritem_tv_price);
		holder.giftName = (TextView)convertView.findViewById(R.id.useritem_tv_title);
		holder.cb = (CheckBox)convertView.findViewById(R.id.checkBox);
		holder.ar = (ImageView)convertView.findViewById(R.id.arrow);
		holder.layout = (LinearLayout)convertView.findViewById(R.id.main_ll_itemUserList);
	}


	public void onClick(View arg){

	}








}