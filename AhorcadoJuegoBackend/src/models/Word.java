package models;

public class Word {
	private String name;
	private WordCategory category;
	private String hint;
	
	public Word(String name, WordCategory category, String hint) {
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

	public WordCategory getCategory() {
		return category;
	}

	public void setCategory(WordCategory category) {
		this.category = category;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}
	
	
}
