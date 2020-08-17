package com.techleads.app.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "userRoles_seq", initialValue = 1001, allocationSize = 1)
@ToString(exclude="roles")
public class Users implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userRoles_seq")
	private Integer id;
	private String userName;
	private String password;
	private String email;
	//@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	//private Set<Roles> roles=new HashSet<>();
	@ElementCollection
	@CollectionTable(
	name="user_roles",
	joinColumns= @JoinColumn(name="user_id")
	)
	private Set<String> roles;
	

}
