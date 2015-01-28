package com.sogifty.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.sogifty.R;
import com.sogifty.model.Friend;
import com.sogifty.model.Gift;
/**
 * If you are familiar with Adapter of ListView,this is the same as adapter with
 * few changes
 * 
 */
public class ListProvider implements RemoteViewsFactory {
	private static final int WIDTH = 85;
	private static final int HEIGHT = 85;
	private List<Friend> listUsers = new ArrayList<Friend>();
	private List<Gift> listGift = new ArrayList<Gift>();
	private List<String> listBitmapUrl = new ArrayList<String>();
	private List<String> listDescription = new ArrayList<String>();
	private Context context = null;
	

	public ListProvider(Context context, Intent intent, List<Friend> list, List<Gift> listGift) {
		this.context = context;

		this.listGift = listGift; 
		listUsers = list;
		//UrlImageViewHelper.loadUrlDrawable(context, listGift.get(0).getImgUrl());
		if(listGift != null && listGift.size() != 0){
			listBitmapUrl.add(listGift.get(0).getImgUrl());
			listDescription.add("Offrez lui "+listGift.get(0).getName() + "!!");
		}
		else{
			listBitmapUrl.add("");
			listDescription.add("");
		}
		for(int i=1;i<getCount();i++){
			listBitmapUrl.add("");
			listDescription.add("");
		}
		
	}

	public int getCount() {
		return listUsers.size();
	}

	public long getItemId(int position) {
		return position;
	}
	
	
	/*
	 * Similar to getView of Adapter where instead of Viewwe return RemoteViews
	 */

	public RemoteViews getViewAt(int position) {
		final RemoteViews remoteView = new RemoteViews(
				context.getPackageName(), R.layout.list_row);
		Friend f = listUsers.get(position);
		remoteView.setTextViewText(R.id.heading, f.getNom());
		remoteView.setTextViewText(R.id.content, "Anniversaire dans "+f.getRemainingDay()+" jours.");
		if(listBitmapUrl.get(position).compareTo("") == 0){
			remoteView.setBitmap(R.id.imageViewWidget,"setImageBitmap",
					Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888));
			remoteView.setViewVisibility(R.id.imageViewWidget, CheckBox.GONE);
		}
		else{
			System.out.println("lala url ok" + listGift.get(position).getImgUrl());
			System.out.println(f.getNom());
			UrlImageViewHelper.loadUrlDrawable(context, listGift.get(position).getImgUrl());
			try {
			    Thread.sleep(1000);                 
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			remoteView.setBitmap(R.id.imageViewWidget,"setImageBitmap",
					UrlImageViewHelper.getCachedBitmap(listBitmapUrl.get(position)));
			
//			new DownloadImageWidgetTask(remoteView).execute();
			remoteView.setViewVisibility(R.id.imageViewWidget, CheckBox.VISIBLE);
		}
		
		remoteView.setTextViewText(R.id.suggestion,listDescription.get(position));
		
		
		// Next, set a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in StackWidgetProvider.
        Bundle extras = new Bundle();
        
        //Rajouter ici les extras que l'on veut, par exemple : spécifier l'url du cadeau à ouvrir dans le navigateur
        //String giftUrl = u.getUrl(); 
        //extras.putString(WidgetProvider.EXTRA_URL_GIFT, g.getUrl());
        extras.putInt(WidgetProvider.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        // Make it possible to distinguish the individual on-click
        // action of a given item
        remoteView.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntent);
        
        
        Bundle extrasForFriendDetail = new Bundle();
        
        extrasForFriendDetail.putString(WidgetProvider.EXTRA_FRIEND_INDEX, Integer.toString(f.getId()));
        Intent fillInIntentDetail = new Intent();
        fillInIntentDetail.putExtras(extrasForFriendDetail);
        remoteView.setOnClickFillInIntent(R.id.heading, fillInIntentDetail);
        remoteView.setOnClickFillInIntent(R.id.content, fillInIntentDetail);
        remoteView.setOnClickFillInIntent(R.id.suggestion, fillInIntentDetail);
        remoteView.setOnClickFillInIntent(R.id.imageViewWidget, fillInIntentDetail);

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
		System.out.println("create");
	}

	public void onDataSetChanged() {
		System.out.println("datachanged");
	}

	public void onDestroy() {
	}

}
