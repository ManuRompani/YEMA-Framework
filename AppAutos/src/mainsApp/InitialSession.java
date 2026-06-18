package mainsApp;

import dtos.Command;
import dtos.Response;
import exceptions.InvalidCommandException;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import framework_controllers.ControllerLocator;
import interfaces.ICommunicator;
import services.ServiceLocator;
import utils.CommandParser;
import utils.CommunicatorConsole;
import utils.SessionData;
import utils.Context;

class InitialSession implements Runnable {
	
	private ServiceLocator serviceLocator;
	private ControllerLocator controllerLocator;
	private ICommunicator communicator;
	private CommandParser parser;
	private SessionData sessionData;
	
	public InitialSession(ServiceLocator serviceLocator, ICommunicator communicator) {
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
		Context context = new Context(this.sessionData);
		
		communicator.send("Hola " + this.sessionData.getUserName()); //puedo usar this.sessionData.getAttribute(username)
		
		while(true) {
			String sMessage = communicator.receive();
			try {
				System.out.println(sMessage);
				
				if(sMessage.toLowerCase().equals("salir")) {
					communicator.send("Saliendo....");
					return;//esto termina la vida del hilo
				}
				
				Command command = parser.Parse(sMessage);

				BaseController controller = this.controllerLocator.getController(command.getResource());
				//aca se crea el objeto contexto
				response = controller.Ejecutar(command, context);//el sesion data va a pasar a contexto
				
			} catch (InvalidCommandException e) {
				response.setMessage(e.getMessage());
			} catch (ServiceNotImplementedException e) {
				response.setMessage(e.getMessage());
			} //catch (ClassNotFoundException e) {
				//response.setMessage(e.getMessage());
			//}
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
