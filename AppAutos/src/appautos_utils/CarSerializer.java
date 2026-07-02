package appautos_utils;

import models.Car;
import services.Serializer;

public class CarSerializer implements Serializer {

	@Override
	public String serialize(Object car) {
		
		String serialized = "licensePlate|speed\n";
		serialized += ((Car) car).getLicensePlate() + "|" + ((Car) car).getSpeed();
		return serialized;
	}

	

}
