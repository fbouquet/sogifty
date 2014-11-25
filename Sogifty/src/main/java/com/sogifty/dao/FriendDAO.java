package com.sogifty.dao;

import com.sogifty.dao.dto.Friend;


public class FriendDAO extends AbstractDAO<Friend> {
	public FriendDAO() {
		setType(Friend.class);
	}
}
