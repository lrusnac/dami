package me.bedifferent.dami;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class DataFrame {
	private Object[][] df;
	private String[] header;
	private Class[] types;
	private int[] interestedColumns;
	
	String csvFile;
	
	public DataFrame(String[] header, Class[] types, int[] columns, String csvFile) {
		if(header.length != types.length || types.length != columns.length) {
			System.err.println("the arrays must have the same length");
			return;
		}
		this.header = header;
		this.interestedColumns = columns;
		this.types = types;
		this.csvFile = csvFile;
		
		this.readFromCSV(";", true);
	}
	
	private void readFromCSV(String seperator, boolean hasHeader) {
		List<Object[]> lines = new ArrayList<Object[]>();
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(new File(csvFile)));
		} catch (FileNotFoundException e) {
			System.err.println("No such file");
			e.printStackTrace();
		}

		String line;

		try {
			while ((line = bufRdr.readLine()) != null) {
				if (hasHeader) {
					hasHeader = false;
					continue;
				}
				String[] arr = line.split(seperator); 
				
				Object[] row = new Object[interestedColumns.length];
				
				for(int i=0; i<interestedColumns.length; i++) {
					if(types[i].equals(Integer.class)) {
						row[i] = Integer.parseInt(arr[interestedColumns[i]]);
					} else if(types[i].equals(Double.class)) {
						row[i] = Double.parseDouble(arr[interestedColumns[i]]);
					} else if(types[i].equals(Boolean.class)) {
						row[i] = Boolean.parseBoolean(arr[interestedColumns[i]]);
					} else {
						try {
							row[i] = types[i].getConstructor(String.class).newInstance(arr[interestedColumns[i]]);
						} catch (InstantiationException
								| IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException
								| NoSuchMethodException | SecurityException e) {
							System.err.println("cannot cast to type");
							e.printStackTrace();
						}
					}
					
				}
				
				lines.add(row);			
			}
			
			Object[][] ret = new Object[lines.size()][];
			bufRdr.close();
			df = lines.toArray(ret);
		} catch (IOException e) {
			System.err.println("csv file parsing error");
			e.printStackTrace();
		}
	}
	
	public int getSize() {
		return df.length;
	}
	
	public Object[] getColumn(String name) {
		int columNumber = headerNameToColNumber(name);
		return getColumn(columNumber);
	}
	
	public Object[] getColumn(int columNumber) {
		Object[] col = new Object[getSize()];
		
		for(int i=0; i<col.length; i++) {
			col[i] = df[i][columNumber];
		}
		
		return col;
	}
	
	private int headerNameToColNumber(String name) {
		for(int i=0; i<header.length; i++) {
			if(header[i].equals(name)) return i;
		}
		return -1;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Arrays.toString(header)).append("\n");
		for (Object[] line : df) {
			sb.append(Arrays.toString(line)).append("\n");
		}
		
		return sb.toString();
	}
}
