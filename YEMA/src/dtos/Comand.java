package dtos;

public class Comand<T> {
	private String resource;
	private String action;
	private T parameter;
	
	public Comand(String resource, String action, T parameter) {
		this.resource = resource;
		this.action = action;
		this.parameter = parameter;
	}
	
	public String getResource() {
		return this.resource;
	}
	public String getAction() {
		return this.action;
	}
	public T getParameter() {
		return this.parameter;
	}
}
