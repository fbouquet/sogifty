package com.sogifty.tasks.listeners;

public interface OnAddFriendTaskListener {
	void onAddFriendComplete();
	
	void onAddFriendFailed(String errorMessage);
}
