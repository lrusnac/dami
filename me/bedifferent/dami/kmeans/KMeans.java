package me.bedifferent.dami.kmeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.bedifferent.dami.dataframe.Attribute;
import me.bedifferent.dami.dataframe.DataFrame;

public class KMeans {

	private static List<List<KMeansRow>> kmeans(List<KMeansRow> df, int k) {		
		// get k random initial centroids
		List<KMeansRow> shuffledCentroids = new ArrayList<>(df);
		Collections.shuffle(shuffledCentroids);
		
		List<KMeansRow> centroids = shuffledCentroids.subList(0, k);
		for(int i = 0; i<centroids.size(); i++) {
			centroids.get(i).setId(i);
		}
		
		int iterations = 0;
		List<KMeansRow> oldCentroids = new ArrayList<KMeansRow>();
		List<Integer> labels = null;
		
		while(!oldCentroids.equals(centroids)) {
			oldCentroids = centroids;
			iterations ++;
			
			labels = getLabels(df, centroids);
			
			centroids = getCentroids(df, labels, k);
		}
		
		System.out.println("kmeans finished in " + iterations + " iterations");
		
		List<List<KMeansRow>> clusters = new ArrayList<>();
		for (int i = 0; i < centroids.size(); i++) {
			clusters.add(new ArrayList<>());
		}
		for(int i=0; i<labels.size(); i++) {
			int l = labels.get(i);
			clusters.get(l).add(df.get(i));
		}
		
		return clusters;
	}
	
	
	private static List<Integer> getLabels(List<KMeansRow> df, List<KMeansRow> centroids) {
		// for each KMeansRow choose the closest centroid, in the end return the list of labels of every row
		List<Integer> labels = new ArrayList<>();
		
		for(KMeansRow row: df) {
			double minDistance = Double.MAX_VALUE;
			KMeansRow closestCentroid = null;
			
			for(KMeansRow centroid: centroids) {
				double dist = centroid.distance(row);
				if(dist < minDistance) {
					minDistance = dist;
					closestCentroid = centroid;
				}
			}
			labels.add(closestCentroid.getId());
		}
		
		return labels;
	}
	
	private static List<KMeansRow> getCentroids(List<KMeansRow> df, List<Integer> labels, int k) {
		// each centroid is the mean points assigned to a label
		double[] sumNumberOfGames = new double[k];
		double[] sumHoursPlaying = new double[k];
		int[] count = new int[k];
		
		for(int i=0; i<labels.size(); i++) {
			int l = labels.get(i);
			sumNumberOfGames[l] += df.get(i).getNumberOfGames();
			sumHoursPlaying[l] += df.get(i).getHoursPlaying();
			count[l] ++;
		}
		
		// if a centroid has 0 element I need to pick one random point in my dataframe and set it as a centroid
		List<KMeansRow> centroids = new ArrayList<KMeansRow>();
		for(int i=0; i<k; i++) {
			centroids.add(new KMeansRow(i, sumNumberOfGames[i]/count[i], sumHoursPlaying[i]/count[i]));
		}
		return centroids;
	}
	
	public static void runKMeans(DataFrame df) {
		 List<KMeansRow> rows = new LinkedList<>();
	        // from df to KNNRows
	        for (Attribute[] att : df) {
	            int hoursPlaying;
	            switch (att[7].getLiteral()) {
	            case "never":
	                hoursPlaying = 0;
	                break;
	            case "< 5 hours a week":
	                hoursPlaying = 1;
	                break;
	            case "< 10 hours a week":
	                hoursPlaying = 2;
	                break;
	            case "< 20 hours a week":
	                hoursPlaying = 3;
	                break;
	            case "> 20 hours a week":
	                hoursPlaying = 4;
	                break;
	            default:
	                hoursPlaying = 0;
	            }

	            int numberOfGames = att[8].getLiteral().equals("i have not played any of these games") ? 0
	                    : att[8].getLiteral().split(",").length;
	            rows.add(new KMeansRow(numberOfGames, hoursPlaying));
	        }

	        int i=1;
	        List<List<KMeansRow>> clusters = kmeans(rows, 3);
	        for(List<KMeansRow> cluster: clusters) {
	        	System.out.println("cluster " + (i++) + " " + cluster);
	        }
	}
}
