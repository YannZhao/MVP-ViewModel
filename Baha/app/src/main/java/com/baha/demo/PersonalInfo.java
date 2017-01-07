package com.baha.demo;

/**
 * Created by Yann on 07/01/2017.
 */

public class PersonalInfo {

	public PersonalInfo(String name, String gender, String hobbies) {
		this.name = name;
		this.gender = gender;
		this.hobbies = hobbies;
	}

	private String name;

	private String gender;

	private String hobbies;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
}
