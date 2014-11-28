package com.sogifty.model;

import org.codehaus.jackson.annotate.JsonProperty;


public class Friend implements Comparable<Friend> {  
  private String nom;  
  private String prenom;
  private String fonction;
  private int age;
  private int _id;
  private String avatar;
  private long remainingDay;

  
public void setAge(int age) {
	this.age = age;
}

  

  private String gender;
  
  public Friend(){
	  
  }

  public void setNom(String nom) {  
	  this.nom = nom;
  }
  
  public void setPrenom(String prenom) {
	  this.prenom = prenom;
  }
  
  public String getFonction() {
		return fonction;
	  }

  public void setAvatar(String avatar) {
	  this.avatar = avatar;
	  
  }
  
  public String getAvatar(){
	  return avatar;
  }
	 
  public void setFonction(String fonction) {
		this.fonction = fonction;
	}
  
  public String getNom() {
	  return nom;
  }
  public String getPrenom() {
	  return prenom;
  }
  public int getAge() {
	  return age;
  }
 
  public void setId(int id) {
	  this._id = id;
  }
  
  public int getId() {
	  return _id;
  }

public long getRemainingDay() {
	return remainingDay;
}

public void setRemainingDay(long l) {
	this.remainingDay = l;
}

@JsonProperty("gender")
public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

@Override
public int compareTo(Friend otherUser){

	long dateOU = otherUser.getRemainingDay();
	long dateU = this.getRemainingDay();
	
	if(dateOU > dateU){
		return -1;
	}
	else if(dateOU < dateU){
		return 1;
	}
	else{
		return 0;
	}
}





  

}  
