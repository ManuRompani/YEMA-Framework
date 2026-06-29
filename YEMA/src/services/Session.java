package services;

import dtos.Command;
import dtos.Credentials;
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
	
	public Session(ControllerLocator controllerLocator, 
			ServiceLocator serviceLocator,
			CommandParser commandParser, // Se utiliza un solo command parser para todo el programa, no es de E/S asi que no deberia ser bloqueante
			ICommunicator communicator)
	{
		super();	
		this.controllerLocator = controllerLocator;
		this.communicator = communicator;
		this.parser = commandParser;
		this.serviceLocator = serviceLocator;
	}

	
	//Este metodo se inicia cuando en Main se crea un nuevo hilo (linea 45)  - Yami
	@Override
	public void run() {	

		Response response = new Response();
		

		communicator.send("Ingrese su usuario: "); 
		String username = communicator.receive();
		
		communicator.send("Ingrese su contraseña:");
		String password = communicator.receive();
		
		Credentials creds = new Credentials(username, password);
		if()		
		
		
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
				
		
				Context context = new Context(this.sessionData, this.serviceLocator);
				response = controller.Ejecutar(command, context);	
					
				
			} catch (InvalidCommandException e) {
				response.setMessage(e.getMessage());
			} catch (ServiceNotImplementedException e) {
				response.setMessage(e.getMessage());
			} catch (Exception e) {
				response.setMessage(e.getMessage());
				// ya lo probe no da error sin el return. Si da error no es por el return, 
				// no tiene nada q ver, la excepcion ya se esta manejando
			}
			
			//si el mensaje recibido no es null, muestro respuesta, sino sigo esperando
			if(response.getMessage() != null) {				
				communicator.send(response.getMessage());
			}
			
		}

	}
}