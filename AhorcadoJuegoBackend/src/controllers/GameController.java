package controllers;

import annotations.AuthorizedRoles;
import containers.GameContainer;
import containers.WordCategoryContainer;
import containers.WordContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import juegoUtils.WordCategorySerializer;
import models.Word;
import services.ServiceLocator;
import utils.Context;

import java.util.ArrayList;
import java.util.List;

@AuthorizedRoles(roles = {"admin","player"})
public class GameController extends BaseController {
	private GameContainer gameContainer;
	private WordContainer wordContainer;
	private WordCategoryContainer categoryContainer;

	@Override
	public Response Ejecutar(Command command, Context context) {
		Response response = new Response();
		String action = command.getAction();

		switch(action) {
			// Player
			case "start":
				response.setMessage(gameContainer.startGame(context.getSessionData()));
				break;
			case "guess":
				response.setMessage(gameContainer.guessLetter(command.getParameter("letter"), context.getSessionData()));
				break;
			case "hint":
				response.setMessage(gameContainer.getHint(context.getSessionData()));
				break;
			case "status":
				response.setMessage(gameContainer.getStatus(context.getSessionData()));
				break;

			// @admin?
			case "list":
				response.setMessage(listWords(command));
				break;
			case "categories":
				response.setMessage(listCategories());
				break;

			default:
				response.setMessage("Acción no reconocida: " + action);
		}
		return response;
	}

	private String listWords(Command command) {
		String catId = command.getParameter("categoryId");
		ArrayList<Word> words;

		if (catId != null) {
			words = wordContainer.getWordsByCategory(catId);
		} else {
			words = wordContainer.getAllWords();
		}

		StringBuilder sb = new StringBuilder("PALABRAS:\n");
		for (Word w : words) {
			sb.append("- ").append(w.getName())
			  .append(" [").append(w.getCategory()).append("]")
			  .append(" Pista: ").append(w.getHint()).append("\n");
		}
		return sb.toString();
	}

	private String listCategories() {
		WordCategorySerializer serializer = new WordCategorySerializer();
		List<String> cats = this.categoryContainer.getAll();
		return serializer.serialize(cats);
	}

	@Override
	public void iniciarServicios(ServiceLocator sl) {
		try {
			this.wordContainer = sl.getService(WordContainer.class);
			this.categoryContainer = sl.getService(WordCategoryContainer.class);
			this.gameContainer = sl.getService(GameContainer.class);
		} catch (ServiceNotImplementedException e) {
			throw new RuntimeException("Error crítico. Inicializacion de servicio " + e.getMessage() + " fallida.");
		}
	}
}
