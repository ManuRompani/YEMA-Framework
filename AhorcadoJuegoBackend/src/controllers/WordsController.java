package controllers;

import annotations.AuthorizedRoles;
import containers.WordCategoryContainer;
import containers.WordContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import utils.Context;
import services.ServiceLocator;

@AuthorizedRoles(roles = {"admin","player"})
public class WordsController extends BaseController {
	//
	// PODRIAMOS TENER LOS CONTROLADORES POR ROL?? EJ: PLAYER_CONTROLLER 
	// Y COINCIDE CON COMO MANEJAMOS LA AUTENTICACION
	//
	//
	// Recuerden que no necesita constructor para los servicios ya que se inyectan en iniciarServicios
	// y iniciarServicios por medio del serviceLocator accede a los servicios que
	// ya fueron creados en el builder, donde tambien se llama a iniciarServicios
	private WordContainer wordContainer;
	private WordCategoryContainer categoryContainer;
	
	@Override
	public Response Ejecutar(Command comand, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void iniciarServicios(ServiceLocator sl) {
			try {
				this.wordContainer = sl.getService(WordContainer.class);
				this.categoryContainer = sl.getService(WordCategoryContainer.class);
			} catch (ServiceNotImplementedException e) {
				throw new RuntimeException("Error crítico. Inicializacion de servicio " + e.getMessage() + " fallida.");
			}
	}
	
	// list, add, update, delete, categories
}
