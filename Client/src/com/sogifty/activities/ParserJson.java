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
	
	public String executeParseId(){
		try{
			JSONObject userId = new JSONObject(stringToParse);
			return userId.getString("id");
			
		}catch (JSONException e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
