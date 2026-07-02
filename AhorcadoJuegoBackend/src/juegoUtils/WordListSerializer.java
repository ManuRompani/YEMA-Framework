package juegoUtils;

import java.util.List;

import models.Word;
import services.Serializer;

public class WordListSerializer implements Serializer {

	@Override
	public String serialize(Object obj) {
		try {
			List<Word> words = (List<Word>) obj;

			if (words == null || words.isEmpty()) {
				return "";
			}

			StringBuilder serialized = new StringBuilder();

			for (int i = 0; i < words.size(); i++) {
				Word word = words.get(i);

				serialized.append(word.getName())
					.append("|")
					.append(word.getCategory())
					.append("|")
					.append(word.getHint())
					.append("|")
					.append(word.getScore());

				if (i < words.size() - 1) {
					serialized.append(";");
				}
			}

			return serialized.toString();

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
