package com.sogifty.tasks.listeners;

public interface OnDeleteFriendTaskListener {
	void deleteFriendComplete();
	
	void deleteFriendFailed(String errorMessage);
}
