package services;

import java.net.ServerSocket;
import java.net.Socket;

import exceptions.CommunicatorException;
import utils.CommunicatorConsole;
import utils.Constants;


public class YemaApp implements Runnable  {
	private ServerSocket serverSocket = null;
	private ServiceLocator serviceLocator = null;
	//private ControllerLocator controllerLocator = null;
	private String communicatorType = Constants.SOCKET;
	
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
	//Geters y Seters
	public String getCommunicatorType() {
		return communicatorType;
	}
	public void setCommunicatorType(String communicatorType) {
		this.communicatorType = communicatorType;
	}
	//
	//
	//
	@Override
	public void run() {
		if(communicatorType.equals(Constants.SOCKET)) {
			while(true) {
				try {
					System.out.println("SOCKET");
					System.out.println("PUERTO " + serverSocket.getLocalPort());
					System.out.println("Escuchando...");
					
					Socket soc = serverSocket.accept();
					System.out.println("Alguien se conecto");
					
					Thread hilo = new Thread(
							new Session(serviceLocator, 
							new CommunicatorConsole(soc.getOutputStream(), soc.getInputStream())));
					
					hilo.start();
				}
				catch(Exception e) {
					//No termino de decidir que pasa aca
				}
			}
		}
		else if(communicatorType.equals(Constants.CONSOLE)) {
			System.out.println("CONSOLE");
			System.out.println("Escuchando...");
			//Crea logica para consola
		}
		else {
			throw new CommunicatorException("Unrecognized communicator");
		}
		
	}
}
