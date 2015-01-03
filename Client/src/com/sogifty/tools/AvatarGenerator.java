package com.sogifty.tools;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class AvatarGenerator {
	static public String generateUrlAvatar(String name, String gender){
		//String assets = context.getAssets().open();
		String path = "avatars";
		int sum = 0;
		int nbOfImages = 0;
		
		for(int u = 0; u < name.length(); u++){
			sum += name.charAt(u);
		}
		
				
		if(gender.equalsIgnoreCase("F")){
			path += "/F/";
			nbOfImages = 4;
			Log.i("GENDER", gender);
		} else {
			path += "/M/";
			nbOfImages = 9;
		}
		
		return path + String.valueOf(sum % nbOfImages + 1) + ".png";
	}
	
	static public Bitmap generate(String name, String gender, Context context) throws IOException{
		
			return BitmapFactory.decodeStream(context.getAssets().open(generateUrlAvatar(name, gender)));
	}
	
	
	
	
}
