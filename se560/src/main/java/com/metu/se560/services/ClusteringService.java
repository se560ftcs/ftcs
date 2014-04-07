package com.metu.se560.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import edu.metu.se560.model.Cluster;
import edu.metu.se560.model.Tweet;

public class ClusteringService extends Thread {
	
	private BlockingQueue<String> queue;
	private static List<Cluster> clusters = new ArrayList<Cluster>();
	private static Integer numberOfClusters;

	public ClusteringService(BlockingQueue<String> queue, Integer numberOfClusters) {
		this.queue = queue;
		this.numberOfClusters = numberOfClusters;
		initClusters(numberOfClusters*2);
	}
	
	public void initClusters(int clusterCount) {
		for (int i = 0; i < clusterCount; i++) {
			clusters.add(new Cluster());
		}
	}

	@Override
	public void run() {
		System.out.println("Starting ClusteringService");
		while(true) {
			try {
				String line = queue.take();
				Tweet tweet = new Tweet(line);
				if (tweet.getDescription().length()>5) {
					addToClustering(tweet);
				}
				//System.out.println("Added tweet to clustering: "+ line);
				Thread.sleep(1); //yava������������������������������������������������������ ilerlesin g������������������������������������������������������relim
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	private boolean initOk = false;
	private int tmpCount  = 0;
	private void addToClustering(Tweet msg) {
		if (! initOk) {
			for (int i = 0; i < clusters.size(); i++) {
				if (clusters.get(i).getPrototype() == null) {
					clusters.get(i).addTweet(msg); 
					return;
				}
			}
		}
		initOk = true;
	    //En yak������������������������������������������������������n cluster'a ekle
		int maxSimilarityIndex=-1;
	    double maxSimilarity=0;
	    
	    for(int j=0; j<clusters.size();j++) { 		    	
	    	double jaccardSimilarity = msg.jaccardSimilarity(clusters.get(j).getPrototype());
			if(maxSimilarity<jaccardSimilarity)
	    	{
	    		maxSimilarityIndex= j;
	    		maxSimilarity=jaccardSimilarity;
	    	}
	    }
	    if (maxSimilarityIndex>-1) { //Yeni cluster olusturmak uzere baska clustera al
	    	clusters.get(maxSimilarityIndex).addTweet(msg);
	    }
	    tmpCount++;
	    if (tmpCount>100) {
	    	tmpCount = 0;
			System.out.println(getClusterString());
	    }
	}

	public static String getClusterString() {
		StringBuffer result = new StringBuffer();
		List<Cluster> listCopy = new ArrayList<Cluster>();
		listCopy.addAll(clusters);
		Collections.sort(listCopy, new Comparator<Cluster>() {
			public int compare(Cluster o1, Cluster o2) {
				if (o1.getLastUpdTime() != null
						&& o2.getLastUpdTime()!= null) {
					return o1.getLastUpdTime().compareTo(o2.getLastUpdTime());
				} else {
					return 0;
				}
			};
		});
		result.append("{\"name\": \"");
		result.append("Clusters");
		result.append("\", \"children\": [");
		for(int i=0;i<numberOfClusters && i<listCopy.size();i++) {
			Cluster c = listCopy.get(i);
			if (c.getPrototype() != null) {
			result.append(
					"{\"name\": \""+
							c.getPrototype().getDescriptionUtf()
							+"\", \"size\": "+
							c.getTweets().size()
							+"}"
					);
			result.append(",");
			}
		}
		result.deleteCharAt(result.length()-1); // , sil
		result.append("]}");
		return result.toString();
	}
}
