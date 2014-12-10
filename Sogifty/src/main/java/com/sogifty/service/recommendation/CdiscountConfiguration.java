package com.sogifty.service.recommendation;

import com.sogifty.dao.dto.Tag;



public class CdiscountConfiguration implements Configuration {

	private final static String BASE_URL								= "http://www.cdiscount.com";
	private final static String SEARCH_URL_SUFFIX						= "/search/10";
	private final static String END_SEARCH_URL							= ".html";
	private final static String PRODUCT_LIST_PRODUCTS_SELECTOR			= "#lpBloc > li[data-sku]";
	private final static String PRODUCT_LIST_PRODUCT_URL_SELECTOR		= "a:first-child";
	private final static String PRODUCT_LIST_PRODUCT_URL_ATTRIBUTE		= "href";
//	private final static String TITLE_SELECTOR			= ".prdtBTit";
//	private final static String DESCRIPTION_SELECTOR	= "p.prdtBDesc";
//	private final static String PRICE_SELECTOR			= ".price";
//	private final static String PICTURE_SELECTOR		= ".prdtBImg";
//	private final static String PICTURE_URL_ATTRIBUTE	= "data-src";
	private final static String PRODUCT_DETAIL_BASE_SELECTOR 			= "#fpContent ";
	private final static String PRODUCT_DETAIL_TITLE_SELECTOR			= PRODUCT_DETAIL_BASE_SELECTOR + "[itemprop=name]";
	private final static String PRODUCT_DETAIL_DESCRIPTION_SELECTOR		= PRODUCT_DETAIL_BASE_SELECTOR + "[itemprop=description]";
	private final static String PRODUCT_DETAIL_PRICE_SELECTOR			= PRODUCT_DETAIL_BASE_SELECTOR + "[itemprop=price]";
	private final static String PRODUCT_DETAIL_PICTURE_SELECTOR			= PRODUCT_DETAIL_BASE_SELECTOR + "[itemprop=image]";
	private final static String PRODUCT_DETAIL_PICTURE_URL_ATTRIBUTE	= "href";


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

	public String getProductListProductsSelector() {
		return PRODUCT_LIST_PRODUCTS_SELECTOR;
	}

	public String getProductListProductUrlSelector() {
		return PRODUCT_LIST_PRODUCT_URL_SELECTOR;
	}

	public String getProductListProductUrlAttribute() {
		return PRODUCT_LIST_PRODUCT_URL_ATTRIBUTE;
	}

	public String getProductDetailTitleSelector() {
		return PRODUCT_DETAIL_TITLE_SELECTOR;
	}

	public String getProductDetailDescriptionSelector() {
		return PRODUCT_DETAIL_DESCRIPTION_SELECTOR;
	}

	public String getProductDetailPriceSelector() {
		return PRODUCT_DETAIL_PRICE_SELECTOR;
	}

	public String getProductDetailPictureSelector() {
		return PRODUCT_DETAIL_PICTURE_SELECTOR;
	}

	public String getProductDetailPictureUrlAttribute() {
		return PRODUCT_DETAIL_PICTURE_URL_ATTRIBUTE;
	}
}
