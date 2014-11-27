package com.sogifty.model;

import java.util.ArrayList;
import java.util.List;

public class Friends {  
    private List<Friend> listFriends;  
    
    public Friends(){
    	listFriends = new ArrayList<Friend>();
    }
    
    
    public List<Friend> getListFriends() {  
        return listFriends;  
    }  
  
    public void setListFriends(List<Friend> listFriends) {  
        this.listFriends = listFriends;  
    }
    
    public Friend getFriendById(int id){
    	for(Friend f : listFriends )
    	{
    		if(id == f.getId()){
    			return f;
    		}
    	}
    	return null;
    }
    public void addFriend(Friend f){
    	this.listFriends.add(f);
    }
    
}   
