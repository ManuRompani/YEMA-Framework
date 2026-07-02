package juegoUtils;

public class GameGuessResponse {
	private String status;
	private String maskedWord;
	private String revealedWord;
	private int lives;

	public GameGuessResponse(String status, String maskedWord, String revealedWord, int lives) {
		this.status = status;
		this.maskedWord = maskedWord;
		this.revealedWord = revealedWord;
		this.lives = lives;
	}

	public String getStatus() { return status; }
	public String getMaskedWord() { return maskedWord; }
	public String getRevealedWord() { return revealedWord; }
	public int getLives() { return lives; }
}
