package com.practice.databaseEngine;
//import java.io.*;
import java.util.*;
//import java.util.regex.*;
import org.json.JSONObject;

public class FilterData {
	
	void getPrintSelectFields(Query qr) {
		System.out.println(qr.getSelectFields());
	}
	
	public void createJSON(Query qr,FileRead fr) {    
	    JSONObject queryJsonObj = new JSONObject();
	    
	    //fr.getHeading().size()
	    //fr.getRowList().size()
	    
	    if(qr.getSelectFields().size() == 1 && qr.getSelectFields().contains("*"))
	    {
	    	for(int i = 0;i<3;i++)
	    	{
	    		ArrayList<String> al = new ArrayList<String>();
	    		for(int j=1;j<10;j++)
	    		{
	    			al.add(fr.getRowList().get(j).get(i));
	    		}
	    		System.out.println(al);
		    	queryJsonObj.put(fr.getHeading().get(i), al);
	    	}
	    	
	    	System.out.println(queryJsonObj.toString());
	    }
	    
	    else {
	    
		    for(int i = 0;i<qr.getSelectFields().size();i++)
		    {
		    	ArrayList<String> al = new ArrayList<String>();
		    	queryJsonObj.put(qr.getSelectFields().get(i), al);
		    	
		    }
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
