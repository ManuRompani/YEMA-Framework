package controllers;

import annotations.AuthorizedRoles;
import containers.GameContainer;
import containers.WordCategoryContainer;
import containers.WordContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import juegoUtils.GameGuessResponse;
import juegoUtils.GameResponseSerializer;
import juegoUtils.GameStartResponse;
import models.Word;
import services.ServiceLocator;
import utils.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ahorcadojuego_utils.WordCategorySerializer;

@AuthorizedRoles(roles = {"admin","player"})
public class GameController extends BaseController {
	private GameContainer gameContainer;
	private WordContainer wordContainer;
	private WordCategoryContainer categoryContainer;
	private GameResponseSerializer gameSerializer;
	private WordCategorySerializer categorySerializer;

	@Override
	public Response Ejecutar(Command command, Context context) {
		Response response = new Response();
		String action = command.getAction();

		switch(action) {
			case "start":
				response.setMessage(startGame(context));
				break;
			case "guess":
				response.setMessage(guessLetter(command, context));
				break;
			case "hint":
				response.setMessage(getHint(context));
				break;
			case "status":
				response.setMessage(getStatus(context));
				break;
			case "list":
				response.setMessage(listWords(command));
				break;
			case "categories":
				response.setMessage(listCategories());
				break;
			default:
				response.setMessage(gameSerializer.serializeError("Acción no reconocida: " + action));
		}
		return response;
	}

	private String startGame(Context context) {
		GameStartResponse startResponse = gameContainer.startGame(context.getSessionData());

		if (startResponse == null) {
			return gameSerializer.serializeError("No hay palabras disponibles.");
		}

		return gameSerializer.serializeStart(startResponse);
	}

	private String guessLetter(Command command, Context context) {
		String letter = command.getParameter("letter");

		if (letter == null || letter.trim().isEmpty()) {
			return gameSerializer.serializeError("Parámetro 'letter' requerido.");
		}

		GameGuessResponse guessResponse = gameContainer.guessLetter(letter.trim(), context.getSessionData());

		if (guessResponse == null) {
			Word word = (Word) context.getSessionData().getAttribute("currentWord");
			Boolean finished = (Boolean) context.getSessionData().getAttribute("gameFinished");

			if (word == null) {
				return gameSerializer.serializeError("No hay partida en curso. Usá 'start'.");
			}
			if (finished) {
				return gameSerializer.serializeError("El juego ya terminó. Usá 'start' para una nueva partida.");
			}
			HashSet<Character> guessed = (HashSet<Character>) context.getSessionData().getAttribute("guessedLetters");
			if (guessed.contains(Character.toUpperCase(letter.charAt(0)))) {
				return gameSerializer.serializeError("Ya probaste la letra '" + Character.toUpperCase(letter.charAt(0)) + "'.");
			}
			return gameSerializer.serializeError("Error al procesar la letra.");
		}

		return gameSerializer.serializeGuess(guessResponse);
	}

	private String getHint(Context context) {
		String hint = gameContainer.getHint(context.getSessionData());
		return gameSerializer.serializeHint(hint);
	}

	private String getStatus(Context context) {
		GameGuessResponse statusResponse = gameContainer.getStatus(context.getSessionData());

		if (statusResponse == null) {
			return gameSerializer.serializeError("No hay partida en curso.");
		}

		return gameSerializer.serializeGuess(statusResponse);
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
		List<String> cats = this.categoryContainer.getAll();
		return categorySerializer.serialize(cats);
	}

	@Override
	public void iniciarServicios(ServiceLocator sl) {
		try {
			this.wordContainer = sl.getService(WordContainer.class);
			this.categoryContainer = sl.getService(WordCategoryContainer.class);
			this.gameContainer = sl.getService(GameContainer.class);
			this.gameSerializer = sl.getService(GameResponseSerializer.class);
			this.categorySerializer = sl.getService(WordCategorySerializer.class);
		} catch (ServiceNotImplementedException e) {
			throw new RuntimeException("Error crítico. Inicializacion de servicio " + e.getMessage() + " fallida.");
		}
	}
}
