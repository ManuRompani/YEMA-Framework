package containers;

import java.util.ArrayList;
import java.util.Optional;

import appautos_exceptions.CarNotFoundException;
import models.Car;

public class CarContainer {
	private ArrayList<Car> carsList;
	
	public CarContainer() {
		this.carsList = new ArrayList<Car>(); 
	}
	
	public ArrayList<Car> getCarsList(){
		return new ArrayList(this.carsList);
	}
	
	public void addCar(Car newCar) {
		carsList.add(newCar);
	}
	
	public void editCar(String licensePlate, double speed) throws CarNotFoundException {
		Optional<Car> car = this.carsList.stream()
								.filter(c -> c.getLicensePlate().equals(licensePlate))
								.findAny();
		
		  if (car.isPresent()) {
		        car.get().setSpeed(speed);
		  } 
		  else {
			  throw new CarNotFoundException();
		  }
	}
}
