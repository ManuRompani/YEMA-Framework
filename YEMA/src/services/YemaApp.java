package services;

import java.net.ServerSocket;
import java.net.Socket;

import exceptions.CommunicatorException;
import framework_controllers.ControllerLocator;
import interfaces.ICommunicator;
import utils.CommandParser;
import utils.CommunicatorConsole;
import utils.CommunicatorSocket;
import utils.Constants;


public class YemaApp implements Runnable  {
	private ServiceLocator serviceLocator;
	private ControllerLocator controllerLocator;
	private ServerSocket serverSocket;
	private CommandParser commandParser = new CommandParser();
	
	// si se usa socket debe recibir socket != null
	public YemaApp(ServiceLocator serviceLocator, 
			ControllerLocator controllerLocator, 
			ServerSocket serverSocket) 
	{
		this.serviceLocator = serviceLocator;
		this.controllerLocator = controllerLocator;
		this.serverSocket = serverSocket;
	}

	
	@Override
	public void run() {
		//Scoket
		if(this.serverSocket != null) {
			while(true) {
				try {
					System.out.println("SOCKET");
					System.out.println("PUERTO " + this.serverSocket.getLocalPort());
					System.out.println("Escuchando...");
					
					Socket soc = serverSocket.accept();
					System.out.println("Alguien se conecto");
					
					Session session = new Session(
							this.controllerLocator,
							this.serviceLocator,
							this.commandParser,
							new CommunicatorSocket(soc));
					
					session.run();
				}
				catch(Exception e) {
					//No termino de decidir que pasa aca
				}
			}
		}
		//Consola
		else{
			System.out.println("CONSOLE");
			System.out.println("Escuchando...");
			
			CommunicatorConsole console = new CommunicatorConsole(System.out, System.in);
			
			Session session = new Session(
					this.controllerLocator,
					this.serviceLocator,
					this.commandParser,
					console);
			
			session.run();
		}
	}
}
