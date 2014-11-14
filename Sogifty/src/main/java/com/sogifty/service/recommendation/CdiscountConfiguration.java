package com.sogifty.service.recommendation;


public class CdiscountConfiguration extends Configuration {

	private final static String BASE_URL			= "http://www.cdiscount.com/search/10/";
	private final static String END_URL				= ".html#_his_";
	private final static String ID_PRODUCT_LIST		= "lpBloc";
	private final static String CLASS_PRODUCT		= "prdtBloc";
	private final static String CLASS_TITLE			= "prdtBTit";
	private final static String CLASS_DESCRIPTION	= "prdtBDesc";
	private final static String CLASS_PRICE			= "price";
	private final static String CLASS_PICTURE		= "prdtBImg";
	private final static String ATTR_PICTURE		= "data-src";

	public CdiscountConfiguration() {
		super(	BASE_URL, END_URL, ID_PRODUCT_LIST, CLASS_PRODUCT, CLASS_TITLE, 
				CLASS_DESCRIPTION, CLASS_PRICE, CLASS_PICTURE, ATTR_PICTURE);
	}
	
	@Override
	public String getSearchUrl(String tag) {
		return BASE_URL + tag + END_URL;
	}
}
