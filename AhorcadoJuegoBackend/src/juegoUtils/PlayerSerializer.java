package juegoUtils;

import models.Player;
import services.Serializer;

public class PlayerSerializer implements Serializer{

	 @Override
	    public String serialize(Object t) {
	        try {
	            Player player = (Player) t;

	            String values = "";

	            values += player.getId();
	            values += "%" + player.getName();
	            values += "%" + player.getPass();
	            values += "%" + player.getRole();
	            values += "%" + player.getRounds();
	            values += "%" + player.getPoints();

	            return values;

	        } catch (Exception e) {
	            throw new RuntimeException(e.getMessage());
	        }
	    }
}
