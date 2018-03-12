package com.practice.databaseEngine;
//we need to import java.util package . java.lang package imports automatically

import java.util.Scanner;
import java.util.ArrayList;

public class Index {
	
	public void printTokens(Query objQuery) {
		
		System.out.println("Words in query : ");
		for(String i : objQuery.getTokens()) {
			if(i.contentEquals(","))
			{
				continue;
			}   
			System.out.println("  "+i);
		}
		System.out.println();
		System.out.println();
	}
	
	public void printSource(Query objQuery) {
		
		System.out.println("Source : ");
		objQuery.setSource(objQuery.getTokens());
		System.out.println(objQuery.getSource());
		System.out.println();
	}
	
	public void printBasePart(Query objQuery) {
		
		System.out.println("Base Part : ");
		objQuery.setBasePart(objQuery.getTokens());
		for(String i : objQuery.getBasePart()) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println();
		
	}
	
	public void printFilterPart(Query objQuery) {
		
		System.out.println("Filter Part : ");
		objQuery.setFilterPart(objQuery.getTokens());
		for(String i : objQuery.getFilterPart()) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println();
		
	}
	
	public void printConditions(Query objQuery) {
		
		System.out.println("Conditions : ");
		objQuery.setConditions(objQuery.getFilterPart());
		
		for(ArrayList<String> i : objQuery.getConditions()) {
			for(String j : i)
			{
				System.out.print(j + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printLogicalOperatorSeq(Query objQuery) {
		
		System.out.println("Logical Operators in seq : ");
		objQuery.setLogicalOpSeq(objQuery.getFilterPart());
		for(String i : objQuery.getLogicalOpSeq()) {
			System.out.println(i);
		}
		System.out.println();
	}
	
	public void printSelectFields(Query objQuery) {
		
		System.out.println("Select Fields : ");
		objQuery.setSelectFields(objQuery.getBasePart());
		
		for(String i : objQuery.getSelectFields()) {
			System.out.println(i);
		}
		System.out.println();
	}
	
	public void printOrderByFields(Query objQuery) {
		
		System.out.println("Order By Fields : ");
		objQuery.setOrderByFields(objQuery.getFilterPart());
		for(String i : objQuery.getOrderByFields()) {
			System.out.println(i);
		}
		System.out.println();
	}
	
	public void printGroupByFields(Query objQuery) {
		
		System.out.println("Group By Fields : ");
		objQuery.setGroupByFields(objQuery.getFilterPart());
		for(String i : objQuery.getGroupByFields()) {
			System.out.println(i);
		}
		System.out.println();
	}
	
	public void printAggregateFields(Query objQuery) {
		
		System.out.println("Aggregate Fields : ");
		objQuery.setAggregateFields(objQuery.getBasePart());
		for(String i : objQuery.getAggregateFields()) {
			System.out.println(i);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		//System.in is input stream class.  System.out is an output stream class. sc is a pointer
		Scanner sc = new Scanner(System.in);
		
		//creating a new object of query
		Query query = new Query();		
		
		//taking input from user and initializing input
		query.setInput(sc.nextLine());
		
		//initializing tokens		
		query.setTokens(query.getInput());
		
		// printing the tokens
		new Index().printTokens(query);
		
		// printing the base part
	    new Index().printBasePart(query);
		
		// printing the source
		new Index().printSource(query);
		
		// printing the filter part
		new Index().printFilterPart(query);
		
		//setting index of 'AND'
		query.setAndIndex(query.getFilterPart());
		
		//setting index of 'OR'
		query.setOrIndex(query.getFilterPart());
		
		//setting index of 'NOT'
		query.setNotIndex(query.getFilterPart());
		
		//setting indexes of all three logical operators
		query.setLogicalOpIndex();
		
		//printing conditions
		new Index().printConditions(query);
		
		//printing Logical Operators in sequence
		new Index().printLogicalOperatorSeq(query);
		
		//printing Select Fields
		new Index().printSelectFields(query);
		
		//printing Order By Fields
		new Index().printOrderByFields(query);
		
		//printing Group By Fields
		new Index().printGroupByFields(query);
		
		//printing Aggregate Fields
		new Index().printAggregateFields(query);
		        
	    //creating an object for performing methods on file
	    FileRead fr = new FileRead();
	    
	    //
	    fr.setRowList();
	    fr.setHeading();
	    fr.setRandomDataFirstRow();
	    fr.setDataType(fr.getRandomDataFirstRow());
	    fr.getDataType();
	    sc.close();
	    
	    FilterData fd = new FilterData();
	    fd.createJSON(query,fr);
	    
	    //Extracting Data from file
	    
		}		
	}

// regex
// test cases
// Select heelo dfjd jd from
// Select * from ipl.csv
// Select * from ipl.csv where hello = "true"
// select A , B , avg(win_by_wickets) , min(win_by_runs) from ipl.csv where season > 2016 and city= 'Bangalore' order by hello , bye group by qwerty
// select A, B, avg(win_by_wickets), min(win_by_runs) from ipl.csv where season > 2016 and city= 'Bangalore' group by qwerty order by hello , bye
// select A , B , avg(win_by_wickets) , min(win_by_runs) from ipl.csv where season > 2016 and city= 'Bangalore' or date = "12-02-2014" order by hello , bye group by qwerty, geep
// select city , date , avg(win_by_wickets) , min(win_by_runs) from ipl.csv where season > 2010 and city= 'Bangalore' or field = 'bat' order by season , id group by team1   