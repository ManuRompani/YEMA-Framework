package ahorcadojuego_utils;

import java.util.List;

import models.Player;
import services.Serializer;

public class ScoreSerializer implements Serializer<List<Player>>{

	@Override
	public String serialize(List<Player> players) {		
		
			if (players == null || players.isEmpty()) {
				return "";
			}

			StringBuilder serialized = new StringBuilder();

			for (int i = 0; i < players.size(); i++) {
				Player p = players.get(i);
				serialized.append(p.getName())
				  .append("|")
				  .append(p.getPoints())
				  .append("|")
				  .append(p.getRounds());
				
				//coloco un ; entre cada player, exceptuando el ultimo
				if (i < players.size() - 1) {
					serialized.append(";");
				}
			}

			return serialized.toString();
		}
	}


