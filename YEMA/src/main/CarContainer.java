package main;

import java.util.ArrayList;
import java.util.Optional;

public class CarContainer {
	private ArrayList<Car> carsList;
	
	public CarContainer(ArrayList<Car> carsList) {
		this.carsList = carsList; 
	}
	
	public ArrayList<Car> getCarsList(){
		return new ArrayList(this.carsList);
	}
	
	public void addCar(Car newCar) {
		carsList.add(newCar);
	}
	
	public void editCar(String licensePlate, double speed) throws Exception {
		Optional<Car> car = this.carsList.stream()
								.filter(c -> c.getLicensePlate().equals(licensePlate))
								.findAny();
		
		  if (car.isPresent()) {
		        car.get().setSpeed(speed);
		  } 
		  else {
			  throw new Exception("Car not found");
		  }
	}
}
