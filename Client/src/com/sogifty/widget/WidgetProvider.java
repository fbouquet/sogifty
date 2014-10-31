package com.sogifty.widget;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.sogifty.activities.Constants;
import com.sogifty.activities.FriendDetailActivity;
import com.sogifty.activities.HelloAndroidActivity;
import com.sogifty.activities.Setting;
import com.sogifty.activities.UserAdapter;
import com.sogifty.model.User;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.sogifty.R;

public class WidgetProvider extends AppWidgetProvider {
	 public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
	 public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";
	 public static final String EXTRA_URL_GIFT = "com.example.android.stackwidget.EXTRA_URL_GIFT";
	 public static final String FRIEND_INFOS = "com.example.android.stackwidget.FRIEND_INFOS";
	 public static final String EXTRA_FRIEND_INDEX = "com.example.android.stackwidget.EXTRA_FRIEND_INDEX";

	/* 
	 * this method is called every 30 mins as specified on widgetinfo.xml
	 * this method is also called on every phone reboot
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		/*int[] appWidgetIds holds ids of multiple instance of your widget
		 * meaning you are placing more than one widgets on your homescreen*/
		for (int i = 0; i < N; ++i) {
			
			//RemoteViews remoteViews = updateWidgetListView(context,appWidgetIds[i], appWidgetManager);
			


            // Sets up the intent that points to the StackViewService that will
            // provide the views for this collection.
            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            // When intents are compared, the extras are ignored, so we need to embed the extras
            // into the data so that the extras will not be ignored.
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            rv.setRemoteAdapter(R.id.listViewWidget, intent);
    
            // The empty view is displayed when the collection has no items. It should be a sibling
            // of the collection view.
            rv.setEmptyView(R.id.listViewWidget, R.id.empty_view);

            // This section makes it possible for items to have individualized behavior.
            // It does this by setting up a pending intent template. Individuals items of a collection
            // cannot set up their own pending intents. Instead, the collection as a whole sets
            // up a pending intent template, and the individual items set a fillInIntent
            // to create unique behavior on an item-by-item basis.
            Intent toastIntent = new Intent(context, WidgetProvider.class);
            // Set the action for the intent.
            // When the user touches a particular view, it will have the effect of
            // broadcasting TOAST_ACTION.
            toastIntent.setAction(WidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.listViewWidget, toastPendingIntent);
            
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);


			//appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
			
			
		}

		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
	public void onReceive(Context context, Intent intent) {
               
        if (intent.getAction().equals(TOAST_ACTION)) {
        	if(intent.hasExtra(EXTRA_URL_GIFT)){
	        	int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
	            //Toast.makeText(context, "Touched view " + String.valueOf(viewIndex), Toast.LENGTH_SHORT).show();
	            String url = intent.getStringExtra(EXTRA_URL_GIFT);
	            
	            Intent i = new Intent(Intent.ACTION_VIEW);
	            i.setData(Uri.parse(url));
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(i);
        	}
        	else if (intent.hasExtra(EXTRA_FRIEND_INDEX)){
	        	int friendIndex = intent.getIntExtra(EXTRA_FRIEND_INDEX, 0);
	            //Toast.makeText(context, "Touched view " + String.valueOf(viewIndex), Toast.LENGTH_SHORT).show();
	            
	            Intent i = new Intent(context, FriendDetailActivity.class);
	            /* REPLACER TOUT CA PAR JUSTE friendId*/
	            //i.putExtra("friendId", friendIndex);
	            
	            i.putExtra("name", "test");
				
				i.putExtra("avatar", "lol");
				i.putExtra("id", friendIndex);
	            
	            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            context.startActivity(i);
	        }
        	
        }
        
        super.onReceive(context, intent);
    }
	
	

}
