package services;

import annotations.AuthorizedRoles;
import dtos.Command;
import dtos.Credentials;
import dtos.Response;
import exceptions.InvalidCommandException;
import exceptions.ServiceNotImplementedException;
import exceptions.ValidatorException;
import framework_controllers.BaseController;
import framework_controllers.ControllerLocator;
import interfaces.ICommunicator;
import interfaces.IUserManager;
import model.RoleBase;
import model.UserBase;
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

		
		while (true) {
			String username="";
			String password="";
			String resp="";
			UserBase user;
			Credentials creds;
			
			//creo que para un futuro un switch viene mejor que tantos if, yami :)
			try {
				communicator.send("Bienvenido, estas registrado? Para finalizar escribi salir");
				resp = communicator.receive();		
				
				if(resp == null || resp.equalsIgnoreCase("salir") ) {
					System.out.println("Cliente desconectado");
					return;
				}
				
				if(!resp.equalsIgnoreCase("si") && !resp.equalsIgnoreCase("no") && !resp.equalsIgnoreCase("salir")){					communicator.send("Debes responder si/no/salir");
					continue;
				}				
				
				// ======REGISTRO NUEVO USUARIO===========
				if (resp.equalsIgnoreCase("no")) {
					communicator.send("Elegi un username y escribilo:");
					username = communicator.receive();
					communicator.send("Ahora escribi una contrasenia:");
					password = communicator.receive();
					user = new UserBase(1, username, password, null);
					serviceLocator.getService(IUserManager.class).registerUser(user);
					communicator.send("Tu usuario "+ user.getName()+" fue creado con exito.");
				}		
												
				
				// ======INGRESO DE CREDENCIALES===========
				communicator.send("Ingrese su usuario: ");
				username = communicator.receive();
				communicator.send("Ingrese su contraseña:");
				password = communicator.receive();
				creds = new Credentials(username, password);
				
				// ======AUTENTICACION===========
				user = serviceLocator.getService(IUserManager.class).authenticate(creds);									
					if (user != null) {
						this.sessionData = new SessionData(user);
						communicator.send("Hola " + this.sessionData.getUserName());						
						break;
					} else {
						response.setMessage("Credenciales invalidas");						
						}
			} catch (ServiceNotImplementedException e) {
				response.setMessage("El servicio " + e.getMessage() + " no se encuentra.");
			}catch(Exception e) {
				System.out.println("Cliente desconectado");
				return;
			}
			
			communicator.send(response.getMessage());
		}

		Context context = new Context(this.sessionData, this.serviceLocator);
		
		// =======INGRESO DE COMANDOS===========
		// Una vez el usuario se loguea con exito, entramos en loop de comandos.
		
		
		while (true) {

			String sMessage = communicator.receive();

			try {
				System.out.println(sMessage);

				if (sMessage.toLowerCase().trim().equals("salir")) {
					communicator.send("Saliendo....");
					System.out.println("Hilo terminado: " + Thread.currentThread().getName());// SOLO PARA PRUEBAS
					return;
				}

				Command command = parser.Parse(sMessage);
				
				BaseController controller = this.controllerLocator.getController(command.getResource());
				
				// Se valida que el usuario este autorizado a usar el controlador
				//
				// Lo valido en la sesion para que cada usuario se autogestione y no que 
				// el controlador que todos comparten deba decir si esta o no autorizado
				if(!this.isUserAuthorizedToUse(controller)) {
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
	
	private boolean isUserAuthorizedToUse(Object obj) {
		boolean hasAnnotation = obj.getClass().isAnnotationPresent(AuthorizedRoles.class);

	    if (!hasAnnotation) {
	        return true;
	    }
	    
	    RoleBase userRole = this.sessionData.getUser().getRole();
	    
	    if(userRole == null) {
	    	return false;
	    }
	    
	    AuthorizedRoles annotation = obj.getClass().getAnnotation(AuthorizedRoles.class);

	    String[] roles = annotation.roles();

	    for (String role : roles) {
	        if (role.equals(userRole.getName())) {
	            return true;
	        }
	    }

	    return false;
	}
}