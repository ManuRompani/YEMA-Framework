package juegoUtils;

import models.Word;
import services.Serializer;

public class WordSerializer implements Serializer {

    @Override
    public String serialize(Object t) {
        try {
            Word word = (Word) t;

            String values = "";

            values += word.getName();
            values += "|" + word.getCategory();
            values += "|" + word.getHint();
            values += "|" + word.getScore();

            return values;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
