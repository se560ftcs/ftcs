package edu.metu.se560.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import edu.metu.se560.utils.SeRollingFileWriter;

public class RollingFileQueueFeeder implements Runnable {
	private static final long SLEEP_TIME = 20*1000L; //1 dk

	BlockingQueue<String> queue;
	Integer lowLimit;
	String filePath;
	String filePrefix;
	SeRollingFileWriter rollingFileWriter;

	public RollingFileQueueFeeder(BlockingQueue<String> queue, Integer lowLimit, String filePath, String filePrefix, SeRollingFileWriter rollingFileWriter) {
		this.queue = queue;
		this.lowLimit = lowLimit;
		this.filePath = filePath;
		this.filePrefix = filePrefix;
		this.rollingFileWriter = rollingFileWriter;
	}

	@Override
	public void run() {
		System.out.println("Starting RollingFileQueueService");
		try {
			Thread.sleep(SLEEP_TIME*2);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			try {
				System.out.println("Tweets left not still processed by clustering system.");
				if (queue.size()<1 || queue.remainingCapacity() <= lowLimit) {
					int count = loadLastFileToQueue();
					System.out.println("New file transferred from file system to cluster queue. Tweets size:"+count);
				}
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private int loadLastFileToQueue() {
		int count = 0;
		try {
			String filePath = getOldestFilePath();
			if (filePath != null) {
				if (filePath.equals(rollingFileWriter.getPathName())) { //dosya aktif yaz?l?yorsa kapatt?r
					rollingFileWriter.forceRollover();
				}
				 BufferedReader br = new BufferedReader(new FileReader(filePath));
				    try {
				        String line = br.readLine();
				        while (line != null) {
				        	queue.add(line);
				            line = br.readLine();
				            count++;
				        }
				    } finally {
				        br.close();
				    }
				new File(filePath).delete(); //delete processed file
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;

	}

	private String getOldestFilePath() {
		List<String> results = new ArrayList<String>();
		File[] files = new File(filePath).listFiles();

		Arrays.sort( files, new Comparator()
		{
		    public int compare(Object o1, Object o2) {

		        if (((File)o1).lastModified() < ((File)o2).lastModified()) {
		            return -1;
		        } else if (((File)o1).lastModified() > ((File)o2).lastModified()) {
		            return +1;
		        } else {
		            return 0;
		        }
		    }

		}); 
		for (File file : files) {
		    if (file.isFile() && file.getName().startsWith(filePrefix) 
		    		&& file.getName().length()>filePrefix.length()+4) {
		    	System.out.println(file.getName());
		        return file.getPath();
		    }
		}
		
		return null;
	}

}
