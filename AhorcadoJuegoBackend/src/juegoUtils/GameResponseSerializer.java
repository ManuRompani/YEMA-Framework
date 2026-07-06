package juegoUtils;

import services.Serializer;

public class GameResponseSerializer implements Serializer {

	@Override
	public String serialize(Object obj) {
		return "";
	}

	public String serializeStart(GameStartResponse response) {
		if (response == null) {
			return "bad=No hay palabras disponibles.";
		}
		return response.getMaskedWord()
			+ "|" + response.getLives()
			+ "|" + response.getHint()
			+ "|" + response.getCategory();
	}

	public String serializeGuess(GameGuessResponse response) {
		if (response == null) {
			return "bad=Error al procesar la respuesta.";
		}

		switch (response.getStatus()) {
			case "won":
				return "won|" + response.getRevealedWord() + "|" + response.getLives();
			case "lost":
				return "lost|" + response.getRevealedWord() + "|0";
			default:
				return response.getMaskedWord()
					+ "|" + response.getLives()
					+ "|playing";
		}
	}

	public String serializeHint(String hint) {
		if (hint == null) {
			return "bad=No hay partida activa.";
		}
		return "hint|" + hint;
	}

	public String serializeError(String message) {
		return "bad=" + message;
	}
}
