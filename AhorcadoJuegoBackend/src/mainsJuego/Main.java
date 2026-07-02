package mainsJuego;

import java.io.IOException;

import containers.GameContainer;
import containers.MemoryUserManager;
import containers.ScoreContainer;
import containers.WordCategoryContainer;
import containers.WordContainer;
import controllers.AdminController;
import controllers.AuthController;
import controllers.ScoreController;
import controllers.WordsController;
import juegoUtils.PlayerDeserializer;
import juegoUtils.PlayerSerializer;
import juegoUtils.WordCategorySerializer;
import juegoUtils.WordDeserializer;
import juegoUtils.WordListSerializer;
import controllers.GameController;
import services.YemaApp;
import services.YemaAppBuilder;

public class Main {

	public static void main(String[] args) throws IOException{
		YemaAppBuilder builder = new YemaAppBuilder();
		
		builder.useSocketAsCommunicator();
		builder.setSocketPort(80);
		
		builder.addController("game", new GameController());
		builder.addController("score", new ScoreController());
		builder.addController("auth", new AuthController());
		builder.addController("admin", new AdminController());
		builder.addController("words", new WordsController());
		
		MemoryUserManager userManager = new MemoryUserManager();
		WordContainer wordContainer = new WordContainer();
		GameContainer gameContainer = new GameContainer(wordContainer);
		
		builder.addService(userManager);
		builder.addService(wordContainer);
		builder.addService(new WordCategoryContainer());
		builder.addService(new ScoreContainer(userManager));
		builder.addService(new PlayerDeserializer());
		builder.addService(new PlayerSerializer());
		builder.addService(new WordDeserializer());
		builder.addService(new WordCategorySerializer());
		builder.addService(new WordListSerializer());
		
		builder.addService(gameContainer);
		YemaApp app = builder.build();
		
		app.run();
	}

}
