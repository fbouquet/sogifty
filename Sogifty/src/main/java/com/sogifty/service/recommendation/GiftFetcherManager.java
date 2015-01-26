package com.sogifty.service.recommendation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sogifty.dao.PreferencesDAO;
import com.sogifty.dao.dto.Gift;
import com.sogifty.dao.dto.Preferences;
import com.sogifty.dao.dto.Tag;
import com.sogifty.dao.dto.Website;
import com.sogifty.exception.SogiftyException;

public class GiftFetcherManager {
	
	protected final Logger logger = Logger.getLogger(GiftFetcherManager.class);

	private static final int DEFAULT_NB_GIFTS_TO_FETCH	= 10;
	private static final int NB_GIFT_FETCHERS			= 2;
	
	private Map<Website, AbstractGiftsFetcher> giftFetchers = new HashMap<Website, AbstractGiftsFetcher>();
	private int nbGiftsToFetch;
	
	private PreferencesDAO preferencesDAO = new PreferencesDAO();

	public GiftFetcherManager() {
		Set<Preferences> preferences = null;
		try {
			preferences = preferencesDAO.findAll();
		} catch (SogiftyException e) {
			logger.fatal("Can't get the preferences from database.");
		}
		
		if(preferences != null && !preferences.isEmpty()) {
			this.nbGiftsToFetch = preferences.iterator().next().getNumberOfGifts(); // for the moment, the preferences is global for all the users.
		} else {
			this.nbGiftsToFetch = DEFAULT_NB_GIFTS_TO_FETCH;
		}
		
		int nbGiftsByFetcher = nbGiftsToFetch / NB_GIFT_FETCHERS;
		
		giftFetchers.put(Website.CDISCOUNT, new CDiscountGiftsFetcher(nbGiftsByFetcher));
		giftFetchers.put(Website.AMAZON, new AmazonGiftsFetcher(nbGiftsByFetcher));
	}
	
	public List<Gift> fetchGifts(Tag tag) throws SogiftyException {
		List<Gift> gifts = new ArrayList<Gift>();
		Set<Entry<Website, AbstractGiftsFetcher>> fetchers = giftFetchers.entrySet();
		for (Entry<Website, AbstractGiftsFetcher> fetcher : fetchers) {
			gifts.addAll(fetcher.getValue().fetchGifts(tag));
		}
		return gifts;
	}
	
	public Gift updateGift(Gift gift) throws SogiftyException {
		return giftFetchers.get(gift.getWebsite()).updateGift(gift);
	}
}
