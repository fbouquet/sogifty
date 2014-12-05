package com.sogifty.activities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.sogifty.model.Friend;
import com.sogifty.model.Friends;


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
			JSONArray tagsJson;
			Friend f;
			for(int i=0;i<friends.length();++i){
				f = new Friend();
				friendJson = friends.getJSONObject(i);
				name = friendJson.getString("name");
				birthday = friendJson.getString("birthdate");
				id = friendJson.getString("id");
				tagsJson = friendJson.getJSONArray("tags");
				List<String> tagsString = new ArrayList<String>();
				for (int j=0;j<tagsJson.length();++j) {
					tagsString.add(tagsJson.getJSONObject(j).getString("label"));
				}
				f.setTags(tagsString);
				f.setNom(name);
				f.setAge(getAge(birthday));
				f.setId(Integer.parseInt(id));
				f.setRemainingDay(getRemainingDay(birthday));
				f.setBirthdayDate(birthday);
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
	
	static public int getAge(String birthdate){
		
		Calendar calendar =new GregorianCalendar();
		calendar.setTime(new Date());
		int currentYear = calendar.get(Calendar.YEAR);
		
		Date birthDate;
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		int birthYear = 0;
		int age = 0;
		try {
			birthDate = myFormat.parse(birthdate);
			calendar.setTime(birthDate);
			birthYear = calendar.get(Calendar.YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		age = currentYear - birthYear;
		return age;
		
	}
	
	static public long getRemainingDay(String birthdate){
		
		Date currentDate = new Date();
		
		SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
		String birthDateSplit[] = birthdate.split("-");
		
		Calendar calendar =new GregorianCalendar();
		calendar.setTime(currentDate);
		String birthday = birthDateSplit[0]+"-"+birthDateSplit[1]+"-"+calendar.get(Calendar.YEAR);
		
		Date birthDate;
		long remainingDay = 0;
		try {
			birthDate = myFormat.parse(birthday);
			remainingDay = TimeUnit.DAYS.convert(birthDate.getTime()-currentDate.getTime(), TimeUnit.MILLISECONDS);
			if(remainingDay <0){
				birthday = birthDateSplit[0]+"-"+birthDateSplit[1]+"-"+(calendar.get(Calendar.YEAR)+1);
				birthDate = myFormat.parse(birthday);
				remainingDay = TimeUnit.DAYS.convert(birthDate.getTime()-currentDate.getTime(), TimeUnit.MILLISECONDS);
				
			}
				
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return remainingDay;
	}
	
}
