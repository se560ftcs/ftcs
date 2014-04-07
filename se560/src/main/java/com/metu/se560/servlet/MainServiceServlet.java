package com.metu.se560.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.metu.se560.services.ClusteringService;
import com.metu.se560.services.FileBasedTwitterService;
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
	
		List<String> parameters = Lists.newArrayList("futbol"); //default
		try {
			Reader paramReader = new InputStreamReader(getServletContext().getResourceAsStream("/WEB-INF/resource/teamsDictionary.txt"), "utf8"); 
			parameters = readerToStr(paramReader);
			paramReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String consumerKey = this.getInitParameter("consumerKey"); 
		String consumerSecret = this.getInitParameter("consumerSecret");
		String token = this.getInitParameter("token");
		String secret = this.getInitParameter("secret");
		String liveTweets = this.getInitParameter("live");
		
		String filePath = "/temp";
		String filePrefix = "Tweets";
		
	    BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
	    if (liveTweets.equals("1")) {
	        TwitterService twitterService = new TwitterService(parameters, filePrefix, consumerKey, consumerSecret, token, secret);
	        new Thread(twitterService).start();
	        
	    	RollingFileQueueFeeder f = new RollingFileQueueFeeder(queue, 100, filePath, filePrefix, twitterService.getSeRollingFileWriter());
	    	new Thread(f).start();
	    } else {
		
	    	FileBasedTwitterService twitterService = new FileBasedTwitterService(queue);
	    	new Thread(twitterService).start();
	    }
	    
        ClusteringService clusteringService = new ClusteringService(queue, 10);
        new Thread(clusteringService).start();
	}

	private List<String> readerToStr(Reader paramReader) throws IOException {
		BufferedReader reader = new BufferedReader(paramReader);
		List<String> out = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
			out.add(line);
            System.out.println(line);
        }
        return out;
	}

}
