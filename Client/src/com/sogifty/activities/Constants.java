package com.sogifty.activities;

public class Constants {
	
	/**
	 * Constants for return person object fields
	 */
	public final String FirstName = "FirstName";
	public final String LastName = "LastName";
	public final String Id = "Id";
	public final String FriendId = "FriendId";
	public final String Alias = "Alias";
	public final String Gender= "Gender";
	public final String BirthDate = "BirthDate";
	public final String Friend = "Friend"; 
	public final String UserId = "UserId";
	public final String AvatarUrl = "AvatarUrl";
	
	/**
	 * Constants for return FriendNotif object fields
	 */
	public final String giftId = "giftId";
	public final String giftName = "giftName";
	public final String giftDescription = "giftDescription";
	public final String giftThumbnail = "photoLink";
	public final String giftLink = "giftLink";
		
	
	/**
	 * SOAP request informations 
	 */
	public final String WSDL_TARGET_NAMESPACE = "http://DemoWebService.com/";	
	public final String OPERATION_NAME = "GetPersonById";
	public final String OPERATION_GET_FRIEND_LIST = "GetFreindList";
	public final String URL = "http://192.168.20.24:8083/DemoService.asmx";
	public final String SOAP_ACTION = "http://DemoWebService.com/GetPersonById";	
	public final String SOAP_ACTION_GET_FRIEND_LIST = "http://DemoWebService.com/GetFreindList";	
	public final String OPERATION_GET_NOTIFICATION_FRIEND = "getNotificationWithGift";
}