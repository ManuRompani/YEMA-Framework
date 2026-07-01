package models;

public class WordCategory {
	private int id;
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public WordCategory(int id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
