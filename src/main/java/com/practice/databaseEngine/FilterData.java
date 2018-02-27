package com.practice.databaseEngine;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import org.json.JSONObject;

public class FilterData {
	

	void getPrintSelectFields(Query qr) {
		System.out.println(qr.getSelectFields());
	}
	
	public static void createJSON(Query qr,FileRead fr) {    
	    JSONObject queryJsonObj = new JSONObject();
	    
	    ArrayList<String> allFields = new ArrayList<String>();
	    allFields.add("*");
	    
	    if(qr.getSelectFields() == allFields)
	    {
	    	for(int i = 0;i<fr.getHeading().size();i++)
	    	{
	    		
	    	}
	    }
	    
	    for(int i = 0;i<qr.getSelectFields().size();i++)
	    {
	    	ArrayList<String> al = new ArrayList<String>();
	    	queryJsonObj.put(qr.getSelectFields().get(i), al);
	    	
	    }
	    
	    /*
	    // {"id": 100001, "nationality", "American"}
	    JSONObject passportJsonObj = new JSONObject();
	    passportJsonObj.put("id", 100001);
	    passportJsonObj.put("nationality", "American");
	    // Value of a key is a JSONObject
	    tomJsonObj.put("passport", passportJsonObj);
	    
	    if (prettyPrint) {
	        // With four indent spaces
	        System.out.println(tomJsonObj.toString(4));
	    } else {
	        System.out.println(tomJsonObj.toString());
	    }*/
	}
}
