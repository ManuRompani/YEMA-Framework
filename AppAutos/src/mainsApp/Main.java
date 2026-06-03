package mainsApp;

import containers.CarContainer;
import controllers.CarController;
import framework_controllers.BaseController;
import interfaces.ICommunicator;
import models.Car;
import services.ServiceLocator;
import utils.CommunicatorConsole;

public class Main {

	public static void main(String[] args) {
		CarContainer carContainer = new CarContainer();
		CommunicatorConsole communicator = new CommunicatorConsole(System.out, System.in);
		ServiceLocator<BaseController> serviceLocator = new ServiceLocator<>();
		
		
		//REGISTRO UN NUEVO SERVICIO EN SERVICELOCATOR, PASANDOLE, KEY, NUEVO CONTROLADOR
		//DE AUTO QUE RECIBE UN CONTENDOR DE AUTOS Y UN COMUNICADOR
		serviceLocator.registerService("car", new CarController(carContainer, communicator));
		
		//A MODO DE PRUEBAS HARDCODEO UN AUTO NUEVO Y LO AÑADO
		Car autoNuevo = new Car("ABC", 25);
		carContainer.addCar(autoNuevo);
		
		InitialSession initialSession = new InitialSession(serviceLocator, communicator);
		
		initialSession.run();
	}

}
