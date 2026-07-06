package containers;

import java.util.HashSet;

import models.Word;
import utils.SessionData;
import juegoUtils.GameStartResponse;
import juegoUtils.GameGuessResponse;

public class GameContainer {
	private static final int INITIAL_LIVES = 6;
	private WordContainer wordContainer;

	public GameContainer(WordContainer wordContainer) {
		this.wordContainer = wordContainer;
	}

	// ════════════════════════════════════════
	//  INICIAR PARTIDA
	// ════════════════════════════════════════
	public GameStartResponse startGame(SessionData session) {
		Word word = wordContainer.getRandomWord();

		if (word == null) {
			return null;
		}

		session.setAttribute("currentWord", word);
		session.setAttribute("lives", INITIAL_LIVES);
		session.setAttribute("guessedLetters", new HashSet<Character>());
		session.setAttribute("gameFinished", false);
		session.setAttribute("gameWon", false);

		String masked = getMaskedWord(word, new HashSet<>());
		return new GameStartResponse(masked, INITIAL_LIVES, word.getHint(), word.getCategory());
	}

	// ════════════════════════════════════════
	//  ADIVINAR LETRA
	// ════════════════════════════════════════
	public GameGuessResponse guessLetter(String letter, SessionData session) {
		if (letter == null || letter.length() != 1) {
			return null;
		}

		Word word = (Word) session.getAttribute("currentWord");
		if (word == null) {
			return null;
		}

		Boolean finished = (Boolean) session.getAttribute("gameFinished");
		if (finished) {
			return null;
		}

		char guess = Character.toUpperCase(letter.charAt(0));
		HashSet<Character> guessed = (HashSet<Character>) session.getAttribute("guessedLetters");

		if (guessed.contains(guess)) {
			return null;
		}

		guessed.add(guess);
		String wordName = word.getName();

		if (wordName.indexOf(guess) >= 0) {
			// ACIERTO
			if (isWordFullyGuessed(wordName, guessed)) {
				session.setAttribute("gameFinished", true);
				session.setAttribute("gameWon", true);
				int lives = (int) session.getAttribute("lives");
				return new GameGuessResponse("won", "", wordName, lives);
			}
			int lives = (int) session.getAttribute("lives");
			String masked = getMaskedWord(word, guessed);
			return new GameGuessResponse("playing", masked, "", lives);
		} else {
			// ERROR
			int lives = (int) session.getAttribute("lives") - 1;
			session.setAttribute("lives", lives);

			if (lives <= 0) {
				session.setAttribute("gameFinished", true);
				session.setAttribute("gameWon", false);
				return new GameGuessResponse("lost", "", wordName, 0);
			}
			String masked = getMaskedWord(word, guessed);
			return new GameGuessResponse("playing", masked, "", lives);
		}
	}

	// ════════════════════════════════════════
	//  VER PISTA
	// ════════════════════════════════════════
	public String getHint(SessionData session) {
		Word word = (Word) session.getAttribute("currentWord");

		if (word == null) {
			return null;
		}

		return word.getHint();
	}

	// ════════════════════════════════════════
	//  ESTADO DE LA PARTIDA
	// ════════════════════════════════════════
	public GameGuessResponse getStatus(SessionData session) {
		Word word = (Word) session.getAttribute("currentWord");

		if (word == null) {
			return null;
		}

		Boolean finished = (Boolean) session.getAttribute("gameFinished");
		if (finished) {
			Boolean won = (Boolean) session.getAttribute("gameWon");
			String status = won ? "won" : "lost";
			return new GameGuessResponse(status, "", word.getName(), 0);
		}

		int lives = (int) session.getAttribute("lives");
		HashSet<Character> guessed = (HashSet<Character>) session.getAttribute("guessedLetters");
		String masked = getMaskedWord(word, guessed);
		return new GameGuessResponse("playing", masked, "", lives);
	}

	// ════════════════════════════════════════
	//  METODOS AUXILIARES
	// ════════════════════════════════════════

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

	private boolean isWordFullyGuessed(String wordName, HashSet<Character> guessed) {
		for (char c : wordName.toCharArray()) {
			if (c != ' ' && !guessed.contains(c)) {
				return false;
			}
		}
		return true;
	}
}
