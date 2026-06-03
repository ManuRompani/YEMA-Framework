package controllers;

import java.util.List;

import appautos_exceptions.InvalidCarFormatException;
import containers.CarContainer;
import dtos.Command;
import dtos.Response;
import framework_controllers.BaseController;
import models.Car;
import utils.CommunicatorConsole;

public class CarController extends BaseController {
	private CarContainer carContainer;
	private CommunicatorConsole com;
	
	public CarController(CarContainer carContainer, CommunicatorConsole com) {
		this.carContainer = carContainer;
		this.com = com;
	}
	
	@Override
	public Response Ejecutar(Command comand) {
		switch(comand.getAction()) {
			case("getCars"):
				com.send(this.getCars().toString());
				break;
		}
		
		return null;
	}
	
	public List<Car> getCars(){
		return this.carContainer.getCarsList();
	}
	
	public void addCar(Car car) throws InvalidCarFormatException{
		if(this.isCarValid(car)) {
			this.carContainer.addCar(car);
		}
		throw new InvalidCarFormatException();
	}
	
	private boolean isCarValid(Car car) {
		try {			
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}
