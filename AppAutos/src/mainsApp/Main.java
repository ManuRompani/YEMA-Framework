package mainsApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import containers.CarContainer;
import controllers.CarController;
import framework_controllers.BaseController;
import interfaces.ICommunicator;
import models.Car;
import services.ServiceLocator;
import services.YemaAppBuilder;
import utils.CommunicatorConsole;

public class Main {
	public static void main(String[] args) throws IOException {
		YemaAppBuilder builder = new YemaAppBuilder();
		
		builder.addService(new CarContainer());
		
		builder.addController("car", new CarController());
		
		
	}
	/*public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(80);
		CarContainer carContainer = new CarContainer();
		//CommunicatorConsole communicator = new CommunicatorConsole(System.out, System.in);
		ServiceLocator serviceLocator = new ServiceLocator();
		
		
		//REGISTRO UN NUEVO SERVICIO EN SERVICELOCATOR, PASANDOLE, KEY, NUEVO CONTROLADOR
		//DE AUTO QUE RECIBE UN CONTENDOR DE AUTOS Y UN COMUNICADOR
		serviceLocator.registerService(new CarController(carContainer));
		
		//Falta Builder de la app, va en el framework
		
		//A MODO DE PRUEBAS HARDCODEO UN AUTO NUEVO Y LO AÑADO
		Car autoNuevo = new Car("ABC", 25);
		carContainer.addCar(autoNuevo);
		System.out.println(autoNuevo.toString());
		//---------------------------------------
		
		//se acepta la conexcion
		//se crea el hilo
		
		System.out.println("Esperando conexiones...");
		while(true) {
			Socket soc = ss.accept();
			System.out.println("Alguien se conecto");
			Thread hilo = new Thread(new InitialSession(serviceLocator, 
					new CommunicatorConsole(soc.getOutputStream(), soc.getInputStream())));
			
			hilo.start();
		}
	}*/

}
