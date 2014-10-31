package com.sogifty.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.sogifty.model.Gift;
import com.sogifty.model.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;
import com.sogifty.R;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter with
 * few changes
 * 
 */
public class ListProvider implements RemoteViewsFactory {
	private List<User> listUsers = new ArrayList<User>();
	private List<Gift> listGift = new ArrayList<Gift>();
	private Context context = null;



	public ListProvider(Context context, Intent intent, List<User> list, List<Gift> listGift) {
		this.context = context;

		this.listGift = listGift; 
		listUsers = list;
		getImageInCache();
	}

	private void getImageInCache() {
		Iterator<User> i = listUsers.iterator();

		while (i.hasNext()) {
			User u = i.next();
			UrlImageViewHelper.loadUrlDrawable(context, u.getAvatar());
		}
		
		Iterator<Gift> i2 = listGift.iterator();

		while (i2.hasNext()) {
			Gift g = i2.next();
			UrlImageViewHelper.loadUrlDrawable(context, g.getImgUrl() );
		}
		

	}

	public int getCount() {
		return listUsers.size();
	}

	public long getItemId(int position) {
		return position;
	}
	
	public Gift getGiftForFriend(User u){
		Iterator<Gift> i = listGift.iterator();

		while (i.hasNext()) {
			Gift g = i.next();
			if (g.getFriendId() == u.getId()){
				return g;
			}
		}
		
		return null;
	}

	/*
	 * Similar to getView of Adapter where instead of Viewwe return RemoteViews
	 */

	public RemoteViews getViewAt(int position) {
		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.list_row);
		User u = listUsers.get(position);
		Gift g = getGiftForFriend(u);
		
		remoteView.setTextViewText(R.id.heading, u.getNom());
		remoteView.setTextViewText(R.id.content, "Anniversaire dans "+u.getRemainingDay()+" jours.");
		if(g.getPrice() == null)
			remoteView.setTextViewText(R.id.price, "?€");
		else	
			remoteView.setTextViewText(R.id.price, g.getPrice());

		remoteView.setBitmap(R.id.imageView, "setImageBitmap",
				UrlImageViewHelper.getCachedBitmap(g.getImgUrl()));
		
		/*Intent fillInIntent = new Intent();
        fillInIntent.putExtra(WidgetProvider.TOAST_ACTION, position);
        remoteView.setOnClickFillInIntent(R.id.imageView, fillInIntent);*/
		
		// Next, set a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in StackWidgetProvider.
        Bundle extras = new Bundle();
        
        //Rajouter ici les extras que l'on veut, par exemple : spécifier l'url du cadeau à ouvrir dans le navigateur
        //String giftUrl = u.getUrl(); 
        extras.putString(WidgetProvider.EXTRA_URL_GIFT, g.getUrl());
        extras.putInt(WidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        // Make it possible to distinguish the individual on-click
        // action of a given item
        remoteView.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        
        
        Bundle extrasForFriendDetail = new Bundle();
        
        extrasForFriendDetail.putString(WidgetProvider.EXTRA_FRIEND_INDEX, u.getId());
        Intent fillInIntentDetail = new Intent();
        fillInIntentDetail.putExtras(extrasForFriendDetail);
        remoteView.setOnClickFillInIntent(R.id.heading, fillInIntentDetail);
        remoteView.setOnClickFillInIntent(R.id.content, fillInIntentDetail);
        remoteView.setOnClickFillInIntent(R.id.price, fillInIntentDetail);

		return remoteView;
	}

	public RemoteViews getLoadingView() {
		return null;
	}

	public int getViewTypeCount() {
		return 1;
	}

	public boolean hasStableIds() {
		return true;
	}

	public void onCreate() {
	}

	public void onDataSetChanged() {
	}

	public void onDestroy() {
	}

}
