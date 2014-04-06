package com.metu.se560.servlet;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.metu.se560.services.ClusteringService;
import com.metu.se560.services.TwitterService;

import edu.metu.se560.file.RollingFileQueueFeeder;

/**
 * Servlet implementation class MainServiceServlet
 */
public class MainServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MainServiceServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	
		List<String> parameters = Lists.newArrayList("a","e", "i", "u", "o");
				/*
				Lists.newArrayList("süper lig", "futbol", "maç", 
				"fener","cimbom","sarı kanarya","kara kartal", "ts", "bordo mavi", 
				"sarı lacivert", "siyah beyaz", "sarı kırmızı", "bjk", "gs", "futbol", 
				"fenerbahçe", "galatasaray", "beşiktaş", "trabzonspor");
				*/
		String consumerKey = this.getInitParameter("consumerKey"); 
		String consumerSecret = this.getInitParameter("consumerSecret");
		String token = this.getInitParameter("token");
		String secret = this.getInitParameter("secret");
		
		String filePath = "/temp";
		String filePrefix = "Tweets";
		
			
        TwitterService twitterService = new TwitterService(parameters, filePrefix, consumerKey, consumerSecret, token, secret);
        
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
    	RollingFileQueueFeeder f = new RollingFileQueueFeeder(queue, 100, filePath, filePrefix, twitterService.getSeRollingFileWriter());
    	new Thread(f).start();

        ClusteringService clusteringService = new ClusteringService(queue, 10);
        new Thread(clusteringService).start();
        new Thread(twitterService).start();
	}

}
