package com.sogifty.tasks.listeners;

import com.sogifty.model.Friends;

public interface OnGetFriendListTaskListener {

	void onGetFriendListComplete(Friends friendList);
	
	void onGetFriendListFailed(String message);
	
}
