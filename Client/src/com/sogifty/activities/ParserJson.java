package com.sogifty.activities;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.Context;

import com.sogifty.model.Users;

public class ParserJson {
	Users usersList = new Users();
	Context context = null;
	public ParserJson(Context c){
		context = c;
	}
	
	private static ObjectMapper sMapper = new ObjectMapper();
	static {
		sMapper.configure(
				org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);
	}
	

	public final Users gettingJson() {

		try {
			usersList = sMapper.readValue(context.getAssets().open("users.json"),
					Users.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return usersList;
		// Toast.makeText(this,
		// String.valueOf(usersList.getUserById(5).getId()),
		// Toast.LENGTH_LONG).show();

	}
}
