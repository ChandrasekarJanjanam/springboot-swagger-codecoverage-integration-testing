package com.chandra.microservice.v1.api.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * An entity User composed by four fields (userid, username, gender, occupation).
 * The Entity annotation indicates that this class is a JPA entity.
 * The Table annotation specifies the name for the table in the db.
 * 
 * @author Chandra
 *
 */
@Entity
public class UserDetails {

	/*
	 * Default constructor
	 */
	public UserDetails(){
		super();
	}
	
	/*
	 * overloaded constructor
	 */
	public UserDetails(String userid, String username, String gender, String occupation) {
		super();
		this.userid = userid;
		this.username = username;
		this.gender = gender;
		this.occupation = occupation;
	}

	// ------------------------
	// PRIVATE FIELDS
	// ------------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userid;
	@Column(name = "username")
	private String username;
	@Column(name = "gender")
	private String gender;
	@Column(name = "occupation")
	private String occupation;


	// ------------------------
	// PUBLIC METHODS
	// ------------------------
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation
	 *            the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

}
