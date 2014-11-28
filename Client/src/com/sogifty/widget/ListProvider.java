package com.sogifty.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.sogifty.model.Friend;
import com.sogifty.model.Gift;

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
	private List<Friend> listUsers = new ArrayList<Friend>();
	private List<Gift> listGift = new ArrayList<Gift>();
	private Context context = null;



	public ListProvider(Context context, Intent intent, List<Friend> list, List<Gift> listGift) {
		this.context = context;

		this.listGift = listGift; 
		listUsers = list;
		getImageInCache();
	}

	private void getImageInCache() {
		Iterator<Friend> i = listUsers.iterator();

		while (i.hasNext()) {
			Friend f = i.next();
			UrlImageViewHelper.loadUrlDrawable(context, f.getAvatar());
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
	
	public Gift getGiftForFriend(Friend f){
		Iterator<Gift> i = listGift.iterator();

		while (i.hasNext()) {
			Gift g = i.next();
			if (g.getFriendId() == Integer.toString(f.getId())){
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
		Friend f = listUsers.get(position);
		Gift g = getGiftForFriend(f);
		
		remoteView.setTextViewText(R.id.heading, f.getNom());
		remoteView.setTextViewText(R.id.content, "Anniversaire dans "+f.getRemainingDay()+" jours.");
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
        
        extrasForFriendDetail.putString(WidgetProvider.EXTRA_FRIEND_INDEX, Integer.toString(f.getId()));
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
