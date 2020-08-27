package com.techleads.app.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements Serializable {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private long id;
	//private String fullname;
	private String username;
	private String password;
	private boolean active;
	private String roles;

}
