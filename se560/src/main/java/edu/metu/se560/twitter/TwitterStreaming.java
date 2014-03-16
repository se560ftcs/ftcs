package edu.metu.se560.twitter;
/**
 * Copyright 2013 Twitter, Inc.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterStreaming {

  public void oauth(String consumerKey, String consumerSecret, String token, String secret) throws InterruptedException {
    BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
    StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
    // add some track terms
    endpoint.trackTerms(Lists.newArrayList("süper lig", "futbol", "maç", "fener","cimbom","sarı kanarya","kara kartal", "ts", "bordo mavi", "sarı lacivert", "siyah beyaz", "sarı kırmızı", "bjk", "gs", "futbol", "fenerbahçe", "galatasaray", "beşiktaş", "trabzonspor"));
    //endpoint.trackTerms(Lists.newArrayList("a","e","i","o","u","ı","ü","ö"));
    endpoint.languages(Lists.newArrayList("tr"));

    Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
    // Authentication auth = new BasicAuth(username, password);

    // Create a new BasicClient. By default gzip is enabled.
    Client client = new ClientBuilder()
      .hosts(Constants.STREAM_HOST)
      .endpoint(endpoint)
      .authentication(auth)
      .processor(new StringDelimitedProcessor(queue))
      .build();

    // Establish a connection
    client.connect();

    FileOutputStream f = null;
    try{
     f = new FileOutputStream(new File(new Date().getTime()+".txt"));
    // Do whatever needs to be done with messages
    for (int msgRead = 0; msgRead < 100000; msgRead++) {
      String msg = queue.take()+"\n";
      System.out.println(msg);
      f.write(msg.getBytes());
    }
    } catch (Exception e) {
    	e.printStackTrace();
    } finally {
    	if (f!= null) {
    		try {
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

   
    client.stop();

  }
  public static void main(String[] args) {
	  try {
		  String[] ars = new String[] {"","","",""};
		  new TwitterStreaming().oauth(ars[0], ars[1], ars[2], ars[3]);
	  } catch (InterruptedException e) {
		  System.out.println(e);
	  }
  }
}
