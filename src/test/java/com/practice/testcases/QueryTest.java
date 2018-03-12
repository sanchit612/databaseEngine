package com.practice.testcases;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import com.practice.databaseEngine.*;

import java.util.ArrayList;

import org.junit.Assert;
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
	Query queryTest = null;
	@BeforeAll
	protected static void init() {
		//System.out.println("called before all testcases");
	}
	
	@BeforeEach
	protected void setup() {
		queryTest = new Query();
		//System.out.println("called before each test case");
	}
	
	@Test
	protected void testTokens() {
		String query = "select id from everything";
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("select");
		expected.add("id");
		expected.add("from");
		expected.add("everything");
		Assert.assertEquals("successful",expected,queryTest.setTokens(query));	
	}
	
	@AfterEach
	protected void teardown() {
		queryTest = null;
		//System.out.println("called after each test case");
	}
	
	@AfterAll
	protected static void cleanup() {
		//System.out.println("called after all test cases");
	}
}
