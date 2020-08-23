package com.techleads.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techleads.app.model.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
	
	Optional<Users> findByUsername(String userName);

}
