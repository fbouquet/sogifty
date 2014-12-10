package com.sogifty.service.recommendation;

import com.sogifty.dao.dto.Tag;


public interface Configuration {
	
	public String getBaseUrl();
	
	public String getSearchUrl(Tag tag);
	
	public String getProductListProductsSelector();

	public String getProductListProductUrlSelector();

	public String getProductListProductUrlAttribute();

	public String getProductDetailTitleSelector();

	public String getProductDetailDescriptionSelector();

	public String getProductDetailPriceSelector();

	public String getProductDetailPictureSelector();

	public String getProductDetailPictureUrlAttribute();
}
