package juegoUtils;

import java.util.List;
import services.Serializer;

public class WordCategorySerializer implements Serializer {

	@Override
	public String serialize(Object obj) {
	    List<String> categories = (List<String>) obj;

	    if (categories == null || categories.isEmpty()) {
	        return "";
	    }

	    StringBuilder serialized = new StringBuilder();

	    for (int i = 0; i < categories.size(); i++) {
	        String cat = categories.get(i);

	        serialized.append(cat);

	        if (i < categories.size() - 1) {
	            serialized.append(";");
	        }
	    }

	    return serialized.toString();
	}
}