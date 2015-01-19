package com.sogifty.widget;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.sogifty.activities.Constants;
import com.sogifty.activities.Setting;
import com.sogifty.model.Friend;
import com.sogifty.model.Gift;

public class WidgetService extends RemoteViewsService {
	private List<Gift> listGift = new ArrayList<Gift>();
	private List<Friend> listFriends = new ArrayList<Friend>();
	
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		int appWidgetId = intent.getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
		Constants projectConstants = new Constants();

		SoapObject  request = new SoapObject(
				projectConstants.WSDL_TARGET_NAMESPACE,
				projectConstants.OPERATION_GET_NOTIFICATION_FRIEND);

		request.addProperty("UserId", Setting.UserID);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(
				projectConstants.URL);
		
		listFriends = WidgetProvider.listFriends;
		listGift = WidgetProvider.listGift;
		
		return (new ListProvider(this.getApplicationContext(), intent, listFriends, listGift));
	}

	

}
