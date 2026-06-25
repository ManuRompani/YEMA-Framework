package services;

import java.io.IOException;
import java.net.ServerSocket;

import framework_controllers.BaseController;
import framework_controllers.ControllerLocator;
import utils.Constants;

public class YemaAppBuilder {
	private ServiceLocator serviceLocator = null;
	private ControllerLocator controllerLocator = null;
	private String communicatorType = Constants.SOCKET;
	
	private ServerSocket serverSocket = null;
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
		if(this.controllerLocator == null) {
			this.controllerLocator = new ControllerLocator();
		}
		this.controllerLocator.registerController(key, value);;
	}


	
	public YemaApp build() throws IOException {
		if(communicatorType.equals(Constants.SOCKET)) {
			serverSocket = new ServerSocket(this.socketPort);
		}
		
		return new YemaApp(this.serviceLocator, this.controllerLocator, this.serverSocket);
	}
}
