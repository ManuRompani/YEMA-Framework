package framework_controllers;
import dtos.Command;
import dtos.Response;
import interfaces.ICommunicator;
import services.ServiceLocator;
import utils.Context;

public abstract class BaseController{

	public abstract Response Ejecutar(Command comand, Context context);
	
	public abstract void iniciarServicios(ServiceLocator sl);
}
