package com.techleads.app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.techleads.app.dto.LoginDto;

@TestMethodOrder(Alphanumeric.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc

class JwtSbBlogApiApplicationTests {

	@LocalServerPort
	int port;
	@Autowired
	private MockMvc mvc;
	@Autowired
	TestRestTemplate template;
	static String user, pass, jwt, postBody, postTitle;
	static int postCount, postId = -1;

	public String generateString() {
		String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random random = new Random();
		candidateChars.charAt(random.nextInt(candidateChars.length()));
		String randStr = "";
		while (randStr.length() < 8)
			randStr += candidateChars.charAt(random.nextInt(candidateChars.length()));
		return randStr;
	}

	@Test
	public void test1_register() {
		try {
			user = generateString();
			pass = generateString();
			JSONObject json = new JSONObject();
			json.put("name", user).put("password", pass);
			mvc.perform(post("http://localhost:" + port + "/register").contentType(MediaType.APPLICATION_JSON)
					.content(json.toString())).andExpect(status().isOk())
					.andExpect(jsonPath("$.data", is("email Email cannot be blank")));
			json.put("email", user + "@gmail.com");
			mvc.perform(post("http://localhost:" + port + "/register").contentType(MediaType.APPLICATION_JSON)
					.content(json.toString())).andExpect(status().isOk())
					.andExpect(jsonPath("$.data", is("User Registered")));
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}
	@Test
	public void test2_login() {
		try {
			LoginDto obj = new LoginDto();
			obj.setEmail(user);
			obj.setPassword(pass);
			JSONObject json = new JSONObject(
					template.postForEntity("http://localhost:" + port + "/login/", obj, String.class).getBody());
			
			
			assertEquals(json.getString("data"), "Invalid Username or Password");
			
			
			obj.setEmail(user + "@gmail.com");
			json = new JSONObject(
					template.postForEntity("http://localhost:" + port + "/login/", obj, String.class).getBody());
			jwt = json.getString("data");
			assert (!jwt.equals(""));
			HttpHeaders headers = new HttpHeaders();
			headers.set("authorization", "Bearer " + (char) ((int) jwt.charAt(0) + 1) + jwt.substring(1, jwt.length()));
			ResponseEntity<String> res = template.exchange("http://localhost:" + port + "/api/getPostCount",
					HttpMethod.GET, new HttpEntity<String>(headers), String.class);
			assert (res.getBody().contains("Unable to read JSON value"));
			assertEquals(500, res.getStatusCodeValue());
			headers.set("authorization", "Bearer " + jwt);
			json = new JSONObject(template.exchange("http://localhost:" + port + "/api/getPostCount", HttpMethod.GET,
					new HttpEntity<String>(headers), String.class).getBody());
			postCount = json.getInt("data");
			assert (postCount >= 0);
		} catch (Exception e) {
			e.printStackTrace();
			assert (false);
		}
	}

	
}
