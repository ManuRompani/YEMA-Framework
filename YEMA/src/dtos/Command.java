package dtos;

import java.util.HashMap;

public class Command {
	private String resource;
	private String action;
	private HashMap<String, String> parameters;

	
	public Command(String resource, String action, HashMap<String, String> parameters) {
		this.resource = resource;
		this.action = action;
		this.parameters = parameters;
	}
	
	public String getResource() {
		return this.resource;
	}
	
	public String getAction() {
		return this.action;
	}
	
	public String getParameter(String key) {
		return this.parameters.get(key);
	}
}
