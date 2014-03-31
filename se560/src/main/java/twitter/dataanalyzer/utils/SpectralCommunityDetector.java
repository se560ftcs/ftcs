/**
 * 
 */
package twitter.dataanalyzer.utils;

import java.io.IOException;
import java.util.List;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import edu.metu.se560.model.Tweet;

/**
 * @author pulkit
 *
 */
public class SpectralCommunityDetector implements CommunityDetector {

	/* (non-Javadoc)
	 * @see twitter.dataanalyzer.graphbuilder.CommunityDetector#cluster(int[][])
	 */
	public List<List<Tweet>> cluster(int[][] A, List<Tweet> users, int k) {
		
		int[] vertexDegree = new int[A.length];
		int nEdges = 0;	// These are number of directed edges. Therefore, 2|E| for undirected graph
		
		double[][] modularityMatrix = new double[A.length][A[0].length];
		
		for (int i = 0; i < A.length; ++i) {
			for (int j = 0; j < A[i].length; ++j) {
				nEdges += A[i][j];
				vertexDegree[i] += A[i][j];
			}
		}
		
		for (int i = 0; i < A.length; ++i) {
			for (int j = 0; j < A[i].length; ++j) {
				modularityMatrix[i][j] = A[i][j] - vertexDegree[i]*vertexDegree[j]/(double)nEdges;
			}
		}
		EigenvalueDecomposition evd = new EigenvalueDecomposition(new Matrix(modularityMatrix));	// Calculate the eigen vectors
		
		double[] imagEigenValues = evd.getImagEigenvalues();
		double[] realEigenValues = evd.getRealEigenvalues();
		Matrix eigenVectors = evd.getV();

		double[][] EigenMatrix = eigenVectors.getArray();
		
		List<List<Tweet>> clusters = new KMeans().cluster(EigenMatrix, users, k, 100);
		
		/*
		try {
			TwitterFileUtils.write(eigenVectors.getArray(), "tmp/eigenVectors.txt");
			TwitterFileUtils.write(evd.getD().getArray(), "tmp/eigenValues.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return clusters;
		
	}

	/* (non-Javadoc)
	 * @see twitter.dataanalyzer.graphbuilder.CommunityDetector#cluster(boolean[][])
	 */
	public List<List<Tweet>> cluster(boolean[][] A, List<Tweet> users, int k) {
		int[][] A_int = new int[A.length][A[0].length];
		
		for (int i = 0; i < A.length; ++i) {
			for (int j = 0; j < A[i].length; ++j) {
				A_int[i][j] = (A[i][j])?1:0; 
			}
		}
		return cluster(A_int, users, k);
	}

}
