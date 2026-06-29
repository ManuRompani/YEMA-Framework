package mainsApp;

import java.io.IOException;
import containers.CarContainer;
import containers.MemoryUserManager;
import controllers.CarController;
import services.YemaApp;
import services.YemaAppBuilder;

public class Main {
	public static void main(String[] args) throws IOException {
		YemaAppBuilder builder = new YemaAppBuilder();
		
		// comunicacion
		builder.useSocketAsCommunicator();
		builder.setSocketPort(80);
		
		// controladores
		builder.addController("car", new CarController());
		
		// servicios
		builder.addService(new CarContainer());
		builder.addService(new MemoryUserManager());
		
		YemaApp app = builder.build();
		
		app.run();
	}

}
