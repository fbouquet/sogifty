package com.sogifty.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.annotate.JsonProperty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class Friend implements Comparable<Friend> {  
  private String nom;  
  private String prenom;
  private String fonction;
  private int age;
  private int _id;
  private String avatar;
  private int remainingDay;

  
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

public int getRemainingDay() {
	return remainingDay;
}

public void setRemainingDay(int remainingDay) {
	this.remainingDay = remainingDay;
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

	int dateOU = otherUser.getRemainingDay();
	int dateU = this.getRemainingDay();
	
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