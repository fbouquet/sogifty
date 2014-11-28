package com.sogifty.service.recommendation;

import com.sogifty.dao.dto.Tag;



public class CdiscountConfiguration implements Configuration {

	private final static String BASE_SEARCH_URL			= "http://www.cdiscount.com/search/10/";
	private final static String END_SEARCH_URL			= ".html";
	private final static String PRODUCTS_SELECTOR		= "#lpBloc > li[data-sku]";
	private final static String TITLE_SELECTOR			= ".prdtBTit";
	private final static String DESCRIPTION_SELECTOR	= "p.prdtBDesc";
	private final static String PRICE_SELECTOR			= ".price";
	private final static String PICTURE_SELECTOR		= ".prdtBImg";
	private final static String PICTURE_URL_ATTRIBUTE	= "data-src";

	public CdiscountConfiguration() {
		
	}
	
	public String getSearchUrl(Tag tag) {
		return BASE_SEARCH_URL + convertSpaces(tag.getLabel()) + END_SEARCH_URL;
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
}
