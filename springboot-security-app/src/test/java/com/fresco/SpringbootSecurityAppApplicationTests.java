package com.fresco;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.techleads.app.SpringbootSecurityAppApplication;

@SpringBootTest(classes  = SpringbootSecurityAppApplication.class)
class SpringbootSecurityAppApplicationTests {

	 @BeforeAll
	  public static void setUpBeforeClass() throws Exception {
	   System.out.println("In @BeforeAll");
	  }

	  @BeforeEach
	  public void setUp() throws Exception {
	   System.out.println("\n"+"In @BeforeEach");
	  }

		
	  @Test
	  public void testDummy() {
	   System.out.println("\n"+"In testDummy()");
	  }

	  @Test
	  public void testData() {
	    System.out.println("\n"+"In testData()");
	  }
			
	  @AfterEach
	  public void tearDown() throws Exception {
	   System.out.println("\n"+"In @AfterEach");
	  }

	  @AfterAll
	  public static void tearDownAfterClass() throws Exception {
	   System.out.println("\n"+"In @AfterAll");
	  }

}
