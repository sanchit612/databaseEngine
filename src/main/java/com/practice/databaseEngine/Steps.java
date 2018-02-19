package com.practice.databaseEngine;
//we need to import java.util package . java.lang package imports automatically
import java.util.*;

class Query{

private String input;
private ArrayList<String> tokens  = new ArrayList<String>();
private String source;
private ArrayList<String> basePart = new ArrayList<String>();
private ArrayList<String> filterPart = new ArrayList<String>();
private ArrayList<ArrayList<String>> conditions = new ArrayList<ArrayList<String>>();
private ArrayList<Integer> andIndex = new ArrayList<Integer>();
private ArrayList<Integer> orIndex = new ArrayList<Integer>();
private ArrayList<Integer> notIndex = new ArrayList<Integer>();
private ArrayList<Integer> logicalOpIndex = new ArrayList<Integer>();
private ArrayList<String> logicalOpSeq = new ArrayList<String>();
private ArrayList<String> selectFields = new ArrayList<String>();
private ArrayList<String> orderByFields = new ArrayList<String>();
private ArrayList<String> groupByFields = new ArrayList<String>();
private ArrayList<String> aggregateFields = new ArrayList<String>();



public String getInput() {
	return input;
}

public void setInput(String data) {
	input = data;
}

public ArrayList<String> getTokens() {
	return tokens;
}

public void setTokens(String data) {
	StringTokenizer st = new StringTokenizer(input);
	while (st.hasMoreTokens()) {  
		tokens.add(st.nextToken());
    }
}

public String getSource() {
	return source;
}

public void setSource(ArrayList<String> data) {
	int fromIndex = data.indexOf("from");
	source =  data.get(fromIndex+1);
}

public ArrayList<String> getBasePart() {
	return basePart;
}

public void setBasePart(ArrayList<String> data) {
	int whereIndex = data.indexOf("where");
	for(int i=0 ; i < whereIndex ; i++) {
		basePart.add(data.get(i));
	}
	
}

public ArrayList<String> getFilterPart() {
	return filterPart;
}

public void setFilterPart(ArrayList<String> data) {
	int whereIndex = data.indexOf("where");
	for(int i=whereIndex+1 ; i < data.size() ; i++) {
		filterPart.add(data.get(i));
	}
	
}

public ArrayList<ArrayList<String>> getConditions() {
	return conditions;
}

public void setConditions(ArrayList<String> data) {
	int count = 0;
	int start = 0;
	int end = logicalOpIndex.get(count);
	
	//System.out.println(logicalOpIndex);
	//System.out.println(end);
	//System.out.println(logicalOpIndex.size());
	for(int i = 0; i < logicalOpIndex.size() + 1 ; i++)
	{
		ArrayList<String> test = new ArrayList<String>();
		//System.out.println(start);
		//System.out.println(end);
		for(int  j = start ; j < end ; j++)
		{
			test.add(data.get(j));
		}
		//System.out.println(test);
		conditions.add(test);
		if(count == logicalOpIndex.size())
		{
			break;
		}
		start = logicalOpIndex.get(count) + 1;
		count++;
		if(count == logicalOpIndex.size())
		{
			end = data.size();
		}
		else
		{
			end = logicalOpIndex.get(count);
		}
	}
}

public ArrayList<Integer> getAndIndex() {
	return andIndex;
}

public void setAddIndex(ArrayList<String> data) {
	for(int i = 0; i < data.size();i++) {
		if(data.get(i).equals("and"))
		{
			andIndex.add(i);
		}	
}
}
	
public ArrayList<Integer> getOrIndex() {
		return orIndex;
	}

public void setOrIndex(ArrayList<String> data) {
		for(int i = 0; i < data.size();i++) {
			if(data.get(i).equals("or"))
			{
				orIndex.add(i);
			}	
	}

}

public ArrayList<Integer> getNotIndex() {
	return notIndex;
}

public void setNotIndex(ArrayList<String> data) {
	for(int i = 0; i < data.size();i++) {
		if(data.get(i).equals("not"))
		{
			notIndex.add(i);
		}	
}

}

public ArrayList<Integer> getLogicalOpIndex() {
	return logicalOpIndex;
}

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
	
	Collections.sort(logicalOpIndex);
}

public ArrayList<String> getLogicalOpSeq() {
	return logicalOpSeq;
}

public void setLogicalOpSeq(ArrayList<String> data) {
	int count = 0;
	for(int i = 0; i < data.size(); i++)
	{
		if(i == logicalOpIndex.get(count))
		{
			logicalOpSeq.add(data.get(i));
			count++;
		}
		if(count == logicalOpIndex.size())
		{
			break;
		}
	}
	
}

public ArrayList<String> getSelectFields() {
	return selectFields;
}

public void setSelectFields(ArrayList<String> data) {
	int fromIndex = data.indexOf("from");
	//System.out.println(fromIndex);
	//System.out.println(basePart);
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
	for(int i = 0; i < data.size();i++) {
		if(data.get(i).length()>4)
		{
		if(data.get(i).substring(0,3).equals("min(") ||
				data.get(i).substring(0,3).equals("max(") ||
				data.get(i).substring(0,3).equals("sum(") ||
				data.get(i).substring(0,3).equals("avg(") ||
				data.get(i).substring(0,5).equals("count(")
		  )
		{
			aggregateFields.add(data.get(i));
		}
	}	
	}
}





/*add more functions above */ 
}

public class Steps {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);    //System.in is input stream class.  System.out is an output stream class. sc is a pointer
		Query query = new Query();		//creating a new object of query
		
		query.setInput(sc.nextLine());		//taking input from user
		sc.close();		//closing the scanner
				
		query.setTokens(query.getInput());
		
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
		
	}

}

//select A , B , avg(win_by_wickets) , min(win_by_runs) from ipl.csv where season > 2016 and city= 'Bangalore' order by hello , bye group by qwerty
//select A, B, avg(win_by_wickets), min(win_by_runs) from ipl.csv where season > 2016 and city= 'Bangalore' group by qwerty order by hello , bye
