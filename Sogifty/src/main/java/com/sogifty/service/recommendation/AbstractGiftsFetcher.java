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

import com.sogifty.dao.GiftDAO;
import com.sogifty.dao.dto.Gift;
import com.sogifty.dao.dto.Tag;
import com.sogifty.exception.SogiftyException;

public abstract class AbstractGiftsFetcher {
	
	private final String USER_AGENT = "Mozilla";
	private static final Logger logger = Logger.getLogger(AbstractGiftsFetcher.class);
	private static final int NB_GIFTS_TO_FETCH = 3;
	
	private GiftDAO giftDao = new GiftDAO();
	
	protected abstract String getSearchUrl(Tag tag);
	protected abstract String getProductUrlSelector();
	protected abstract String getProductNameSelector();
	protected abstract String getProductDescriptionSelector();
	protected abstract String getProductPriceSelector();
	protected abstract String getProductPictureSelector();
	
	protected abstract String getProductUrl(Element element);
	protected abstract String getProductName(Element element);
	protected abstract String getProductDescription(Element element);
	protected abstract String getProductPrice(Element element);
	protected abstract String getProductPictureUrl(Element element);
	
	public List<Gift> fetchGifts(Tag tag) throws SogiftyException {
		logger.info("Starting gift Fetch for tag '" + tag.getLabel() + "'.");
		List<Gift> gifts = new ArrayList<Gift>();
		Document fetchedProductList;
		try {
			fetchedProductList = Jsoup.connect(getSearchUrl(tag)).userAgent(USER_AGENT).get();
			
			Elements productsUrlElts = fetchedProductList.select(getProductUrlSelector());

			for (int i = 0; i < productsUrlElts.size() && i < NB_GIFTS_TO_FETCH; ++i) {
				Gift gift = null;
				Element product = productsUrlElts.get(i);
				String productUrl = getProductUrl(product);
				try {
					gift = toGift(productUrl);
				} catch (Exception e) {
					continue;
				}
				
				if (gift != null) {
					gift.addTag(tag);
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
	
	public Gift updateGift(Gift gift) throws SogiftyException {
		Gift updatedGift = null;
		
		try {
			updatedGift = toGift(gift.getUrl());
		} catch (Exception e) {
			return null;
		}
		
		updatedGift.setId(gift.getId());
		updatedGift.setTags(gift.getTags());
		updatedGift.setCreation(gift.getCreation());
		giftDao.update(updatedGift);
		
		return updatedGift;	
	}
	
	private Gift toGift(String giftUrl) throws IOException {
		Gift gift = new Gift();
		
		Document productDetails = Jsoup.connect(giftUrl).userAgent(USER_AGENT).get();
		
		Elements nameElts = productDetails.select(getProductNameSelector());
		gift.setName(getProductName(nameElts.get(0)));
		
		Elements descriptionElts = productDetails.select(getProductDescriptionSelector());
		gift.setDescription(getProductDescription(descriptionElts.get(0)));
		
		Elements priceElts = productDetails.select(getProductPriceSelector());
		gift.setPrice(getProductPrice(priceElts.get(0)));
		
		Elements pictureElts = productDetails.select(getProductPictureSelector());
		gift.setPictureUrl(getProductPictureUrl(pictureElts.get(0)));
		
		gift.setUrl(giftUrl);
		gift.setCreation(new Date());
		gift.setLastUpdate(new Date());
		
		return gift;
	}
	
	private void saveGifts(List<Gift> gifts) throws SogiftyException {
		 giftDao.createGifts(gifts);
	}
}
