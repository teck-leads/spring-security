package com.techleads.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techleads.app.model.ApplicationUser;



public interface ApplicationUserRepository  extends JpaRepository<ApplicationUser, String>{
	//Optional<ApplicationUser> findByUsername(String userName);

}
