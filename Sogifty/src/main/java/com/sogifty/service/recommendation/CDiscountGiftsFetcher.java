package com.sogifty.service.recommendation;

import org.jsoup.nodes.Element;

import com.sogifty.dao.dto.Tag;

public class CDiscountGiftsFetcher extends AbstractGiftsFetcher {

	private final static String BASE_URL								= "http://www.cdiscount.com";
	private final static String SEARCH_URL_SUFFIX						= "/search/10";
	private final static String END_SEARCH_URL							= ".html";
	private final static String PRODUCTS_SELECTOR						= "#lpBloc > li[data-sku] ";
	private final static String PRODUCT_URL_SELECTOR					= PRODUCTS_SELECTOR + "a:first-child ";
	private final static String PRODUCT_URL_ATTRIBUTE					= "href"; 
	private final static String PRODUCT_BASE_SELECTOR 					= "#fpContent ";
	private final static String PRODUCT_NAME_SELECTOR					= PRODUCT_BASE_SELECTOR + "[itemprop=name]";
	private final static String PRODUCT_DESCRIPTION_SELECTOR			= PRODUCT_BASE_SELECTOR + "[itemprop=description]";
	private final static String PRODUCT_PRICE_SELECTOR					= PRODUCT_BASE_SELECTOR + "[itemprop=price]";
	private final static String PRODUCT_PICTURE_SELECTOR				= PRODUCT_BASE_SELECTOR + "[itemprop=image]";
	private final static String PRODUCT_PICTURE_URL_ATTRIBUTE			= "href";
	
	public String getSearchUrl(Tag tag) {
		return BASE_URL + SEARCH_URL_SUFFIX + "/" + convertSpaces(tag.getLabel()) + END_SEARCH_URL;
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
		return BASE_URL + element.attr(PRODUCT_URL_ATTRIBUTE);
	}
	
	@Override
	protected String getProductName(Element element) {
		return element.text();
	}
	
	@Override
	protected String getProductDescription(Element element) {
		return element.text();
	}
	@Override
	protected String getProductPrice(Element element) {
		return element.text();
	}
	@Override
	protected String getProductPictureUrl(Element element) {
		return element.attr(PRODUCT_PICTURE_URL_ATTRIBUTE);
	}
}
