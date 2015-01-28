package com.sogifty.tasks;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        System.out.println("imageUrl : "+urldisplay);
        if(urldisplay != null && urldisplay.compareTo("") != 0){
	        Bitmap mIcon11 = null;
	        try {
	        	URL url = new URL(urldisplay);
	            InputStream in = url.openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return mIcon11;
        }
        else{
        	return null;
        }
        	
        
    }

    protected void onPostExecute(Bitmap result) {
    	if(result != null){
    		bmImage.setImageBitmap(result);
    	}
    	
    }
}