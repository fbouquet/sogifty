package com.sogifty.service.recommendation;


public abstract class Configuration {
	protected String baseUrl;
	protected String endUrl;
	
	protected String idProductList;
	protected String classProduct;
	protected String classTitle;
	protected String classDescription;
	protected String classPrice;
	protected String classPicture;
	protected String attrPicture;
	
	public Configuration(	String baseUrl, String endUrl, String idProductList, String classProduct, 
							String classTitle, String classDescription, String classPrice, 
							String classPicture, String attrPicture) {
		this.baseUrl			= baseUrl;
		this.endUrl				= endUrl;
		this.idProductList		= idProductList;
		this.classProduct		= classProduct;
		this.classTitle			= classTitle;
		this.classDescription	= classDescription;
		this.classPrice			= classPrice;
		this.classPicture		= classPicture;
		this.attrPicture		= attrPicture;
	}
	
	public abstract String getSearchUrl(String tag);

	public String getBaseUrl() {
		return baseUrl;
	}

	public String getEndUrl() {
		return endUrl;
	}

	public String getIdProductList() {
		return idProductList;
	}

	public String getClassProduct() {
		return classProduct;
	}

	public String getClassTitle() {
		return classTitle;
	}

	public String getClassDescription() {
		return classDescription;
	}

	public String getClassPrice() {
		return classPrice;
	}

	public String getClassPicture() {
		return classPicture;
	}

	public String getAttrPicture() {
		return attrPicture;
	}
}
