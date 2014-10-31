package com.sogifty.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Users {  
    private List<User> listUsers;  
    
  
    public List<User> getListUsers() {  
        return listUsers;  
    }  
  
    @JsonProperty("user")  
    public void setListUsers(List<User> listUsers) {  
        this.listUsers = listUsers;  
    }
    
    public User getUserById(String id){
    	for(User u : listUsers )
    	{
    		if(id == u.getId()){
    			return u;
    		}
    	}
    	return null;
    }
}   
