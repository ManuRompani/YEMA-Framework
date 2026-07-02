package services;

import annotations.AuthorizedRoles;
import dtos.Command;
import dtos.Response;
import exceptions.InvalidCommandException;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import framework_controllers.ControllerLocator;
import interfaces.ICommunicator;
import utils.CommandParser;
import utils.Context;
import utils.SessionData;

public class Session implements Runnable {

	private ServiceLocator serviceLocator;
	private ControllerLocator controllerLocator;
	private ICommunicator communicator;
	private CommandParser parser;
	private SessionData sessionData;

	public Session(ControllerLocator controllerLocator, ServiceLocator serviceLocator, CommandParser commandParser,
			ICommunicator communicator) {
		super();
		this.controllerLocator = controllerLocator;
		this.communicator = communicator;
		this.parser = commandParser;
		this.serviceLocator = serviceLocator;
	}

	// Este metodo se inicia cuando en Main se crea un nuevo hilo (linea 45) - Yami
	@Override
	public void run() {

		Response response = new Response();
		Context context = new Context(this.serviceLocator);		
		
		while (true) {
			String sMessage = communicator.receive();
			
			if (sMessage == null) {
			    System.out.println("Cliente desconectado");
			    return;
			}
			
			try {
				System.out.println(sMessage);
				if (sMessage.toLowerCase().trim().equals("salir")) {
					communicator.send("Saliendo....");
					System.out.println("Hilo terminado: " + Thread.currentThread().getName()); // SOLO PARA PRUEBAS
					return;
				}

				Command command = parser.Parse(sMessage);				
				BaseController controller = this.controllerLocator.getController(command.getResource());
				// Se valida que el usuario este autorizado a usar el controlador
				//
				// Lo valido en la sesion para que cada usuario se autogestione y no que 
				// el controlador que todos comparten deba decir si esta o no autorizado
				if(!this.isUserAuthorizedToUse(controller, context)) {
					response.setMessage("Unauthorized access.");
				}
				else {
					response = controller.Ejecutar(command, context);
					
				}

			} catch (InvalidCommandException e) {
				response.setMessage(e.getMessage());
			} catch (ServiceNotImplementedException e) {
				response.setMessage(e.getMessage());
			} catch (Exception e) {
				response.setMessage(e.getMessage());

			}

			if (response.getMessage() != null) {
				communicator.send(response.getMessage());
			}

		}

	}
	
	private boolean isUserAuthorizedToUse(Object obj, Context context) {
		boolean hasAnnotation = obj.getClass().isAnnotationPresent(AuthorizedRoles.class);

	    if (!hasAnnotation) {
	        return true;
	    }
	    	   
	    
	    if(context == null || context.getUser() == null) {
	    	return false;
	    }
	    
	    String userRole = context.getUser().getRole();//le consulto al contexto quien es el user que tiene y que rol
	    
	    
	    if(userRole == null) {
	    	return false;
	    }
	    
	    AuthorizedRoles annotation = obj.getClass().getAnnotation(AuthorizedRoles.class);

	    String[] roles = annotation.roles();

	    for (String role : roles) {
	        if (role.equals(userRole)) {
	            return true;
	        }
	    }

	    return false;
	}
}