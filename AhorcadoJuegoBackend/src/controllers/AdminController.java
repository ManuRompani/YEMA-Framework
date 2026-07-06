package controllers;

import java.util.ArrayList;
import java.util.List;

import annotations.AuthorizedRoles;
import consts.Roles;
import containers.WordCategoryContainer;
import containers.WordContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import exceptions.ValidatorException;
import framework_controllers.BaseController;
import juegoUtils.WordCategorySerializer;
import juegoUtils.WordDeserializer;
import juegoUtils.WordListSerializer;
import models.Word;
import utils.Context;
import services.ServiceLocator;

@AuthorizedRoles(roles = Roles.ADMIN)
public class AdminController extends BaseController {

	private WordCategoryContainer categories;
	private WordContainer words;
	private WordDeserializer wordDeserializer;
	private WordCategorySerializer categorySerializer;
	private WordListSerializer wordListSerializer;

	@Override
	public Response Ejecutar(Command command, Context context) {
		switch (command.getAction()) {
			case "add-word":
				return this.addWord(command, context);

			case "update-word":
				return this.updateWord(command, context);

			case "delete-word":
				return this.deleteWord(command, context);

			case "get-words":
				return this.getWords(command, context);

			case "get-categories":
				return this.getCategories(command, context);

			case "add-category":
				return this.addCategory(command, context);

			case "delete-category":
				return this.deleteCategory(command, context);

			case "update-category":
				return this.updateCategory(command, context);

			default:
				Response response = new Response();
				response.setMessage("bad=Accion no reconocida: " + command.getAction());
				return response;
		}
	}

	@Override
	public void iniciarServicios(ServiceLocator sl) {
		try {
			this.categories = sl.getService(WordCategoryContainer.class);
			this.words = sl.getService(WordContainer.class);
			this.wordDeserializer = sl.getService(WordDeserializer.class);
			this.categorySerializer = sl.getService(WordCategorySerializer.class);
			this.wordListSerializer = sl.getService(WordListSerializer.class);
		} catch (ServiceNotImplementedException se) {
			throw new RuntimeException("Error crítico: inicializacion del servicio " + se.getMessage() + " fallida.");
		}
	}

	private Response addWord(Command command, Context cont) {
		Response response = new Response();

		try {
			String sWord = command.getParameter("word");

			Word word = this.wordDeserializer.deserealize(sWord, Word.class);
			word.setUsername(cont.getUser().getName());

			this.words.addWord(word);

			response.setMessage("ok");
		} catch (ValidatorException e) {
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			response.setMessage("bad=" + e.getMessage());
		}

		return response;
	}

	private Response updateWord(Command command, Context cont) {
		Response response = new Response();

		try {
			String oldWord = command.getParameter("oldWord");
			String sNewWord = command.getParameter("newWord");

			Word newWord = this.wordDeserializer.deserealize(sNewWord, Word.class);
			newWord.setUsername(cont.getSessionData().getUserName());

			this.words.updateWord(oldWord, newWord);

			response.setMessage("ok");
		}
		catch (ValidatorException e) {
			response.setMessage("bad" + e.getMessage());
		}
		catch (Exception e) {
			response.setMessage("bad=" + e.getMessage());
		}

		return response;
	}

	private Response deleteWord(Command command, Context cont) {
		Response response = new Response();

		try {
			String wordName = command.getParameter("word");

			if (wordName == null || wordName.trim().isEmpty()) {
				response.setMessage("bad=El nombre de la palabra no puede estar vacío.");
				return response;
			}

			boolean removed = this.words.removeWord(wordName.trim());

			if (removed) {
				response.setMessage("ok");
			} else {
				response.setMessage("bad=La palabra no existe.");
			}
		} catch (Exception e) {
			response.setMessage("bad=" + e.getMessage());
		}

		return response;
	}

	private Response getWords(Command command, Context cont) {
		Response response = new Response();

		ArrayList<Word> palabras = this.words.getAllWords();
		
		if(palabras.isEmpty()) {
			response.setMessage("null");
		}else {
			String sPalabras = this.wordListSerializer.serialize(palabras);
			
			response.setMessage(sPalabras);	
		}
		
		return response;
	}

	private Response getCategories(Command command, Context cont) {
		Response response = new Response();

		List<String> categories = this.categories.getAll();
		if(categories.isEmpty()) {
			response.setMessage("null");
		}
		else {
			String sCategories = this.categorySerializer.serialize(categories);
			response.setMessage(sCategories);
		}
		
		return response;
	}

	private Response addCategory(Command command, Context cont) {
		Response response = new Response();

		try {
			String categoryName = command.getParameter("name");

			if (categoryName == null || categoryName.trim().isEmpty()) {
				response.setMessage("bad=El nombre de la categoría no puede estar vacío.");
				return response;
			}

			categoryName = categoryName.trim();

			List<String> existing = this.categories.getAll();
			for (String cat : existing) {
				if (cat.equalsIgnoreCase(categoryName)) {
					response.setMessage("repetida");
					return response;
				}
			}

			this.categories.add(categoryName);
			response.setMessage("ok");
		} catch (Exception e) {
			response.setMessage("bad=" + e.getMessage());
		}

		return response;
	}

	private Response deleteCategory(Command command, Context cont) {
		Response response = new Response();

		try {
			String categoryName = command.getParameter("name");

			if (categoryName == null || categoryName.trim().isEmpty()) {
				response.setMessage("bad=El nombre de la categoría no puede estar vacío.");
				return response;
			}

			boolean removed = this.categories.delete(categoryName.trim());

			if (removed) {
				response.setMessage("ok");
			} else {
				response.setMessage("bad=La categoría no existe.");
			}
		} catch (Exception e) {
			response.setMessage("bad=" + e.getMessage());
		}

		return response;
	}

	private Response updateCategory(Command command, Context cont) {
		Response response = new Response();

		try {
			String oldName = command.getParameter("oldName");
			String newName = command.getParameter("newName");

			if (oldName == null || oldName.trim().isEmpty()) {
				response.setMessage("bad=El nombre actual de la categoría no puede estar vacío.");
				return response;
			}

			if (newName == null || newName.trim().isEmpty()) {
				response.setMessage("bad=El nuevo nombre de la categoría no puede estar vacío.");
				return response;
			}

			oldName = oldName.trim();
			newName = newName.trim();

			List<String> existing = this.categories.getAll();
			for (String cat : existing) {
				if (cat.equalsIgnoreCase(newName) && !cat.equalsIgnoreCase(oldName)) {
					response.setMessage("repetida");
					return response;
				}
			}

			boolean deleted = this.categories.delete(oldName);
			if (!deleted) {
				response.setMessage("bad=La categoría no existe.");
				return response;
			}

			this.categories.add(newName);

			ArrayList<Word> allWords = this.words.getAllWords();
			for (Word word : allWords) {
				if (word.getCategory().equalsIgnoreCase(oldName)) {
					word.setCategory(newName);
				}
			}

			response.setMessage("ok");
		} catch (Exception e) {
			response.setMessage("bad=" + e.getMessage());
		}

		return response;
	}
}