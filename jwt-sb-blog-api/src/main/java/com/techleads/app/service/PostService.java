package com.techleads.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techleads.app.dto.PostDTO;
import com.techleads.app.model.Posts;
import com.techleads.app.model.UserResponse;
import com.techleads.app.model.Users;
import com.techleads.app.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	private String POST_SAVED="Published";
	private String POST_BODY_EMPTY="body should not be empty";
	
	public UserResponse savePost(PostDTO dto, Users user) {
		try {
			UserResponse respo = new UserResponse();
			if (null == dto.getBody()) {
				respo.setData(POST_BODY_EMPTY);
			} else {
				Posts post = new Posts();
				post.setPostTitle(dto.getTitle());
				post.setPostBody(dto.getBody());
				
				post.setPublishedBy(user);
				
				postRepository.save(post);
				respo.setData(POST_SAVED);
			}

			return respo;
		} catch (Exception e) {
			throw e;
		}

	}
	
	public PostDTO findPostById(Integer id) {
		PostDTO dto = new PostDTO();
		Optional<Posts> findById = postRepository.findById(id);

		if (findById.isPresent()) {
			Posts post = findById.get();
			dto.setPost_id(post.getPostId());
			dto.setTitle(post.getPostTitle());
			dto.setBody(post.getPostBody());

		}
		return dto;

	}

}
