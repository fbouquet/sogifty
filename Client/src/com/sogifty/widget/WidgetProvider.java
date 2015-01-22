package com.sogifty.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.sogifty.R;
import com.sogifty.activities.StartActivity;
import com.sogifty.model.Friend;
import com.sogifty.model.Friends;
import com.sogifty.model.Gift;
import com.sogifty.tasks.GetFriendListTask;
import com.sogifty.tasks.GetGiftsTask;
import com.sogifty.tasks.listeners.OnGetFriendListTaskListener;
import com.sogifty.tasks.listeners.OnGetGiftsTaskListener;

public class WidgetProvider extends AppWidgetProvider implements OnGetFriendListTaskListener, OnGetGiftsTaskListener{
	 public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
	 public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";
	 public static final String EXTRA_URL_GIFT = "com.example.android.stackwidget.EXTRA_URL_GIFT";
	 public static final String FRIEND_INFOS = "com.example.android.stackwidget.FRIEND_INFOS";
	 public static final String EXTRA_FRIEND_INDEX = "com.example.android.stackwidget.EXTRA_FRIEND_INDEX";
	
	/* 
	 * this method is called every 30 mins as specified on widgetinfo.xml
	 * this method is also called on every phone reboot
	 */
	 private AppWidgetManager awm;
	 int[] appWidgetIds;
	 Context context;
	 
	static public List<Gift> listGift = new ArrayList<Gift>();
	static public List<Friend> listFriends = new ArrayList<Friend>();
		
	 
	 
	 
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		awm = appWidgetManager;
		this.appWidgetIds = appWidgetIds;
		this.context = context;
		
		new GetFriendListTask(context, this, true).execute();
		
		
	}
	
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(TOAST_ACTION)) {
        	if(intent.hasExtra(EXTRA_URL_GIFT)){
	        	int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
	            String url = intent.getStringExtra(EXTRA_URL_GIFT);
	            
	            Intent i = new Intent(Intent.ACTION_VIEW);
	            i.setData(Uri.parse(url));
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(i);
        	}
        	else if (intent.hasExtra(EXTRA_FRIEND_INDEX)){
		            
	            Intent i = new Intent(context, StartActivity.class);
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(i);
	        }
        	
        }
        
        super.onReceive(context, intent);
    }
	
	@Override
	public void onGetFriendListComplete(Friends friendsListparam) {
		listFriends = friendsListparam.getListFriends();
		System.out.println("getFriendListComplete");
		if(listFriends != null && listFriends.size() != 0){
			new GetGiftsTask(context, this, true).execute(String.valueOf(listFriends.get(0).getId()));
			
		}
		else{
			if(listFriends.isEmpty())
				displayMessage(context.getResources().getString(R.string.friendlist_no_friend));
		}

	}

	@Override
	public void onGetFriendListFailed(String message) {
		displayMessage(message);
	}



	@Override
	public void onGetGiftsComplete(List<Gift> giftList) {
		System.out.println("getGiftListComplete");
		this.listGift = giftList;
		updateAfterTasks();
	}
	
	private void updateAfterTasks(){
		System.out.println("updating after task");
		final int N = appWidgetIds.length;
		/*int[] appWidgetIds holds ids of multiple instance of your widget
		 * meaning you are placing more than one widgets on your homescreen*/
		for (int i = 0; i < N; ++i) {
            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            rv.setRemoteAdapter(R.id.listViewWidget, intent);
            rv.setEmptyView(R.id.listViewWidget, R.id.empty_view);

            Intent toastIntent = new Intent(context, WidgetProvider.class);
            toastIntent.setAction(WidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.listViewWidget, toastPendingIntent);
            
            awm.updateAppWidget(appWidgetIds[i], rv);
		}

		
		super.onUpdate(context, awm, appWidgetIds);
	}


	@Override
	public void onGetGiftsFailed(String message) {
		displayMessage(message);
	}
	
	
	private void displayMessage(String message){
		AlertDialog.Builder adb = new AlertDialog.Builder(context);
		adb.setMessage(message);
		AlertDialog ad = adb.create();
		ad.show();
	}

	
	

}
