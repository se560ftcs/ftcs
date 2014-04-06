package edu.metu.se560.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Cluster {

	private static final int THRESHOLD = 200;
	
	private Tweet prototype;
	private List<Tweet> tweets = new ArrayList<Tweet>();
	private ClusterType type;
	private Calendar lastUpdTime;
	
	public Cluster() {
	}
	
	public Tweet getPrototype() {
		return prototype;
	}
	public void setPrototype(Tweet prototype) {
		this.prototype = prototype;
	}
	public List<Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	public ClusterType getType() {
		return type;
	}
	public void setType(ClusterType type) {
		this.type = type;
	}
	
	int addedTweetCount = 0;
	public void addTweet(Tweet tweet) {
		tweets.add(tweet);
		if (prototype == null) {
			prototype = tweet;
		}
		if (addedTweetCount++>THRESHOLD) {
			addedTweetCount = 0;
			reCalculatePrototype();
		}
		this.lastUpdTime = Calendar.getInstance();
	}

	/**
	 * Tweets'in read-only kopyasiyla calisacak sekilde duzelt
	 */
	private void reCalculatePrototype() {
		
		int size = tweets.size();
		double[][] similarity = new double[size][size+1];
		for (int i = 0; i < size-1; i++) {
			Tweet t1 = tweets.get(i);
			for (int j = i+1; j < size; j++) {
				Tweet t2 = tweets.get(j);
				double jaccardSimilarity = t1.jaccardSimilarity(t2);
				similarity[i][j] = jaccardSimilarity;
				similarity[j][i] = jaccardSimilarity;
				//System.out.println(i+"-"+j+"="+jaccardSimilarity);
			}
		}
		
		for (int i = 0; i < size-1; i++) {
			similarity[i][size] = Utils.sumArray(similarity[i], size);
		}
		
		int maxI = -1;
		double maxVal = 0.;
		
		for (int i = 0; i < size-1; i++) {
			double val = similarity[i][size];
			if (val>maxVal) {
				maxVal = val;
				maxI = i;
			}
		}
		prototype = tweets.get(maxI);
		
	}

	public Calendar getLastUpdTime() {
		return lastUpdTime;
	}

	
	
	
}
