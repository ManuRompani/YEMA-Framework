package main;

public class ComandParser<T> {
	
	public Comand Parse(String parseString) {
		String[] segments = parseString.split("/");
		
		String resource = segments[0];
		String action = segments[1];
		T parameter = (T) segments[2];

		return new Comand(resource, action, parameter);
	}
}
