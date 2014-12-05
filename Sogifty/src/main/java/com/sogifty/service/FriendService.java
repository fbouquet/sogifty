package com.sogifty.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.Response;

import com.sogifty.dao.FriendDAO;
import com.sogifty.dao.TagDAO;
import com.sogifty.dao.UserDAO;
import com.sogifty.dao.dto.Friend;
import com.sogifty.dao.dto.Tag;
import com.sogifty.dao.dto.User;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.FriendModel;
import com.sogifty.service.model.TagModel;

public class FriendService {

	private FriendDAO friendDAO = new FriendDAO();
	private UserDAO userDAO = new UserDAO();
	private TagDAO tagDAO = new TagDAO();

	public FriendModel create(int userId, FriendModel friend) throws SogiftyException {
		User user = userDAO.getById(userId);
		Set<Tag> tags = toTags(friend.getTags());

		return new FriendModel(friendDAO.create(new Friend().setName(friend.getName())
				.setBirthdate(friend.getBirthdate())
				.setUser(user)
				.setTags(tags)));
	}

	public FriendModel update(int userId, int friendId, FriendModel friend) throws SogiftyException {
		checkParameters(userId, friendId, friend);
		User user = userDAO.getById(userId);
		Set<Tag> tags = toTags(friend.getTags());

		return new FriendModel(friendDAO.update(new Friend().setId(friendId)
				.setName(friend.getName())
				.setBirthdate(friend.getBirthdate())
				.setUser(user)
				.setTags(tags)));
	}

	public void delete(Integer friendId) throws SogiftyException {
		friendDAO.delete(friendId);
	}

	public List<FriendModel> getFriends(int userId) throws SogiftyException {
		List<FriendModel> returnedFriends = new LinkedList<FriendModel>();
		Iterator<Friend> friendsIterator = userDAO.getFriends(userId).iterator();
		while (friendsIterator.hasNext()) {
			returnedFriends.add(new FriendModel(friendsIterator.next()));
		}
		return returnedFriends;
	}

	private void checkParameters(int userId, int friendId, FriendModel friend) throws SogiftyException {
		if (getUserId(getFriendById(friendId)) != userId) {
			throw new SogiftyException(Response.Status.FORBIDDEN);
		}
		if (friend.getName() == null || friend.getBirthdate() == null) {
			throw new SogiftyException(Response.Status.BAD_REQUEST);
		}
	}

	private Friend getFriendById(int friendId) throws SogiftyException {
		return friendDAO.getById(friendId);
	}

	private int getUserId(Friend friend) throws SogiftyException {
		return friend.getUser().getId().intValue();
	}

	// Converts TagModels to Tags AND CREATES Tags in DB if necessary
	private Set<Tag> toTags(Set<TagModel> tagModels) throws SogiftyException {
		if (tagModels == null || tagModels.isEmpty()) {
			return null;
		}
		Set<Tag> tags = new HashSet<Tag>(0);
		for (TagModel tagModel : tagModels) {
			// If the tag is not in the database, create it
			String tagLabel = tagModel.getLabel().toLowerCase();
			Tag dbTag = tagDAO.getByLabel(tagLabel);
			if (dbTag == null) {
				Integer createdId = tagDAO.create(new Tag().setLabel(tagLabel));
				tags.add(new Tag().setId(createdId).setLabel(tagLabel));
			}
			else {
				// Add the existing database tag
				tags.add(dbTag);
			}
		}
		return tags;
	}
}
