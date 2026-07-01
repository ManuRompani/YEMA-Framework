package containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import models.Player;
import validators.IntValidator;
import exceptions.ValidatorException;

public class ScoreContainer {
	private ArrayList<Player> playersList;
	
	public ScoreContainer() {
		this.playersList = new ArrayList<>();
	}
	
	public ArrayList<Player> getPlayersSorted() {
        ArrayList<Player> sorted = new ArrayList<>(playersList);
        Collections.sort(sorted, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getPoints(), p1.getPoints());
            }
        });
        return sorted;
    }
	
	//@admin?
	public void addOrUpdatePlayer() {
		
	}
	
	public Player getPlayer(String username) {
        for (Player player : playersList) {
            if (player.getName().equalsIgnoreCase(username)) {
                return player;
            }
        }
        return null;
    }
	
	public int getPlayerCount() {
        return playersList.size();
    }
}
