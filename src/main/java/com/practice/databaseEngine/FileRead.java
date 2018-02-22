package com.practice.databaseEngine;
import java.io.*;
import java.util.*;
import java.util.regex.*;


public class FileRead {
	
	private ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
	private ArrayList<String> heading = new ArrayList<String>();
	private ArrayList<String> randomDataFirstRow = new ArrayList<String>();
	private String[][] dataType = new String[3][];
	
	public ArrayList<ArrayList<String>> getRowList(){
		return rowList;
	}
	
	public ArrayList<String> getHeading(){
		return heading;
	}
	
	public ArrayList<String> getRandomDataFirstRow(){
		return randomDataFirstRow;
	}
	
	public ArrayList<ArrayList<String>> setRowList() {
		BufferedReader br = null;
        //list of string arrays
        
        try {
			br = new BufferedReader(new FileReader("./ipl.csv"));
			String line;
			
	        //got the column names
			while ((line = br.readLine()) != null) {
				ArrayList<String> arr = new ArrayList<String>(Arrays.asList(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")));
		        //everything stored in this list
				rowList.add(arr);
			}
			//System.out.println(rowList.get(0));
			//System.out.println(rowList.get(1));
		} 
        
        catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return rowList;
	}
	
	public void setHeading() {
		//heading.add(rowList.get(0));
		
		for(int i=0;i<rowList.get(0).size();i++)
		{
			heading.add(rowList.get(0).get(i));
		}
		//System.out.println(heading);
	}
	
	public void setRandomDataFirstRow() {
		
		for(int i=0;i<rowList.get(2).size();i++)
		{
			randomDataFirstRow.add(rowList.get(2).get(i));
		}
		//System.out.println(randomDataFirstRow);
	}
	public void setDataType(ArrayList<String> firstDataRow) {
		//contains the first row
		//for integers and date
		Pattern number = Pattern.compile("[0-9]+");
		Pattern date = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher m;
				
		ArrayList<String> type = new ArrayList<String>();
		for(String s : firstDataRow) {
			m = date.matcher(s);
			if(m.find()) {
				type.add("Date");
			}
			else {
				m = number.matcher(s);
				if(m.find())
					type.add("Integer");
				else
					type.add("String");
			}
		}
		int z = heading.size();
		//System.out.println(z);
		int y = randomDataFirstRow.size();
		int x = type.size();
		dataType[0] = new String[z];
		dataType[1] = new String[y];
		dataType[2] = new String[x];
		for(int j = 0;j<z;j++)
		{
			dataType[0][j] = heading.get(j);
			
		}
		for(int j = 0;j<y;j++)
		{
		dataType[1][j] = randomDataFirstRow.get(j);
		}
		//System.out.println(dataType[0].length);
		for(int j = 0;j<x;j++)
		{
		dataType[2][j] = type.get(j);
		}

	}
	
	public void getDataType()
	{
		
	    for(int i=0;i<(dataType[0].length);i++)
	    {
	    	for(int j = 0;j<3;j++)
	    	{
	    		if(dataType[j][i].length() <8)
	    		{
	    		System.out.print(dataType[j][i]);
	    		System.out.print("\t\t\t\t");
	    		}
	    		else
	    		{
	    			System.out.print(dataType[j][i]);
		    		System.out.print("\t\t\t");
	    		}
	    	}
	    	System.out.println();
	    }
	}
}