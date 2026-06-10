package controllers;

import appautos_utils.CarSerializer;
import appautos_utils.GenericDeserializer;
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
	private GenericDeserializer deserializer;
	
	public CarController(CarContainer carContainer, CommunicatorConsole com) {
		this.carContainer = carContainer;
		this.com = com;
		this.serializer = new CarSerializer();
		this.deserializer = new GenericDeserializer();
	}
	
	@Override
	public void Ejecutar(Command command) {
		switch(command.getAction()) {
			case("get-car"):
				com.send(this.getCarByLicensePlate(command.getParameter("licensePlate")).getMessage());
				break;
			case("add-car"):	
				com.send("En construccion");
			break;
		}
	}
	
	
	//Acciones
	private Response getCars(){
		return null;
		//return this.carContainer.getCarsList();
	}
	
	private Response getCarByLicensePlate(String license){
		System.out.println("Buscando: " + "[" + license + "]");
		
		Response response = new Response();
		Car car = this.carContainer.getCarByLicensePlate(license);
		
		if(car == null) {
			response.setMessage("Car not found");			
		}
		else {
			String carSerialized = this.serializer.serialize(car);
			response.setMessage(carSerialized);			
		}
		//Elimine los return dentro del if y centralice en un solo return.
		//El contenido del response es seteado dentro de cualquiera de los 2 bloques.
		//Llega hasta aqui con el mensaje que corresponda.
		return response;
	}
	
	private Response addCar(Car car){
			Response response = new Response();
			
			if(existsCar(car.getLicensePlate())) {
				
			response.setMessage("La patente ya existe");	
			
			}else if(isCarValid(car)) {
				
				response.setMessage("La velocidad no puede ser negativa");		
				
			}else{
				
				this.carContainer.addCar(car);
				response.setMessage("Auto registrado exitosamente");				
			}
		
			return response;
	}
	
	
	
	
	/*--- VALIDACIONES ---*/

	
	private boolean existsCar(String licensePlate) {
		return carContainer.getCarsList().stream().anyMatch(car -> car.getLicensePlate().equalsIgnoreCase(licensePlate));
	}
	
	//Ayudas
	private boolean isCarValid(Car car) {
		
		if(car == null) {
			return false;
		}		
		
		if(car.getLicensePlate() == null || car.getLicensePlate().trim().isEmpty()) {
			return false;
		}
		
		if(car.getSpeed() < 0) {
			return false;
		}
		
		return true;
	}
}
