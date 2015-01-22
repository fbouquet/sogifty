package com.sogifty.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.sogifty.R;
import com.sogifty.activities.ParserJson;
import com.sogifty.model.Friends;
import com.sogifty.model.Gift;
import com.sogifty.tasks.listeners.OnGetFriendListTaskListener;
import com.sogifty.tasks.listeners.OnGetGiftsTaskListener;

public class GetGiftsTask extends AsyncTask<String, String, Boolean>{
	private static final String USER_ID = "user_id";
	private static final String LOADING = "Loading..";
	private static final String URL_SUFFIX_REGISTER = "users/<userId>/friends/<friendId>/gifts";
	
	private ProgressDialog progressDialog;
	private Context context;
	private int httpStatus;
	private List<Gift> giftList;
	private String errorMessage;
	private OnGetGiftsTaskListener callback;
	private boolean widget;
	
	public GetGiftsTask(Context context, OnGetGiftsTaskListener callback, boolean widget) {
		this.context = context;
		this.progressDialog = new ProgressDialog(this.context);
		this.callback = callback;
		this.widget = widget;
	}
	
	
	@Override
    protected void onPreExecute()
    {
		if (widget == false)
			progressDialog = ProgressDialog.show(this.context,"",LOADING);
    }
	@Override
	protected Boolean doInBackground(String... userConnectionItems) {
		String urlKnowingUserId = this.context.getResources().getString(R.string.web_url_init)+URL_SUFFIX_REGISTER.replace("<userId>", String.valueOf(loadUserId()));
		String urlKnowingFriendId = urlKnowingUserId.replace("<friendId>", userConnectionItems[0]);
		System.out.println("friend Id :::"+userConnectionItems[0]);
		System.out.println("url :::"+urlKnowingFriendId);
	    return callServerConnectionWebService(urlKnowingFriendId);
	}
	@Override
	protected void onPostExecute(Boolean resultCall){
		if(resultCall.booleanValue()){
			Collections.sort(giftList);
			callback.onGetGiftsComplete(giftList);
		}
		else{
			callback.onGetGiftsFailed(errorMessage);
		}
		progressDialog.dismiss();
	}
	
	
	private boolean callServerConnectionWebService(String webServiceUrlInit) {
		System.out.println("try to connect");
	       
		if(isConnected()){
			String result = GET(webServiceUrlInit);
			if(httpStatus == 200){
				ParserJson parser = new ParserJson(result);
				giftList = parser.executeParseGift();
				return true;
			}
			else{
				errorMessage = getErrorMessage();
				return false;
			}
		}
		else{
			errorMessage =  this.context.getResources().getString(R.string.no_network_connection_error);
			return false;
		}
	}
	
	
	
	private String getErrorMessage() {
		String message = context.getResources().getString(R.string.unknown_error);
		System.out.println(httpStatus);
		if(httpStatus == context.getResources().getInteger(R.integer.user_http_error)){
			message = context.getResources().getString(R.string.http_error);
		}
		else if(httpStatus == context.getResources().getInteger(R.integer.intern_error)){
			message = context.getResources().getString(R.string.internal_error);
		}
		return message;
	}


	public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) 
            return true;
        else
            return false;    
    }
	public String GET(String url){
        InputStream inputStream = null;
        String result = null;
        System.out.println("try to get on "+url);
        try {
        	HttpClient httpclient = new DefaultHttpClient();
        	
        	HttpGet httpGet = new HttpGet(url);
        
            
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            System.out.println("try to execute");
            
            HttpResponse httpResponse = httpclient.execute(httpGet);
            System.out.println("exectuted on "+url);
            
            inputStream = httpResponse.getEntity().getContent();
            httpStatus = httpResponse.getStatusLine().getStatusCode();
            android.util.Log.i("status",""+httpStatus);
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
            }
            else{
                httpStatus = this.context.getResources().getInteger(R.integer.user_http_error);
            }	
	        } catch (MalformedURLException e){
	        	httpStatus = this.context.getResources().getInteger(R.integer.user_http_error);
	            e.printStackTrace();
			} catch (ProtocolException e){
				httpStatus = this.context.getResources().getInteger(R.integer.user_http_error);
	            e.printStackTrace();
			} catch (IOException e){
				httpStatus = this.context.getResources().getInteger(R.integer.user_http_error);
	            e.printStackTrace();
			}finally {
				if(inputStream != null){
					closeInputStream(inputStream);
				}
				
			}
        return result;
    }
	private void closeInputStream(InputStream inputStream) {
		try{
			inputStream.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}


	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
	private int loadUserId(){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getInt(USER_ID, context.getResources().getInteger(R.integer.user_id_default));
	}
	
}
