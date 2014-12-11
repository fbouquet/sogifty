package com.sogifty.activities;

import java.util.List;

import com.sogifty.model.Gift;
import com.sogifty.model.Gifts;

import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

public class GiftPagerAdapter extends FragmentStatePagerAdapter {
	
    private static final int NUMBER_GIFT_MAX = 5;
    private Gifts giftsToPrint;
	
    public GiftPagerAdapter(FragmentManager fragmentManager, Gifts gifts) {
        super(fragmentManager);
        giftsToPrint = gifts;
       
    }

    @Override
    public GiftPager getItem(int position) {
    	
        return GiftPager.create(position, giftsToPrint);
    }

    @Override
    public int getCount() {
        return NUMBER_GIFT_MAX;
    }
}