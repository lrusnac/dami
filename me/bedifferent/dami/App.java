package me.bedifferent.dami;

public class App {

	public static void main(String args[]) {
		DataFrame df = new DataFrame();
		
		boolean skipHeaderRow = false;
		df.readFromCSV("DataMining 2016 (Responses).csv", ";", "-", skipHeaderRow);
		
		System.out.println(df.toString());
	}
}
