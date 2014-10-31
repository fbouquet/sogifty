package com.sogifty.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.annotate.JsonProperty;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class User {  
  private String nom;  
  @JsonProperty("prenom")  
  private String prenom;
  @JsonProperty("fonction")
  private String fonction;
  
  @JsonProperty("age")
  private int age;
  
  @JsonProperty("_id")
  private String _id;
  
  private String avatar;
  
  private String remainingDay;
  

  private String gender;

  @JsonProperty("nom")  
  public void setNom(String nom) {  
	  this.nom = nom;
  }
  
  public void setPrenom(String prenom) {
	  this.prenom = prenom;
  }
  
  public String getFonction() {
		return fonction;
	  }

  //@JsonProperty("avatar")
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
 
  public void setId(String id) {
	  this._id = id;
  }
  
  public String getId() {
	  return _id;
  }

public String getRemainingDay() {
	return remainingDay;
}

public void setRemainingDay(String remainingDay) {
	this.remainingDay = remainingDay;
}

@JsonProperty("gender")
public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

  

}  
