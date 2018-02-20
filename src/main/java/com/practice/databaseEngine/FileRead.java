package com.practice.databaseEngine;
import java.io.*;

public class FileRead {
	
	public void readFile() throws Exception {
		String splitBy = ",";
		BufferedReader br = new BufferedReader(new FileReader("~/Documents/ipl.csv"));
		String line = br.readLine();
        while((line = br.readLine())!=null){
             String[] b = line.split(splitBy);
             System.out.println(b[0]);
        }
        br.close();
	}

}
