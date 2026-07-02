package ahorcadojuego_utils;

import java.util.List;
import models.WordCategory;
import services.Serializer;

public class WordCategorySerializer implements Serializer<List<WordCategory>> {

    @Override
    public String serialize(List<WordCategory> categories) {
        if (categories == null || categories.isEmpty()) {
            return "";
        }

        StringBuilder serialized = new StringBuilder();

        for (int i = 0; i < categories.size(); i++) {
            WordCategory cat = categories.get(i);
            serialized.append(cat.getId())
                .append("%")
                .append(cat.getDescription());

            if (i < categories.size() - 1) {
                serialized.append(";");
            }
        }

        return serialized.toString();
    }
}