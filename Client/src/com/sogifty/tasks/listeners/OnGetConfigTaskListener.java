package com.sogifty.tasks.listeners;

import com.sogifty.model.Config;

public interface OnGetConfigTaskListener {

	void onGetConfigFailed(String message);
	
	void onGetConfigComplete(Config config);
	
	
}
