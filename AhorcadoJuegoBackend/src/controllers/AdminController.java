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
		switch(command.getAction()) {
		case "add-word":
			return this.addWord(command, context);
		default:
			Response response = new Response();
			response.setMessage("Accion no reconocida: " + command.getAction());
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
	
	private Response addWord(Command command, Context cont){
		Response response = new Response();
		try {
			String sWord = command.getParameter("word");
			Word word = this.wordDeserializer.deserealize(sWord, Word.class);
			word.setUsername(cont.getSessionData().getUserName());
			
			this.words.addWord(word);
			
			response.setMessage("ok");
		}
		catch(ValidatorException e) {
			response.setMessage(e.getMessage());
		}
		
		return response;
	}

}
