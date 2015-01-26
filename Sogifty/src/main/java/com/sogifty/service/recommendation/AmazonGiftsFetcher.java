package com.sogifty.service.recommendation;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sogifty.dao.dto.Tag;
import com.sogifty.dao.dto.Website;

public class AmazonGiftsFetcher extends AbstractGiftsFetcher {

	private final static String BASE_URL								= "http://www.amazon.fr";
	private final static String SEARCH_URL_SUFFIX						= "/s/ref=nb_sb_noss?url=field-keywords=";
	private final static String PRODUCTS_SELECTOR						= "#resultsCol li[data-asin] ";
	private final static String PRODUCT_URL_SELECTOR					= PRODUCTS_SELECTOR + "a.s-access-detail-page ";
	private final static String PRODUCT_URL_ATTRIBUTE					= "href"; 
	private final static String PRODUCT_NAME_SELECTOR					= "#productTitle ";
	private final static String PRODUCT_DESCRIPTION_SELECTOR			= "#feature-bullets ";
	private final static String PRODUCT_DESCRIPTION_LINES_SELECTOR		= "ul li";
	private final static String PRODUCT_PRICE_SELECTOR					= "#priceblock_ourprice ";
	private final static String PRODUCT_PICTURE_SELECTOR				= "#landingImage ";
	private final static String PRODUCT_PICTURE_URL_ATTRIBUTE			= "src";
	
	private final static String EURO_SYMBOL 							= "\u20AC";
	
	public AmazonGiftsFetcher(int nbGiftsToFetch) {
		super(nbGiftsToFetch);
	}
	
	public String getSearchUrl(Tag tag) {
		return BASE_URL + SEARCH_URL_SUFFIX + convertSpaces(tag.getLabel());
	}
	
	private String convertSpaces(String label) {
		return label.replaceAll(" ", "\\+");
	}
	
	public String getProductUrlSelector() {
		return PRODUCT_URL_SELECTOR;
	}
	
	public String getProductNameSelector() {
		return PRODUCT_NAME_SELECTOR;
	}
	
	public String getProductDescriptionSelector() {
		return PRODUCT_DESCRIPTION_SELECTOR;
	}
	
	public String getProductPriceSelector() {
		return PRODUCT_PRICE_SELECTOR;
	}
	
	public String getProductPictureSelector() {
		return PRODUCT_PICTURE_SELECTOR;
	}
	
	@Override
	protected String getProductUrl(Element element) {
		return element.attr(PRODUCT_URL_ATTRIBUTE);
	}
	
	@Override
	protected String getProductName(Element element) {
		return element.text();
	}
	
	@Override
	protected String getProductDescription(Element element) {
		Elements lignesDesc = element.select(PRODUCT_DESCRIPTION_LINES_SELECTOR);
		StringBuilder description = new StringBuilder();
		for (Element e : lignesDesc) {
			description.append(e.text()).append(System.lineSeparator());
		}
		
		return description.toString();
	}
	
	@Override
	protected String getProductPrice(Element element) {
		String price = element.text().substring(4); // remove "EUR " before the price 
		
		return price + EURO_SYMBOL;
	}
	
	@Override
	protected String getProductPictureUrl(Element element) {
		return element.attr(PRODUCT_PICTURE_URL_ATTRIBUTE);
	}

	@Override
	protected Website getWebSite() {
		return Website.AMAZON;
	}
}
