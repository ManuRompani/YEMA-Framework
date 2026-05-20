package main;

public class Comand {
	private String Resource;
	private String Action;
	private String Parameter;
	
	public Comand(String resource, String action, String parameter) {
		this.Resource = resource;
		this.Action = action;
		this.Parameter = parameter;
	}
	
	public String getResource() {
		return this.Resource;
	}
	public String getAction() {
		return this.Action;
	}
	public String getParameter() {
		return this.Parameter;
	}
}
