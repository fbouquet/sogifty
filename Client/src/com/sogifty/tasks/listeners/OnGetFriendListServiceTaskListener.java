package com.sogifty.tasks.listeners;

import com.sogifty.model.Friends;

public interface OnGetFriendListServiceTaskListener {

	void onGetFriendListServiceComplete(Friends friendList);
	
	void onGetFriendListServiceFailed(String message);
	
}
