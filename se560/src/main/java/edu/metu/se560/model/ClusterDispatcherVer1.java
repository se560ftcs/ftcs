/**
 * 
 */
package edu.metu.se560.model;

import java.util.Date;
import java.util.List;

import twitter.dataanalyzer.utils.SpectralCommunityDetector;
import twitter.dataanalyzer.utils.TwitterMatrixUtils;

import com.aliasi.spell.TfIdfDistance;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.tokenizer.ZemberekStemmerTokenizerFactory;

/**
 * @author pulkit
 * 
 */
public class ClusterDispatcherVer1 {


	private static double descriptionSimliaritythreshold = 0.1;
	
	private static int nClusters = 10;

	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		List<Tweet> tweets = getTwits();
		tweets = tweets.subList(0, 1000);
//		CommunityDetector spectralCommunityDetector = new SpectralCommunityDetector();
		
		double[][] userDescriptionSimilarity = findDescriptionSimliarity(tweets);
		boolean[][] userDescriptionGraph = TwitterMatrixUtils.toGraph(userDescriptionSimilarity,
				descriptionSimliaritythreshold);
		
		SpectralCommunityDetector spectralCommunityDetector = new SpectralCommunityDetector();
		List<List<Tweet>> communities = spectralCommunityDetector.cluster(userDescriptionGraph, tweets, nClusters );
		
		tweets.clear();
		System.out.println(tweets.size());
		int i = 1;
		for (List<Tweet> community : communities) {
			System.out.println("");
			System.out.println("Cluster:"+ (i++));
			for (Tweet u : community) {
				System.out.println(u.getDescription());
				tweets.add(u);
			}
		}
		System.out.println(new Date());
	}

	private static List<Tweet> getTwits() throws Exception {
		 return ClusterDispatcher.readFromFile("/se560/src/main/java/edu/metu/se560/twitter/1394470743403.txt");
	}

	
	
	public static double[][] findDescriptionSimliarity(List<Tweet> users) {
		TokenizerFactory tokenizerFactory = new ZemberekStemmerTokenizerFactory(IndoEuropeanTokenizerFactory.INSTANCE);
		
		TfIdfDistance tfIdf = new TfIdfDistance(tokenizerFactory);

		double[][] descriptionSimilarity = new double[users.size()][users.size()];

		for (Tweet u : users) {
			if (u.getDescription() != null) {
				tfIdf.handle(u.getDescription());
			}
		}

		for (int i = 0; i < users.size(); ++i) {
			if (users.get(i).getDescription() == null)
				continue;
			for (int j = 0; j < users.size(); ++j) {
				if (users.get(j).getDescription() == null)
					continue;
				descriptionSimilarity[i][j] = tfIdf.proximity(users.get(i).getDescription(), users.get(j)
						.getDescription());
			}
		}

		return descriptionSimilarity;
	}

}
