package edu.metu.se560.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClusterDispatcher {
	
	private static final int MAX_BUFFER_SIZE = 100000;

	public static void main(String[] args) throws Exception {
	   ClusterDispatcher disp = new ClusterDispatcher();
	   disp.initClusters(10);
	   disp.execute();
	}

	List<Cluster> clusters = new ArrayList<Cluster>();
	
	public void initClusters(int clusterCount) {
		for (int i = 0; i < clusterCount; i++) {
			clusters.add(new Cluster());
		}
	}
	
	public void execute() throws Exception {
		 BlockingQueue<Tweet> queue = new LinkedBlockingQueue<Tweet>(MAX_BUFFER_SIZE);
		 queue.addAll(readFromFile("/Users/semimac/git/se560/se560/src/main/java/edu/metu/se560/twitter/1394470743403.txt"));
		 for (int i = 0; i < clusters.size(); i++) {
			 Tweet msg = queue.take();
			 clusters.get(i).addTweet(msg);   
		 }
		
		 for (int msgRead = 0; msgRead < 100; msgRead++) {
		    Tweet msg = queue.take();
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
		    if (maxSimilarityIndex>-1) { //Yeni cluster oluﬂturmak üzere baﬂka clustera al
		    	clusters.get(maxSimilarityIndex).addTweet(msg);
		    }
		 }
		 for (Iterator iterator = clusters.iterator(); iterator.hasNext();) {
			Cluster cluster = (Cluster) iterator.next();
			System.out.println("Cluster");
			System.out.println(cluster.getPrototype().toString());
			for (Iterator iterator2 = cluster.getTweets().iterator(); iterator2.hasNext();) {
				Tweet tweet = (Tweet) iterator2.next();
				System.out.println(tweet.toString());
			}
			
		}
	}

	private Collection<Tweet> readFromFile(String fileName) throws Exception {
		Collection<Tweet> result = new ArrayList<Tweet>();
		BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.length()>5) {
				Tweet tweet = new Tweet(line);
				result.add(tweet);
			}
		}
		br.close();
		return result;
		
	}
}
