package edu.metu.se560.twitter;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import edu.metu.se560.utils.SeRollingFileWriter;

public class RollingFileQueueFeeder implements Runnable {
	private static final long SLEEP_TIME = 1000 * 60 * 2L;

	BlockingQueue<String> queue;
	Integer lowLimit;
	String filePrefix;
	SeRollingFileWriter rollingFileWriter;

	public RollingFileQueueFeeder(BlockingQueue<String> queue, Integer lowLimit, String filePrefix, SeRollingFileWriter rollingFileWriter) {
		this.queue = queue;
		this.lowLimit = lowLimit;
		this.filePrefix = filePrefix;
		this.rollingFileWriter = rollingFileWriter;
	}

	public void run() {
		while (true) {
			try {
				if (queue.remainingCapacity() <= lowLimit) {
					loadLastFileToQueue();
				}
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void loadLastFileToQueue() {
		RandomAccessFile aFile;
		try {
			String filePath = getOldestFilePath();
			if (filePath != null) {
				if (filePath.equals(rollingFileWriter.getPathName())) { //dosya aktif yazÝlÝyorsa kapattÝr
					rollingFileWriter.forceRollover();
				}
				aFile = new RandomAccessFile(filePath, "r");
				FileChannel inChannel = aFile.getChannel();
				long fileSize = inChannel.size();
				ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
				inChannel.read(buffer);
				buffer.rewind();
				buffer.flip();
				StringBuffer str = new StringBuffer();
				for (int i = 0; i < fileSize; i++) {
					char ch = (char) buffer.get();
					if (ch =='\n') {
						queue.add(str.toString());
						str.setLength(0);
					} else {
						str.append(ch);
					}
				}
				inChannel.close();
				aFile.close();
				new File(filePath).delete(); //delete processed file
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getOldestFilePath() {
		String path = "";
		List<String> results = new ArrayList<String>();
		File[] files = new File(path).listFiles();

		Arrays.sort( files, new Comparator()
		{
		    public int compare(Object o1, Object o2) {

		        if (((File)o1).lastModified() > ((File)o2).lastModified()) {
		            return -1;
		        } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
		            return +1;
		        } else {
		            return 0;
		        }
		    }

		}); 
		for (File file : files) {
		    if (file.isFile() && file.getName().startsWith(filePrefix)) {
		    	
		        return file.getPath();
		    }
		}
		
		return null;
	}

}
