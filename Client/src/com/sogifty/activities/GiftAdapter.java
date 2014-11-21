package com.sogifty.activities;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sogifty.model.Gift;


public class GiftAdapter extends BaseAdapter {
	List<Gift> gifts;
	LayoutInflater inflater;
	Context context = null;

	
	public GiftAdapter(Context context,List<Gift> gifts) {
		inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.gifts = gifts;
		
	}
	
	
	public int getCount() {
		return gifts.size();
	}

	public Object getItem(int position) {
		return gifts.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	
	private class ViewHolder {
		TextView price;

		ImageView iv;
		
	}
	
	
	


	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if(convertView == null) {
			holder = new ViewHolder();
//			convertView = inflater.inflate(R.layout.giftitem, null);
//
//			holder.price = (TextView)convertView.findViewById(R.id.priceGift);
//			holder.iv = (ImageView)convertView.findViewById(R.id.imageGift);
//			
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Gift g = gifts.get(position);

		holder.price.setText(g.getPrice());
		
		
//		String imgUrl = g.getImgUrl();
//		
//		if(imgUrl != null){
//			UrlImageViewHelper.setUrlDrawable(holder.iv, imgUrl);
//			
//		}
//		else
//			
		holder.iv.setImageResource(android.R.drawable.ic_menu_gallery);
		
		
		

		return convertView;
	}
	
}
