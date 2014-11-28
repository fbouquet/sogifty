package com.sogifty.tasks.listeners;

public interface OnConnectionTaskListener {

	void onConnectionFailed(String message);
	
	void onConnectionComplete(int userId);
	
	
}
