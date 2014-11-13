package com.sogifty.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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

	private static final String EMAIL = "email";
	private static final String PASSWD = "passwd";
	private static final String USER = "user";
	private static final String EMPTY_ERROR = "response_empty_error";
	private static final int GET_ID_ERROR = -1;
	private static final String LOADING = "loading..";
	private static final String USER_ID = "user_id";
	
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
			if(idUserJsonString != EMPTY_ERROR){
				ParserJson parser = new ParserJson(idUserJsonString);
				String id = parser.executeParseId();
				return Integer.parseInt(id);
			}
			else{
				return GET_ID_ERROR;
			}
		}
		else{
			return GET_ID_ERROR;
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
        	HttpClient httpclient = new DefaultHttpClient();
        	
        	HttpPost httpPost = new HttpPost(url);
 
            String json = "";
            JSONObject insideJsonObject = new JSONObject();
            insideJsonObject.put(EMAIL, email);
            insideJsonObject.put(PASSWD, passwd);
            JSONObject userJsonObject = new JSONObject();
            userJsonObject.put(USER,insideJsonObject);
            json = userJsonObject.toString();
 
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
 
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            inputStream = httpResponse.getEntity().getContent();
 
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = EMPTY_ERROR;
 
        } catch (Exception e) {
            e.printStackTrace();;
        }
 
        return result;
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
