package com.sogifty.tasks.listeners;

public interface DeleteFriendTaskListener {
	void deleteFriendComplete();
	
	void deleteFriendFailed(String errorMessage);
}
