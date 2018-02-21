package com.practice.databaseEngine;
import java.io.*;
import java.util.*;
import java.util.regex.*;


public class FileRead {
	
	private ArrayList<ArrayList<String>> heading = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> randomDataFirstRow = new ArrayList<ArrayList<String>>();
	private String[][] dataType = new String[heading.size()][2];
	
	public ArrayList<ArrayList<String>> getHeading(){
		return heading;
	}
	
	public ArrayList<ArrayList<String>> getRandomDataFirstRow(){
		return randomDataFirstRow;
	}
	
	public ArrayList<ArrayList<String>> getData(int startRowNumber, int numberOfRows) {
		BufferedReader br = null;
        //list of string arrays
        ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
        try {
			br = new BufferedReader(new FileReader("/home/sapient/Documents/ipl.csv"));
			String line;
			
	        //got the column names
			while ((line = br.readLine()) != null) {
				ArrayList<String> arr = new ArrayList<String>(Arrays.asList(line.split(",")));
		        //everything stored in this list
				rowList.add(arr);
			}
			//System.out.println(rowList.get(0));
			//System.out.println(rowList.get(1));
			if(startRowNumber == 0 && numberOfRows == 1)
			{
				
				heading.add(rowList.get(0));
				return heading;
				
			}
			
			if(startRowNumber == 1 && numberOfRows == 1)
			{
				randomDataFirstRow.add(rowList.get(1));
				return randomDataFirstRow;
			}
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
	
	public void setDataType(ArrayList<ArrayList<String>> firstDataRow) {
		//contains the first row
		ArrayList<String> columns = firstDataRow.get(0);
		//for integers and date
		Pattern number = Pattern.compile("[0-9]+");
		Pattern date = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher m;
				
		ArrayList<String> type = new ArrayList<String>();
		for(String s : columns) {
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
		String[] head = heading.get(0).toArray(new String[0]);
		String[] col = columns.toArray(new String[0]);

			for(int j = 0 ; j < head.length;j++)
			{
				dataType[j][0] = head[j];
				dataType[j][1] = col[j];
			}
			
			for(int i = 0;i<2;i++)
			{
				for(int j = 0;j<head.length;j++)
				{
					System.out.println(dataType[i][j]);
				}
			}
	}
	
	public String[][] getDataType()
	{
		return dataType;
	}
}