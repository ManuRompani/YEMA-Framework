package containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Player;

public class ScoreContainer {
	
	//lo vamos a necesitar para saber el puntaje individual
	//porque si no entra en ranking no hay contra que comparar
	//su marca porque no se guardaria en otro lado 
	private MemoryUserManager userManager;
	private List<Player> bestFiveRanking = new ArrayList<Player>();
	
	public ScoreContainer(MemoryUserManager userManager) {
		this.userManager = userManager;		
	}
	
	public ArrayList<Player> getPlayersSortedByScore() {
		ArrayList<Player> sorted = userManager.getPlayersList();
		Collections.sort(sorted, new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				return Integer.compare(p2.getPoints(), p1.getPoints());
			}
		});
		return sorted;
	}

	public void updatePlayerScore(Player player) {
		if(player == null || player.getName()==null) return;
		
		userManager.getPlayersList().stream()
	    .filter(p -> p.getName().equalsIgnoreCase(player.getName()))
	    .findFirst()
	    .ifPresent(p -> {
	        if (player.getPoints() > p.getPoints()) {
	            p.setPoints(player.getPoints());
	            p.setRounds(player.getRounds());
	        }
	    });
	}

	public void updateBestFiveRanking(Player player) {
		//1.valido si existe en la lista mejores 5
		    Player existing = null;
		    for (Player p : bestFiveRanking) {
		        if (p.getName().equalsIgnoreCase(player.getName())) {
		            existing = p;
		            break;
		        }
		    }

		    //2.si existe, seteo los nuevos valores
		    if (existing != null) {
		        if (player.getPoints() > existing.getPoints()) {
		            existing.setPoints(player.getPoints());
		            existing.setRounds(player.getRounds());
		        }
		        
		        //3.comparo si ese jugador que ya esta
		        //mejoro su puntaje y automaticamente ordeno
		        //la lista de mejores 5
		        bestFiveRanking.sort((a, b) -> Integer.compare(b.getPoints(), a.getPoints()));
		    } else {
		    	
		    	//4.si no existe, añado al jugador a la lista y ordeno la lista
		    	//para luego evaluar si hay mas 5, y si es asi, elimino al 5
		    	//dejando el ranking con los mejores 5
		        bestFiveRanking.add(player);
		        bestFiveRanking.sort((a, b) -> Integer.compare(b.getPoints(), a.getPoints()));
		        if (bestFiveRanking.size() > 5) {
		            bestFiveRanking.remove(5);
		        }
		    }
		}
	
	public List<Player> getBestFive() {
		List<Player> resultado = new ArrayList<Player>(this.bestFiveRanking);
		
		if(resultado.isEmpty()) {
			return null;
		}
		
		return resultado;
	}
}
