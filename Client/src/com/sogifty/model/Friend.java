package com.sogifty.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class Friend implements Comparable<Friend>, Serializable {  

	private static final long serialVersionUID = 1L;
	private String nom;  
	private String prenom;
	private String fonction;
	private int age;
	private int _id;
	private String avatar;
	private long remainingDay;
	private String birthdayDate;
	protected List<String> tags;

	
	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getBirthdayDate() {
		return birthdayDate;
	}

	public void setBirthdayDate(String birthdayDate) {
		this.birthdayDate = birthdayDate;
	}



	
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
	public String getTagsinJsonString(){
		String tagsString = "[";
		for (int i=0;i<tags.size();++i) {
			tagsString+="{\"label\": \""+tags.get(i)+"\"}";
			if(i<tags.size()-1)
				tagsString+=",";
		}
		tagsString+="]";
		return tagsString;
	}
	public String getTagsinPointString(){
		String tagsString = "";
		for (String tag : tags) {
			tagsString+="-"+tag+"\n";
			
		}
		return tagsString;
	}

}  
