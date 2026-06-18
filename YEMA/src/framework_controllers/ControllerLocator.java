package framework_controllers;

import framework_controllers.BaseController;
import exceptions.ServiceNotImplementedException;
import java.util.HashMap;

public class ControllerLocator {
	private HashMap<String, BaseController> controllers;
	
	public ControllerLocator() {
		this.controllers = new HashMap<>();
	}
	
	
	public BaseController getController(String resource) throws ServiceNotImplementedException {
		BaseController controller = this.controllers.get(resource.toLowerCase());
		if (controller == null) {
			throw new ServiceNotImplementedException();
		}
		return controller;
	}
	
}
