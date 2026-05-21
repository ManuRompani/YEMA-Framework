package main;

public class Comand<T> {
	private String Resource;
	private String Action;
	private T Parameter;
	
	public Comand(String resource, String action, T parameter) {
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
	public T getParameter() {
		return this.Parameter;
	}
}
