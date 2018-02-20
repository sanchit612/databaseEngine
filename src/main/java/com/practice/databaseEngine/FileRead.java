package com.practice.databaseEngine;
import java.io.*;
import java.util.*;
import java.util.regex.*;


public class FileRead {
	
	public ArrayList<String[]> allData() {
		BufferedReader br = null;
        //list of string arrays
        ArrayList<String[]> rowList = new ArrayList<String[]>();
        try {
			br = new BufferedReader(new FileReader("/home/sapient/Documents/ipl.csv"));
			String line;
			String[] arr;
	        //got the column names
			while ((line = br.readLine()) != null) {
				arr = line.split(",");
		        //everything stored in this list
				rowList.add(arr);
			}
			
									
		} catch (FileNotFoundException e) {
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
	
	public ArrayList<String> getDatatype(ArrayList<String[]> rowList) {
		//contains the first row
		String[] columns = rowList.get(1);
		//for integers and date
		Pattern number = Pattern.compile("[0-9]+");
		Pattern date = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher m;
				
		ArrayList<String> datatype = new ArrayList<String>();
		for(String s : columns) {
			m = date.matcher(s);
			if(m.find()) {
				datatype.add("Date");
			}
			else {
				m = number.matcher(s);
				if(m.find())
					datatype.add("Integer");
				else
					datatype.add("String");
			}
		}
		return datatype;

	}


}