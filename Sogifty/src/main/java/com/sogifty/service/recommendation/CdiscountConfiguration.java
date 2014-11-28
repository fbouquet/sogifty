package com.sogifty.service.recommendation;

import com.sogifty.dao.dto.Tag;



public class CdiscountConfiguration implements Configuration {

	private final static String BASE_URL				= "http://www.cdiscount.com";
	private final static String SEARCH_URL_SUFFIX		= "/search/10";
	private final static String END_SEARCH_URL			= ".html";
	private final static String PRODUCTS_SELECTOR		= "#lpBloc > li[data-sku]";
	private final static String TITLE_SELECTOR			= ".prdtBTit";
	private final static String DESCRIPTION_SELECTOR	= "p.prdtBDesc";
	private final static String PRICE_SELECTOR			= ".price";
	private final static String PICTURE_SELECTOR		= ".prdtBImg";
	private final static String PICTURE_URL_ATTRIBUTE	= "data-src";
	private final static String PRODUCT_URL_SELECTOR	= "a:first-child";
	private final static String PRODUCT_URL_ATTRIBUTE	= "href";

	public CdiscountConfiguration() {
		
	}
	
	public String getSearchUrl(Tag tag) {
		return BASE_URL + SEARCH_URL_SUFFIX + "/" + convertSpaces(tag.getLabel()) + END_SEARCH_URL;
	}
	
	public String getBaseUrl() {
		return BASE_URL;
	}
	
	private String convertSpaces(String label) {
		return label.replaceAll(" ", "\\+");
	}
	
	public String getProductsSelector() {
		return PRODUCTS_SELECTOR;
	}

	public String getTitleSelector() {
		return TITLE_SELECTOR;
	}

	public String getDescriptionSelector() {
		return DESCRIPTION_SELECTOR;
	}

	public String getPriceSelector() {
		return PRICE_SELECTOR;
	}
	public String getPictureSelector() {
		return PICTURE_SELECTOR;
	}
	
	public String getPictureUrlAttribute() {
		return PICTURE_URL_ATTRIBUTE;
	}

	public String getProductUrlSelector() {
		return PRODUCT_URL_SELECTOR;
	}

	public String getProductUrlAttribute() {
		return PRODUCT_URL_ATTRIBUTE;
	}
}
