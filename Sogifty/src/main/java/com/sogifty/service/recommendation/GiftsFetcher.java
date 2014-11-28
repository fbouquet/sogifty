package com.sogifty.service.recommendation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sogifty.dao.dto.Gift;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;

public class GiftsFetcher {
	
	private final String USER_AGENT = "Mozilla";
	private static final Logger logger = Logger.getLogger(GiftsFetcher.class);
	private Configuration configuration;
	
	public GiftsFetcher(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public List<Gift> fetchGifts(Tag tag) throws SogiftyException {
		logger.info("Starting gift Fetch for tag '" + tag.getLabel() + "'.");
		List<Gift> gifts = new ArrayList<Gift>();
		Document doc;
		try {
			doc = Jsoup.connect(configuration.getSearchUrl(tag)).userAgent(USER_AGENT).get();
			
			Elements products = doc.select(configuration.getProductsSelector());
			
			for (Element product : products) {
				Gift gift = null;
				try {
					gift = toGift(product);
				} catch (Exception e) {
					continue;
				}
				
				if (gift != null) {
					gifts.add(gift);
				}
			}
		} catch (IOException e) {
			logger.fatal("Exception when fetching gifts: ", e);
			throw new SogiftyException(Status.INTERNAL_SERVER_ERROR);
		}
		
		saveGifts(gifts);
		
		logger.info("End of gift fetch for tag '" + tag.getLabel() + "'.");
		
		return gifts;
	}
	
	private Gift toGift(Element product) {
		Gift gift = new Gift();
		
		Elements titleElts = product.select(configuration.getTitleSelector());
		gift.setName(titleElts.get(0).text());
		
		Elements descriptionElts = product.select(configuration.getDescriptionSelector());
		gift.setDescription(descriptionElts.get(0).text());
		
		Elements priceElts = product.select(configuration.getPriceSelector());
		gift.setPrice(priceElts.get(0).text());
		
		Elements pictureElts = product.select(configuration.getPictureSelector());
		gift.setPictureUrl(pictureElts.get(0).attr(configuration.getPictureUrlAttribute()));
		
		gift.setCreation(new Date());
		gift.setLastUpdate(new Date());
		
		Elements urlElts = product.select(configuration.getProductUrlSelector());
		gift.setUrl(configuration.getBaseUrl() + urlElts.attr(configuration.getProductUrlAttribute()));
		return gift;
	}
	
	private void saveGifts(List<Gift> gifts) {
		/* GiftDAO giftDao = new GiftDAO();
		 * giftDao.createGifts(gifts);
		 */
	}
}
