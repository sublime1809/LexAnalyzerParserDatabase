package project1;

public class TokenBuilder {
	private static String tokenValue = "";
	private static int line = -1;
	
	public static void append(char c) {
		tokenValue += c;
		if(line == -1) {
			line = InputReader.getLine();
		}
	}
	public static int getLine() {
		return line;
	}
	public static String getTokenValue() {
		String toReturn = tokenValue;
		tokenValue = "";
		line = -1;
		return toReturn;
	}
}
