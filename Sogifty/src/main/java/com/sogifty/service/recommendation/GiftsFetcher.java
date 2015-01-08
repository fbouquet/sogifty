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

public class GiftsFetcher {
	
	private final String USER_AGENT = "Mozilla";
	private static final Logger logger = Logger.getLogger(GiftsFetcher.class);
	private static final int NB_GIFTS_TO_FETCH = 3;
	
	private Configuration configuration;
	
	private GiftDAO giftDao = new GiftDAO();
	
	public GiftsFetcher(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public List<Gift> fetchGifts(Tag tag) throws SogiftyException {
		logger.info("Starting gift Fetch for tag '" + tag.getLabel() + "'.");
		List<Gift> gifts = new ArrayList<Gift>();
		Document fetchedProductList;
		try {
			fetchedProductList = Jsoup.connect(configuration.getSearchUrl(tag)).userAgent(USER_AGENT).get();
			
			Elements productsUrlElts = fetchedProductList.select(configuration.getProductListProductsSelector()
														  + configuration.getProductListProductUrlSelector());

			for (int i = 0; i < productsUrlElts.size() && gifts.size() < NB_GIFTS_TO_FETCH; ++i) {
				Gift gift = null;
				Element product = productsUrlElts.get(i);
				String productUrl = configuration.getBaseUrl() + product.attr(configuration.getProductListProductUrlAttribute());
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
		
		updatedGift.setCreation(gift.getCreation());
		giftDao.update(updatedGift);
		
		return updatedGift;	
	}
	
	private Gift toGift(String giftUrl) throws IOException {
		Gift gift = new Gift();
		
		Document productDetails = Jsoup.connect(giftUrl).userAgent(USER_AGENT).get();
		
		
		Elements titleElts = productDetails.select(configuration.getProductDetailTitleSelector());
		gift.setName(titleElts.get(0).text());
		
		Elements descriptionElts = productDetails.select(configuration.getProductDetailDescriptionSelector());
		gift.setDescription(descriptionElts.get(0).text());
		
		Elements priceElts = productDetails.select(configuration.getProductDetailPriceSelector());
		gift.setPrice(priceElts.get(0).text());
		
		Elements pictureElts = productDetails.select(configuration.getProductDetailPictureSelector());
		gift.setPictureUrl(pictureElts.get(0).attr(configuration.getProductDetailPictureUrlAttribute()));
		
		gift.setUrl(giftUrl);
		gift.setCreation(new Date());
		gift.setLastUpdate(new Date());
		
		return gift;
	}
	
	private void saveGifts(List<Gift> gifts) throws SogiftyException {
		 giftDao.createGifts(gifts);
	}
}
