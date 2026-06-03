package framework_controllers;
import dtos.Command;
import dtos.Response;
import interfaces.ICommunicator;

public abstract class BaseController{
	
	/*PORQUE TODOS LOS CONTROLADORES VAN A NECESITAR UN COMUNICADOR, LE PASO
	 * LA INTERFAZ PARA QUE CONOZCA EL CONTRATO, LUEGO EN MAIN LE ENTREGO
	 * EL COMUNICADORCONSOLA */
	protected ICommunicator communicator; 
	public abstract void Ejecutar(Command comand);
	
	
}
