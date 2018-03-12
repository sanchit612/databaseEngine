package com.practice.databaseEngine;

import java.util.ArrayList;
import java.util.StringTokenizer;

import edu.emory.mathcs.backport.java.util.Collections;

public class Query {
	
	//Query in the form of string
	private String input;
	
	// Token form of string
	private ArrayList<String> tokens  = new ArrayList<String>();
	
	// Source to fetch data from
	private String source;
	
	// Base part of query - Select * from ipl.csv
	private ArrayList<String> basePart = new ArrayList<String>();
	
	// Filter part of query - city = "Chennai" and .....
	private ArrayList<String> filterPart = new ArrayList<String>();
	
	// All conditions listed
	private ArrayList<ArrayList<String>> conditions = new ArrayList<ArrayList<String>>();
	
	//Position of index of keyword 'and' based on tokens
	private ArrayList<Integer> andIndex = new ArrayList<Integer>();
	
	//Position of index of keyword 'or' based on tokens
	private ArrayList<Integer> orIndex = new ArrayList<Integer>();
	
	//Position of index of keyword 'not' based on tokens
	private ArrayList<Integer> notIndex = new ArrayList<Integer>();
	
	//Position of index of all logical operators based on tokens
	private ArrayList<Integer> logicalOpIndex = new ArrayList<Integer>();
	
	// List of logical operators in sequence
	private ArrayList<String> logicalOpSeq = new ArrayList<String>();
	
	//List of select fields
	private ArrayList<String> selectFields = new ArrayList<String>();
	
	//List of order By fields
	private ArrayList<String> orderByFields = new ArrayList<String>();
	
	//List of group By fields
	private ArrayList<String> groupByFields = new ArrayList<String>();
	
	// List of aggregate functions
	private ArrayList<String> aggregateFields = new ArrayList<String>();


	// getting input variable
	public String getInput() {
		return input;
	}

	//setting input variable
	public void setInput(String data) {
		input = data;
	}

	//getting tokens variable
	public ArrayList<String> getTokens() {
		return tokens;
	}
	
	//setting tokens variable
	public ArrayList<String> setTokens(String data) {
		
		//for test case purposes
		ArrayList<String> test = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(data);
		while (st.hasMoreTokens()) {  
			tokens.add(st.nextToken());
	    }
		
		//checking for invalid queries
		//if word count of query is less than 4
		if(tokens.size() < 4){
			System.out.println("Not a valid query");
			System.exit(0);
		}
		
		//if first word is not select or third word is not for
		if(!tokens.get(0).toLowerCase().contentEquals("select") || !tokens.contains("from")){
			System.out.println("Not a valid query");
			System.exit(0);
		}
		
		//for test cases
		test = tokens;
		return test;
	}
	
	//getting the base part
	public ArrayList<String> getBasePart() {
		return basePart;
	}

	//setting the base part
	public void setBasePart(ArrayList<String> data) {
		
		// finding the index of where keyword
		int whereIndex = data.indexOf("where");
		
		// if where is present
		if(whereIndex != -1){
			for(int i=0 ; i < whereIndex ; i++) {
				basePart.add(data.get(i));
			}
		}else
		{
			for(int i=0 ; i < data.size() ; i++) {
				basePart.add(data.get(i));
			}
		}
	}
	
	// getting source
	public String getSource() {
		return source;
	}
	
	// setting source
	public void setSource(ArrayList<String> data) {
	
		//it is for a single source. If there are multiple files, use reg ex
		//finding the index of FROM since source will be just after this keyword
		int fromIndex = data.indexOf("from");
		
		// if from is present and from is not the last token of query ( a wrong query )
		if(fromIndex != -1 &&  (fromIndex != data.size()-1)){
			source =  data.get(fromIndex+1);
		}
		
	}
	
	//getting the filter part
	public ArrayList<String> getFilterPart() {
		return filterPart;
	}
	
	//setting the filter part
	public void setFilterPart(ArrayList<String> data) {
		
		//finding the index of where
		int whereIndex = data.indexOf("where");
		
		//if where is present
		if(whereIndex != -1){
			for(int i=whereIndex+1 ; i < data.size() ; i++) {
				filterPart.add(data.get(i));
			}
		}else
		{
			System.out.println("Query has no filter part");
		}
	}
	
	// getting the AND index
	public ArrayList<Integer> getAndIndex() {
			return andIndex;
	}

	//setting the AND index
	public void setAndIndex(ArrayList<String> data) {
			
		// string comparison for every token	
		for(int i = 0; i < data.size();i++) {
			if(data.get(i).equals("and")){
				andIndex.add(i);
			}	
		}
	}
		
