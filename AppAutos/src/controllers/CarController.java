package controllers;

import appautos_utils.CarSerializer;
import annotations.AuthorizedRoles;
import containers.CarContainer;
import dtos.Command;
import dtos.Response;
import exceptions.ServiceNotImplementedException;
import framework_controllers.BaseController;
import models.Car;
import services.ServiceLocator;
import utils.Context;

@AuthorizedRoles(roles = "admin")
public class CarController extends BaseController {
	private CarContainer carContainer;
	private CarSerializer serializer;
	
	public CarController() {
		this.serializer = new CarSerializer();
	}

	// El serviceL viaja dentro de context, ahora solo le pasamos la accion al switch
	@Override
	public Response Ejecutar(Command command, Context context){
		
	
		Response response = new Response();	
		
		switch(command.getAction()) {
		case("get-cars"):
			return this.getCars();
			case("get-car"):
				return this.getCarByLicensePlate(command.getParameter("licensePlate"));
			case("add-car"):
				return this.addCar( command.getParameter("speed"),
									command.getParameter("licensePlate"),
									context.getSessionData().getUserName() );
			default:				
				response.setMessage("No action was taken");
				return response;
		}
	}
	
	//Acciones
	private Response getCars(){
		Response response = new Response();
		String result ="";
		for(Car car : carContainer.getCarsList()) {
			result += this.serializer.serialize(car) + "\n";
		}		
		
		if(result.isEmpty()) {
			response.setMessage("No cars register");
		}else {
			response.setMessage(result);
		}
		
		return response;
		
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
	
	@Override
	public void iniciarServicios(ServiceLocator sl) {
			try {
				this.carContainer = sl.getService(CarContainer.class);
			} catch (ServiceNotImplementedException e) {
				throw new RuntimeException("Error crítico. Inicializacion de servicio " + e.getMessage() + " fallida.");
			}
	}
}
