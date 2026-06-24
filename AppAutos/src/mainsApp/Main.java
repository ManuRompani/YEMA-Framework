package mainsApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import containers.CarContainer;
import controllers.CarController;
import framework_controllers.BaseController;
import framework_controllers.ControllerLocator;
import interfaces.ICommunicator;
import models.Car;
import services.ServiceLocator;
import utils.CommunicatorConsole;
import utils.CommunicatorSocket;

public class Main {

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(80);
		CarContainer carContainer = new CarContainer();	
		ServiceLocator serviceLocator = new ServiceLocator();//Lo dejamos para el futuro poder registrar los servicios por ej: Parser, Serializer  - Yami
		ControllerLocator controllerLocator = new ControllerLocator();
		
		
		//Modifique esta linea, para que el registro del controlador sea en el lugar adecuado
		//el responsable de controladores es ControllerLocator no ServiceLocator - Yami
		controllerLocator.registerController("Car", new CarController(carContainer));
		
		/*==================================================*/
		/*	Falta Builder de la app, va en el framework		*/
		/* =================================================*/
		 
		
		/*=== SOLO PARA PRUEBAS - Luego lo borramos ====*/
		Car autoNuevo = new Car("ABC", 25);
		carContainer.addCar(autoNuevo);
		System.out.println(autoNuevo.toString());
		/*==============================================*/
		
		//INICIO DE LA APP
		System.out.println("SERVIDOR INICIADO - Esperando conexiones...");
		
		
		while(true) {
			//Paso 1: Esperamos hasta que un cliente se conecte y guardamos su socket - Yami
			Socket soc = ss.accept();
			System.out.println("Alguien se conecto");
			
			//Paso 2: una vez aceptada la conexion, creamos un nuevo hilo que recibe una nueva InitialSession a la que
			//le pasamos un ControllerLocator y un nuevo Comunicador por socket. No le pasamos un nuevo controller locator,
			//porque se comparte en todas las sesiones, lo unico que es por cada sesion es el comunicador por socket. - Yami
			Thread hilo = new Thread(new AppSession(controllerLocator, 
					new CommunicatorSocket(soc))); //Cambio de comunicador, por socket 
			
			//Paso 3: iniciamos el hilo - Yami
			hilo.start();
		}
		
		
	}

}
