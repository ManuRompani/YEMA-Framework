package models;

public class Car {
	private String licensePlate;
	private double speed;
	
	public Car() {
		
	}
	
	public Car(String licensePlate, double speed) {
		super();
		this.licensePlate = licensePlate;
		this.speed = speed;
	}
	
	
	public String getLicensePlate() {
		return licensePlate;
	}

	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}


	@Override
	public String toString() {
		return "Car [licensePlate=" + licensePlate + ", speed=" + speed + "]";
	}
	
	
	
	
}
