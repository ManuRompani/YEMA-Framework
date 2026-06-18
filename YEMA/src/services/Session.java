package services;

import dtos.Command;
import dtos.Response;
import exceptions.InvalidCommandException;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import interfaces.ICommunicator;
import utils.CommandParser;
import utils.SessionData;

public class Session implements Runnable {
	
	private ServiceLocator serviceLocator = null;
	//private ControllerLocator controllerLocator = null;
	private ICommunicator communicator = null;
	private CommandParser parser = null;
	private SessionData sessionData;
	
	public Session(ServiceLocator serviceLocator, ICommunicator communicator) {
		super();
		this.serviceLocator = serviceLocator;
		this.communicator = communicator;
		this.parser = new CommandParser();
	}

	
	@Override
	public void run() {
		//El comunicador se le inyecta al controlador brindado por el service controller y desde ahi se hace todo.
		//Lo unico que se envia desde este metodo run son los errores generales
		Response response = new Response();
		
		//PARA PROBAR, PERO EL REQUISITO A FUTURO ES: PRIMERO PREGUNTA
		//QUIEN ES Y QUE EL DATO PERSISTA DURANTE LA SESION. 
		communicator.send("Ingrese su nombre: "); 
		String username = communicator.receive();
		this.sessionData = new SessionData(username);
		
		communicator.send("Hola " + this.sessionData.getUserName()); //puedo usar this.sessionData.getAttribute(username)
		
		while(true) {
			String sMessage = communicator.receive();
			try {
				System.out.println(sMessage);
				Command command = parser.Parse(sMessage);

				String className = "controllers." + command.getResource() + "Controller";
				Class<?> cls = Class.forName(className);
				BaseController controller = (BaseController) this.serviceLocator.getService(cls);
				controller.setSessionData(sessionData);
				response = controller.Ejecutar(command);
				
			} catch (InvalidCommandException e) {
				response.setMessage(e.getMessage());
			} catch (ServiceNotImplementedException e) {
				response.setMessage(e.getMessage());
			} catch (ClassNotFoundException e) {
				response.setMessage(e.getMessage());
			}
			//Dejar sin controlar para desarrollo
			/*catch(Exception e) {
				response.setMessage("Unhandled exception");
			}*/
			
			
			if(response.getMessage() != null) {				
				communicator.send(response.getMessage());
			}
		
		}
	}	
}