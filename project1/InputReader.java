package project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class InputReader {
	
	private static char currentChar;
	private static BufferedReader reader;
	private static int line = 1;
	
	public InputReader(File current) throws IOException {
		reader = new BufferedReader(new FileReader(current));
		try {
			currentChar = (char) reader.read();
			if(currentChar == '\n') {
				line++;
			}
		} catch (IOException e) {
			currentChar = (char) -1;
		}
	}
	/*public InputReader(String current) {
		reader = new BufferedReader(new StringReader(current));
		try {
			currentChar = (char) reader.read();
			if(currentChar == '\n') {
				line++;
			}
		} catch (IOException e) {
			currentChar = (char) -1;
		}
	}*/
	
	public static boolean hasNext() {
		return (currentChar != (char)-1);
	}
	
	public static char getCurrent() {
		return currentChar;
	}
	
	public static char getNextWithWhiteSpace() {
		try {
			currentChar = (char) reader.read();
		} catch (IOException e) {
			currentChar = (char) -1;
		}
		return currentChar;
	}
	
	public static char getNext() {
		try {
			currentChar = (char) reader.read();
			if(currentChar == '\n') {
				line++;
			}
			
		} catch (IOException e) {
			System.out.println("Got an IOException.");
		}
		return currentChar;
	}
	
	public static int getLine() {
		return line;
	}
}
