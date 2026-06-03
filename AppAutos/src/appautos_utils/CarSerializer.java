package appautos_utils;

import models.Car;
import services.Serializer;

public class CarSerializer implements Serializer<Car> {

	@Override
	public String serialize(Car car) {
		String serialized = "licensePlate|speed\n";
		serialized += car.getLicensePlate() + "|" + car.getSpeed();
		return serialized;
	}

}
