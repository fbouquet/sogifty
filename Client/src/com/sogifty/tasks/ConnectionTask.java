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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.sogifty.R;
import com.sogifty.activities.ParserJson;
import com.sogifty.tasks.listeners.OnConnectionTaskListener;

public class ConnectionTask extends AsyncTask<String,Integer,Boolean>{

	private static final String EMAIL = "email";
	private static final String PASSWD = "pwd";
	private static final String LOADING = "Loading..";
	private static final String URL_SUFFIX_REGISTER = "login";
	private static final int ALREADY_EXISTS_INTEGER = 404;
	
	
	private ProgressDialog progressDialog;
	private Context context;
	private int httpStatus;
	private int userId;
	private String errorMessage;
	private OnConnectionTaskListener callback;
	
	public ConnectionTask(Context context, OnConnectionTaskListener connectionActivity) {
		this.context = context;
		this.progressDialog = new ProgressDialog(this.context);
		this.callback = connectionActivity;
	}
	
	
	@Override
    protected void onPreExecute()
    {
        progressDialog = ProgressDialog.show(this.context,"",LOADING);
    }
	
	@Override
	protected Boolean doInBackground(String... userConnectionItems) {
		return callServerConnectionWebService(userConnectionItems[0],userConnectionItems[1],
				this.context.getResources().getString(R.string.web_url_init)+URL_SUFFIX_REGISTER);
	}
	@Override
	protected void onPostExecute(Boolean resultCall){
		if(resultCall.booleanValue()){
			callback.onConnectionComplete(userId);
		}
		else{
			callback.onConnectionFailed(errorMessage);
		}
		progressDialog.dismiss();
	}
	
	
	private boolean callServerConnectionWebService(String email, String passwd, String webServiceUrlInit) {
		if(isConnected()){
			String result = POST(email,passwd,webServiceUrlInit);
			if(httpStatus == 200){
				ParserJson parser = new ParserJson(result);
				String id = parser.executeParseId();
				userId =  Integer.parseInt(id);
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
		if(httpStatus == context.getResources().getInteger(R.integer.user_http_error)){
			message = context.getResources().getString(R.string.http_error);
		}
		else if(httpStatus == ALREADY_EXISTS_INTEGER){
			message = context.getResources().getString(R.string.user_passwd_no_correspond);
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
	public String POST(String email, String passwd, String url){
        InputStream inputStream = null;
        String result = null;
        try {
        	HttpClient httpclient = new DefaultHttpClient();
        	
        	HttpPost httpPost = new HttpPost(url);
        	
            String json = "";
            JSONObject userJsonObject = new JSONObject();
            try {
				userJsonObject.put(EMAIL, email);
			} catch (JSONException e) {
				e.printStackTrace();
			}
            try {
				userJsonObject.put(PASSWD, passwd);
			} catch (JSONException e) {
				e.printStackTrace();
			}
            json = userJsonObject.toString();
 
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            
            HttpResponse httpResponse = httpclient.execute(httpPost);
            
            inputStream = httpResponse.getEntity().getContent();
            httpStatus = httpResponse.getStatusLine().getStatusCode();
            android.util.Log.i("status",""+httpStatus);
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
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
	
}
