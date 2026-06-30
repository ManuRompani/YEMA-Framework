package controllers;

import appautos_utils.UserSerializer;
import containers.MemoryUserManager;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import model.UserBase;
import services.ServiceLocator;
import utils.Context;

public class UserController extends BaseController{

	private MemoryUserManager userManager;
	private UserSerializer serializer;
	
	public UserController() {
		this.userManager = new MemoryUserManager();
		this.serializer = new UserSerializer();
	}
	
	@Override
	public Response Ejecutar(Command command, Context context) {
		Response response = new Response();	
		
		switch(command.getAction()) {
		case("get-users"):
			
		case("get-user"):
			
		}
		return response;
	}

	public Response getUserByUsername(String username) {
		Response response = new Response();
		UserBase ub = this.userManager.getUser(username);
		
		if(ub == null) {
			response.setMessage("Usuario no encontrado");
		}else {
			String userSerialized = this.serializer.serialize(ub);
			//faltaria setear el response?
		}
		return response;
	}
	
	
	@Override
	public void iniciarServicios(ServiceLocator sl) {
		try {
			this.userManager = sl.getService(MemoryUserManager.class);
		} catch (ServiceNotImplementedException e) {
			throw new RuntimeException("Error crítico. Inicializacion de servicio " + e.getMessage() + " fallida.");
		}
	}

}
