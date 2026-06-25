package framework_controllers;
import dtos.Command;
import dtos.Response;
import interfaces.ICommunicator;
import services.ServiceLocator;
import utils.Context;

public abstract class BaseController{
	/*PORQUE TODOS LOS CONTROLADORES VAN A NECESITAR UN COMUNICADOR, LE PASO
	 * LA INTERFAZ PARA QUE CONOZCA EL CONTRATO, LUEGO EN MAIN LE ENTREGO
	 * EL COMUNICADORCONSOLA */
	// no hace falta comunicador
	// protected ICommunicator communicator; 
	public abstract Response Ejecutar(Command comand, Context context, ServiceLocator serviceLocator);
	
}
