package com.techleads.app.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class ApplicationUser {
	@Id
	public String username;
	public String userEmail;
	public String password;
	public String mobile;
	public String location;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
	name="user_roles",
	joinColumns= @JoinColumn(name="user_id")
	)
	private Set<String> roles;

	


	public ApplicationUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}


	public ApplicationUser() {

	}
	

	public ApplicationUser(String username, String userEmail, String password, String mobile, String location) {
		this.username = username;
		this.userEmail = userEmail;
		this.password = password;
		this.mobile = mobile;
		this.location = location;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public Set<String> getRoles() {
		return roles;
	}


	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}


	@Override
	public String toString() {
		return "ApplicationUser [username=" + username + ", userEmail=" + userEmail + ", password=" + password
				+ ", mobile=" + mobile + ", location=" + location + ", roles=" + roles + "]";
	}

	

}
