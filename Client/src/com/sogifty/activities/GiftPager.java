package com.sogifty.activities;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sogifty.R;
import com.sogifty.model.Gift;
import com.sogifty.model.Gifts;
import com.sogifty.tasks.DownloadImageTask;

public class GiftPager extends Fragment {
	private static final int NB_GIFTS_DEFAULT_VALUE = 0;
	private static final String NB_GIFTS = "nb_gifts";
	public static final String ARG_PAGE = "page";
	private static final String GIFTARG = "gifts";

	private int pageNumber;
	private Gifts gifts;
	private int max_gifts;
	
	public static GiftPager create(int _pageNumber, Gifts giftsToPrint, int maxGifts) {
		GiftPager fragment = new GiftPager();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, _pageNumber);
		args.putInt(NB_GIFTS, maxGifts);
		args.putSerializable(GIFTARG, giftsToPrint);
		fragment.setArguments(args);
		return fragment;
	}

	public GiftPager() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageNumber = getArguments().getInt(ARG_PAGE);
		gifts = (Gifts) getArguments().getSerializable(GIFTARG);
	
//		if(gifts != null && getView() != null){
//			setGiftItems(getView(), gifts.getGiftList().get(((int)Math.random())%gifts.getGiftList().size()));
//		}
//		else{
//			System.out.println("view nulle");
//		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View V = inflater.inflate(R.layout.gift_pager_item, container, false);
		ViewGroup rootView = (ViewGroup) inflater
				.inflate(R.layout.gift_pager_item, container, false);
		int pos = getArguments().getInt(ARG_PAGE);
		
		int max_gift = getArguments().getInt(NB_GIFTS);
		int mod = min(gifts.getGiftList().size(),max_gift);
		if(gifts != null && (gifts.getGiftList().size() != 0) ){
			System.out.println("position"+pos);
			setGiftItems(rootView, gifts.getGiftList().get((pos)%mod));
		}
		else{
			hideGiftItems(rootView);
		}
		
		
		return rootView;
	}

	private int min(int size, int max_gift) {
		if(size <= max_gift)
			return size;
		else
			return max_gift;
	}

	public int getPageNumber() {
		return pageNumber;
	}
	private void setGiftItems(View v, final Gift g){
		ImageView gift = (ImageView) v.findViewById(R.id.giftitem_iv_gift);
		new DownloadImageTask(gift).execute(g.getImgUrl());
		gift.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View v){
		        Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_VIEW);
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.setData(Uri.parse(g.getUrl()));
		        startActivity(intent);
		    }
		});
		
//		System.out.println("lcccc"+g.getImgUrl());
		TextView title = (TextView) v.findViewById(R.id.giftPager_tv_title);
		title.setText(g.getName());
		title.setTextColor(getResources().getColor(R.color.red));
		TextView description = (TextView) v.findViewById(R.id.giftPager_tv_description);
		description.setText(g.getDecription());
		TextView price = (TextView) v.findViewById(R.id.giftPager_tv_price);
		price.setText(g.getPrice());
		price.setTextColor(getResources().getColor(R.color.red));
		
		
	 	
	}
	private void hideGiftItems(View v){
		ImageView gift = (ImageView) v.findViewById(R.id.giftitem_iv_gift);
		TextView title = (TextView) v.findViewById(R.id.giftPager_tv_title);
		TextView description = (TextView) v.findViewById(R.id.giftPager_tv_description);
		TextView price = (TextView) v.findViewById(R.id.giftPager_tv_price);
		gift.setVisibility(TRIM_MEMORY_UI_HIDDEN);
		title.setVisibility(TRIM_MEMORY_UI_HIDDEN);
		description.setVisibility(TRIM_MEMORY_UI_HIDDEN);
		price.setVisibility(TRIM_MEMORY_UI_HIDDEN);
	 	
	}
	
		
}
