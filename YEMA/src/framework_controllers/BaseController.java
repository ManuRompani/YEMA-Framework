package framework_controllers;

import dtos.Command;
import dtos.Response;

public abstract class BaseController {

	public abstract Response Ejecutar(Command comand);
}
