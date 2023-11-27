package com.jakala.test.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 
 * @author Daniele Pariota
 * @lastmodifiedon 27/11/2023
 * 
 */

@Entity
public class Contract 
{
	final static Set<String> VALID_TYPES = new HashSet<>(Arrays.asList("gas", "electricity", "both"));
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String contracttype;
	private LocalDate createdon;
	private LocalDate lastmodifiedon;
	
	@ManyToOne
	@JoinColumn(name="userid") 	// FK
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContractType() {
		return contracttype;
	}

	public void setContractType(String contractType) {
		this.contracttype = contractType;
	}

	public LocalDate getCreatedOn() {
		return createdon;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdon = createdOn;
	}

	public LocalDate getLastModifiedOn() {
		return lastmodifiedon;
	}

	public void setLastModifiedOn(LocalDate lastModifiedOn) {
		this.lastmodifiedon = lastModifiedOn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isValid()
    {
        return VALID_TYPES.contains(contracttype.toLowerCase());
    }
	
	
	
	
}
