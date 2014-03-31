/**
 * 
 */
package twitter.dataanalyzer.utils;

import java.util.List;

import edu.metu.se560.model.Tweet;

/**
 * @author pulkit and sapan
 *
 */
public interface CommunityDetector {

	List<List<Tweet>> cluster(int[][] A, List<Tweet> users, int k);
	List<List<Tweet>> cluster(boolean[][] A, List<Tweet> users, int k);
}
