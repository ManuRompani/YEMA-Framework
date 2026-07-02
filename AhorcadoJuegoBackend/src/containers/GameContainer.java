package containers;

import java.util.HashSet;

import models.Word;
import utils.SessionData;

public class GameContainer {
	private static final int INITIAL_LIVES = 6;
	private WordContainer wordContainer;

	public GameContainer(WordContainer wordContainer) {
		this.wordContainer = wordContainer;
	}

	// ════════════════════════════════════════
	//  INICIAR PARTIDA
	// ════════════════════════════════════════
	public String startGame(SessionData session) {
		Word word = wordContainer.getRandomWord();

		if (word == null) {
			return "No hay palabras disponibles. Contactá al admin.";
		}

		// Guardamos el estado en la sesión del jugador
		session.setAttribute("currentWord", word);
		session.setAttribute("lives", INITIAL_LIVES);
		session.setAttribute("guessedLetters", new HashSet<Character>());
		session.setAttribute("gameFinished", false);
		session.setAttribute("gameWon", false);

		return "¡Juego iniciado!\nPalabra: " + getMaskedWord(word, new HashSet<>())
			+ "\nVidas: " + INITIAL_LIVES;
	}

	// 
	//  ADIVINAR LETRA
	// 
	public String guessLetter(String letter, SessionData session) {
		if (letter == null || letter.length() != 1) {
			return "Parámetro 'letter' requerido (1 letra).";
		}

		Word word = (Word) session.getAttribute("currentWord");
		if (word == null) {
			return "No hay partida en curso. Usá 'start'.";
		}

		Boolean finished = (Boolean) session.getAttribute("gameFinished");
		if (finished) {
			return "El juego ya terminó. Usá 'start' para una nueva partida.";
		}

		char guess = Character.toUpperCase(letter.charAt(0));
		HashSet<Character> guessed = (HashSet<Character>) session.getAttribute("guessedLetters");

		if (guessed.contains(guess)) {
			return "Ya probaste la letra '" + guess + "'. Intentá otra.";
		}

		guessed.add(guess);
		String wordName = word.getName();

		if (wordName.indexOf(guess) >= 0) {
			//  ACIERTO 
			if (isWordFullyGuessed(wordName, guessed)) {
				session.setAttribute("gameFinished", true);
				session.setAttribute("gameWon", true);
				int lives = (int) session.getAttribute("lives");
				return "¡Ganaste! La palabra era: " + wordName
					+ "\nVidas restantes: " + lives;
			}
			int lives = (int) session.getAttribute("lives");
			return "¡Correcto! La letra '" + guess + "' está.\n"
				+ getMaskedWord(word, guessed) + "\nVidas: " + lives;
		} else {
			//  ERROR 
			int lives = (int) session.getAttribute("lives") - 1;
			session.setAttribute("lives", lives);

			if (lives <= 0) {
				session.setAttribute("gameFinished", true);
				session.setAttribute("gameWon", false);
				return "¡Perdiste! La palabra era: " + wordName;
			}
			return "La letra '" + guess + "' no está.\n"
				+ getMaskedWord(word, guessed) + "\nVidas: " + lives;
		}
	}

	// 
	//  VER PISTA
	// 
	public String getHint(SessionData session) {
		Word word = (Word) session.getAttribute("currentWord");

		if (word == null) {
			return "No hay partida activa.";
		}

		return "Pista: " + word.getHint();
	}

	// 
	//  ESTADO DE LA PARTIDA
	// 
	public String getStatus(SessionData session) {
		Word word = (Word) session.getAttribute("currentWord");

		if (word == null) {
			return "No hay partida en curso.";
		}

		Boolean finished = (Boolean) session.getAttribute("gameFinished");
		if (finished) {
			Boolean won = (Boolean) session.getAttribute("gameWon");
			String result = won ? "¡Ganaste!" : "Perdiste.";
			return result + " La palabra era: " + word.getName();
		}

		int lives = (int) session.getAttribute("lives");
		HashSet<Character> guessed = (HashSet<Character>) session.getAttribute("guessedLetters");
		return "Palabra: " + getMaskedWord(word, guessed)
			+ "\nVidas: " + lives
			+ "\nLetras probadas: " + guessed;
	}

	// 
	//  METODOS AUXILIARES
	// 

	// "DULCEDELECHE" con letras [D, E] → "D _ _ C E _ _ E _ _ E"
	private String getMaskedWord(Word word, HashSet<Character> guessed) {
		StringBuilder masked = new StringBuilder();
		for (char c : word.getName().toCharArray()) {
			if (c == ' ') {
				masked.append("  ");
			} else if (guessed.contains(c)) {
				masked.append(c).append(" ");
			} else {
				masked.append("_ ");
			}
		}
		return masked.toString().trim();
	}

	// Verifica si todas las letras fueron adivinadas
	private boolean isWordFullyGuessed(String wordName, HashSet<Character> guessed) {
		for (char c : wordName.toCharArray()) {
			if (c != ' ' && !guessed.contains(c)) {
				return false;
			}
		}
		return true;
	}
}
