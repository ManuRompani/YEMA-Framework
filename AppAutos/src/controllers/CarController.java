package controllers;

import appautos_utils.CarSerializer;
import appautos_utils.CarDeserializer;
import containers.CarContainer;
import dtos.Command;
import dtos.Response;
import framework_controllers.BaseController;
import models.Car;
import utils.CommunicatorConsole;
import utils.Context;

public class CarController extends BaseController {
	private CarContainer carContainer;
	private CarSerializer serializer;
	private CarDeserializer deserializer;
	
	public CarController(CarContainer carContainer) {
		this.carContainer = carContainer;
		this.serializer = new CarSerializer();
		this.deserializer = new CarDeserializer();
	}
	
	// AHORA EL CONTROLADOR RETORNA UN REPSONSE Y NO SE ENCARGA DE COMUNICADOR
	// YA QUE ES 1 PARA CADA SESION!!!
	// ejecuta la accion recibida en el comando, recuerden comando es ej: car/get-car/licensePlate=ABC
	@Override
	public Response Ejecutar(Command command, Context context){
		switch(command.getAction()) {
			case("get-car"):
				return this.getCarByLicensePlate(command.getParameter("licensePlate"));
			case("add-car"):
				return this.addCar( command.getParameter("speed"),
									command.getParameter("licensePlate"),
									context.getSessionData().getUserName() );
				default:
					Response response = new Response();
					response.setMessage("No action was taken");
					return response;
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
	
	private Response addCar(String speed, String licensePlate, String userName){
			Response response = new Response();
			double dSpeed = Double.parseDouble(speed);
			
			if(this.carContainer.getCarByLicensePlate(licensePlate) != null) {
				
			response.setMessage("La patente ya existe");	
			
			}else if(dSpeed < 0) {
				
				response.setMessage("La velocidad no puede ser negativa");		
				
			}else{
				Car car = new Car(licensePlate, dSpeed);
				car.setUserName(userName);
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
