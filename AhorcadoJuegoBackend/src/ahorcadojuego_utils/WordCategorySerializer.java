package ahorcadojuego_utils;

import java.util.List;
import models.WordCategory;
import services.Serializer;

public class WordCategorySerializer implements Serializer {

    @Override
    public String serialize(Object cats) {
        List<WordCategory> categories = (List<WordCategory>) cats;
        
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