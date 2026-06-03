package controllers;

import java.util.List;

import appautos_exceptions.InvalidCarFormatException;
import appautos_utils.CarSerializer;
import containers.CarContainer;
import dtos.Command;
import dtos.Response;
import framework_controllers.BaseController;
import models.Car;
import utils.CommunicatorConsole;

public class CarController extends BaseController {
	private CarContainer carContainer;
	private CommunicatorConsole com;
	private CarSerializer serializer;
	
	public CarController(CarContainer carContainer, CommunicatorConsole com) {
		this.carContainer = carContainer;
		this.com = com;
		this.serializer = new CarSerializer();
	}
	
	// ejecuta la accion recibida en el comando, recuerden comando es ej: car/get-car/licensePlate=ABC
	// si se necesita enviar datos se hace aqui mismo en el comunicador
	@Override
	public void Ejecutar(Command command) {
		switch(command.getAction()) {
			case("get-car"):
				com.send(this.getCarByLicensePlate(command.getParameter("licensePlate")).getMessage());
				break;
		}
	}
	
	
	//Acciones
	private Response getCars(){
		return null;
		//return this.carContainer.getCarsList();
	}
	
	private Response getCarByLicensePlate(String license){
		Response response = new Response();
		Car car = this.carContainer.getCarByLicensePlate(license);
		
		if(car == null) {
			response.setMessage("Car not found");
			return response;
		}
		else {
			String carSerialized = this.serializer.serialize(car);
			response.setMessage(carSerialized);
			return response;
		}
	}
	
	private Response addCar(Car car) throws InvalidCarFormatException{
		if(this.isCarValid(car)) {
			this.carContainer.addCar(car);
		}
		throw new InvalidCarFormatException();
	}
	
	
	
	//Ayudas
	private boolean isCarValid(Car car) {
		try {			
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}
