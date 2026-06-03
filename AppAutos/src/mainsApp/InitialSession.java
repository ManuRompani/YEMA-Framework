package mainsApp;

import dtos.Command;
import dtos.Response;
import exceptions.InvalidCommandException;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import interfaces.ICommunicator;
import services.ServiceLocator;
import utils.CommandParser;
import utils.CommunicatorConsole;

class InitialSession implements Runnable {
	
	private ServiceLocator<BaseController> controllerLocator;
	private ICommunicator communicator;
	private CommandParser parser;
	
	public InitialSession(ServiceLocator<BaseController> controllerLocator, ICommunicator communicator) {
		super();
		this.controllerLocator = controllerLocator;
		this.communicator = communicator;
		this.parser = new CommandParser();
	}

	
	@Override
	public void run() {
		Response response = new Response();
		
		//PARA PROBAR, PERO EL REQUISITO A FUTURO ES: PRIMERO PREGUNTA
		//QUIEN ES Y QUE EL DATO PERSISTA DURANTE LA SESION. 
		communicator.send("Estoy funcionando"); 
		
		while(true) {
			String sMessage = communicator.receive();
			
			try {
				Command command = parser.Parse(sMessage);
				
				BaseController controller = this.controllerLocator.getService(command.getResource());
				response = controller.Ejecutar(command);
				
			} catch (InvalidCommandException e) {
				
				response.setMessage(e.getMessage());
			} catch (ServiceNotImplementedException e) {
				
				response.setMessage(e.getMessage());
			}
			
			communicator.send(response.getMessage()); //ESTE RESPONSE DEVUELVE UN NULL
		}
		
	}

}
