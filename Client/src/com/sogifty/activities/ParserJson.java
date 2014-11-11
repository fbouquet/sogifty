package com.sogifty.activities;

import org.json.JSONObject;
import org.json.JSONException;

import android.content.Context;


public class ParserJson {
	private static final String ERROR = "error";
	Context context = null;
	String stringToParse;
	
	public ParserJson(String stringToParse){
		this.stringToParse = stringToParse;
	}
	
	public String executeParse(){
		try{
			JSONObject json = new JSONObject(stringToParse);
			JSONObject user =  json.getJSONObject("user");
			return user.getString("name");
			
		}catch (JSONException e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
