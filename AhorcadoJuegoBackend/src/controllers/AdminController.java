package controllers;

import annotations.AuthorizedRoles;
import consts.Roles;
import containers.WordCategoryContainer;
import containers.WordContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import exceptions.ValidatorException;
import framework_controllers.BaseController;
import juegoUtils.WordDeserializer;
import models.Word;
import utils.Context;
import services.ServiceLocator;

@AuthorizedRoles(roles = Roles.ADMIN)
public class AdminController extends BaseController {

	private WordCategoryContainer categories;
	private WordContainer words;
	private WordDeserializer wordDeserializer;

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

		// TODO: implementar actualización de palabra.
		// Ruta esperada desde el front:
		// admin/update-word/word=NOMBRE|CATEGORIA|PISTA|PUNTAJE

		response.setMessage("bad=Metodo update-word aun no implementado");
		return response;
	}

	private Response deleteWord(Command command, Context cont) {
		Response response = new Response();

		// TODO: implementar eliminación de palabra.
		// Ruta esperada desde el front:
		// admin/delete-word/word=NOMBRE

		response.setMessage("bad=Metodo delete-word aun no implementado");
		return response;
	}

	private Response getWords(Command command, Context cont) {
		Response response = new Response();

		// TODO: implementar listado de palabras.
		// Formato esperado por el front:
		// PALABRA|CATEGORIA|PISTA|PUNTAJE;PALABRA|CATEGORIA|PISTA|PUNTAJE

		response.setMessage("bad=Metodo get-words aun no implementado");
		return response;
	}

	private Response getCategories(Command command, Context cont) {
		Response response = new Response();

		// TODO: implementar listado de categorías.
		// Formato esperado por el front:
		// Cultura;Gastronomia;Deporte

		response.setMessage("bad=Metodo get-categories aun no implementado");
		return response;
	}
}