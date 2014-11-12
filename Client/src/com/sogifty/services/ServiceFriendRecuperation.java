package com.sogifty.services;

import com.sogifty.model.Friend;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public class ServiceFriendRecuperation extends IntentService{

	private static final String SERVICE = "service_friend_recuperation";
	private static final String WEBSERVICE_URL_INIT = null;
	private static final Intent BROADCAST_ACTION = null;
	private static final String BROADCAST_VALUE = "com.example.sogifty.service.BROADCAST_VALUE";
	private static String FRIEND_JSON_STRING = "com.example.sogifty.service.FRIEND_JSON_STRING";
	
	public ServiceFriendRecuperation(String name) {
		super(SERVICE);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String friendJson = intent.getStringExtra(FRIEND_JSON_STRING);
		Friend friendToGet = callServerFriendRecuperationService(WEBSERVICE_URL_INIT);
		broadcastResult(friendToGet);
	}
	public static Intent getIntent(Context ctx, String jsonFriend){
		Intent FriendRecuperationServiceIntent = new Intent(ctx, ServiceFriendRecuperation.class);
		FriendRecuperationServiceIntent.putExtra(FRIEND_JSON_STRING, jsonFriend);
		return FriendRecuperationServiceIntent;
	}
	private void broadcastResult(Friend friendToGet) {
		Intent broadcastIntent = new Intent(BROADCAST_ACTION);
		//broadcastIntent.putExtra(BROADCAST_VALUE, friendToGet);
		try {
		    Thread.sleep(1000);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		//LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
	}

	private Friend callServerFriendRecuperationService(String webserviceUrlInit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
