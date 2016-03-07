package me.bedifferent.dami;

import java.util.Arrays;

public class App {

	public static void main(String args[]) {
		int[] colums = {1, 2, 3, 4, 5, 6, 7, 20, 21, 44, 45};
		Class[] types = {Integer.class, String.class, String.class, Double.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
		String[] header = {"age", "gender", "shoesize", "height", "course", "interess", "languages", "playing", "games", "row", "seat"};
		DataFrame df = new DataFrame(header, types, colums, "DataMining 2016 (Responses).csv");
		System.out.println(df);
		
		System.out.println("ages: " + Arrays.toString(df.getColumn("age")));
		
		System.out.println("data frame size: " + df.getSize());
	}
}
