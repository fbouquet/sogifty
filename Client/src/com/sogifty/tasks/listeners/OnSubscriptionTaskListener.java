package com.sogifty.tasks.listeners;

public interface OnSubscriptionTaskListener {

	void onSubscriptionFailed(String message);
	
	void onSubscriptionComplete(int userId);
	
	
}