	//getting the OR index
	public ArrayList<Integer> getOrIndex() {
			return orIndex;
	}
	
	//setting the OR index
	public void setOrIndex(ArrayList<String> data) {
		
		// string comparison for every token
		for(int i = 0; i < data.size();i++) {
			if(data.get(i).equals("or")){
				orIndex.add(i);
			}	
		}

	}
	
	// getting the NOT index
	public ArrayList<Integer> getNotIndex() {
		return notIndex;
	}
	
	//setting the NOT index
	public void setNotIndex(ArrayList<String> data) {
		
		// string comparison for every token
		for(int i = 0; i < data.size();i++) {
			if(data.get(i).equals("not")){
				notIndex.add(i);
			}	
		}

	}

	//getting the logical operator index
	public ArrayList<Integer> getLogicalOpIndex() {
		return logicalOpIndex;
	}
	
	//setting the logical operator index
	public void setLogicalOpIndex() {
		for(int j : andIndex) {
			logicalOpIndex.add(j);
		}
		for(int j : orIndex) {
			logicalOpIndex.add(j);
		}
		for(int j : notIndex) {
			logicalOpIndex.add(j);
		}
		
		//sorting the indexes. Gives a sorted array list that says I have operator at these indexes
		Collections.sort(logicalOpIndex);
	}
	
	//getting the conditions
	public ArrayList<ArrayList<String>> getConditions() {
		return conditions;
	}
	
	//setting the conditions
	public void setConditions(ArrayList<String> data) {
		
		//finding the index of Order and Group
		int orderIndex = data.indexOf("order");
		int groupIndex = data.indexOf("group");
		
		//if logical operators (AND, OR, NOT) are present
		if(logicalOpIndex.size() != 0)
		{
			int count = 0;		
			int start = 0; 		//initializing value of start as zero
			int end = logicalOpIndex.get(count); //initializing value of end to first logical op's index
			
			//loop will go on for portions divided by logical operators
			// if there are 3 logical operators then loop will go on 4 times (always +1 times)
			for(int i = 0; i < logicalOpIndex.size() + 1 ; i++)
			{
				ArrayList<String> test = new ArrayList<String>();
				for(int  j = start ; j < end ; j++)
				{
					test.add(data.get(j));		//test has one condition at the end of this loop
				}

				conditions.add(test);	//that condition is added to conditions variable
				
				if(count == logicalOpIndex.size())		//if count points to last logical operator
					//means we have added the last condition
				{
					break;
				}
				
				start = logicalOpIndex.get(count) + 1;	//start is now next token after logical operator
				count++;	//count is incremented to show we are dealing with next logical operator
				
				//if after incrementing the count, it points to last logical operator
				if(count == logicalOpIndex.size())
				{
					//if order by and group by both are present
					if((orderIndex != -1) && (groupIndex != -1))
					{
						// if order by comes before group by 
						if(orderIndex < groupIndex)
						{
							end = orderIndex;
						}
						//group by comes before order by
						else
						{
							end = groupIndex;
						}
					}
					
					// if group by is absent. Only order by is present
					else if((orderIndex != -1) && (groupIndex == -1))
					{
						end = orderIndex;
					}
					
					//// if order by is absent. Only group by is present
					else if((orderIndex == -1) && (groupIndex != -1))
					{
						end = groupIndex;
					}
					
					//both order by and group by are absent
					else
					{
						end = data.size();
					}
				}
				
				//count after incrementing does not points to last logical operator
				else
				{
					end = logicalOpIndex.get(count);
				}
			}
		}
		
		//if logical operators are not present. only one condition is present
		else
		{
			ArrayList<String> test = new ArrayList<String>();
			for(int i = 0; i < data.size() ; i++)
			{
				test.add(data.get(i));	//condition is added to test after this loop
			}
			conditions.add(test); //test is added to conditions variable.
		}
	}
	
    //get logical operator sequence
	public ArrayList<String> getLogicalOpSeq() {
		return logicalOpSeq;
	}
	
	//set logical operator sequence
	public void setLogicalOpSeq(ArrayList<String> data) {
		
		//if logical operators are present
		if(logicalOpIndex.size() != 0)
		{
			//initializing count to 0
			int count = 0;
			//traversing through all the filter part
			for(int i = 0; i < data.size(); i++)
			{
				//comparing with indexes of logical operators and making sure we are handling exceptions
				if((i == logicalOpIndex.get(count)) && (count <logicalOpIndex.size()))
				{
					logicalOpSeq.add(data.get(i));
					count++;
				}
				
				//if index becomes equal to the size of the list
				if(count == logicalOpIndex.size())
				{
					break;
				}
			}
		}
	}
	
