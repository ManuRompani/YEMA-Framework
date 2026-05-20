package main;

public class ComandParser {
	
	public Comand Parse(String parseString) {
		String[] segments = parseString.split("/");
		
		String resource = segments[0];
		String action = segments[1];
		String parameter = segments[2];
		
		return new Comand(resource,action,parameter);
	}
}
