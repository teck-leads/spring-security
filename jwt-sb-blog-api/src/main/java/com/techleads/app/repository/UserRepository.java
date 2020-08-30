package com.techleads.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techleads.app.model.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {
	Optional<Users> findByName(String username);
	Optional<Users> findByEmail(String email);
	//Users findByName(String username);
}
