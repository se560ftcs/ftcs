package com.metu.se560.services;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.clapper.util.io.IOExceptionExt;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import edu.metu.se560.utils.SeRollingFileWriter;

public class TwitterService implements Runnable {
	
	private List<String> filterParameters;
	private String consumerKey; 
	private String consumerSecret; 
	private String token; 
	private String secret;
	
	private SeRollingFileWriter seRollingFileWriter;
	
	private static final int MAX_FILE_SIZE  = 1*1024*1024;
	private static final int MAX_FILE_COUNT = 10;
	
	
	
	public TwitterService(List<String> filterParameters, String filePrefix, String consumerKey, String consumerSecret, String token, String secret) {
		this.filterParameters = filterParameters;
		this.consumerKey = consumerKey; 
		this.consumerSecret = consumerSecret; 
		this.token = token; 
		this.secret = secret;
		try {
			seRollingFileWriter = new SeRollingFileWriter("/temp/"+filePrefix+"${n}.txt", "UTF-8", MAX_FILE_SIZE, MAX_FILE_COUNT);
		} catch (IOExceptionExt e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Starting TwitterService");
	    final BlockingQueue<String> queue = new LinkedBlockingQueue<String>(1000);
	    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
	    // add some track terms
	    endpoint.trackTerms(filterParameters);
	    endpoint.languages(Lists.newArrayList("tr"));

	    Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
	    // Authentication auth = new BasicAuth(username, password);

	    // Create a new BasicClient. By default gzip is enabled.
	    final Client client = new ClientBuilder()
	      .hosts(Constants.STREAM_HOST)
	      .endpoint(endpoint)
	      .authentication(auth)
	      .processor(new StringDelimitedProcessor(queue))
	      .build();

	    // Establish a connection
	    client.connect();
	    
		try {
			int a = 1;
		    while (a==1) {
		    	String msg = queue.take()+"\n";
			    seRollingFileWriter.println(msg);  
		    } 
		    client.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SeRollingFileWriter getSeRollingFileWriter() {
		return seRollingFileWriter;
	}

	
}
