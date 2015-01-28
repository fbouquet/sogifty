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
//        URI uri = null;
//		URL url = null;
//
//		//create URI
//		try {
//		    uri = new URI(urldisplay);
//		    System.out.println("URI created: " + uri);
//		}
//		catch (URISyntaxException e) {
//			System.out.println("URI Syntax Error: " + e.getMessage());
//		}
//
//		// Convert URI to URL
//		try {
//		    url = uri.toURL();
//		    System.out.println("URL from URI: " + url);
//		}
//		catch (MalformedURLException e) {
//			System.out.println("Malformed URL: " + e.getMessage());
//		}
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

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}