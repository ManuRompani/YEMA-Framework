package containers;

import java.util.ArrayList;
import java.util.List;


public class WordCategoryContainer {
	private List<String> categories;
	
	public WordCategoryContainer() {
		this.categories = new ArrayList<String>();
	}
	
	public List<String> getAll(){
		return new ArrayList<String>(this.categories);
	}
	
	public boolean add(String cat) {
		return this.categories.add(cat);
	}
	
	public boolean delete(String id) {
		return this.categories.removeIf(c -> c.equals(id));
	}
}
