package com.sogifty.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sogifty.dao.GiftDAO;
import com.sogifty.dao.TagDAO;
import com.sogifty.dao.dto.Gift;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;
import com.sogifty.service.recommendation.CdiscountConfiguration;
import com.sogifty.service.recommendation.GiftsFetcher;
import com.sogifty.util.DateUtils;


/*
 * Update periodically the gifts in the database. Two behaviours :
 * - If a gift wasn't updated since GIFT_UPDATE_TIME day(s), we update it
 * - If a gift was created since more than a GIFT_LIFE_TIME day(s), we delete it
 */
public class PeriodicGiftService implements Runnable {
	private static final int GIFT_UPDATE_TIME	= 10;
	private static final int GIFT_LIFE_TIME		= 30;

	private GiftDAO	giftDAO = new GiftDAO();
	private TagDAO tagDAO = new TagDAO();
	private GiftsFetcher giftsFetcher = new GiftsFetcher(new CdiscountConfiguration());

	private static final Logger logger = Logger.getLogger(PeriodicGiftService.class);

	public void run(){
		Set<Tag> tags = null;

		List<Gift> giftsToDelete = new ArrayList<Gift>();

		try {
			tags = tagDAO.findAll();
		} catch (SogiftyException e) {
			logger.fatal("Exception getting the tags : " + e.getStatus() + " " + e.getMessage());
		}

		for(Tag tag : tags)
			for (Gift gift : tag.getGifts()) {
				Date creationDate	= gift.getCreation();
				Date lastUpdate		= gift.getLastUpdate();
				Date todayDate		= new Date();

				if(DateUtils.getDateDiff(creationDate, todayDate) > GIFT_LIFE_TIME) {
					giftsToDelete.add(gift);
				} else if(DateUtils.getDateDiff(lastUpdate, todayDate) > GIFT_UPDATE_TIME) {
					try {
						logger.info("This gift has to be uptdated : " + gift);
						giftsFetcher.updateGift(gift);
					} catch (SogiftyException e) {
						logger.error("The gift is detected as problematic, we will delete it.");
						try {
							giftDAO.delete(gift.getId());
						} catch (SogiftyException e1) {
							logger.fatal("Can't delete te problematic gift.");
						}
					}
				}
			}

		// Delete the gifts too old
		logger.info(giftsToDelete.size() + " gift(s), too old, are going to be deleted from the database.");
		if(!giftsToDelete.isEmpty()) {
			try {
				giftDAO.deleteGifts(giftsToDelete);
			} catch (SogiftyException e) {
				logger.fatal(e.getMessage());
			}
		}
	}
}
