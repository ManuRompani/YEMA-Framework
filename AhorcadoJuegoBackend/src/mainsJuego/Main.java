package mainsJuego;

import java.io.IOException;

import containers.MemoryUserManager;
import containers.ScoreContainer;
import containers.WordCategoryContainer;
import containers.WordContainer;
import controllers.AuthController;
import controllers.ScoreController;
import controllers.WordsController;
import juegoUtils.PlayerDeserializer;
import juegoUtils.PlayerSerializer;
import services.YemaApp;
import services.YemaAppBuilder;

public class Main {

	public static void main(String[] args) throws IOException{
		YemaAppBuilder builder = new YemaAppBuilder();
		
		builder.useSocketAsCommunicator();
		builder.setSocketPort(80);
		
		builder.addController("words", new WordsController());
		builder.addController("score", new ScoreController());
		builder.addController("auth", new AuthController());
		
		MemoryUserManager userManager = new MemoryUserManager();
		
		builder.addService(userManager);;
		builder.addService(new WordContainer());
		builder.addService(new WordCategoryContainer());
		builder.addService(new ScoreContainer(userManager));
		builder.addService(new PlayerDeserializer());
		builder.addService(new PlayerSerializer());
		
		YemaApp app = builder.build();
		
		app.run();
	}

}
