package containers;

import java.util.ArrayList;
import java.util.List;

import models.WordCategory;

public class WordCategoryContainer {
	private List<WordCategory> categories;
	
	public WordCategoryContainer() {
		this.categories = new ArrayList<WordCategory>();
	}
	
	public List<WordCategory> getAll(){
		return new ArrayList<WordCategory>(this.categories);
	}
	
	public WordCategory getCategoryById(int id) {
		return this.categories.stream().filter(c -> c.getId() == id).findFirst().get();
	}
	
	public boolean add(WordCategory cat) {
		return this.categories.add(cat);
	}
	
	public boolean delete(int id) {
		return this.categories.removeIf(c -> c.getId() == id);
	}
}
