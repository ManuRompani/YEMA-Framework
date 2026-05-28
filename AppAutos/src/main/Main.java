package main;

import containers.CarContainer;
import controllers.CarController;
import framework_controllers.BaseController;
import interfaces.ICommunicator;
import services.ServiceLocator;
import utils.CommunicatorConsole;

public class Main {

	public static void main(String[] args) {
		CarContainer carContainer = new CarContainer();
		ICommunicator communicator = new CommunicatorConsole(System.out, System.in);
		ServiceLocator<BaseController> serviceLocator = new ServiceLocator<>();
		
		serviceLocator.registerService("car", new CarController(carContainer));
		
		InitialSession initialSession = new InitialSession(serviceLocator, communicator);
		
		initialSession.run();
	}

}
