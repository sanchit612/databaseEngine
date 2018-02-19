package com.practice.testcases;
//import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import com.practice.databaseEngine.Query;

import org.junit.platform.runner.JUnitPlatform;



@RunWith(JUnitPlatform.class)
public class QueryTest {
	Query query = null;
	@BeforeAll
	public static void init() {
		System.out.println("called before all testcases");
	}
	
	@BeforeEach
	public void setup() {
		query = new Query();
		System.out.println("called before each test case");
	}
	
	@Test
	public void testTokens() {
		
	}
	
	@AfterEach
	public void teardown() {
		query = null;
		System.out.println("called after each test case");
	}
	
	@AfterAll
	public static void cleanup() {
		System.out.println("called after all test cases");
	}
	

}
