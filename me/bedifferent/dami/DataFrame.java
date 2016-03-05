package me.bedifferent.dami;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFrame {
	private Object[][] df;
	
	public void readFromCSV(String csvFile, String seperator, String nullValue, boolean skipHeaderRow) {
		List<Object[]> lines = new ArrayList<Object[]>();
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(new File(csvFile)));
		} catch (FileNotFoundException e) {
			System.out.println("No such file");
			e.printStackTrace();
		}

		String line;

		try {
			while ((line = bufRdr.readLine()) != null) {
				if (skipHeaderRow) {
					skipHeaderRow = false;
					continue;
				}

				String[] arr = line.split(seperator); 

				for(int i = 0; i < arr.length; i++){
					if(arr[i].equals("")){
						arr[i] = nullValue;
					}				
				}

				lines.add(arr);			
			}
			
			Object[][] ret = new Object[lines.size()][];
			bufRdr.close();
			df = lines.toArray(ret);
		} catch (IOException e) {
			System.out.println("csv file parsing error");
			e.printStackTrace();
		}
	}
	
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Object[] line : df) {
			sb.append(Arrays.toString(line)).append("\n");
		}
		
		return sb.toString();
	}
}
