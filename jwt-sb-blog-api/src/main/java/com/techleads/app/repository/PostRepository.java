package com.techleads.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techleads.app.model.Posts;

public interface PostRepository extends JpaRepository<Posts,Integer> {

  
}
