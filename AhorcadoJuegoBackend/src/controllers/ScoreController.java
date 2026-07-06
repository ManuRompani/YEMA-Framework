package controllers;

import java.util.List;

import annotations.AuthorizedRoles;
import containers.ScoreContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import juegoUtils.ScoreSerializer;
import utils.Context;
import models.Player;
import services.ServiceLocator;


public class ScoreController extends BaseController {
	private ScoreContainer scoreContainer;
	private ScoreSerializer ss = new ScoreSerializer();
	
	@Override
	public void iniciarServicios(ServiceLocator sl) {
		try {
			this.scoreContainer =sl.getService(ScoreContainer.class);
		} catch (ServiceNotImplementedException se) {
			throw new RuntimeException("Error crítico: inicializacion del servicio " + se.getMessage() + " fallida.");
		}
	}
		
	@Override
	public Response Ejecutar(Command command, Context context) {
		Response response = new Response();
        
        switch (command.getAction()) {
            case "update":
                String ptsS = command.getParameter("points");
                String roundsS = command.getParameter("rounds");
                String username = context.getUser().getName();

                if (ptsS == null || roundsS == null) {
                    response.setMessage("Faltan parametros: points y rounds requeridos.");
                    return response;
                }

                try {
                    int points = Integer.parseInt(ptsS);
                    int rounds = Integer.parseInt(roundsS);

                    Player player = new Player(0, username, null, null, rounds, points);
                    scoreContainer.updatePlayerScore(player);
                    scoreContainer.updateBestFiveRanking(player);

                    response.setMessage("Puntaje del jugador " + player.getName() +" actualizado correctamente.");
                } catch (NumberFormatException e) {
                    response.setMessage("Error: puntos y rondas deben ser numeros enteros.");
                }
                break;

            case "positions":            	
                List<Player> ranking = scoreContainer.getBestFive();
                
                String dataRankingSerialized =  ss.serialize(ranking);
                
                if(dataRankingSerialized == null || dataRankingSerialized.isEmpty()) {
                	response.setMessage("No hay puntajes registrados aun");
                }else {
                	response.setMessage(dataRankingSerialized);
                }
                
                break;

            case "myposition":
                String currentUser = context.getUser().getName();
                List<Player> topFive = scoreContainer.getBestFive();
                boolean found = false;

                for (int i = 0; i < topFive.size(); i++) {
                    if (topFive.get(i).getName().equalsIgnoreCase(currentUser)) {
                        response.setMessage("Estas en la posicion #" + (i + 1)
                                + " con " + topFive.get(i).getPoints()
                                + " puntos en " + topFive.get(i).getRounds() + " rondas.");
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    response.setMessage("No estas en el top 5. Segui jugando!");
                }
                break;

            default:
                response.setMessage("Accion no reconocida: " + command.getAction());
        }

        return response;
    }
	}