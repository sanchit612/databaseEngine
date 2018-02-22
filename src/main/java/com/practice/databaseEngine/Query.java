package com.practice.databaseEngine;
import java.util.*;

public class Query {
	

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

	public ArrayList<String> setTokens(String data) {
		ArrayList<String> test = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(data);
		while (st.hasMoreTokens()) {  
			tokens.add(st.nextToken());
	    }
		//System.out.println(tokens.size());
		//System.out.println(tokens.get(0).toLowerCase());
		//System.out.println(tokens.get(2).toLowerCase());
		if(tokens.size() < 4)
		{
			System.out.println("Not a valid query");
			System.exit(0);
		}
		if(!tokens.get(0).toLowerCase().contentEquals("select") || !tokens.contains("from"))
		{
			System.out.println("Not a valid query");
			System.exit(0);
		}
		test = tokens;
		//System.out.println(test);
		return test;
	}

	public String getSource() {
		return source;
	}

	public void setSource(ArrayList<String> data) {
		//for a single source. If there are multiple files, use reg ex
		int fromIndex = data.indexOf("from");
		if(fromIndex != -1 &&  (fromIndex != data.size()-1))
		{
			source =  data.get(fromIndex+1);
		}
		
	}

	public ArrayList<String> getBasePart() {
		return basePart;
	}

	public void setBasePart(ArrayList<String> data) {
		int whereIndex = data.indexOf("where");
		if(whereIndex != -1)
		{
			for(int i=0 ; i < whereIndex ; i++) {
				basePart.add(data.get(i));
			}
		}
		else
		{
			for(int i=0 ; i < data.size() ; i++) {
				basePart.add(data.get(i));
			}
		}
	}

	public ArrayList<String> getFilterPart() {
		return filterPart;
	}

	public void setFilterPart(ArrayList<String> data) {
		int whereIndex = data.indexOf("where");
		if(whereIndex != -1)
		{
			for(int i=whereIndex+1 ; i < data.size() ; i++) {
				filterPart.add(data.get(i));
			}
		}
		else
		{
			System.out.println("Query has no filter part");
		}
	}

	/*from here control goes to AND OR NOT LOGICAL functions*/
	public ArrayList<ArrayList<String>> getConditions() {
		return conditions;
	}
	
	public void setConditions(ArrayList<String> data) {
		int orderIndex = data.indexOf("order");
		int groupIndex = data.indexOf("group");
		if(logicalOpIndex.size() != 0)
		{
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
					if((orderIndex != -1) && (groupIndex != -1))
					{
						if(orderIndex < groupIndex)
						{
							end = orderIndex;
						}
						else
						{
							end = groupIndex;
						}
					}
					
					else if((orderIndex != -1) && (groupIndex == -1))
					{
						end = orderIndex;
					}
					else if((orderIndex == -1) && (groupIndex != -1))
					{
						end = groupIndex;
					}
					else
					{
						end = data.size();
					}
				}
				else
				{
					end = logicalOpIndex.get(count);
				}
			}
		}
		else
		{
			ArrayList<String> test = new ArrayList<String>();
			for(int i = 0; i < data.size() ; i++)
			{
				test.add(data.get(i));
			}
			conditions.add(test);
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
	
	/*from here control goes to conditions part*/

	public ArrayList<String> getLogicalOpSeq() {
		return logicalOpSeq;
	}

	public void setLogicalOpSeq(ArrayList<String> data) {
		if(logicalOpIndex.size() != 0)
		{
			int count = 0;
			for(int i = 0; i < data.size(); i++)
			{
				if(i == logicalOpIndex.get(count))  //handle exception here
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
	}

	public ArrayList<String> getSelectFields() {
		return selectFields;
	}

	public void setSelectFields(ArrayList<String> data) {
		int fromIndex = data.indexOf("from");
		//regex for A,B
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





	/*add more functions above */ 
	}