	// start from here//
	
	//get selected fields
	public ArrayList<String> getSelectFields() {
		return selectFields;
	}
	
	//setting select fields
	public void setSelectFields(ArrayList<String> data) {
		
		//getting index of from
		int fromIndex = data.indexOf("from");

		for(int i = 1; i < fromIndex ; i++)
		{
			//System.out.println(i);
			//System.out.println(data.get(i));
			//System.out.println(data.get(2));
			if(data.get(i).equals(",")) {
				continue;
			}
			if (data.get(i) != null && data.get(i).length() > 0 && data.get(i).charAt(data.get(i).length() - 1) == ',') {
				//System.out.println("error");
				String test = data.get(i).substring(0, data.get(i).length() - 1);
				data.remove(i);
				data.add(i,test);
				//System.out.println(data.get(i));
		    }
			selectFields.add(data.get(i));
		}
	}

	public ArrayList<String> getOrderByFields() {
		return orderByFields;
	}

	public void setOrderByFields(ArrayList<String> data) {
		int orderIndex = data.indexOf("order");
		if(orderIndex != -1)
		{
			int groupIndex = data.indexOf("group");
			int end;
			
			if(orderIndex < groupIndex)
			{
				end = groupIndex;
			}
			
			else
			{
				end = data.size();
			}
			
			for(int i = orderIndex + 2 ; i < end ; i++)
			{
				if(data.get(i).equals(",")) {
					continue;
				}
				if (data.get(i) != null && data.get(i).length() > 0 && data.get(i).charAt(data.get(i).length() - 1) == ',') {
					//System.out.println("error");
					String test = data.get(i).substring(0, data.get(i).length() - 1);
					data.remove(i);
					data.add(i,test);
					//System.out.println(data.get(i));
			    }
				orderByFields.add(data.get(i));
			}
		}
		
		else
		{
			System.out.println("Query has no Order By Field");
		}
	}

	public ArrayList<String> getGroupByFields() {
		return groupByFields;
	}

	public void setGroupByFields(ArrayList<String> data) {
		int groupIndex = data.indexOf("group");
		if(groupIndex != -1)
		{
			int orderIndex = data.indexOf("order");
			int end;
			
			if(orderIndex > groupIndex)
			{
				end = orderIndex;
			}
			
			else
			{
				end = data.size();
			}
			
			for(int i = groupIndex + 2 ; i < end ; i++)
			{
				if(data.get(i).equals(",")) {
					continue;
				}
				if (data.get(i) != null && data.get(i).length() > 0 && data.get(i).charAt(data.get(i).length() - 1) == ',') {
					//System.out.println("error");
					String test = data.get(i).substring(0, data.get(i).length() - 1);
					data.remove(i);
					data.add(i,test);
					//System.out.println(data.get(i));
			    }
				groupByFields.add(data.get(i));
			}
		}
		
		else
		{
			System.out.println("Query has no Group By Field");
		}
	}

	public ArrayList<String> getAggregateFields() {
		return aggregateFields;
	}

	public void setAggregateFields(ArrayList<String> data) {
		//handle the comma values, spaces using regex. will do in future. done for now
		for(int i = 0; i < data.size();i++) {
			//System.out.println(data.get(i).length());
			if(data.get(i).length()>6)
			{
				//System.out.println(data.get(i).substring(0,3));
				
				//System.out.println(data.get(i).substring(0,3).equals("min("));
				
			if(data.get(i).substring(0,4).equals("min(") ||
					data.get(i).substring(0,4).equals("max(") ||
					data.get(i).substring(0,4).equals("sum(") ||
					data.get(i).substring(0,4).equals("avg(") ||
					data.get(i).substring(0,6).equals("count(")
			  )
			{
				aggregateFields.add(data.get(i));
			}
		}
			
		}
		
		if(aggregateFields.size() == 0 )
		{
			System.out.println("Query does not have aggregate field");
		}
	}
	/*
	Query(Query obj){
		input = obj.input;
		tokens  = obj.tokens;
		source = obj.source;
		basePart = obj.basePart;
		filterPart = obj.filterPart;
		conditions = obj.conditions;
		andIndex = obj.andIndex;
		orIndex = obj.orIndex;
		notIndex = obj.notIndex;
		logicalOpIndex = obj.logicalOpIndex;
		logicalOpSeq = obj.logicalOpSeq;
		selectFields = obj.selectFields;
		orderByFields = obj.orderByFields;
		groupByFields = obj.groupByFields;
		aggregateFields = obj.aggregateFields;
		
	}
	*/



	/*add more functions above */ 
	}
