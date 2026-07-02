package juegoUtils;

import models.Word;
import services.Deserializer;

public class WordDeserializer implements Deserializer {

    @Override
    public <T> T deserealize(String t, Class<T> c) {
        try {
            String[] values = t.split("\\|");

            String name = values[0];
            String category = values[1];
            String hint = values[2];
            String score = values[3];
            
            int iscore = Integer.parseInt(score);

            Word word = new Word(name, category, hint, iscore);

            return (T) word;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
