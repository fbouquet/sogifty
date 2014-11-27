package com.sogifty.activities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.sogifty.model.Friend;
import com.sogifty.model.Friends;

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
	public Friends executeParseFriendList(){
		try{
			JSONArray friends = new JSONArray(stringToParse);
			Friends friendList = new Friends();
			String birthday;
			String name;
			String id;
			JSONObject friendJson;
			Friend f;
			for(int i=0;i<friends.length();++i){
				f = new Friend();
				friendJson = friends.getJSONObject(i);
				name = friendJson.getString("name");
				System.out.println(name);
				birthday = friendJson.getString("birthdate");
				System.out.println(birthday);
				id = friendJson.getString("id");
				System.out.println(id);
				f.setNom(name);
				f.setAge(getCurrentDate().annee - getFriendBirthDate(birthday).annee);
				f.setId(Integer.parseInt(id));
				f.setRemainingDay((getCurrentDate().jour-getFriendBirthDate(birthday).jour));
				friendList.addFriend(f);
				
			}
			return friendList;
			
		}catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public class persoDate {
		int annee;
		int mois;
		int jour;
	}
	
	public persoDate getCurrentDate(){
		Calendar calendar =new GregorianCalendar();
		calendar.setTime(new Date());
		
		persoDate pD = new persoDate();
		
		pD.annee =calendar.get(Calendar.YEAR);
		pD.mois =calendar.get(Calendar.MONTH);
		pD.jour =calendar.get(Calendar.DAY_OF_MONTH);
		
		return pD;
		
	}
	
	public persoDate getFriendBirthDate(String date){
		String[] dateItems = date.split("-");
		persoDate pD = new persoDate();
		pD.jour = Integer.parseInt(dateItems[0]);
		pD.mois = Integer.parseInt(dateItems[1]);		
		pD.annee = Integer.parseInt(dateItems[2]);
		return pD;
	}
	
}
