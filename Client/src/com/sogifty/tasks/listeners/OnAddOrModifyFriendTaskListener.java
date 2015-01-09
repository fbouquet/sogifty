package com.sogifty.tasks.listeners;

public interface OnAddOrModifyFriendTaskListener {
	void onAddOrModifyFriendComplete();
	
	void onAddOrModifyFriendFailed(String errorMessage);
}
