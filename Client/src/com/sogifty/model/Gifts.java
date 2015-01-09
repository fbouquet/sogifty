package com.sogifty.model;

import java.io.Serializable;
import java.util.List;

public class Gifts implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Gift> giftList;
	
	public Gifts(List<Gift> gifts){
		giftList = gifts;
	}

	public List<Gift> getGiftList() {
		return giftList;
	}

	public void setGiftList(List<Gift> giftList) {
		this.giftList = giftList;
	}
	
	

}
