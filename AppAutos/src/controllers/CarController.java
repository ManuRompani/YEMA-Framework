package controllers;

import java.util.List;

import containers.CarContainer;
import dtos.Command;
import framework_controllers.BaseController;
import models.Car;

public class CarController extends BaseController {
	private CarContainer carContainer;
	
	public CarController(CarContainer carContainer) {
		this.carContainer = carContainer;
	}
	
	@Override
	public void Ejecutar(Command comand) {
		switch(comand.getAction()) {
			case("getCars"):
				this.getCars();
				break;
		}
		
	}
	
	public List<Car> getCars(){
		return this.carContainer.getCarsList();
	}
}
