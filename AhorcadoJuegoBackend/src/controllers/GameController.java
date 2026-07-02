package controllers;

import annotations.AuthorizedRoles;
import containers.GameContainer;
import containers.WordCategoryContainer;
import containers.WordContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import models.Word;
import models.WordCategory;
import services.ServiceLocator;
import utils.Context;

import java.util.ArrayList;

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
			case "add":
				response.setMessage(addWord(command));
				break;
			case "delete":
				response.setMessage(deleteWord(command));
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
			words = wordContainer.getWordsByCategory(Integer.parseInt(catId));
		} else {
			words = wordContainer.getAllWords();
		}

		StringBuilder sb = new StringBuilder("PALABRAS:\n");
		for (Word w : words) {
			sb.append("- ").append(w.getName())
			  .append(" [").append(w.getCategory().getDescription()).append("]")
			  .append(" Pista: ").append(w.getHint()).append("\n");
		}
		return sb.toString();
	}

	private String addWord(Command command) {
		String name = command.getParameter("name");
		String hint = command.getParameter("hint");
		String catId = command.getParameter("categoryId");

		if (name == null || hint == null || catId == null) {
			return "Faltan parámetros: name, hint, categoryId requeridos.";
		}

		try {
			WordCategory cat = categoryContainer.getCategoryById(Integer.parseInt(catId));
			Word word = new Word(name.toUpperCase(), cat, hint);
			wordContainer.addWord(word);
			return "Palabra '" + name.toUpperCase() + "' agregada correctamente.";
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	private String deleteWord(Command command) {
		String name = command.getParameter("name");
		if (name == null) {
			return "Parámetro 'name' requerido.";
		}
		boolean removed = wordContainer.removeWord(name);
		return removed
			? "Palabra '" + name + "' eliminada."
			: "Palabra '" + name + "' no encontrada.";
	}

	private String listCategories() {
		ArrayList<WordCategory> cats = (ArrayList<WordCategory>) categoryContainer.getAll();
		StringBuilder sb = new StringBuilder("CATEGORÍAS:\n");
		for (WordCategory c : cats) {
			sb.append("[").append(c.getId()).append("] ").append(c.getDescription()).append("\n");
		}
		return sb.toString();
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
