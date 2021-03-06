package com.techleads.app.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techleads.app.dto.LoginDto;
import com.techleads.app.dto.PostDTO;
import com.techleads.app.dto.UpdatePostDTO;
import com.techleads.app.model.Posts;
import com.techleads.app.model.Users;
import com.techleads.app.service.PostService;
import com.techleads.app.service.UserService;
import com.techleads.app.util.JWTUtil;
import com.techleads.app.util.PostData;
import com.techleads.app.util.PostsData;
import com.techleads.app.util.PostsEmpty;
import com.techleads.app.util.UserResponse;

//https://spring.io/guides/tutorials/bookmarks/
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private JWTUtil jWTUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(value = { "/register" })
	public ResponseEntity<UserResponse> registerUser(@RequestBody Users user) {
		try {
			UserResponse response = userService.saveUser(user);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw e;
		}

	}

	@PostMapping(value = { "/login/" })
	public ResponseEntity<LoginDto> loginUser(@RequestBody LoginDto userRequest) {

		try {
			if (!userRequest.isValid(userRequest.getEmail())) {
				userRequest.setData("Invalid Username or Password");
				return new ResponseEntity<>(userRequest, HttpStatus.OK);
			} else {

				// validate username/pwd with db
				/* Authentication authenticate = */authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
				String token = jWTUtil.generateToken(userRequest.getEmail());
				userRequest.setToken(token);
				userRequest.setData(token);
				userRequest.setMsg("Authentication successful!");
				return new ResponseEntity<>(userRequest, HttpStatus.OK);
			}
		} catch (AuthenticationException e) {
			throw e;
		}

	}

	@GetMapping(value = { "/api/getPostCount" })
	public ResponseEntity<LoginDto> loggedInUser(Principal pricipal) {
		try {
			LoginDto resp = new LoginDto();
			if (null != pricipal) {
				Users user = userService.findByEmail(pricipal.getName());
				List<Posts> postsList = user.getPostsList();
				int postsCount = postsList.size();
				resp.setName(pricipal.getName());
				resp.setData(String.valueOf(postsCount));
				return new ResponseEntity<>(resp, HttpStatus.OK);
			} else {
				resp.setData("Unable to read JSON value");
				return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			throw e;
		}

	}

	/* Save a POST */
	@PostMapping(value = { "/api/publish" })
	public ResponseEntity<PostDTO> savePost(@RequestBody PostDTO postDto, Principal pricipal) {
		try {
			PostDTO postDTO = new PostDTO();
			if (null != pricipal) {
				Users user = userService.findByEmail(pricipal.getName());
				UserResponse response = postService.savePost(postDto, user);
				postDTO.setData(response.getData());
				return new ResponseEntity<>(postDTO, HttpStatus.OK);
			} else {
				postDTO.setData("Unable to read JSON value");
				return new ResponseEntity<>(postDTO, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	/* update a POST */
	@PostMapping(value = { "/api/updatePost" })
	public ResponseEntity<UserResponse> updatePost(@RequestBody UpdatePostDTO postDto, Principal pricipal) {
		try {
			UserResponse resp = new UserResponse();
			if (null != pricipal) {
				postService.updatePost(postDto);
				resp.setData("Post updated");
				return new ResponseEntity<>(resp, HttpStatus.OK);
			} else {
				resp.setData("Post not updated");
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping(value = { "/api/getPost" })
	public ResponseEntity<PostsData> findAllPosts(Principal pricipal) {
		try {
			List<Posts> postsList = new ArrayList<>();
			List<PostDTO> dtos = new ArrayList<>();
			PostsData data = new PostsData();
			if (null != pricipal) {
				Users user = userService.findByEmail(pricipal.getName());
				postsList = user.getPostsList();

				postsList.forEach(post -> {
					PostDTO dto = new PostDTO();
					dto.setPost_id(post.getPostId());
					dto.setTitle(post.getPostTitle());
					dto.setBody(post.getPostBody());
					dtos.add(dto);
				});

				data.setData(dtos);
				return new ResponseEntity<>(data, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(data, HttpStatus.OK);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	@GetMapping(value = { "/api/getPost/{id}" })
	public ResponseEntity<?> findByPostId(@PathVariable("id") Integer id, Principal pricipal) {
		try {
			PostData data = new PostData();
			if (null != pricipal) {
				Users user = userService.findByEmail(pricipal.getName());
				PostDTO dto = postService.findPostById(id);

				if (dto.getPost_id() == 0) {
					PostsEmpty noPost = new PostsEmpty();
					noPost.setData("Post Not Found");
					return new ResponseEntity<>(noPost, HttpStatus.OK);
				} else {
					data.setData(dto);
				}
				data.setUsername(user.getName());
				return new ResponseEntity<>(data, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(data, HttpStatus.OK);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	@GetMapping(value = { "/api/getPostByUser/{userId}" })
	public ResponseEntity<PostsData> findPostsByUserId(@PathVariable("userId") Integer userId, Principal pricipal) {
		try {
			List<Posts> postsList = new ArrayList<>();
			List<PostDTO> dtos = new ArrayList<>();
			PostsData data = new PostsData();
			if (null != pricipal) {
				Users user = userService.findUserById(userId);
				postsList = user.getPostsList();

				postsList.forEach(post -> {
					PostDTO dto = new PostDTO();
					dto.setPost_id(post.getPostId());
					dto.setTitle(post.getPostTitle());
					dto.setBody(post.getPostBody());
					dto.setCreated_by(post.getCreated_by());
					dtos.add(dto);
				});

				if (dtos.size() == 0) {
					PostDTO dto = new PostDTO();
					dto.setData("No posts by user Id " + userId);
					dtos.add(dto);
				}

				data.setData(dtos);
				return new ResponseEntity<>(data, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(data, HttpStatus.OK);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	/* Delete a POST */
	@GetMapping(value = { "/api/deletePost/{postId}" })
	public ResponseEntity<UserResponse> deletePostById(@PathVariable("postId") Integer postId, Principal pricipal) {
		try {
			UserResponse resp = new UserResponse();
			if (null != pricipal) {
				UserResponse deletePostById = postService.deletePostById(postId);
				resp.setData(deletePostById.getData());
				return new ResponseEntity<>(resp, HttpStatus.OK);
			} else {
				resp.setData("Post not Deleted");
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}

		} catch (Exception e) {
			throw e;
		}
	}

}
