package models;

public class Word {
	private String name;
	private String category;
	private String hint;
	
	public Word(String name, String category, String hint) {
		super();
		this.name = name;
		this.category = category;
		this.hint = hint;
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
	
	
}
