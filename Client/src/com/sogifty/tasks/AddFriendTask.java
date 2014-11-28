package com.sogifty.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.sogifty.R;
import com.sogifty.tasks.listeners.OnAddFriendTaskListener;

public class AddFriendTask extends AsyncTask<String,Integer,Boolean>{
	private static final String USER_ID = "user_id";
	private static final String LOADING = "Loading..";
	private static final String URL_SUFFIX_REGISTER = "users/<userId>/friends";
	private static final String NAME = "name";
	private static final String BIRTHDATE = "birthdate";
	
	private ProgressDialog progressDialog;
	private Context context;
	private int httpStatus;
	private String errorMessage;
	private OnAddFriendTaskListener callback;
	
	public AddFriendTask(Context context, OnAddFriendTaskListener callback) {
		this.context = context;
		this.progressDialog = new ProgressDialog(this.context);
		this.callback = callback;
	}
	
	
	@Override
    protected void onPreExecute()
    {
        progressDialog = ProgressDialog.show(this.context,"",LOADING);
    }
	
	@Override
	protected Boolean doInBackground(String... userConnectionItems) {
		return callServerConnectionWebService(userConnectionItems[0],userConnectionItems[1],
				this.context.getResources().getString(R.string.web_url_init)+URL_SUFFIX_REGISTER.replace("<userId>", String.valueOf(loadUserId())));
	}
	@Override
	protected void onPostExecute(Boolean resultCall){
		if(resultCall.booleanValue()){
			callback.onAddFriendComplete();
		}
		else{
			callback.onAddFriendFailed(errorMessage);
		}
		progressDialog.dismiss();
	}
	
	
	private boolean callServerConnectionWebService(String name, String birthdate, String webServiceUrlInit) {
		if(isConnected()){
			POST(name,birthdate,webServiceUrlInit);
			if(httpStatus == 200){
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
	public String POST(String name, String birthdate, String url){
        InputStream inputStream = null;
        String result = null;
        try {
        	System.out.println(name);
        	System.out.println(birthdate);
        	System.out.println(url);
        	HttpClient httpclient = new DefaultHttpClient();
        	
        	HttpPost httpPost = new HttpPost(url);
        	
            String json = "";
            JSONObject userJsonObject = new JSONObject();
            try {
				userJsonObject.put(NAME, name);
			} catch (JSONException e) {
				e.printStackTrace();
			}
            try {
				userJsonObject.put(BIRTHDATE, birthdate);
			} catch (JSONException e) {
				e.printStackTrace();
			}
            json = userJsonObject.toString();
            System.out.println(userJsonObject.toString());
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            
            HttpResponse httpResponse = httpclient.execute(httpPost);
            
            inputStream = httpResponse.getEntity().getContent();
            httpStatus = httpResponse.getStatusLine().getStatusCode();
            android.util.Log.i("status",""+httpStatus);
            System.out.println("add");
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
