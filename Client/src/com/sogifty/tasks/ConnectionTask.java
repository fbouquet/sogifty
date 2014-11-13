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
import com.sogifty.activities.ParserJson;

public class ConnectionTask extends AsyncTask<String,Integer,Integer>{

	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String PASSWD = "pwd";
	private static final String EMPTY_ERROR = "response_empty_error";
	private static final String MALDORMED_ERROR = "malformed_error";
	private static final String PROTOCOL_ERROR = "protocol_error";
	private static final String IO_ERROR = "io_error";
	private static final String LOADING = "Loading..";
	private static final String USER_ID = "user_id";
	private static final String FINALLY_ERROR = "error";
	
	private ProgressDialog progressDialog;
	private Context context;
	
	public ConnectionTask(Context context) {
		this.context = context;
		this.progressDialog = new ProgressDialog(this.context);
	}
	
	
	@Override
    protected void onPreExecute()
    {
        progressDialog = ProgressDialog.show(this.context,"",LOADING);
    }
	
	@Override
	protected Integer doInBackground(String... userConnectionItems) {
		return callServerConnectionWebService(userConnectionItems[0],userConnectionItems[1],this.context.getResources().getString(R.string.web_url_init));
	}
	@Override
	protected void onPostExecute(Integer resultId){
		saveMyId(resultId);
		progressDialog.dismiss();
	}
	
	private void saveMyId(Integer resultId) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(USER_ID, resultId);
		editor.commit();
	}
	
	private int callServerConnectionWebService(String email, String passwd, String webServiceUrlInit) {
		if(isConnected()){
			String idUserJsonString = POST(email,passwd,webServiceUrlInit);
			if(idUserJsonString.equals(EMPTY_ERROR)  
					|| idUserJsonString.equals(MALDORMED_ERROR)
					|| idUserJsonString.equals(PROTOCOL_ERROR)
					|| idUserJsonString.equals(IO_ERROR)
					|| idUserJsonString.equals(FINALLY_ERROR)
					){
				return this.context.getResources().getInteger(R.integer.user_id_error);
			}
			else{
				ParserJson parser = new ParserJson(idUserJsonString);
				String id = parser.executeParseId();
				return Integer.parseInt(id);
			}
		}
		else{
			return this.context.getResources().getInteger(R.integer.user_id_error);
		}
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
        String result = "";
        try {
        	System.out.println("dans Post1"+inputStream);
            
        	HttpClient httpclient = new DefaultHttpClient();
        	System.out.println("dans Post2"+inputStream);
            
        	HttpPost httpPost = new HttpPost(url);
        	System.out.println("dans Post3"+inputStream);
            
            String json = "";
            JSONObject userJsonObject = new JSONObject();
            try {
				userJsonObject.put(NAME, email);
			} catch (JSONException e) {
				e.printStackTrace();
			}
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
            System.out.println("dans Post4"+inputStream);
            
            json = userJsonObject.toString();
 
            StringEntity se = new StringEntity(json);
            System.out.println("dans Post5"+inputStream);
            
            httpPost.setEntity(se);
 
            
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            System.out.println("dans Post6"+inputStream);
            
            HttpResponse httpResponse = httpclient.execute(httpPost);
            System.out.println("dans Post7"+inputStream);
            
            inputStream = httpResponse.getEntity().getContent();
            System.out.println("dans Post8"+inputStream);
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = EMPTY_ERROR;
 
	        } catch (MalformedURLException e){
	        	result = MALDORMED_ERROR;
	            e.printStackTrace();
			} catch (ProtocolException e){
				result = PROTOCOL_ERROR;
	            e.printStackTrace();
			} catch (IOException e){
				result = IO_ERROR;
	            e.printStackTrace();
			}finally {
				if(inputStream != null){
					closeInputStream(inputStream);
				}
				
			}
        System.out.println("iciiiiii"+result);
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
