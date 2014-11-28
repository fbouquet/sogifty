package com.sogifty.activities;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sogifty.R;

public class GiftPager extends Fragment {


	public static final String ARG_PAGE = "page";

	private int pageNumber;

	
	public static GiftPager create(int _pageNumber) {
		GiftPager fragment = new GiftPager();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, _pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public GiftPager() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pageNumber = getArguments().getInt(ARG_PAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater
				.inflate(R.layout.gift_pager_item, container, false);
		return rootView;
	}

	public int getPageNumber() {
		return pageNumber;
	}
}