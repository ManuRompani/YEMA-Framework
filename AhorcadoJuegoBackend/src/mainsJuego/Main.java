package mainsJuego;

import java.io.IOException;

import containers.ScoreContainer;
import containers.WordContainer;
import controllers.ScoreController;
import controllers.WordsController;
import models.Word;
import services.YemaApp;
import services.YemaAppBuilder;

public class Main {

	public static void main(String[] args) throws IOException{
		YemaAppBuilder builder = new YemaAppBuilder();
		
		builder.useSocketAsCommunicator();
		builder.setSocketPort(80);
		
		builder.addController("words", new WordsController());
		builder.addController("score", new ScoreController());
		
		builder.addService(new WordContainer());
		builder.addService(new ScoreContainer());
		
		YemaApp app = builder.build();
		
		app.run();
	}

}
