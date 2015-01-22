package com.sogifty.activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.sogifty.model.Gifts;

public class GiftPagerAdapter extends FragmentStatePagerAdapter {
	
   private static final int NB_GIFTS_DEFAULT_VALUE = 0;
	private static final String NB_GIFTS = "nb_gifts";
    private Gifts giftsToPrint;
    private Context ctx;
	
    public GiftPagerAdapter(FragmentManager fragmentManager, Gifts gifts, Context ctx) {
        super(fragmentManager);
        giftsToPrint = gifts;
        this.ctx = ctx;
       
    }

    @Override
    public GiftPager getItem(int position) {
    	
        return GiftPager.create(position, giftsToPrint, this.getCount());
    }

    @Override
    public int getCount() {
        return loadNbGifts();
    }
    private int loadNbGifts(){
    	SharedPreferences preferences  = PreferenceManager.getDefaultSharedPreferences(ctx);
    	return preferences.getInt(NB_GIFTS, NB_GIFTS_DEFAULT_VALUE);
    }
}