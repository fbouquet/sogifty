package com.sogifty.activities;

import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

public class GiftPagerAdapter extends FragmentStatePagerAdapter {
	
    private static final int NUMBER_GIFT_MAX = 5;
	
    public GiftPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public GiftPager getItem(int position) {
        return GiftPager.create(position);
    }

    @Override
    public int getCount() {
        return NUMBER_GIFT_MAX;
    }
}