package com.sogifty.widget;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.sogifty.activities.Constants;
import com.sogifty.activities.HelloAndroidActivity;
import com.sogifty.activities.ParserJson;
import com.sogifty.activities.Setting;
import com.sogifty.activities.UserAdapter;
import com.sogifty.model.Gift;
import com.sogifty.model.User;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
	/*
	 * So pretty simple just defining the Adapter of the listview
	 * here Adapter is ListProvider
	 * */
	
	private ParserJson parser = new ParserJson("lala");
	private List<Gift> listGift = new ArrayList<Gift>();
	private List<User> listUsers = new ArrayList<User>();
	
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		int appWidgetId = intent.getIntExtra(
				AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
		//new GetFriendList().execute();
		
		
		/* DEBUT DE LA REQUETE SERVEUR POUR CHOPER CE QU'ON VEUT. PEUT ETRE QU'IL FAUT LA METTRE DANS UNE ASYNC, À TESTER */
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
/*
		try {
			httpTransport.call(
					projectConstants.SOAP_ACTION_GET_FRIEND_LIST, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response == null) {
				Log.i("Demo Test", "reponse null");
			} else {
				Log.i("Demo Test", "reponse not null");
				for (int i = 0; i < response.getPropertyCount(); i++) {
					User u = new User();
					Gift g = new Gift();
					
					SoapObject friendNotif = (SoapObject) response
							.getProperty(i);
					
					u.setId(Integer.parseInt(friendNotif.getPropertyAsString(projectConstants.FriendId)));
					u.setNom(friendNotif.getPropertyAsString(projectConstants.FirstName));
					u.setPrenom(friendNotif.getPropertyAsString(projectConstants.LastName));
					u.setPrenom(friendNotif.getPropertyAsString(projectConstants.AvatarUrl));
					
					
					g.setUrl(friendNotif.getPropertyAsString(projectConstants.giftLink));
					g.setImgUrl(friendNotif.getPropertyAsString(projectConstants.giftThumbnail));
					g.setFriendId(friendNotif.getPropertyAsString(projectConstants.FriendId));
					
					listUsers.add(u);
					listGift.add(g);					
				}


			}
		} catch (Exception exception) {
			Log.i("Exception", exception.getMessage());
		}
		*/
		/* FIN DE LA REQUETE */
		
		//Donc ici c'est cool on passe la liste qu'on veut en paramètre
		User u = new User();
		u.setAvatar("http://image.jeuxvideo.com/images/pc/t/h/the-banner-saga-pc-00x.jpg");
		u.setNom("Julie Belle");
		u.setId("1");
		u.setRemainingDay("2");
		
		listUsers.add(u);
		
		u = new User();
		u.setAvatar("http://image.jeuxvideo.com/chroniques-images/0011/dmc-devil-may-cry-pc-00118146-1389610521-1389614776-accroche.jpg");
		u.setNom("Maxime le Boxeur");
		u.setId("2");
		u.setRemainingDay("4");
		listUsers.add(u);
		
		u = new User();
		u.setAvatar("http://image.jeuxvideo.com/chroniques-images/0011/dmc-devil-may-cry-pc-00118146-1389610521-1389614776-accroche.jpg");
		u.setNom("Simon l'Enfant");
		u.setId("3");
		u.setRemainingDay("7");
		
		listUsers.add(u);
		
		
		Gift g = new Gift();
		
		g.setUrl("http://shop.uniformchanges.co.uk/index.php?main_page=index&cPath=10");
		g.setImgUrl("http://shop.uniformchanges.co.uk/images/G%20SKT01%20box%20pleat%20skirt.jpg");
		g.setFriendId("1");
		
		g.setPrice("19");
		
		listGift.add(g);
		
		g = new Gift();
		
		g.setUrl("http://rhone-alpes.all.biz/gant-de-boxe-adidas-g16327");
		g.setImgUrl("http://www.fr.all.biz/img/fr/catalog/16327.jpeg");
		g.setFriendId("2");
		
		g.setPrice("14");
		
		listGift.add(g);
		
		g = new Gift();
		
		g.setUrl("http://french.alibaba.com/product-gs/toy-car-kids-car-kids-ride-on-car-268704440.html");
		g.setImgUrl("http://img.alibaba.com/photo/268704440/Toy_Car_Kids_Car_Kids_Ride_on_Car.jpg");
		g.setFriendId("3");
		
		g.setPrice("199");
		
		listGift.add(g);
		
		return (new ListProvider(this.getApplicationContext(), intent, listUsers, listGift));
		//return (new ListProvider(this.getApplicationContext(), intent, listUsers, listGift));
	}
	
	class GetFriendList extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			Log.w("TEST", "KIK");
			
			
		}

		protected String doInBackground(String... args) {


			Log.w("TEST", "KIK");
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			Log.e("TEST", "KIK");


		}

	}


}
