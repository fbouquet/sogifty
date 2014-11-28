package com.sogifty.service.recommendation;

import com.sogifty.dao.dto.Tag;


public interface Configuration {
	
	public String getBaseUrl();
	
	public String getSearchUrl(Tag tag);
	
	public String getProductsSelector();

	public String getTitleSelector();

	public String getDescriptionSelector();

	public String getPriceSelector();
	
	public String getPictureSelector();
	
	public String getPictureUrlAttribute();
	
	public String getProductUrlSelector();

	public String getProductUrlAttribute();
}
