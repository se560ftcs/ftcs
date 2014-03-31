package edu.metu.se560.utils;

import org.clapper.util.io.IOExceptionExt;

public class Dene {

	public static final int FILE_SIZE = 35*1024*1024;
	
	public static void main(String[] args) throws IOExceptionExt {
		SeRollingFileWriter se = new SeRollingFileWriter("/temp/twit${n}.txt", null, FILE_SIZE, 10);
		se.println("12345678901");
		se.println("12345678902");
		se.println("12345678903");
		se.println("12345678904");
		se.println("12345678905");
		se.println("12345678906");
		se.println("12345678907");
		se.println("12345678908");
		se.println("12345678908");
		
	}
}
