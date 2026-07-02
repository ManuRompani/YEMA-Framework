package controllers;

import annotations.AuthorizedRoles;
import consts.Roles;
import containers.WordCategoryContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import utils.Context;
import services.ServiceLocator;

@AuthorizedRoles(roles = Roles.ADMIN)
public class AdminController extends BaseController {
	private WordCategoryContainer categories;
	
	@Override
	public Response Ejecutar(Command comand, Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void iniciarServicios(ServiceLocator sl) {
		try {
			this.categories = sl.getService(WordCategoryContainer.class);
		} catch (ServiceNotImplementedException se) {
			throw new RuntimeException("Error crítico: inicializacion del servicio " + se.getMessage() + " fallida.");
		}
		
	}

}
