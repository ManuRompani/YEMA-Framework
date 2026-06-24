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
import utils.SessionData;
import utils.Context;

class InitialSession implements Runnable {
	
	private ServiceLocator serviceLocator;
	private ControllerLocator controllerLocator;
	private ICommunicator communicator;
	private CommandParser parser;
	private SessionData sessionData;
	
	public InitialSession(ControllerLocator controllerLocator, ICommunicator communicator) {
		super();	
		this.controllerLocator = controllerLocator;
		this.communicator = communicator;
		this.parser = new CommandParser();
	}

	
	//Este metodo se inicia cuando en Main se crea un nuevo hilo (linea 45)  - Yami
	@Override
	public void run() {	
		//creamos un nuevo objeto response para guardar respuestas del servidor  - Yami
		Response response = new Response();
		
		
		//Paso 1: preguntamos al usuario quien es  - Yami
		communicator.send("Ingrese su nombre: "); 
		String username = communicator.receive();
		
		//Paso 2: creamos un nuevo SessionData que recibe el nombre consultado y se lo asigna a esta sessionData y saludamos al usuario - Yami
		this.sessionData = new SessionData(username);
		communicator.send("Hola " + this.sessionData.getUserName());
		
		
		//Paso 3: creamos un bucle donde vamos a recibir los comandos a ejecutar  - Yami
		while(true) {
			//Paso 5: Recibo el comando por el comunicador por socket - Yami
			String sMessage = communicator.receive();
			
			try {				
				System.out.println(sMessage); 
						
					//Paso 6: Si lo que escribe usuario es salir, entro aca a finalziar el hilo - Yami
					if(sMessage.toLowerCase().trim().equals("salir")) {
						communicator.send("Saliendo....");
					
						//Esta linea es para que puedas comprobar desde consola que el hilo se finalizo  - Yami
						System.out.println("Hilo terminado: " + Thread.currentThread().getName());
						return;//esto termina la vida del hilo  - Yami
					}
				
				//Paso 7: Parseo el mensaje recibido a un comando
				Command command = parser.Parse(sMessage);
				
				//Paso 8: Busco el controlador pasandole el recurso recibido en el comando
				BaseController controller = this.controllerLocator.getController(command.getResource());
				
				//Paso 9: creamos un nuevo objeto contexto que recibe los datos de la sesion 
				//y los conserva durante la misma, luego llamamos al controlador y ejecutamos
				// pasandole el comando y el contexto, la respuesta de esa ejecucion la guardamos en
				// la variable response para mostrar una respuesta al usuario - Yami
				Context context = new Context(this.sessionData);
				response = controller.Ejecutar(command, context);	
					
				
			} catch (InvalidCommandException e) {
				response.setMessage(e.getMessage());
			} catch (ServiceNotImplementedException e) {
				response.setMessage(e.getMessage());
			} catch (Exception e) {
				response.setMessage(e.getMessage());
				break;
			}
			
			//si el mensaje recibido no es null, muestro respuesta, sino sigo esperando
			if(response.getMessage() != null) {				
			communicator.send(response.getMessage());
			}
			
			
		
		}

	}
}
