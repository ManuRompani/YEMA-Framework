package juegoUtils;

import models.Player;
import services.Deserializer;

public class PlayerDeserializer implements Deserializer {

	@Override
    public <T> T deserealize(String t, Class<T> c) {
        try {
            String[] values = t.split("\\%");

            int id = Integer.parseInt(values[0]);
            String name = values[1];
            String pass = values[2];
            String role = values[3];
            int rounds = Integer.parseInt(values[4]);
            int points = Integer.parseInt(values[5]);

            Player player = new Player(id, name, pass, role, rounds, points);

            return (T) player;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}


// auth/register/player=0|pepe|123|player|0|0