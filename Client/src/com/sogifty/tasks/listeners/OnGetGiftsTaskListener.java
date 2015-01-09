package com.sogifty.tasks.listeners;

import java.util.List;

import com.sogifty.model.Gift;

public interface OnGetGiftsTaskListener {
	void onGetGiftsComplete(List<Gift> giftList);
	void onGetGiftsFailed(String message);
}
