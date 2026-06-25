package models;

public class Car {
	private String licensePlate;
	private double speed;
	private String userName;
	
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
	public void setUserName(String userName) {
		this.userName = userName;
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
