package services;

import java.io.IOException;
import java.net.ServerSocket;

import framework_controllers.BaseController;
import utils.Constants;

public class YemaAppBuilder {
	private ServiceLocator serviceLocator = null;
	
	// Define que tipo de comunicador se va a usar
	private String communicatorType = Constants.SOCKET;
	
	//Propiedades de configuracion del socket
	private int socketPort = 80;

	// CONFIGURACION DE CONSOLA
	public void useConsoleAsCommunicator() {
		this.communicatorType = Constants.CONSOLE;
	}

	// CONFIGURACION DE SOCKET
	public void useSocketAsCommunicator() {
		this.communicatorType = Constants.SOCKET;
	}

	public void setSocketPort(int port) {
		this.socketPort = port;
	}

	
	// Configuracion de servicios
	public void addService(Object obj) {
		if(this.serviceLocator == null) {
			this.serviceLocator = new ServiceLocator();
		}
		this.serviceLocator.registerService(obj);
	}

	// Configuracion de controladores
	public void addController(String key, BaseController value) {
		//this.controllerLocator.Add(key, value);
	}


	
	public YemaApp build() throws IOException {
		YemaApp app = new YemaApp();
		
		//Configuracion del comunicador
		app.setCommunicatorType(this.communicatorType);
		
		if(communicatorType.equals(Constants.SOCKET)) {
			ServerSocket ss = new ServerSocket(this.socketPort);
			app.setServerSocket(ss);
		}
		else {
			// aca se prepararia para que use la consola
		}
		
		
		//Configuracion del serviceLocator
		app.setServiceLocator(this.serviceLocator);
		
		
		//Configuracion de controllerLocator
		// app.setControllerLocator(this.controllerLocator); aun no esta
		
		return new YemaApp();
	}
}
