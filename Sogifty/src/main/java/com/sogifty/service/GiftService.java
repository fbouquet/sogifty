package com.sogifty.service;

import java.util.HashSet;
import java.util.Set;

import com.sogifty.dao.dto.Gift;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;

public class GiftService {

	private TagService tagService = new TagService();
	
	public Set<Gift> getGifts(int friendId) throws SogiftyException {
		Set<Gift> gifts = new HashSet<Gift>();
		
		Set<Tag> tags = tagService.getTags(friendId);
		for (Tag tag : tags) {
			if(tag.getGifts().size() == 0) {
				// call the recommendation engine
			}
			else {
				gifts.addAll(tag.getGifts());
			}
		}
		
		return gifts;
	}
}
