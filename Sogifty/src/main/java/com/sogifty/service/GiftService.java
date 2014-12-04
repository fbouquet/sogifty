package com.sogifty.service;

import java.util.HashSet;
import java.util.Set;

import com.sogifty.dao.dto.Gift;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.model.GiftModel;
import com.sogifty.service.recommendation.CdiscountConfiguration;
import com.sogifty.service.recommendation.GiftsFetcher;

public class GiftService {

	private TagService tagService = new TagService();
	private GiftsFetcher giftsFetcher = new GiftsFetcher(new CdiscountConfiguration());
	
	public Set<GiftModel> getGifts(int friendId) throws SogiftyException {
		Set<GiftModel> gifts	= new HashSet<GiftModel>();
		Set<Tag> tags			= tagService.getTags(friendId);
		
		for (Tag tag : tags) {
			if(tag.getGifts().size() == 0) {
				for(Gift gift : giftsFetcher.fetchGifts(tag)) {
					gifts.add(new GiftModel(gift));
				}
			}
			else {
				for(Gift gift : tag.getGifts()) {
					gifts.add(new GiftModel(gift));
				}
			}
		}
		
		return gifts;
	}
}
