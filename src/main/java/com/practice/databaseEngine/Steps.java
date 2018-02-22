package com.practice.databaseEngine;
//we need to import java.util package . java.lang package imports automatically
import java.util.*;

public class Steps {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);    //System.in is input stream class.  System.out is an output stream class. sc is a pointer
		Query query = new Query();		//creating a new object of query
		
		query.setInput(sc.nextLine());		//taking input from user
		//sc.close();		//closing the scanner
				
		query.setTokens(query.getInput());
		//query.setTokens("select * from sanchit");  //hard coded value
		
		
		System.out.println("Words in query : ");
		for(String i : query.getTokens()) {
			System.out.print(i + " : ");
		}
		System.out.println();
		System.out.println();
		
		
		System.out.println("Filename : ");
		query.setSource(query.getTokens());
		System.out.println(query.getSource());
		System.out.println();
		
		
		System.out.println("Base Part : ");
		query.setBasePart(query.getTokens());
		for(String i : query.getBasePart()) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println();
		
		
		System.out.println("Filter Part : ");
		query.setFilterPart(query.getTokens());
		for(String i : query.getFilterPart()) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.println();
		
		
		query.setAddIndex(query.getFilterPart());
		query.setOrIndex(query.getFilterPart());
		query.setNotIndex(query.getFilterPart());
		
		query.setLogicalOpIndex();
		
		System.out.println("Conditions : ");
		query.setConditions(query.getFilterPart());
		
		for(ArrayList<String> i : query.getConditions()) {
			for(String j : i)
			{
				System.out.print(j + " ");
			}
			System.out.println();
		}
		System.out.println();
		
		System.out.println("Logical Operators in seq : ");
		query.setLogicalOpSeq(query.getFilterPart());
		for(String i : query.getLogicalOpSeq()) {
			System.out.println(i);
		}
		System.out.println();
		
		System.out.println("Select Fields : ");
		query.setSelectFields(query.getBasePart());
		
		for(String i : query.getSelectFields()) {
			System.out.println(i);
		}
		System.out.println();
		
		System.out.println("Order By Fields : ");
		query.setOrderByFields(query.getFilterPart());
		for(String i : query.getOrderByFields()) {
			System.out.println(i);
		}
		System.out.println();
		
		System.out.println("Group By Fields : ");
		query.setGroupByFields(query.getFilterPart());
		for(String i : query.getGroupByFields()) {
			System.out.println(i);
		}
		System.out.println();
		
		System.out.println("Aggregate Fields : ");
		query.setAggregateFields(query.getBasePart());
		for(String i : query.getAggregateFields()) {
			System.out.println(i);
		}
		System.out.println();
		        
	    //object for performing methods on file
	    FileRead fr = new FileRead();
	    fr.setRowList();
	    fr.setHeading();
	    fr.setRandomDataFirstRow();
	    fr.setDataType(fr.getRandomDataFirstRow());
	    fr.getDataType();
	    sc.close();
	    
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