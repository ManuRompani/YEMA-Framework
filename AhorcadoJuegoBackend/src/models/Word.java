package models;

public class Word {
	private String name;
	private String category;
	private String hint;
	private int puntaje;
	
	// usuario que agrego la palabra
	private String username;
	
	public Word(String name, String category, String hint) {
		super();
		this.name = name;
		this.category = category;
		this.hint = hint;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
