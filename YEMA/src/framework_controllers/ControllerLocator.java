package framework_controllers;

import exceptions.ServiceNotImplementedException;

import java.util.Collection;
import java.util.HashMap;

public class ControllerLocator {
	private HashMap<String, BaseController> controllers;
	
	public ControllerLocator() {
		this.controllers = new HashMap<>();
	}
	
	public void registerController(String resource, BaseController controller) {
		this.controllers.put(resource.toLowerCase(), controller);
	}
	
	
	public BaseController getController(String resource) throws ServiceNotImplementedException {
		BaseController controller = this.controllers.get(resource.toLowerCase());
		if (controller == null) {
			throw new ServiceNotImplementedException(resource);
		}
		return controller;
	}
	
	//nuevo: devuelve una coleccion de controladores
	public Collection<BaseController> getControllers(){
		return controllers.values();
	}
}
