package com.techleads.app.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(name = "roles_seq", initialValue = 2001, allocationSize = 1)
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_seq")
	private Integer id;
	private String roleName;
	@ManyToOne
	private Users user;

}
